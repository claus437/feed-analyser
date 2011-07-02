package org.wooddog.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.wooddog.domain.Article;
import org.wooddog.domain.Channel;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

        factory = new SqlSessionFactoryBuilder().build(reader);

        try {
            service.createDb();
        } catch (Throwable x) {
            x.printStackTrace();
        }
    }

    public static SqlSessionFactory getFactory() {
        return factory;
    }

    public static ScoringService getScoringService() {
        return scoringService;
    }


    public static Service getInstance() {
        return service;
    }

    private void createDb() throws IOException, SQLException, ClassNotFoundException {
        Connection connection;
        Statement statement;
        BufferedReader reader;
        String command;

        new File("db").deleteOnExit();
        Class.forName("org.hsqldb.jdbcDriver");
        connection = DriverManager.getConnection("jdbc:hsqldb:db/test");

        reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("database-schema.xml")));
        while ((command = reader.readLine()) != null) {
            if (!command.isEmpty() && !command.startsWith("#")) {
                statement = connection.createStatement();
                statement.execute(command);
                System.out.println("created " + command);
            }
        }
    }

    public void shutdown() throws SQLException {
        DriverManager.getConnection("jdbc:hsqldb:db/test").createStatement().execute("SHUTDOWN");
    }

    public SqlSession getSession() {
        return factory.openSession();
    }


    static Map createParameterMap(Object... parameters) {
        Map<String, Object> parameterMap;

        parameterMap = new HashMap<String, Object>();
        for (int i = 0; i < parameters.length; i++) {
            parameterMap.put(Integer.toString(i), parameters[i]);
        }

        return parameterMap;
    }
}
