package org.wooddog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 18-08-11
 * Time: 19:36
 * To change this template use File | Settings | File Templates.
 */
public class DbDeploy {
    private static String local = "jdbc:mysql://localhost:3306/feedanalyser?user=feedanalyser&password=feedanalyser_";
    private static String pub = "jdbc:mysql://193.219.27.193:3306/feedanalyser?user=feedanalyser&password=feedanalyser_";
    private static String schema = "mysql-schema.xml";
    private static String data = "data.txt";

    private static String url = pub;
    private static String resource = data;

    public static void main(String[] args) throws Exception {
        Connection connection;
        Statement statement;
        InputStream stream;
        BufferedReader reader;
        String command;
        int line;

        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        connection = DriverManager.getConnection(url);

        try {
            line = 0;
            stream = DbDeploy.class.getClassLoader().getResourceAsStream(resource);
            reader = new BufferedReader(new InputStreamReader(stream));

            while ((command = reader.readLine()) != null) {
                System.out.println("> " + command);
                line ++;
                if (!command.isEmpty() && !command.startsWith("#")) {
                    statement = connection.createStatement();
                    try {
                        statement.execute(command);
                    } catch (SQLException x) {
                        throw new RuntimeException("pos #" + line + "@" + Config.get(Config.DB_SCHEMA_DEFINITION) + " failed execution statement " + command + ", " + x.getMessage(), x);
                    }
                }
            }
        } finally {
            connection.close();
        }
    }
}
