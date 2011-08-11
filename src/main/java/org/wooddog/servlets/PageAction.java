package org.wooddog.servlets;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-06-11
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public interface PageAction {
    void execute(Map<String, String[]> parameters);
}
