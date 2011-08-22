package org.wooddog.config;

import org.wooddog.domain.Channel;
import org.wooddog.job.JobManager;
import org.wooddog.job.JobPlan;
import org.wooddog.job.channel.ChannelJob;
import org.wooddog.job.channel.fetcher.ChannelFetcher;
import org.wooddog.job.score.ScoreJob;
import org.wooddog.job.stock.StockLoaderJob;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 18-08-11
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
public class BackgroundJobs {
    private static final BackgroundJobs INSTANCE = new BackgroundJobs();
    private JobManager manager;

    private BackgroundJobs() {
        JobPlan plan;

        manager = new JobManager();

        plan = new JobPlan();
        plan.setMinute(JobPlan.Frequency.EVERY, 15);
        manager.addJob(new StockLoaderJob(), plan);

        plan = new JobPlan();
        plan.setHour(JobPlan.Frequency.EVERY, 1);
        manager.addJob(new ScoreJob(), plan);

        plan = new JobPlan();
        plan.setHour(JobPlan.Frequency.EVERY, 1);
        manager.addJob(new ChannelJob(), plan);
    }

    public static BackgroundJobs getInstance() {
        return INSTANCE;
    }

    public void start() {
        manager.start();
    }

    public void stop() {
        manager.stop();
    }

    public void waitForTermination() {
        manager.waitForTermination();
    }

    public JobManager getManager() {
        return manager;
    }
}
