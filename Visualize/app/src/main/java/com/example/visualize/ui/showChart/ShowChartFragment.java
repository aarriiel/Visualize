package com.example.visualize.ui.showChart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visualize.ContentActivity;
import com.example.visualize.R;
import com.example.visualize.chart.Chart;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class ShowChartFragment extends Fragment {
    private Chart chart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        chart = new Chart(bundle.getString("name"),
                bundle.getString("createTime"),
                bundle.getString("xAxis"),
                bundle.getString("yAxis")
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ContentActivity activity = (ContentActivity) getContext();
        BarChart chartBar = activity.findViewById(R.id.chart_bar);
        chartBar.setData(getBarData());
        XAxis xAxis = chartBar.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return getLabels().get((int) value % getLabels().size());
            }
        });
        xAxis.setTextSize(10f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
    }

    private List<String> getLabels(){
        String[] xStr = chart.getxAxis().split(",");
        List<String> chartLabels = new ArrayList<>();
        chartLabels.add(" ");
        for(int i=0;i<xStr.length;i++){
            chartLabels.add(xStr[i]);
        }
        return chartLabels;
    }

    private BarData getBarData(){
        BarDataSet dataSetA = new BarDataSet(getBarChart(), chart.getName());

        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA); // add the datasets

        return new BarData(dataSets);

    }

    private List<BarEntry> getBarChart(){
        List<BarEntry> bar = new ArrayList<>();
        String[] yStr = chart.getyAxis().split(",");
        System.out.println(yStr[0]);
        bar.add(new BarEntry(0, Float.NaN));
        for (int i = 1; i < yStr.length+1; i++) {
            bar.add(new BarEntry(i, Float.parseFloat(yStr[i-1])));
        }
        return bar;
    }
}
