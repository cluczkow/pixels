package com.luczkow.pixels;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.Date;
import java.util.Random;

/**
 * Created by Chris on 2/1/2016.
 */
class Artist {

    private String name;
    private Pictures pictures;

    private Artist() {}

    Artist(String name) {
        this.name = name;
        this.pictures = new Pictures(Constants.DIR_IN + "\\" + name);
    }

    void create() throws Exception {

        for (int n = 0; n < 50; n++) {

            Random rnd = new Random();

            Path path = pictures.rnd();
            if (path == null) {
                break;
            }
            BufferedImage image = ImageIO.read(new File(path.toString()));

            Palette palette;
            if (rnd.nextBoolean()) {
                palette = new Palette(ImageIO.read(new File(pictures.rnd().toString())));
            }
            else {
                palette = new Palette(image);
            }

            int[][] sortedPixels = PixelSorter.sort(image, PixelSorter.SortType.BRIGHTNESS, PixelSorter.SortDir.ASC);

            int w = image.getWidth();
            int h = image.getHeight();

            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, 0);
                }
            }

            int colorIndex;
            int current = 0;
            int total = w * h;
            Color[] colors = palette.getColors();
            for (int i = 0; i < sortedPixels.length; i++) {
                colorIndex = Math.min((int)((float)current / (float)total * colors.length), colors.length - 1);
                for (int j = 0; j < sortedPixels[i].length; j++) {
                    int row = sortedPixels[i][j] / w;
                    int col = sortedPixels[i][j] % w;
                    image.setRGB(col, row, colors[colorIndex].getRGB());
                }
                current += sortedPixels[i].length;
            }

            File file = new File(
                    Constants.DIR_OUT + "\\" + name + "\\" + String.valueOf(new Date().getTime()) + ".png");
            ImageIO.write(image, "png", file);

            System.out.println(n + file.getName());
        }
    }
}
