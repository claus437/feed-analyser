package org.wooddog.servlets.model;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 28-08-11
 * Time: 12:54
 * To change this template use File | Settings | File Templates.
 */
public interface ModelChangeListener {
    public void onChange(Object src, Object oldValue, Object newValue);
}
