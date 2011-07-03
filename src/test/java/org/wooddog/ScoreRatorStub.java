package org.wooddog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 03-07-11
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
public class ScoreRatorStub implements ScoreRator {
    private static final List<String> rates = new ArrayList<String>();

    public int rate(String company, String content) {
        rates.add(rates.size() + "," + company + ", " + content);
        return rates.size() - 1;
    }

    public static List<String> getRates() {
        return rates;
    }
}
