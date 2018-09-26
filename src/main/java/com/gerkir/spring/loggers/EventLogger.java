package com.gerkir.spring.loggers;

import com.gerkir.spring.beans.Event;

public interface EventLogger {
    void logEvent(Event event);

}
