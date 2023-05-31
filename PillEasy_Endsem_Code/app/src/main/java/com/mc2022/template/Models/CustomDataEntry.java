package com.mc2022.template.Models;

import com.anychart.chart.common.dataentry.ValueDataEntry;

public class CustomDataEntry extends ValueDataEntry {
    public CustomDataEntry(String x, Number value, Number value2, Number value3, Number value4) {
        super(x, value);
        setValue("value2", value2);
        setValue("value3", value3);
        setValue("value4", value4);
    }
}