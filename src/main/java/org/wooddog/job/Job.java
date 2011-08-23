package org.wooddog.job;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 17-08-11
 * Time: 08:50
 * To change this template use File | Settings | File Templates.
 */
public interface Job {
    String getName();
    void execute();
    void terminate();
    int progress();
}
