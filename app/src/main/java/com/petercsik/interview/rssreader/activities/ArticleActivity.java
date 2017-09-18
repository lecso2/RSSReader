package com.petercsik.interview.rssreader.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.petercsik.interview.rssreader.R;

public class ArticleActivity extends Activity {

    public static final String ARTICLE_ACTIVITY_ACTION = "article_activity_action";
    public static final String ARTICLE_ACTIVITY_LINK = "article_activity_link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        if (ARTICLE_ACTIVITY_ACTION.equals(intent.getAction())) {
            String link = intent.getStringExtra(ARTICLE_ACTIVITY_LINK);
            final WebView webView = (WebView) findViewById(R.id.article_activity_webview);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(link);
        }
    }

}
