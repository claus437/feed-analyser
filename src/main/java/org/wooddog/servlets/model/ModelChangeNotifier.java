package org.wooddog.servlets.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 28-08-11
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class ModelChangeNotifier {
    private Object src;
    private List<ModelChangeListener> listeners;


    public ModelChangeNotifier(Object src) {
        this.listeners = new ArrayList<ModelChangeListener>();
        this.src = src;
    }

    public void addModelChangeListener(ModelChangeListener listener) {
        listeners.add(listener);
    }

    public boolean notifyListeners(Object oldValue, Object newValue) {
        boolean changed;


        if (oldValue == null) {
            changed = newValue != null;
        } else {
            changed = !oldValue.equals(newValue);
        }

        if (changed) {
            for (ModelChangeListener listener : listeners) {
                listener.onChange(src, oldValue, newValue);
            }
        }

        return changed;
    }

}
