import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;

/**
 * Seam-carving is a content-aware image resizing technique where the image is reduced in size by one pixel of height (or width) at a time.
 * A vertical seam in an image is a path of pixels connected from the top to the bottom with one pixel in each row.
 *
 * @Author Igor.Zimenco
 */
public class SeamCarver {

    private int[][] colors;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        colors = new int[picture.width()][picture.height()];
        populateColorsArray(picture);
    }

    // current pictures
    public Picture picture() {
        Picture picture = new Picture(colors.length, colors[0].length);
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[0].length; j++) {
                Color color = new Color(this.colors[i][j]);
                picture.set(i, j, color);
            }
        }
        return picture;
    }

    // width of current picture
    public int width() {
        return this.colors.length;
    }

    // height of current picture
    public int height() {
        return this.colors[0].length;
    }

    // energy of pixel at column x and row y
    public double energy(int col, int row) {
        if (col < 0 || col > this.width() - 1 || row < 0 || row > this.height() - 1) {
            throw new IllegalArgumentException();
        }
        if (col == 0 || col == this.width() - 1 || row == 0 || row == this.height() - 1) {
            return 1000.0;
        } else {
            int deltaXRed = red(colors[col - 1][row]) - red(colors[col + 1][row]);
            int deltaXGreen = green(colors[col - 1][row]) - green(colors[col + 1][row]);
            int deltaXBlue = blue(colors[col - 1][row]) - blue(colors[col + 1][row]);

            int deltaYRed = red(colors[col][row - 1]) - red(colors[col][row + 1]);
            int deltaYGreen = green(colors[col][row - 1]) - green(colors[col][row + 1]);
            int deltaYBlue = blue(colors[col][row - 1]) - blue(colors[col][row + 1]);

            return Math.sqrt(Math.pow(deltaXRed, 2) + Math.pow(deltaXBlue, 2) + Math.pow(deltaXGreen, 2) + Math.pow(deltaYRed, 2) + Math.pow(deltaYBlue, 2) + Math.pow(deltaYGreen, 2));
        }

    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        this.colors = transpose(this.colors);
        int[] seam = findVerticalSeam();
        this.colors = transpose(this.colors);
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        int n = this.width() * this.height();
        double[] distTo = new double[n];
        int[] edgeTo = new int[n];

        int[] seam = new int[this.height()];
        Arrays.fill(distTo, 0, this.width(), 0.0);
        Arrays.fill(distTo, this.width(), this.width() * this.height(), Double.POSITIVE_INFINITY);

        // compute shortest path from to to bottom of the image
        for (int index1d = 0; index1d < this.height() * this.width(); index1d++) {
            int col = index1d % this.width();
            int row = (index1d / this.width()) % this.height();

            if (col - 1 >= 0 && row + 1 < this.height()) {
                relax(convert2DIndexTo1D(col, row), col - 1, row + 1, distTo, edgeTo);
            }

            if (row + 1 < this.height()) {
                relax(convert2DIndexTo1D(col, row), col, row + 1, distTo, edgeTo);
            }

            if (row + 1 < this.height() && col + 1 < this.width()) {
                relax(convert2DIndexTo1D(col, row), col + 1, row + 1, distTo, edgeTo);
            }
        }

        // find min dist in last row
        double min = Double.POSITIVE_INFINITY;
        int indexOfMin = -1;
        for (int col = 0; col < this.width(); col++) {
            int index = col + this.width() * (this.height() - 1);
            if (distTo[index] < min) {
                indexOfMin = index;
                min = distTo[index];
            }
        }

        // find seam index
        for (int row = 0; row < this.height(); row++) {
            int y = this.height() - row - 1;
            int x = indexOfMin - y * this.width();
            seam[this.height() - 1 - row] = x;
            indexOfMin = edgeTo[indexOfMin];
        }

        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (this.height() <= 1) throw new IllegalArgumentException();
        if (seam == null) throw new IllegalArgumentException();
        if (seam.length != this.width()) throw new IllegalArgumentException();

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > this.height() - 1)
                throw new IllegalArgumentException();
            if (i < this.width() - 1 && Math.pow(seam[i] - seam[i + 1], 2) > 1)
                throw new IllegalArgumentException();
        }

        int[][] updatedColor = new int[this.width()][this.height() - 1];
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] == 0) {
                System.arraycopy(this.colors[i], seam[i] + 1, updatedColor[i], 0, this.height() - 1);
            } else if (seam[i] == this.height() - 1) {
                System.arraycopy(this.colors[i], 0, updatedColor[i], 0, this.height() - 1);
            } else {
                System.arraycopy(this.colors[i], 0, updatedColor[i], 0, seam[i]);
                System.arraycopy(this.colors[i], seam[i] + 1, updatedColor[i], seam[i], this.height() - seam[i] - 1);
            }

        }
        this.colors = updatedColor;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        this.colors = transpose(this.colors);
        removeHorizontalSeam(seam);
        this.colors = transpose(this.colors);
    }

    private int[][] transpose(int[][] origin) {
        if (origin == null) throw new NullPointerException();
        if (origin.length < 1) throw new IllegalArgumentException();
        int[][] result = new int[origin[0].length][origin.length];
        for (int i = 0; i < origin[0].length; i++) {
            for (int j = 0; j < origin.length; j++) {
                result[i][j] = origin[j][i];
            }
        }
        return result;
    }

    private void relax(int index1d, int col, int row, double[] distTo, int[] edgeTo) {
        int v = index1d, w = convert2DIndexTo1D(col, row);

        if (distTo[w] > distTo[v] + energy(col, row)) {
            distTo[w] = distTo[v] + energy(col, row);
            edgeTo[w] = v;
        }
    }

    private void populateColorsArray(Picture picture) {
        for (int i = 0; i < this.width(); i++) {
            for (int j = 0; j < this.height(); j++) {
                colors[i][j] = picture.get(i, j).getRGB();
            }
        }
    }

    private int convert2DIndexTo1D(int x, int y) {
        return y * this.width() + x;
    }

    private int red(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    private int green(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    private int blue(int rgb) {
        return (rgb >> 0) & 0xFF;
    }
}