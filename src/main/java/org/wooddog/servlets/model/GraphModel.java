package org.wooddog.servlets.model;

import org.wooddog.domain.Graph;
import org.wooddog.domain.History;
import org.wooddog.graph.LineGraph;
import org.wooddog.servlets.ConfigServlet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 27-08-11
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */
public class GraphModel implements ModelChangeListener {
    private static int FILE_COUNT;
    private ModelChangeNotifier notifier;
    private boolean refresh;
    private File base;
    private List<History> historyList;
    private Map<String, String> graphColors;
    private Map<String, Graph> graphs;
    private int width;
    private int height;

    public GraphModel() {
        base = new File(ConfigServlet.getRealPath("img"));

        graphColors = new HashMap<String, String>();
        graphs = new HashMap<String, Graph>();

        notifier = new ModelChangeNotifier(this);
        notifier.addModelChangeListener(this);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        notifier.notifyListeners(this.width, width);
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        notifier.notifyListeners(this.height, height);
        this.height = height;
    }

    public List<History> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<History> historyList) {
        notifier.notifyListeners(this.historyList, historyList);
        this.historyList = historyList;
    }

    public void setGraphColor(String graph, String color) {
        notifier.notifyListeners(graphColors.get(graph), color);
        graphColors.put(graph, color);
    }

    public Map<String, Graph> getGraphs() {
        if (refresh) {
            refresh();
            refresh = false;
        }

        return graphs;
    }

    public void refresh() {
        BufferedImage image;
        Graph graph;

        image = createImage();
        graph = createGraph(History.Field.SCORE, image);
        graphs.put(History.Field.SCORE.name(), graph);

        image = createImage();
        graph = createGraph(History.Field.SHARE, image);
        graphs.put(History.Field.SHARE.name(), graph);

        image = createImage();
        graph = createGraph(History.Field.RECOMMENDATION, image);
        graphs.put(History.Field.RECOMMENDATION.name(), graph);
    }

    private Graph createGraph(History.Field field, BufferedImage image) {
        LineGraph lineGraph;
        Graph graph;
        double[] data;
        File file;
        Color color;

        color = getGraphColor(graphColors.get(field.name()));

        data = History.get(field, historyList);
        file = generateFileName("png");

        lineGraph = new LineGraph();
        lineGraph.setColor(color);
        lineGraph.setData(data);
        lineGraph.setWidth(image.getWidth());
        lineGraph.setHeight(image.getHeight());
        lineGraph.draw(image.createGraphics());

        writeImage(image, "png", file);

        graph = new Graph();
        graph.setColor(color);
        graph.setImage(file.getName());
        graph.setMaxValue(lineGraph.getHighestValue());
        graph.setMidValue(lineGraph.getMiddleValue());
        graph.setMinValue(lineGraph.getLowestValue());

        return graph;
    }

    private BufferedImage createImage() {
        BufferedImage image;
        Graphics2D graphics;

        image = new BufferedImage(width, height,  BufferedImage.TYPE_INT_ARGB);

        graphics = image.createGraphics();
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, width, height);
        graphics.setComposite(AlphaComposite.SrcOver);

        return image;
    }

    private synchronized File generateFileName(String suffix) {
        FILE_COUNT ++;
        return new File(base,  System.currentTimeMillis() + "-" + "GRAPH@" + FILE_COUNT + ".png");
    }

    private void writeImage(BufferedImage image, String type, File file) {
        try {
            System.out.println("writing image " + file.getCanonicalPath());
            ImageIO.write(image, type, file);
        } catch (IOException x) {
            throw new RuntimeException("unable to store file " + file.getAbsolutePath());
        }
    }


    private Color getGraphColor(String color) {
        StringBuffer hexColor;
        int[] tones;

        tones = new int[3];
        hexColor = new StringBuffer(color);

        hexColor.delete(0, 1);

        for (int i = 0; i < tones.length; i++) {
            tones[i] = Integer.parseInt(hexColor.substring(0, 2), 16);
            hexColor.delete(0, 2);
        }

        return new Color(tones[0], tones[1], tones[2]);
    }

    @Override
    public void onChange(Object src, Object oldValue, Object newValue) {
        refresh = true;
    }
}

