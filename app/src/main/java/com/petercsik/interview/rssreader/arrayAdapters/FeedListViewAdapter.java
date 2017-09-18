package com.petercsik.interview.rssreader.arrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.petercsik.interview.rssreader.R;
import com.petercsik.interview.rssreader.parser.RSSItem;

import java.util.List;

public class FeedListViewAdapter extends ArrayAdapter<RSSItem> {

    private final int layoutResourceId;
    private final RSSItem data[];
    private LayoutInflater layoutInflater;

    public FeedListViewAdapter(Context mContext, int layoutResourceId, List<RSSItem> data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.data = data.toArray(new RSSItem[0]);
        this.layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResourceId, parent, false);
        }

        RSSItem rssItem = data[position];
        ((TextView) convertView.findViewById(R.id.feed_content_title)).setText(rssItem.getTitle());
        ((TextView) convertView.findViewById(R.id.feed_content_date)).setText(rssItem.getDate() + " - " + rssItem.getAuthor());

        return convertView;
    }

}

