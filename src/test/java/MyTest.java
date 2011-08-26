import org.junit.Test;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 19-08-11
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public class MyTest {
    String message;
    static double progress;



    @Test
    public void testIt() {
        StringBuffer buffer;
        message = "#FF10DD";
        int[] tones;

        buffer = new StringBuffer(message);
        buffer.delete(0, 1);

        tones = new int[3];
        for (int i = 0; i < tones.length; i++) {
            tones[i] = Integer.parseInt(buffer.substring(0, 2), 16);
            buffer.delete(0, 2);
        }

        new Color(tones[0], tones[1], tones[2]);
    }

    @Test
    public void test2() {
        DecimalFormat f = new DecimalFormat("#,##0.00");
        System.out.println(f.format(1000));
        System.out.println(f.format(100));
        System.out.println(f.format(10));
        System.out.println(f.format(1));
        System.out.println(f.format(0.1));
        System.out.println(f.format(0.10));
    }



}
