package org.wooddog;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-07-11
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class ScoreRatorFactory {

    public static ScoreRator getScoreRator() {
        return create(Config.get(Config.FEEDANALYSER_SCORE_RATOR_IMPL));
    }

    private static ScoreRator create(String className) {
        ScoreRator rator;

        try {
            rator = (ScoreRator) Class.forName(className).newInstance();
        } catch (InstantiationException x) {
            throw new RuntimeException("unable to create instance of " + className + ", " + x.getMessage(), x);
        } catch (IllegalAccessException x) {
            throw new RuntimeException("unable to create instance of " + className + ", " + x.getMessage(), x);
        } catch (ClassNotFoundException x) {
            throw new RuntimeException(className + " not found, " + x.getMessage(), x);
        }

        return rator;
    }
}
