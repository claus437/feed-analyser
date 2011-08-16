package org.wooddog.servlets;

import org.apache.log4j.Logger;
import org.wooddog.ChannelManager;
import org.wooddog.Config;
import org.wooddog.ScoreRunner;
import org.wooddog.dao.Service;
import org.wooddog.stock.StockJob;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
public class ConfigServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ConfigServlet.class);
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        Config.load("org/wooddog/config/mysql.properties");

        try {
            StockJob.getInstance().start();
        } catch (RuntimeException x) {
            LOGGER.error("failed starting stock job " + x.getMessage(), x);
        }

        try {
            ScoreRunner.getInstance().start();
        } catch (RuntimeException x) {
            LOGGER.error("failed starting score job, " + x.getMessage(), x);
        }

        try {
            ChannelManager.getInstance().start();
        } catch (RuntimeException x) {
            LOGGER.error("failed starting channel fetcher, " + x.getMessage(), x);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ScoreRunner scoreRunner;

        StockJob.getInstance().kill();

        scoreRunner = ScoreRunner.getInstance();
        scoreRunner.kill();
        ChannelManager.getInstance().stop();

        while (scoreRunner.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException x) {
                throw new RuntimeException(x);
            }
        }

        try {
            Service.getInstance().shutdown();
        } catch (SQLException x) {
            x.printStackTrace();
        }
    }
}
