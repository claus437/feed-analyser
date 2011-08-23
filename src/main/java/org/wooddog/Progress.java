package org.wooddog;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 23-08-11
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class Progress {
    private double progress;
    private int numberOfUnits;
    private int stepsPerUnit;

    public Progress() {
        progress = 0;
        numberOfUnits = 0;
        stepsPerUnit = 1;
    }

    public void setNumberOfUnits(int units) {
        this.numberOfUnits = units;
    }

    public void setStepsPerUnit(int steps) {
        this.stepsPerUnit = steps;
    }

    public void step() {
        double step;

        step = 100D / this.numberOfUnits;
        step = step / stepsPerUnit;

        progress += step;
    }

    public int getPercentDone() {
        return (int) Math.round(progress);
    }

    public void reset() {
        progress = 0;
        stepsPerUnit = 1;
    }

    public void done() {
        progress = 100;
    }
}
