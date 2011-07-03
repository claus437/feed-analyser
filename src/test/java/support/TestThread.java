package support;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 03-07-11
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public class TestThread {

    @Test
    public void testTest() throws Exception {
        MyThread t;

        t = new MyThread();
        t.start();

        while (t.isAlive()) {
            Thread.sleep(1000);
        }
        t.stop();

        t.start();


    }
}

class MyThread extends Thread {
    public void run() {
        super.run();
        System.out.println("run");
    }
}

