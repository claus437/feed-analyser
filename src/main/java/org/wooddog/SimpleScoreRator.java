package org.wooddog;


public class SimpleScoreRator implements ScoreRator {
    public int rate(String company, String content) {
        return content.contains(company) ? 1 : 0;
    }
}
