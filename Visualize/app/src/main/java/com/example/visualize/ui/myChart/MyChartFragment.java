package com.example.visualize.ui.myChart;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.visualize.ContentActivity;
import com.example.visualize.R;
import com.example.visualize.chart.Chart;
import com.example.visualize.user.User;

import java.util.List;

public class MyChartFragment extends Fragment {

   private MyChartAdapter myChartAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myChartAdapter = new MyChartAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        update();
    }

    public void update() {
        final ContentActivity activity = (ContentActivity) getContext();
        final ListView myChartListView = activity.findViewById(R.id.myChartListView);
        myChartListView.setAdapter(myChartAdapter);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myChartAdapter.clear();
                User user = activity.getUser();
                final List<Chart> charts = Chart.getCharts(user);
                for (Chart chart : charts) {
                    myChartAdapter.add(chart);
                }
            }
        });
    }
}