package org.wooddog.job.score;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 22-08-11
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
public class ScoreFinderTest {
    private ScoreFinder subject;


    @Before
    public void setup() {
        subject = new ScoreFinder();
    }

    @Test
    public void testHasScore() {
        subject.setKeywords(Arrays.asList("Java", "World"));
        Assert.assertEquals(1, subject.rate("Hello Java World"));
    }

    @Test
    public void testHasNoScore() {
        subject.setKeywords(Arrays.asList("Java", "World"));
        Assert.assertEquals(0, subject.rate("Bye .Net thing"));
    }

}
