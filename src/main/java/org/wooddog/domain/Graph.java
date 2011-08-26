package org.wooddog.domain;

import java.awt.Color;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 26-08-11
 * Time: 10:29
 * To change this template use File | Settings | File Templates.
 */
public class Graph {
    private String image;
    private double minValue;
    private double maxValue;
    private double midValue;
    private Color Color;
    private String hexColor;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMidValue() {
        return midValue;
    }

    public void setMidValue(double midValue) {
        this.midValue = midValue;
    }

    public Color getColor() {
        return Color;
    }

    public void setColor(Color color) {
        Color = color;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }
}
