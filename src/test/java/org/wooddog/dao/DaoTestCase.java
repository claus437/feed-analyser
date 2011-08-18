package org.wooddog.dao;

import org.dbunit.DatabaseUnitException;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.wooddog.Config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-07-11
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class DaoTestCase {
    private static JdbcDatabaseTester database;
    protected static IDatabaseConnection connection;

    @BeforeClass
    public static void init() throws Exception {
        Config.load("org/wooddog/config/mysql.properties");

        database = new JdbcDatabaseTester(
                Config.get(Config.DB_DRIVER),
                Config.get(Config.DB_URL),
                Config.get(Config.DB_USERNAME),
                Config.get(Config.DB_PASSWORD));

        connection = database.getConnection();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        database.onTearDown();
    }

    public FlatXmlDataSet getDataSet(String resource) {
        InputStream stream;
        FlatXmlDataSet dataset;
        String qualifiedResource;

        qualifiedResource = getQualifiedPath(resource);

        stream = getClass().getClassLoader().getResourceAsStream(qualifiedResource);
        if (stream == null) {
            throw new RuntimeException("resource " + qualifiedResource + " not found");
        }

        try {
            dataset = new FlatXmlDataSet(stream);
        } catch (DataSetException x) {
            throw new RuntimeException(x);
        } catch (IOException x) {
            throw new RuntimeException(x);
        }

        return dataset;
    }


    public void execute(DatabaseOperation operation, String resource) {
        IDataSet dataset;

        dataset = getDataSet(resource);

        try {
            operation.CLEAN_INSERT.execute(connection, dataset);
        } catch (SQLException x) {
            throw new RuntimeException(x);
        } catch (DatabaseUnitException x) {
            throw new RuntimeException(x);
        }
    }

    public String getQualifiedPath(String relativeResource) {
        String qualifiedPath;

        qualifiedPath = getClass().getCanonicalName();
        qualifiedPath = qualifiedPath.replaceAll("\\.", "/");
        qualifiedPath += "/";
        qualifiedPath += relativeResource;
        qualifiedPath += ".ds.xml";

        return qualifiedPath;
    }

    public void resetPrimaryKey(String table) {
        try {
            connection.getConnection().createStatement().execute("ALTER TABLE " + table + " AUTO_INCREMENT = 1");
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

}
