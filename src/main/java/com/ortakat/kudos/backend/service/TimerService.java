package com.ortakat.kudos.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@Slf4j
public class TimerService {

	public void setupTimer(long delayMinutes, Supplier<String> doing) {
		TimerTask task = new TimerTask() {
			public void run() {
				doing.get();
			}
		};
		Timer timer = new Timer("Timer");
		long delay = delayMinutes * 1000;
		timer.schedule(task, delay);
	}


}
