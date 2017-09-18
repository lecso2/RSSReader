package com.petercsik.interview.rssreader.uiElements;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AlertDialogEditText extends EditText {

    //required to xml
    public AlertDialogEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlertDialogEditText(Context context) {
        super(context);
        this.setTextColor(Color.BLACK);
    }

    public void setUp(String text) {
        this.setText(text);
        this.setSelection(text.length());
        this.setTextColor(Color.BLACK);
    }

    public void setOnKeyListener(final Button button, final TextView textView) {
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    button.callOnClick();
                    return true;
                }
                return false;
            }
        });

        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView.setText(" ");
            }

            @Override
            public void afterTextChanged(Editable s) {}

        });
    }


    public void setOnKeyListener(final AlertDialog dialog) {
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).callOnClick();
                    return true;
                }
                return false;
            }
        });

        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialog.setMessage(" ");
            }

            @Override
            public void afterTextChanged(Editable s) {}

        });
    }
}
