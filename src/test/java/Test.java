import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 20-08-11
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        StreamGobbler std;
        StreamGobbler err;
        Runtime runtime;
        Process process;

        String[] win = new String[] {
                "cmd.exe",
                "/C",
                "dir"
        };

        String[] bash = new String[] {
                "/bin/bash",
                "-c",
                "ls"
        };

        runtime = Runtime.getRuntime();
        process = runtime.exec(win, new String[] {}, new File("c:\\"));

        std = new StreamGobbler("stdout", process.getInputStream(), System.out);
        err = new StreamGobbler("error", process.getErrorStream(), System.out);

        std.start();
        err.start();

        process.waitFor();

        std.stop();
        err.stop();

        System.out.println("exit: " + process.exitValue());

        while (std.isAlive() || err.isAlive()) {
            Thread.sleep(100);
            System.out.print(".");
        }
    }

    static class StreamGobbler extends Thread {
        InputStream in;
        OutputStream out;
        String name;

        public StreamGobbler(String name, InputStream in, OutputStream out) {
            this.name = name;
            this.in = in;
            this.out = out;
        }

        @Override
        public void run() {
            byte[] data;
            int length;

            data = new byte[4096];

            try {
                while ((length = in.read(data)) != -1) {
                    out.write(data, 0, length);
                }
            } catch (IOException x) {
                System.out.println(name + " error: " + x.getMessage());
            }

            System.out.println(name + " done");
        }

    }
}
