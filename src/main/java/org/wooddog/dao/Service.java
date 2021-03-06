package org.wooddog.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.wooddog.Config;
import org.wooddog.dao.service.ScoringServiceDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 19-06-11
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
public class Service {
    private static Service service = new Service();
    private static SqlSessionFactory factory;
    public static ScoringService scoringService;

    static {
        Reader reader;

        try {
            reader = Resources.getResourceAsReader("persistence.xml");
        } catch (IOException x) {
            throw new RuntimeException(x.getMessage(), x);
        }

        factory = new SqlSessionFactoryBuilder().build(reader, Config.getProperties());
    }

    public static SqlSessionFactory getFactory() {
        return factory;
    }

    public static Service getInstance() {
        return service;
    }


    public void shutdown() throws SQLException {
        DriverManager.getConnection("jdbc:hsqldb:db/test").createStatement().execute("SHUTDOWN");
    }

    public SqlSession getSession() {
        return factory.openSession();
    }


    public static Map createParameterMap(Object... parameters) {
        Map<String, Object> parameterMap;

        parameterMap = new HashMap<String, Object>();
        for (int i = 0; i < parameters.length; i++) {
            parameterMap.put(Integer.toString(i), parameters[i]);
        }

        return parameterMap;
    }
}
