package com.mc2022.template.Activities;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.core.ui.MarkersFactory;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Anchor;
import com.anychart.graphics.vector.Stroke;
import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.Models.CustomDataEntry;
import com.mc2022.template.Models.UserBPSugar;
import com.mc2022.template.R;

import java.util.ArrayList;
import java.util.List;


public class BPSugarWeight_GraphView extends AppCompatActivity {

    private final String BP_SYS = "BP SYS";
    private final String BP_DYS = "BP DYS";
    private final String SUGAR = "SUGAR";
    private final String WEIGHT = "WEIGHT";

    private static final String TAG = "BPSugarWeight_GraphView";

    public void showChart(List<DataEntry> seriesData) {
        AnyChartView anyChartView = findViewById(R.id.chartView);
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        Set set = Set.instantiate();
        set.data(seriesData);

        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");

        Line series1 = cartesian.line(series1Mapping);
        MarkersFactory markers1 = series1.markers();
        series1.name(BP_SYS);
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(String.valueOf(Anchor.LEFT_CENTER))
                .offsetX(5d)
                .offsetY(5d);
        markers1.enabled(true);
        markers1.fill("#BBDA00");
        markers1.stroke("2 white");


        Line series2 = cartesian.line(series2Mapping);
        MarkersFactory markers2 = series2.markers();
        series2.name(BP_DYS);
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(String.valueOf(Anchor.LEFT_CENTER))
                .offsetX(5d)
                .offsetY(5d);
        markers2.enabled(true);
        markers2.fill("#00bdda");
        markers2.stroke("2 white");


        Line series3 = cartesian.line(series3Mapping);
        MarkersFactory markers3 = series3.markers();
        series3.name(SUGAR);
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(String.valueOf(Anchor.LEFT_CENTER))
                .offsetX(5d)
                .offsetY(5d);
        markers3.enabled(true);
        markers3.fill("#da008a");
        markers3.stroke("2 white");


        Line series4 = cartesian.line(series4Mapping);
        MarkersFactory markers4 = series4.markers();
        series4.name(WEIGHT);
        series4.hovered().markers().enabled(true);
        series4.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series4.tooltip()
                .position("right")
                .anchor(String.valueOf(Anchor.LEFT_CENTER))
                .offsetX(5d)
                .offsetY(5d);
        markers4.enabled(true);
        markers4.fill("#da008a");
        markers4.stroke("2 white");


        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpsugar_weight_graph_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String start = extras.getString("startdate");
            String end = extras.getString("enddate");
            ArrayList<UserBPSugar> dataholder = new SQLiteHelper(this).readalldatainRange(start, end);
            List<DataEntry> seriesData = new ArrayList<>();
            if (dataholder != null && dataholder.size() > 0) {
                for (UserBPSugar data : dataholder) {
                    float systolic = Float.parseFloat(data.getBp().split("/")[0]);
                    float diastolic = Float.parseFloat(data.getBp().split("/")[1]);
                    seriesData.add(new CustomDataEntry(data.getDateMain(), systolic, diastolic, Float.parseFloat(data.getSugar()), Float.parseFloat(data.getWeight())));
                    Log.d(TAG, data.getDateMain() + " " + data.getBp() + " " + data.getSugar() + " " + data.getWeight());
                }
            }
            showChart(seriesData);
        }
    }
}