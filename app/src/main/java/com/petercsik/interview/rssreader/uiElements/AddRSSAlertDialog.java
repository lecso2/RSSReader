package com.petercsik.interview.rssreader.uiElements;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.petercsik.interview.rssreader.R;
import com.petercsik.interview.rssreader.ValidationUtil;
import com.petercsik.interview.rssreader.activities.MainActivity;
import com.petercsik.interview.rssreader.dao.FeedUrlDao;

public class AddRSSAlertDialog extends AlertDialog implements View.OnClickListener {

    private final FeedUrlDao feedUrlDao;
    private final AlertDialogEditText urlEditText;
    private final MainActivity mainActivity;

    public AddRSSAlertDialog(@NonNull MainActivity mainActivity) {
        super(mainActivity);
        this.feedUrlDao = FeedUrlDao.getInstance(mainActivity);
        this.urlEditText = new AlertDialogEditText(mainActivity);
        this.mainActivity = mainActivity;

        //order matters - no button before show
        setUp(mainActivity);
        show();
        setListeners();
    }

    private void setUp(Context context) {
        urlEditText.setOnKeyListener(this);
        setMessage(mainActivity.getResources().getString(R.string.rss_feed_type_in));
        setView(urlEditText);

        this.setButton(AlertDialog.BUTTON_POSITIVE, context.getResources().getString(R.string.add), new EmptyOnClickListener());
        this.setButton(AlertDialog.BUTTON_NEGATIVE, context.getResources().getString(R.string.cancel), new EmptyOnClickListener());
    }

    public void setListeners() {
        this.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(this);
        this.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == this.getButton(AlertDialog.BUTTON_POSITIVE)) {
            String mValue = urlEditText.getText().toString();
            if (mainActivity.isDuplicate(mValue)) {
                setMessage(mainActivity.getResources().getString(R.string.duplication_error_message));
            } else if (ValidationUtil.isValidURL(mValue)) {
                feedUrlDao.insertFeed(mValue);
                mainActivity.refreshFeedList();
                dismiss();
            } else {
                setMessage(mainActivity.getResources().getString(R.string.validation_error_message));
            }
        } else if (v == this.getButton(AlertDialog.BUTTON_NEGATIVE)) {
            dismiss();
        }
    }

    private static class EmptyOnClickListener implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
    }
}
