package utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import panes.JErrorPane;

public class SortingNetworkGenerator {
    private static final class Comparator {
        int i1, i2;

        Comparator(int i1, int i2) {
            this.i1 = i1;
            this.i2 = i2;
        }

        @Override
        public String toString() {
            return i1 + ":" + i2;
        }

        @Override
        public int hashCode() {
            return (i1 << 16) + i2;
        }

        boolean overlaps(Comparator other) {
            return (this.i1 < other.i1 && other.i1 < this.i2) ||
                   (this.i1 < other.i2 && other.i2 < this.i2) ||
                   (other.i1 < this.i1 && this.i1 < other.i2) ||
                   (other.i1 < this.i2 && this.i2 < other.i2);
        }

        boolean hasSameInput(Comparator other) {
            return this.i1 == other.i1 ||
                   this.i1 == other.i2 ||
                   this.i2 == other.i1 ||
                   this.i2 == other.i2;
        }
    }

    final static int LIMIT = 20000;

    public static boolean verifyPythonVersionAndDialog() {
        // TODO: Remove this method
        return true;
    }

    private static int getMaxInput(List<Comparator> comparators) {
        int maxInput = 0;
        for (Comparator c : comparators) {
            if (c.i2 > maxInput) {
                maxInput = c.i2;
            }
        }
        return maxInput;
    }

    public static boolean encodeNetwork(Integer[] indices, String path) {
        List<Comparator> comparators = new ArrayList<>(indices.length / 2);
        for (int i = 1; i < indices.length; i += 2) {
            comparators.add(new Comparator(indices[i - 1], indices[i]));
        }

        int scale = 1;
        int xScale = scale * 35;
        int yScale = scale * 20;

        StringBuilder comparatorsSvg = new StringBuilder();
        double w = xScale;
        Map<Comparator, Double> group = new HashMap<>();
        for (Comparator c : comparators) {
            for (Comparator other : group.keySet()) {
                if (c.hasSameInput(other)) {
                    for (double pos : group.values()) {
                        if (pos > w) {
                            w = pos;
                        }
                    }
                    w += xScale;
                    group.clear();
                    break;
                }
            }

            double cx = w;
            for (Entry<Comparator, Double> entry : group.entrySet()) {
                Comparator other = entry.getKey();
                double otherPos = entry.getValue();
                if (otherPos >= cx && c.overlaps(other)) {
                    cx = otherPos + xScale / 3.0;
                }
            }

            int y0 = yScale + c.i1 * yScale;
            int y1 = yScale + c.i2 * yScale;
            comparatorsSvg.append("<circle cx='").append(cx).append("' cy='").append(y0).append("' r='3' style='stroke:black;stroke-width:1;fill=yellow'/>")
                          .append("<line x1='").append(cx).append("' y1='").append(y0).append("' x2='").append(cx).append("' y2='").append(y1).append("' style='stroke:black;stroke-width:1'/>")
                          .append("<circle cx='").append(cx).append("' cy='").append(y1).append("' r='3' style='stroke:black;stroke-width:1;fill=yellow'/>");
            group.put(c, cx);
        }

        StringBuilder linesSvg = new StringBuilder();
        w += xScale;
        int n = getMaxInput(comparators) + 1;
        for (int i = 0; i < n; i++) {
            int y = yScale + i * yScale;
            linesSvg.append("<line x1='0' y1='").append(y).append("' x2='").append(w).append("' y2='").append(y).append("' style='stroke:black;stroke-width:1'/>");
        }

        int h = (n + 1) * yScale;
        try (PrintWriter writer = new PrintWriter(path, "UTF-8")) {
            writer.write(
                "<?xml version='1.0' encoding='utf-8'?><!DOCTYPE svg>" +
			    "<svg width='" + w + "px' height='" + h + "px' xmlns='http://www.w3.org/2000/svg'>" +
			    comparatorsSvg +
			    linesSvg +
			    "</svg>"
            );
        } catch (Exception e) {
            JErrorPane.invokeErrorMessage(e, "Sorting Network Visualizer");
            return false;
        }
        return true;
    }

    public static String encodeNetworkAndDisplay(String name, Integer[] indices, int arrayLength) {
        String path = "network_" + name + "_" + arrayLength + ".svg";
        if (!encodeNetwork(indices, path)) {
            return null;
        }
        JOptionPane.showMessageDialog(null, "Successfully saved output to file \"" + path + "\"",
            "Sorting Network Visualizer", JOptionPane.INFORMATION_MESSAGE);
        File file = new File(path);
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
