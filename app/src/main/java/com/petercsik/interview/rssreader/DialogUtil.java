package com.petercsik.interview.rssreader;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

public class DialogUtil {


    public static void showMalformedUrlAlertDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(R.string.malformed_url_error_message)
                .setTitle(R.string.malformed_url_error)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.show();
    }

    public static void showConnectionAlertDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage(R.string.connection_error_message)
                .setTitle(R.string.connection_error)
                .setCancelable(false)
                .setPositiveButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        activity.finish();
                    }
                });
        builder.show();
    }

    public static void showFeedErrorAlertDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage(R.string.parsing_error_message)
                .setTitle(R.string.parsing_error)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        activity.finish();
                    }
                });
        builder.show();
    }

    public static void showShareDialog(final Context context, final String url, final ShareButton shareButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(context.getResources().getString(R.string.share_dialog_message))
                .setTitle(context.getResources().getString(R.string.share_dialog_title))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(url))
                                .build();

                        shareButton.setShareContent(content);
                        shareButton.performClick();
                    }
                });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public static void showInfoDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.info_message)
                .setTitle(R.string.info_title)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

}
