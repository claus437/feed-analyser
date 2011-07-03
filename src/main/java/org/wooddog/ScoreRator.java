package org.wooddog;

import org.wooddog.domain.Scoring;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 02-07-11
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public interface ScoreRator {
    int rate(String company, String content);
}
