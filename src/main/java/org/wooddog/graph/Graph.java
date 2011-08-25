package org.wooddog.graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        drawGrid(graphics, 0, 0, width, height);

        for (LineGraph graph : graphList) {
            graph.setHeight(height);
            graph.setWidth(width);
            graph.setOffsetX(0);

            graph.draw(graphics);
        }
    }


    private void drawGrid(Graphics2D graphics, int x, int y, int width, int height) {
        double cellWidth;
        double cellHeight;
        int pos;

        cellWidth = (double) width / columns;
        cellHeight = (double) height / rows;

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
