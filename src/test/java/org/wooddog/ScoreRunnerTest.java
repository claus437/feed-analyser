package org.wooddog;

import junit.framework.Assert;
import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.wooddog.dao.DaoTestCase;
import support.Wait;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 03-07-11
 * Time: 00:04
 * To change this template use File | Settings | File Templates.
 */
public class ScoreRunnerTest extends DaoTestCase {

    @Test
    public void testSignal() {
        ScoreRunner runner;

        execute(DatabaseOperation.CLEAN_INSERT, "TestSignal");
        runner = new ScoreRunner();
        runner.start();

        Assert.assertEquals(true, runner.isAlive());

        runner.kill();
        Wait.forThread(runner, 5);

        Assert.assertEquals(false, runner.isAlive());
    }

    @Test
    public void testRun() throws Exception {
        ScoreRunner runner;
        ITable expected;
        ITable actual;

        execute(DatabaseOperation.CLEAN_INSERT, "TestRun");
        runner = new ScoreRunner();
        runner.start();


        Wait.forRows(Wait.createStatement(connection, "SELECT COUNT(ID) FROM SCORINGS"), 6, 5);

        expected = getDataSet("TestRun.expected").getTable("SCORINGS");
        actual = connection.createDataSet().getTable("SCORINGS");
        actual = DefaultColumnFilter.includedColumnsTable(actual, expected.getTableMetaData().getColumns());

        Assertion.assertEquals(expected, actual);
    }

    @Test
    public void testRunAndResume() throws Exception {
        ScoreRunner runner;
        ITable expected;
        ITable actual;

        execute(DatabaseOperation.CLEAN_INSERT, "TestRunAndResume#1");
        runner = new ScoreRunner();
        runner.start();

        Wait.forRows(Wait.createStatement(connection, "SELECT COUNT(ID) FROM SCORINGS"), 6, 10);

        execute(DatabaseOperation.INSERT, "TestRunAndResume#2");

        Wait.forRows(Wait.createStatement(connection, "SELECT COUNT(ID) FROM SCORINGS"), 12, 10);

        expected = getDataSet("TestRunAndResume.expected").getTable("SCORINGS");
        actual = connection.createDataSet().getTable("SCORINGS");
        actual = DefaultColumnFilter.includedColumnsTable(actual, expected.getTableMetaData().getColumns());

        Assertion.assertEquals(expected, actual);
    }

}
