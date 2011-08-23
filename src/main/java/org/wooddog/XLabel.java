package org.wooddog;

import javax.tools.ToolProvider;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 23-08-11
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
public class XLabel {
    public enum Align{TOP, MIDDLE, BOTTOM}
    private List<Label> labelList;
    private int width;
    private int height;


    public XLabel(int height) {
        this.labelList = new ArrayList<Label>();
        this.height = height;
    }

    public void draw(Graphics2D graphics) {
        int x;
        int y;
        int top;
        int bottom;
        int middle;
        int middleHeight;
        int bottomHeight;

        middleHeight = 0;
        bottomHeight = 0;

        for (Label label : labelList) {
            label.compute(graphics);
            if (label.align == Align.MIDDLE) {
                middleHeight += label.getHeight();
            }

            if (label.align == Align.BOTTOM) {
                bottomHeight += label.getHeight();
            }
        }

        middle = (height / 2) - (middleHeight / 2);
        middle = middle - 5;

        bottom = height - bottomHeight;

        width = findWidth();

        top = 0;
        y = 0;

        for (Label label : labelList) {
            switch (label.align) {
                case TOP:
                    top += label.getHeight();
                    y = top;
                    break;

                case BOTTOM:
                    bottom += label.getHeight();
                    y = bottom;
                    break;

                case MIDDLE:
                    middle += label.getHeight();
                    y = middle;
                    break;
            }

            x = width - label.getWidth();

            graphics.setColor(label.color);
            graphics.drawString(label.text, x, y);
        }
    }

    public int getWidth() {
        return width;
    }

    public void addLabel(String text, Align alignment, Color color) {
        Label label;

        label = new Label();
        label.text = text;
        label.align = alignment;
        label.color = color;

        labelList.add(label);
    }

    private int findWidth() {
        int widest;
        int width;

        widest = Integer.MIN_VALUE;

        for (Label label : labelList) {
            width = label.getWidth();
            if (width > widest) {
                widest = width;
            }
        }

        return widest;
    }


    private class Label {
        String text;
        Align align;
        Color color;
        Rectangle2D bound;

        void compute(Graphics2D graphics) {
            FontMetrics metrics;

            metrics = graphics.getFontMetrics();
            bound = metrics.getStringBounds(text, graphics);
        }

        int getWidth() {
            return (int) Math.ceil(bound.getWidth());
        }

        int getHeight() {
            return (int) Math.ceil(bound.getHeight());
        }
    }
}
