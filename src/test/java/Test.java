/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 19-08-11
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    String message;
    static double progress;

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
        rateCompanies();
    }

    public static void rateCompanies() {
        int companySize = 6;

        for (int i = 0; i < companySize; i++) {
            progress = 100D / companySize * (i + 1);
            System.out.print("\nprogress: " + (int) progress + " : ");

            rateArticles(100D / companySize);
        }
    }

    public static void rateArticles(double scale) {
        int articles = 20;
        double progressBase;

        progressBase = progress;

        for (int i = 0; i < articles; i++) {
            progress = ((100D / scale) / articles) * (i + 1) + progressBase;
            System.out.print((int) progress + ", ");
        }
    }

}
