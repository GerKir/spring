package com.gerkir.spring;

import com.gerkir.spring.beans.Client;
import com.gerkir.spring.beans.Event;
import com.gerkir.spring.beans.EventType;
import com.gerkir.spring.loggers.EventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class App {
    private Client client;
    private EventLogger defaultLogger;
    private Map<EventType, EventLogger> loggersMap;

    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggersMap) {
        this.client = client;
        this.defaultLogger = eventLogger;
        this.loggersMap = loggersMap;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) context.getBean("app");


        Event event = context.getBean(Event.class);
        app.logEvent(event, "Some event for 1", EventType.INFO);

        event = context.getBean(Event.class);
        app.logEvent(event, "Some event for 2", EventType.ERROR);

        event = context.getBean(Event.class);
        app.logEvent(event, "Some event for 3", null);

        context.close();

    }

    private void logEvent(Event event, String msg, EventType type) {
        EventLogger logger = loggersMap.get(type);
        if (logger == null) logger = defaultLogger;

        String changedMsg = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(client.getGreeting() + changedMsg);

        logger.logEvent(event);
    }
}
