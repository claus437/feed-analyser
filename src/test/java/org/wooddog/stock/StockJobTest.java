package org.wooddog.stock;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wooddog.Config;
import org.wooddog.IOUtil;
import org.wooddog.dao.StockService;
import org.wooddog.domain.Stock;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 15-08-11
 * Time: 19:33
 * To change this template use File | Settings | File Templates.
 */
public class StockJobTest {
    StockJob job;
    int stockListSize;

    @BeforeClass
    public static void init() {
        Config.load("org/wooddog/config/mysql.properties");
    }

    @Test
    public void testSingleRunWithDataAccess() {
        job = new StockJob();
        job.kill();
        job.run();

        IOUtil.wait(job);

        Assert.assertEquals(20, stockListSize);
    }
}
