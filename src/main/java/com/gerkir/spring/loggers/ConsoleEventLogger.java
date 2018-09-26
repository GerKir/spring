package com.gerkir.spring.loggers;

import com.gerkir.spring.beans.Event;

public class ConsoleEventLogger implements EventLogger {

    public void logEvent(Event event) {
        System.out.println(event);
    }
}
