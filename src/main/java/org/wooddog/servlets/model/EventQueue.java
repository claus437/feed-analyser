package org.wooddog.servlets.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 27-08-11
 * Time: 10:54
 * To change this template use File | Settings | File Templates.
 */
public class EventQueue {
    private Set<String> queue;
    private EventHandler eventHandler;


    public EventQueue(EventHandler handler) {
        queue = new HashSet<String>();
        this.eventHandler = handler;
    }


    public void addEvent(Object oldValue, Object newValue, String... name) {
        if(hasChanged(oldValue, newValue)) {
            queue.addAll(Arrays.asList(name));
        }
    }

    public void update() {
        eventHandler.update(queue);
        queue.clear();
    }

    private boolean hasChanged(Object oldValue, Object newValue) {
        if (oldValue == null && newValue != null) {
            return true;
        }

        return oldValue != null && oldValue.equals(newValue);

    }
}
