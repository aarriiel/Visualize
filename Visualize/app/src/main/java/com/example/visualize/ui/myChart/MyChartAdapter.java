package com.example.visualize.ui.myChart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;

import com.example.visualize.ContentActivity;
import com.example.visualize.R;
import com.example.visualize.chart.Chart;
import com.example.visualize.ui.showChart.ShowChartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;

public class MyChartAdapter extends BaseAdapter {

    List<Chart> charts = new ArrayList<>();
    Context context;

    public MyChartAdapter(Context context) {
        this.context = context;
    }

    public void clear() {
        charts.clear();
        notifyDataSetChanged();
    }

    public void add(Chart chart) {
        charts.add(chart);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return charts.size();
    }

    @Override
    public Object getItem(int i) {
        return charts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // This is the backbone of the class, it handles the creation of single ListView row (chat bubble)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ChartViewHolder holder = new ChartViewHolder();
        LayoutInflater chartInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final Chart chart = charts.get(i);

        convertView = chartInflater.inflate(R.layout.my_chart_items, null);
        holder.name = convertView.findViewById(R.id.name);
        holder.time = convertView.findViewById(R.id.time);
        convertView.setTag(holder);

        holder.name.setText(chart.getName());
        holder.time.setText("Created : "+chart.getCreateTime().substring(0,chart.getCreateTime().length()-5).replace('T',' '));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentActivity contentActivity = (ContentActivity) context;
                Bundle bundle = new Bundle();
                bundle.putString("name",chart.getName());
                bundle.putString("xAxis",chart.getxAxis());
                bundle.putString("yAxis",chart.getyAxis());
                bundle.putString("createTime",chart.getCreateTime());
                contentActivity.show(bundle);
            }
        });

        return convertView;
    }

    private class ChartViewHolder {
        public TextView name;
        public TextView time;
    }

}
