package org.wooddog.servlets;

import org.wooddog.ChannelManager;
import org.wooddog.dao.Service;

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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ChannelManager.getInstance().start();
    }

    @Override
    public void destroy() {
        super.destroy();

        ChannelManager.getInstance().stop();

        try {
            Service.getInstance().shutdown();
        } catch (SQLException x) {
            x.printStackTrace();
        }
    }
}