/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 19-08-11
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    String message;

    public Test() {
        message = "Hello";
        System.out.println("construct " + message);
    }

    public void run() {
        System.out.println("run" + message);
    }

    public void stop() {} static {
        System.out.println("stop ");
    }



    public static void main(String[] args) {

        Test test = new Test();
        test.run();
    }
}
