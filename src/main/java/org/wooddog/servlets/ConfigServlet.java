package org.wooddog.servlets;

import org.apache.log4j.Logger;
import org.wooddog.Config;
import org.wooddog.config.BackgroundJobs;
import org.wooddog.dao.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;
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
    private static ServletConfig servletConfig;

    @Override
    public void init(ServletConfig config) throws ServletException {
        BackgroundJobs jobs;

        super.init(config);

        servletConfig = config;
        Config.load("org/wooddog/config/mysql.properties");

        jobs = BackgroundJobs.getInstance();
        jobs.start();
    }

    @Override
    public void destroy() {
        BackgroundJobs jobs;

        super.destroy();

        LOGGER.info("shutting down background tasks");
        jobs = BackgroundJobs.getInstance();
        jobs.stop();
        jobs.waitForTermination();
        LOGGER.info("background tasks terminated");

        try {
            Service.getInstance().shutdown();
        } catch (SQLException x) {
            x.printStackTrace();
        }
    }

    public static String getRealPath(String path) {
        if (servletConfig == null) {
            return path;
        } else {
            return servletConfig.getServletContext().getRealPath(path);
        }
    }
}
