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
public class Artist {

    private Pictures pictures = new Pictures();

    public void create() throws Exception {

        for (int n = 0; n < 50; n++) {

            Random rnd = new Random();

            Path path = pictures.rnd();
            BufferedImage image = ImageIO.read(new File(path.toString()));

            Palette palette;
            if (rnd.nextBoolean()) {
                palette = new Palette(ImageIO.read(new File(pictures.rnd().toString())));
            }
            else {
                palette = new Palette(image);
            }

            int SLOT_COUNT = 766;
            int w = image.getWidth();
            int h = image.getHeight();

            int[] counts = new int[SLOT_COUNT];

            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    Color color = new Color(image.getRGB(x, y));
                    counts[color.getRed() + color.getGreen() + color.getBlue()]++;
                }
            }

            int[][] orderedPixels = new int[SLOT_COUNT][];
            for (int i = 0; i < SLOT_COUNT; i++) {
                orderedPixels[i] = new int[counts[i]];
            }

            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    Color color = new Color(image.getRGB(x, y));
                    int val = color.getRed() + color.getGreen() + color.getBlue();
                    orderedPixels[val][counts[val] - 1] = w * y + x;
                    counts[val]--;
                }
            }

            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, 0);
                }
            }

            int colorIndex;
            int current = 0;
            int total = w * h;
            Color[] colors = palette.getColors();
            for (int i = 0; i < SLOT_COUNT; i++) {
                colorIndex = Math.min((int)((float)current / (float)total * colors.length), colors.length - 1);
                for (int j = 0; j < orderedPixels[i].length; j++) {
                    int row = orderedPixels[i][j] / w;
                    int col = orderedPixels[i][j] % w;
                    image.setRGB(col, row, colors[colorIndex].getRGB());
                }
                current += orderedPixels[i].length;
            }

            File file = new File(Constants.DIR_OUT + "\\" + String.valueOf(new Date().getTime()) + "-" + path.getFileName() + ".png");
            ImageIO.write(image, "png", file);
        }
    }
}
