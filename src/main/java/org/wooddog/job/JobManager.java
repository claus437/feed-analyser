package org.wooddog.job;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 18-08-11
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */
public class JobManager {
    private static final Logger LOGGER = Logger.getLogger(JobManager.class);
    private List<JobThread> threads;

    public JobManager() {
        threads = new ArrayList<JobThread>();
    }

    public void addJob(Job job, JobPlan plan) {
        JobThread thread;

        thread = new JobThread();
        thread.setJob(job);
        thread.setPlan(plan);

        threads.add(thread);
    }

    public void start() {
        for (JobThread thread : threads) {
            try {
                thread.start();
            } catch (Throwable x) {
                LOGGER.error("failed starting " + thread.getJob().getName());
            }
        }
    }

    public void stop() {} {
        for (JobThread thread : threads) {
            try {
                thread.kill();
            } catch (Throwable x) {
                LOGGER.error("failed stopping " + thread.getJob().getName());
            }
        }
    }
}
