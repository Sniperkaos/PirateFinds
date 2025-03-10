package me.cworldstar.piratefinds.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeImpl {
	public static void at(int hour, int minute, int second, Runnable run) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); 
		long now = System.currentTimeMillis();
		scheduler.scheduleAtFixedRate(run, now, (hour * 3600) + (minute * 60) + (second), TimeUnit.SECONDS);
	}
}
