package com.example.customviewtest.utils;

import android.util.Log;

import java.time.Duration;
import java.time.Instant;

public class Stopwatch {
    private static final String TAG = "[Stopwatch]";
    private Instant start;
    private Instant stop;

    private String label;

    public Stopwatch(String label) {
        this.label = label;
    }

    public Stopwatch() {
    }

    public void start() {
        start = Instant.now();
    }

    public static Stopwatch createStarted() {
        Stopwatch sw = new Stopwatch();
        sw.start();
        return sw;
    }

    public static Stopwatch createStarted(String label) {
        Stopwatch sw = new Stopwatch(label);
        sw.start();
        return sw;
    }

    public void stop() {
        stop = Instant.now();
    }

    public Duration getElapsedTime() {
        return Duration.between(start, stop);
    }

    private void logElapsed() {
        logElapsed(null);
    }

    private void logElapsed(String roundLabel) {
        if (start == null || stop == null) return;
        Duration duration = getElapsedTime();
        String seconds = duration.getSeconds() + " s";
        String millis = (int) (duration.getNano() / 1_000_000) + " ms";
        String tempLabel = label;
        if (roundLabel != null) tempLabel = tempLabel + " [" + roundLabel + "]";
        if (tempLabel != null) seconds = tempLabel + ": " + seconds;
        Log.d(TAG, seconds + ", " + millis);
    }

    public void logRound() {
        stop();
        logElapsed();
    }

    public void logRound(String roundLabel) {
        stop();
        logElapsed(roundLabel);
    }

}
