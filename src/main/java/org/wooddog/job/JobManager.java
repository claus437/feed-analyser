package org.wooddog.job;

import org.apache.log4j.Logger;

import java.lang.reflect.Array;
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

    public List<JobThread> getJobList() {
        return threads;
    }

    public void start() {
        for (JobThread thread : threads) {
            try {
                thread.start();
                LOGGER.info("started " + thread.getJob().getName());
            } catch (Throwable x) {
                LOGGER.error("failed starting " + thread.getJob().getName());
            }
        }
    }

    public void stop() {
        for (JobThread thread : threads) {
            try {
                thread.kill();
                LOGGER.info("stopping " + thread.getJob().getName());
            } catch (Throwable x) {
                LOGGER.error("failed stopping " + thread.getJob().getName());
            }
        }
    }

    public void waitForTermination() {
        for (JobThread thread : threads) {
            while(thread.isAlive()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException x) {
                    LOGGER.info("retreived interuption but waiting for " + thread.getName() + " to terminate");
                }
            }
        }
    }
}
