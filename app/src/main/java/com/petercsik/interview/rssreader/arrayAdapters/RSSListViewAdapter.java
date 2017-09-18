package com.petercsik.interview.rssreader.arrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.petercsik.interview.rssreader.R;

import java.util.List;

public class RSSListViewAdapter extends ArrayAdapter<String> {

    private final int layoutResourceId;
    private final String data[];
    private LayoutInflater layoutInflater;

    public RSSListViewAdapter(Context mContext, int layoutResourceId, List<String> data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.data = data.toArray(new String[0]);
        this.layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResourceId, parent, false);
        }

         String rssName = data[position];
        ((TextView) convertView.findViewById(R.id.rss_content_title)).setText(rssName);

        return convertView;
    }

}

