package org.wooddog.servlets;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 25-06-11
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */
public class JspTool {
    public static String formatFullDate(Date date) {
        SimpleDateFormat format;

        if (date == null) {
            return "";
        }

        format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return format.format(date);
    }
}
