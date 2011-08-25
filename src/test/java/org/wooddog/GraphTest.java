package org.wooddog;

import org.junit.Test;
import org.wooddog.graph.Graph;
import org.wooddog.graph.LineGraph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: dencbr
 * Date: 23-08-11
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
public class GraphTest {

    @Test
    public void testGraph() throws Exception {
        Graph graph;
        LineGraph graph1;
        LineGraph graph2;
        LineGraph graph3;
        Window window;

        graph1 = new LineGraph();
        graph1.setData(new double[]{10, 50, 15, 25, 30, 49, 32, 1, 32, 23, 43, 10});
        graph1.setColor(new Color(200, 130, 130));

        graph2 = new LineGraph();
        graph2.setData(new double[]{0, 100, 10, 20, 50, 70, 60, 30, 24, 32, 65});
        graph2.setColor(new Color(130, 200, 130));

        graph3 = new LineGraph();
        graph3.setData(new double[]{19, 73, 20, 11, 55, 11, -10, 8, 73, 10, 11});
        graph3.setColor(new Color(130, 130, 200));

        graph = new Graph();
        graph.addGraph(graph1);
        graph.addGraph(graph2);
        graph.addGraph(graph3);
        graph.setHeight(200);
        graph.setWidth(750);
        graph.setColumns(41);
        graph.setRows(10);
        graph.setGridColor(Color.LIGHT_GRAY);

        window = new Window(graph);

        while (window.isShowing()) {
            Thread.sleep(100);
        }
    }

    @Test
    public void testCreateGif() throws Exception {
        BufferedImage image;
        Graph graph;
        LineGraph lineGraph;
        int width;
        int height;
        Graphics2D graphics;

        width = 500;
        height = 200;

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        lineGraph = new LineGraph();
        lineGraph.setColor(Color.RED);
        lineGraph.setData(new double[]{1,2,5,3,4});

        graph = new Graph();
        graph.addGraph(lineGraph);
        graph.setWidth(width);
        graph.setHeight(height);
        graph.setColumns(5);
        graph.setRows(5);
        graph.setGridColor(Color.LIGHT_GRAY);

        graphics = image.createGraphics();
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, width, height);
        graphics.setComposite(AlphaComposite.SrcOver);

        graph.draw(graphics);

        ImageIO.write(image, "png", new File("graph-red.png"));
        ImageIO.write(image, "gif", new File("graph.gif"));
    }


    public class Window extends JFrame {
        public Window(final Graph graph) {
            setTitle("Hello World");
            setBackground(Color.WHITE);
            setVisible(true);
            setSize(800, 400);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel() {
                @Override
                public void paint(Graphics g) {
                    graph.draw((Graphics2D) g);
                }
            };

            add(panel);
        }
    }
}
