package org.wooddog.servlets.model;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 27-08-11
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
public interface EventHandler {
    void update(Set<String> events);
}
