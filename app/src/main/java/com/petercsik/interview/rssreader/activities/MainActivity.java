package com.petercsik.interview.rssreader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.petercsik.interview.rssreader.DialogUtil;
import com.petercsik.interview.rssreader.R;
import com.petercsik.interview.rssreader.ValidationUtil;
import com.petercsik.interview.rssreader.arrayAdapters.RSSListViewAdapter;
import com.petercsik.interview.rssreader.dao.FeedUrlDao;
import com.petercsik.interview.rssreader.uiElements.AddRSSAlertDialog;
import com.petercsik.interview.rssreader.uiElements.EditRSSAlertDialog;

import java.net.URL;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final FeedUrlDao feedUrlDao = FeedUrlDao.getInstance(this);
    private List<String> feedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        if (!ValidationUtil.isConnectionAvailable(getBaseContext())) {
            DialogUtil.showConnectionAlertDialog(this);
        } else {
            refreshFeedList();
            setupListView();
        }
    }

    public void refreshFeedList() {
        feedList = feedUrlDao.getAllFeeds();
        Collections.sort(feedList);
        ArrayAdapter<String> rssAdapter = new RSSListViewAdapter(getBaseContext(), R.layout.rss_list_view_item, feedList);
        ListView listView = (ListView) findViewById(R.id.rss_list_view);
        listView.setAdapter(rssAdapter);
    }

    private void setupListView() {
        final ListView listView = (ListView) findViewById(R.id.rss_list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                URL url = ValidationUtil.validateURL(feedList.get(position), MainActivity.this);
                if (url != null) {
                    startFeedViewActivity(url);
                }
            }
        });

        listView.setOnItemLongClickListener((new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createEditAlertDialog(feedList.get(position));
                return true;
            }
        }));
    }

    public boolean isDuplicate(String url) {
        return feedList.contains(url.trim());
    }

    private void startFeedViewActivity(URL url) {
        Intent intent = new Intent(getBaseContext(), FeedViewActivity.class);
        intent.setAction(FeedViewActivity.FEED_VIEW_ACTIVITY_ACTION);
        intent.putExtra(FeedViewActivity.FEED_VIEW_ACTIVITY_URL, url);
        startActivity(intent);
    }

    private void createEditAlertDialog(final String selectedUrl) {
        new EditRSSAlertDialog(this, selectedUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_info:
                DialogUtil.showInfoDialog(this);
                return true;
            case R.id.menu_add:
                new AddRSSAlertDialog(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
