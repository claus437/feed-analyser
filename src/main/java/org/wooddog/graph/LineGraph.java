package org.wooddog.graph;

import java.awt.*;
import java.awt.color.ColorSpace;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 23-08-11
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class LineGraph {
    private int width;
    private int height;
    private int[] data;
    private Color color;
    private int offsetX;
    private int offsetY;


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = (int) data[i];
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public void draw(Graphics2D graphics) {
        int resolutionX;
        int resolutionY;

        graphics.setColor(color);

        resolutionX = width / (data.length - 1);

        // TODO: clean up
        if ((high(data) - low(data)) == 0) {
            resolutionY = 0;
        } else {
            resolutionY = height / (high(data) - low(data));
        }

        Stroke stroke = graphics.getStroke();
        graphics.setStroke(new BasicStroke(3,2,1));
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGraph(graphics, resolutionX, resolutionY);
        graphics.setStroke(stroke);
    }

    public int getHighestValue() {
        return high(data);
    }

    public int getLowestValue() {
        return low(data);
    }

    public int getMiddleValue() {
        return ((high(data) - low(data)) / 2) + low(data);
    }

    public void drawGraph(Graphics2D graphics, int resolutionX, int resolutionY) {
        int x1;
        int x2;
        int y1;
        int y2;

        x1 = 0;
        for (int i = 0; i < data.length - 1; i++) {
            x2 = x1 + resolutionX;
            y1 = getY(data[i], resolutionY);
            y2 = getY(data[i + 1], resolutionY);

            draw(graphics, x1 + offsetX, y1, x2 + offsetX, y2);
            x1 = x1 + resolutionX;
        }
    }

    public int getY(int value, int resolution) {
        value = value - low(data);
        value = value * resolution;
        value = value * -1 + height;

        return value;
    }

    private int high(int[] data) {
        int top;

        top = Integer.MIN_VALUE;

        for (int value : data) {
            if (value > top) {
                top = value;
            }
        }

        return top;
    }

    private int low(int[] data) {
        int low;

        low = Integer.MAX_VALUE;

        for (int value : data) {
            if (value < low) {
                low = value;
            }
        }

        return low;
    }

    public void draw(Graphics2D graphics, int x, int y, int x1, int y1) {
        graphics.drawLine(x, y, x1, y1);
        System.out.println(x + " " + y + " " + x1 + " " + y1);
    }
}
