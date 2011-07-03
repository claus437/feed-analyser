package support;

import com.sun.corba.se.spi.ior.IdentifiableFactory;
import org.apache.log4j.Logger;
import org.dbunit.database.IDatabaseConnection;
import sun.util.LocaleServiceProviderPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 03-07-11
 * Time: 00:08
 * To change this template use File | Settings | File Templates.
 */
public class Wait {
    private static final Logger LOGGER = Logger.getLogger(Wait.class);

    public static void forThread(Thread object, int timeout) {
        long end;

        end = System.currentTimeMillis() + (timeout * 1000);

        while (object.isAlive() && System.currentTimeMillis() < end) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException x) {
                throw new RuntimeException(x);
            }
        }
    }

    public static void forRows(PreparedStatement statement, int rows, int timeout) {
        ResultSet rs;
        int count;
        long end;

        end = System.currentTimeMillis() + (timeout * 1000);

        try {
            do {
                rs = statement.executeQuery();
                rs.next();
                count = rs.getInt(1);

                Thread.sleep(1000);
            } while (count < rows && System.currentTimeMillis() < end);

            if (count < rows) {
                LOGGER.warn("time out for rows " + rows);
            }

        } catch (SQLException x) {
            throw new RuntimeException(x);
        } catch (InterruptedException x) {
            throw new RuntimeException(x);
        }
    }

    public static PreparedStatement createStatement(IDatabaseConnection connection, String statement) {
        try {
            return connection.getConnection().prepareStatement(statement);
        } catch (SQLException x) {
            throw new RuntimeException("failed creating statement " + statement);
        }
    }
}
