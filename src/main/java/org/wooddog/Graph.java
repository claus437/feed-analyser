package org.wooddog;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import static org.wooddog.XLabel.Align;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 23-08-11
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class Graph {
    private List<LineGraph> graphList;
    private int columns;
    private int rows;
    private int height;
    private int width;
    private Color gridColor;

    public Graph() {
        graphList = new ArrayList<LineGraph>();
        columns = 11;
        rows = 10;
        height = 400;
        width = 600;
        gridColor = Color.GRAY;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Color getGridColor() {
        return gridColor;
    }

    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    public void addGraph(LineGraph graph) {
        graphList.add(graph);
    }

    public void draw(Graphics2D graphics) {
        XLabel xlabel;
        int gridOffset;

        xlabel = new XLabel(height);
        for (LineGraph graph : graphList) {
            xlabel.addLabel(Integer.toString(graph.getHighestValue()), Align.TOP, graph.getColor());
            xlabel.addLabel(Integer.toString(graph.getMiddleValue()), Align.MIDDLE, graph.getColor());
            xlabel.addLabel(Integer.toString(graph.getLowestValue()), Align.BOTTOM, graph.getColor());
        }

        xlabel.draw(graphics);

        gridOffset = xlabel.getWidth() + 5;

        drawGrid(graphics, gridOffset, 0, width - gridOffset, height);

        for (LineGraph graph : graphList) {
            graph.setHeight(height);
            graph.setWidth(width - gridOffset);
            graph.setOffsetX(gridOffset);

            graph.draw(graphics);
        }
    }


    private void drawGrid(Graphics2D graphics, int x, int y, int width, int height) {
        double cellWidth;
        double cellHeight;
        int pos;

        cellWidth = (double) width / columns;
        cellHeight = (double) height / rows;
        /*
        graphics.drawLine(x, 0, x, 40);
        graphics.drawLine(x + width, 0, x + width, 40);
        graphics.drawLine(0, y, 40, y);
        graphics.drawLine(0, y + height, 40, y + height);
        */
        graphics.setColor(gridColor);

        pos = 0;
        for (double i = x; pos < width + x; i = i + cellWidth) {
            pos = (int) Math.round(i);
            graphics.drawLine(pos, y, pos, y + height);
        }

        pos = 0;
        for (double i = y; pos < height + y; i = i + cellHeight) {
            pos = (int) Math.round(i);
            graphics.drawLine(x, pos, x + width, pos);
        }
    }
}
