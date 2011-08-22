package org.wooddog.job;

import org.apache.log4j.Logger;
import java.util.Date;

public class JobThread extends Thread {
    public enum Status {INITIALIZED, SLEEPING, EXECUTING, TERMINATING, TERMINATED}
    private static final Logger LOGGER = Logger.getLogger(JobThread.class);
    private Job job;
    private JobPlan plan;
    private boolean signal;
    private Date lastRun;
    private Date nextRun;
    private Status status;
    private long executingTime;

    public JobThread() {
        status = Status.INITIALIZED;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobPlan getPlan() {
        return plan;
    }

    public void setPlan(JobPlan plan) {
        this.plan = plan;
    }

    public void run() {
        while (!signal) {
            execute();

            while (System.currentTimeMillis() < nextRun.getTime() && !signal) {
                sleep();
            }
        }

        status = Status.TERMINATED;
    }

    public void execute() {
        long now;
        status = Status.EXECUTING;

        LOGGER.info("executing " + job.getName());

        now = System.currentTimeMillis();
        job.execute();
        executingTime = System.currentTimeMillis() - now;

        lastRun = new Date();
        nextRun = plan.getNextRun(lastRun);
    }

    public void sleep() {
        status = Status.SLEEPING;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException x) {
            signal = true;
        }
    }

    public Date getLastRun() {
        return lastRun;
    }

    public Date getNextRun() {
        return nextRun;
    }

    public void kill() {
        status = Status.TERMINATING;
        signal = true;
        job.terminate();
    }

    public Status getStatus() {
        return status;
    }

    public long getExecutingTime() {
        return executingTime;
    }
}
