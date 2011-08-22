package org.wooddog.config;

import org.wooddog.job.JobManager;
import org.wooddog.job.JobPlan;
import org.wooddog.job.channel.ChannelJob;
import org.wooddog.job.score.ScoreJob;
import org.wooddog.job.stock.StockLoaderJob;

import static org.wooddog.job.JobPlan.Frequency.EVERY;

public class BackgroundJobs {
    private static final BackgroundJobs INSTANCE = new BackgroundJobs();
    private StockLoaderJob stockLoaderJob;
    private ScoreJob scoreJob;
    private ChannelJob channelJob;

    private JobManager manager;

    private BackgroundJobs() {
        JobPlan plan;

        stockLoaderJob = new StockLoaderJob();
        scoreJob = new ScoreJob();
        channelJob = new ChannelJob();

        manager = new JobManager();

        plan = new JobPlan();
        plan.setMinute(EVERY, 15);
        manager.addJob(stockLoaderJob, plan);

        plan = new JobPlan();
        plan.setHour(EVERY, 1);
        manager.addJob(new ScoreJob(), plan);

        plan = new JobPlan();
        plan.setHour(EVERY, 1);
        manager.addJob(new ChannelJob(), plan);
    }

    public StockLoaderJob getStockLoaderJob() {
        return stockLoaderJob;
    }

    public ScoreJob getScoreJob() {
        return scoreJob;
    }

    public ChannelJob getChannelJob() {
        return channelJob;
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
