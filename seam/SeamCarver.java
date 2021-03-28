import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
    private double[][] energy;
    private int[][] pixels;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("Null argument");
        energy = new double[picture.height()][picture.width()];
        pixels = new int[picture.height()][picture.width()];
        for (int i = 0; i < picture.height(); ++i) {
            for (int j = 0; j < picture.width(); ++j) {
                pixels[i][j] = picture.getRGB(j, i);
            }
        }
        for (int i = 0; i < picture.height(); ++i) {
            for (int j = 0; j < picture.width(); ++j) {
                energy[i][j] = energy(j, i);
            }
        }
    }

    // current picture
    public Picture picture() {
        Picture picture = new Picture(pixels[0].length, pixels.length);
        for (int i = 0; i < pixels.length; ++i) {
            for (int j = 0; j < pixels[i].length; ++j) {
                picture.setRGB(j, i, pixels[i][j]);
            }
        }
        return picture;
    }

    // width of current picture
    public int width() {
        return energy[0].length;
    }

    // height of current picture
    public int height() {
        return energy.length;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (y < 0 || y >= height())
            throw new IllegalArgumentException(
                    "row index must be between 0 and " + (height() - 1) + ": " + y);
        if (x < 0 || x >= width())
            throw new IllegalArgumentException(
                    "column index must be between 0 and " + (width() - 1) + ": " + x);
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) return 1000;
        double answer = 0;
        int[] left = new int[3];
        int[] right = new int[3];
        int[] upper = new int[3];
        int[] lower = new int[3];

        int leftRGB = pixels[y][x - 1];
        left[0] = (leftRGB >> 16) & 0xFF;
        left[1] = (leftRGB >> 8) & 0xFF;
        left[2] = leftRGB & 0xFF;

        int rightRGB = pixels[y][x + 1];
        right[0] = (rightRGB >> 16) & 0xFF;
        right[1] = (rightRGB >> 8) & 0xFF;
        right[2] = rightRGB & 0xFF;

        int upperRGB = pixels[y + 1][x];
        upper[0] = (upperRGB >> 16) & 0xFF;
        upper[1] = (upperRGB >> 8) & 0xFF;
        upper[2] = upperRGB & 0xFF;

        int lowerRGB = pixels[y - 1][x];
        lower[0] = (lowerRGB >> 16) & 0xFF;
        lower[1] = (lowerRGB >> 8) & 0xFF;
        lower[2] = lowerRGB & 0xFF;

        for (int i = 0; i < 3; i++) {
            answer += (left[i] - right[i]) * (left[i] - right[i]) + (upper[i] - lower[i]) * (
                    upper[i] - lower[i]);
        }
        return Math.sqrt(answer);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] answer;
        answer = findVerticalSeam();
        transpose();
        return answer;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int h = energy.length;
        int w = energy[0].length;
        int[] answer = new int[h];
        double[][] d = new double[h][w];
        int[][] p = new int[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (i == 0) {
                    d[i][j] = energy[i][j];
                }
                else {
                    d[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        for (int i = 1; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (j == 0) {
                    if (w > 1) {
                        if (d[i - 1][j] < d[i - 1][j + 1]) {
                            d[i][j] = d[i - 1][j] + energy[i][j];
                            p[i][j] = j;
                        }
                        else {
                            d[i][j] = d[i - 1][j + 1] + energy[i][j];
                            p[i][j] = j + 1;
                        }
                    }
                    else {
                        d[i][j] = d[i - 1][j] + energy[i][j];
                        p[i][j] = j;
                    }
                }
                else if (j == w - 1) {
                    if (d[i - 1][j] < d[i - 1][j - 1]) {
                        d[i][j] = d[i - 1][j] + energy[i][j];
                        p[i][j] = j;
                    }
                    else {
                        d[i][j] = d[i - 1][j - 1] + energy[i][j];
                        p[i][j] = j - 1;
                    }
                }
                else {
                    if (d[i - 1][j - 1] <= d[i - 1][j] && d[i - 1][j - 1] <= d[i - 1][j + 1]) {
                        d[i][j] = d[i - 1][j - 1] + energy[i][j];
                        p[i][j] = j - 1;
                    }
                    else if (d[i - 1][j] <= d[i - 1][j - 1] && d[i - 1][j] <= d[i - 1][j + 1]) {
                        d[i][j] = d[i - 1][j] + energy[i][j];
                        p[i][j] = j;
                    }
                    else {
                        d[i][j] = d[i - 1][j + 1] + energy[i][j];
                        p[i][j] = j + 1;
                    }
                }
            }
        }
        double min = Double.POSITIVE_INFINITY;
        int minIndex = 0;
        for (int j = 0; j < w; ++j) {
            if (d[h - 1][j] <= min) {
                min = d[h - 1][j];
                minIndex = j;
            }
        }
        for (int i = h - 1; i >= 0; --i) {
            answer[i] = minIndex;
            minIndex = p[i][minIndex];
        }

        return answer;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("Null argument");
        if (seam.length != energy[0].length) throw new IllegalArgumentException("Wrong seam size");
        for (int i = 0; i < seam.length; ++i) {
            if (seam[i] < 0 || seam[i] >= energy.length)
                throw new IllegalArgumentException("Wrong seam");
        }
        int iter = seam[0];
        for (int i = 1; i < seam.length; ++i) {
            if (Math.abs(seam[i] - iter) > 1) throw new IllegalArgumentException("Wrong seam");
            iter = seam[i];
        }
        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("Null argument");
        if (seam.length != energy.length) throw new IllegalArgumentException("Wrong seam size");
        for (int i = 0; i < seam.length; ++i) {
            if (seam[i] < 0 || seam[i] >= energy[0].length)
                throw new IllegalArgumentException("Wrong seam");
        }
        int iter = seam[0];
        for (int i = 1; i < seam.length; ++i) {
            if (Math.abs(seam[i] - iter) > 1) throw new IllegalArgumentException("Wrong seam");
            iter = seam[i];
        }
        for (int i = 0; i < energy.length; ++i) {
            double[] temp = energy[i];
            energy[i] = new double[temp.length - 1];
            System.arraycopy(temp, 0, energy[i], 0, seam[i]);
            System.arraycopy(temp, seam[i] + 1, energy[i], seam[i], temp.length - seam[i] - 1);
            int[] tempPixels = pixels[i];
            pixels[i] = new int[tempPixels.length - 1];
            System.arraycopy(tempPixels, 0, pixels[i], 0, seam[i]);
            System.arraycopy(tempPixels, seam[i] + 1, pixels[i], seam[i],
                             tempPixels.length - seam[i] - 1);
        }

        for (int i = 0; i < energy.length; ++i) {
            if (seam[i] < energy[i].length) energy[i][seam[i]] = energy(seam[i], i);
            if (seam[i] - 1 >= 0) energy[i][seam[i] - 1] = energy(seam[i] - 1, i);
        }
    }

    private void transpose() {
        int m = energy.length;
        int n = energy[0].length;
        double[][] energyCopy = energy;
        int[][] pixelsCopy = pixels;
        energy = new double[n][m];
        pixels = new int[n][m];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                energy[x][y] = energyCopy[y][x];
                pixels[x][y] = pixelsCopy[y][x];
            }
        }
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        Picture picture = new Picture("6x5.png");
        SeamCarver seamCarver = new SeamCarver(picture);
        StdOut.println(seamCarver.height());
        StdOut.println(seamCarver.width());
        // seamCarver.picture().show();
        // StdOut.println(seamCarver.energy(1, 2));
        // StdOut.println(picture.get(1, 2).getRed());
        // StdOut.println(picture.get(1, 2).getGreen());
        // StdOut.println(picture.get(1, 2).getBlue());
        // for (int i = 0; i < 100; ++i) {
        //   seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        //}
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        StdOut.println(seamCarver.height());
        StdOut.println(seamCarver.width());
        // seamCarver.picture().show();
    }


}
