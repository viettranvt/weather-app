package com.example.weather.constant;

import android.graphics.Color;

public class Format {
    public static final String DATE_TIME = "dd/MM/yyyy HH:mm";
    public static final String DATE = "dd/MM";
    public static final String HOURS = "HH";
    public static final String FULL_DATE = "dd/MM/yyyy";
    public static final String TIME = "HH:mm";
    public static final String DAY_IN_WEEK = "u";
    public static final int[] GRADIENT_NIGHT = new int[] {
            Color.parseColor("#122259"),
            Color.parseColor("#9561a1")
    };
    public static final int[] GRADIENT_DAY = new int[] {
            Color.parseColor("#5f98b8"),
            Color.parseColor("#8aa7b9")
    };
    public static final int[] GRADIENT_DAY_V2 = new int[] {
            Color.parseColor("#6168f8"),
            Color.parseColor("#82c1f4")
    };
}
