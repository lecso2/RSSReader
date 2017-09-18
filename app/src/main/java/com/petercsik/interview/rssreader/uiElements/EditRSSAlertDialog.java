package com.petercsik.interview.rssreader.uiElements;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.share.widget.ShareButton;
import com.petercsik.interview.rssreader.DialogUtil;
import com.petercsik.interview.rssreader.R;
import com.petercsik.interview.rssreader.ValidationUtil;
import com.petercsik.interview.rssreader.activities.MainActivity;
import com.petercsik.interview.rssreader.dao.FeedUrlDao;

public class EditRSSAlertDialog extends Dialog implements View.OnClickListener {

    private final FeedUrlDao feedUrlDao;
    private final String selectedUrl;
    private final MainActivity mainActivity;
    //controls
    private Button deleteButton;
    private Button cancelButton;
    private Button modifyButton;

    public EditRSSAlertDialog(@NonNull MainActivity mainActivity, String selectedUrl) {
        super(mainActivity);
        this.feedUrlDao = FeedUrlDao.getInstance(mainActivity);
        this.selectedUrl = selectedUrl;
        this.mainActivity = mainActivity;

        //order matters - no button, before show
        setContentView(R.layout.rss_edit_dialog);
        show();
        setUp();
        setListeners();
    }

    private void setUp() {
        modifyButton = (Button) findViewById(R.id.rss_edit_dialog_modify);
        deleteButton = (Button) findViewById(R.id.rss_edit_dialog_delete);
        cancelButton = (Button) findViewById(R.id.rss_edit_dialog_cancel);

        final TextView textView = (TextView) findViewById(R.id.rss_edit_dialog_textView);
        final AlertDialogEditText editText = (AlertDialogEditText) findViewById(R.id.rss_edit_dialog_editText);
        editText.setUp(selectedUrl);
        editText.setOnKeyListener(modifyButton, textView);

        ShareButton shareButton = (ShareButton) findViewById(R.id.rss_edit_dialog_share_btn);
        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                postLink(editText.getText().toString());
            }
        });
        shareButton.setEnabled(true);
    }

    private void setListeners() {
        deleteButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        modifyButton.setOnClickListener(this);
    }

    private void setMessage(String message) {
        TextView textView = (TextView) findViewById(R.id.rss_edit_dialog_textView);
        textView.setText(message);
    }

    private void postLink(final String url) {
        if (!ValidationUtil.isValidURL(url)) {
            setMessage(mainActivity.getResources().getString(R.string.facebook_parsing_error_message));
        } else {
            ShareButton shareButton = (ShareButton) findViewById(R.id.rss_edit_dialog_share_btn);
            DialogUtil.showShareDialog(mainActivity, url, shareButton);
        }
    }

    @Override
    public void onClick(View v) {
        AlertDialogEditText editText = (AlertDialogEditText) findViewById(R.id.rss_edit_dialog_editText);

        if (v == modifyButton) {
            String newUrl = editText.getText().toString();
            String oldUrl = selectedUrl;

            if (mainActivity.isDuplicate(newUrl) && !oldUrl.equals(newUrl)) {
                setMessage(mainActivity.getResources().getString(R.string.duplication_error_message));
            } else if (ValidationUtil.isValidURL(newUrl)) {
                feedUrlDao.modifyFeed(oldUrl, newUrl);
                mainActivity.refreshFeedList();
                dismiss();
            } else {
                setMessage(mainActivity.getResources().getString(R.string.validation_error_message));
            }
        } else if (v == cancelButton) {
            dismiss();
        } else if (v == deleteButton) {
            String url = editText.getText().toString();
            feedUrlDao.deleteFeed(url);
            mainActivity.refreshFeedList();
            dismiss();
        }
    }
}
