package com.petercsik.interview.rssreader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.petercsik.interview.rssreader.DialogUtil;
import com.petercsik.interview.rssreader.R;
import com.petercsik.interview.rssreader.ValidationUtil;
import com.petercsik.interview.rssreader.arrayAdapters.FeedListViewAdapter;
import com.petercsik.interview.rssreader.parser.DOMParser;
import com.petercsik.interview.rssreader.parser.RSSItem;

import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedViewActivity extends AppCompatActivity {

    public static final String FEED_VIEW_ACTIVITY_ACTION = "feed_view_activity_action";
    public static final String FEED_VIEW_ACTIVITY_URL = "feed_view_activity_url";
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_view);

        Intent intent = getIntent();
        if (FEED_VIEW_ACTIVITY_ACTION.equals(intent.getAction())) {
            url = (URL) intent.getSerializableExtra(FEED_VIEW_ACTIVITY_URL);
            final Observable<List<RSSItem>> operationObservable = Observable.create(new parsingObservable());
            operationObservable.subscribeOn(Schedulers.io()) // subscribeOn the I/O thread
                    .observeOn(AndroidSchedulers.mainThread()) // observeOn the UI Thread
                    .subscribe(new MyObserver());  //only chained call works
        }

    }

    private class parsingObservable implements ObservableOnSubscribe<List<RSSItem>> {
        @Override
        public void subscribe(@NonNull ObservableEmitter<List<RSSItem>> e) throws Exception {
            DOMParser parser = new DOMParser();
            InputSource is = new InputSource(url.openStream());
            List<RSSItem> result = parser.parseXML(is);

            e.onNext(result);
            e.onComplete();
        }

    }

    private class MyObserver implements Observer<List<RSSItem>> {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(List<RSSItem> feed) {
            final List<RSSItem> feedList = feed;
            if (feed == null || feed.isEmpty()) {
                feed = new ArrayList<>();
            }
            ArrayAdapter<RSSItem> itemsAdapter = new FeedListViewAdapter(getBaseContext(), R.layout.feed_list_view_item, feed);
            final ListView listView = (ListView) findViewById(R.id.feed_list_view);
            listView.setAdapter(itemsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent article = new Intent(getBaseContext(), ArticleActivity.class);
                    article.setAction(ArticleActivity.ARTICLE_ACTIVITY_ACTION);
                    article.putExtra(ArticleActivity.ARTICLE_ACTIVITY_LINK, feedList.get(position).getLink());
                    startActivity(article);
                }
            });
        }

        @Override
        public void onError(Throwable t) {
            if (ValidationUtil.isConnectionAvailable(getBaseContext())) {
                DialogUtil.showFeedErrorAlertDialog(FeedViewActivity.this);
            } else {
                DialogUtil.showConnectionAlertDialog(FeedViewActivity.this);
            }
        }

        @Override
        public void onComplete() {
        }

    }

}
