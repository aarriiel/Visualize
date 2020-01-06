package com.example.visualize.ui.newChart;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.visualize.ContentActivity;
import com.example.visualize.R;
import com.example.visualize.chart.Chart;
import com.example.visualize.user.User;
import com.opencsv.CSVReader;
import java.io.InputStreamReader;
import java.util.List;

public class NewChartFragment extends Fragment {

    public static final int CHOOSE_CSV = 1;
    private Uri fileURI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ContentActivity activity = (ContentActivity) getContext();
        final RadioGroup radioGroup = activity.findViewById(R.id.chooseWay);
        final Button drawButton = activity.findViewById(R.id.drawButton);
        final Button fileButton = activity.findViewById(R.id.fileButton);
        final EditText chartName = activity.findViewById(R.id.ChartNameField);
        final EditText x = activity.findViewById(R.id.XField);
        final EditText y = activity.findViewById(R.id.YField);
        final EditText csv = activity.findViewById(R.id.fileField);
        final EditText column = activity.findViewById(R.id.columnField);
//        final EditText keyword = activity.findViewById(R.id.keywordField);
        final User user = activity.getUser();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                System.out.println("CHANGE");
                switch(checkedId)
                {
                    case R.id.inputData:
                        csv.setEnabled(false);
                        column.setEnabled(false);
//                        keyword.setEnabled(false);
                        fileButton.setClickable(false);
                        x.setEnabled(true);
                        y.setEnabled(true);
                        break;
                    case R.id.loadFile:
                        column.setEnabled(true);
//                        keyword.setEnabled(true);
                        fileButton.setClickable(true);
                        x.setEnabled(false);
                        y.setEnabled(false);
                        break;
                }
            }
        });

        fileButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("text/csv");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Open CSV"), CHOOSE_CSV);
            }
        });
        fileButton.setClickable(false);

        drawButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println(chartName.getText().charAt(0));
                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.inputData:
                            String[] xAxis = x.getText().toString().split(",");
                            String[] yAxis = y.getText().toString().split(",");
                            for(int i=0; i <xAxis.length; i++){
                                System.out.println(xAxis[i]+Double.parseDouble(yAxis[i]));
                            }
                            Chart.create(user.getUsername(), chartName.getText().toString(), x.getText().toString(),y.getText().toString());
                            break;
                        case R.id.loadFile:
                            System.out.println(fileURI);
                            CSVReader reader = new CSVReader(new InputStreamReader(getActivity().getContentResolver().openInputStream(fileURI)));
//                            List myEntries = reader.readAll();
//                            System.out.println(myEntries.get(0));
                            String[] nextRecord;
                            String xlabel="";
                            String yNumber="";
                            Boolean label = true;
                            while ((nextRecord = reader.readNext()) != null) {
                                System.out.println(nextRecord[0]+"<----");
                                if(column.getText()!=null){
                                    for(int i=Integer.parseInt(String.valueOf(column.getText().charAt(0)))-1; i<Integer.parseInt(String.valueOf(column.getText().charAt(2)));i++){
                                        if(label) {
                                            if(xlabel=="")
                                                xlabel += nextRecord[i];
                                            else
                                                xlabel = xlabel + "," + nextRecord[i];
                                        } else {
                                            if(yNumber=="")
                                                yNumber += nextRecord[i];
                                            else
                                                yNumber = yNumber + "," + nextRecord[i];
                                        }
                                    }
                                }else {
                                    for (int i = 0; i < nextRecord.length; i++) {
                                        if (label) {
                                            if (xlabel == "")
                                                xlabel += nextRecord[i];
                                            else
                                                xlabel = xlabel + "," + nextRecord[i];
                                        } else {
                                            if (yNumber == "")
                                                yNumber += nextRecord[i];
                                            else
                                                yNumber = yNumber + "," + nextRecord[i];
                                        }
                                    }
                                }
                                label = false;
                            }
                            String[] x2 = xlabel.split(",");
                            String[] y2 = yNumber.split(",");
                            for(int i=0; i <x2.length; i++){
                                System.out.println(x2[i]+Double.parseDouble(y2[i]));
                            }
                            Chart.create(user.getUsername(), chartName.getText().toString(), xlabel, yNumber);
                            break;
                    }
                    new AlertDialog.Builder(getContext())
                            .setTitle("Success")
                            .setMessage("We saved this chart to the server. See this chart in the My Chart.")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();

                }catch (Exception e){
                    System.out.println(e);
                    new AlertDialog.Builder(getContext())
                            .setTitle("Failed")
                            .setMessage("Error: Wrong data type")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_CSV:
                fileURI = data.getData();
                ContentActivity activity = (ContentActivity) getContext();
                final EditText csv = activity.findViewById(R.id.fileField);
                csv.setText(fileURI.toString().split("/")[fileURI.toString().split("/").length-1]);
                break;
            default:
                break;
        }
    }
}