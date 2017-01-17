package com.luczkow.pixels;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by Chris on 2/1/2016.
 */
class Artist {

    private String id;
    private Pictures pictures;

    private Artist() {}

    Artist(String id) {
        this.id = id;
        this.pictures = new Pictures(Constants.DIR_IN + "\\" + id);
    }

    void create(Params.CompositionType type) throws Exception {

        long startTime = System.currentTimeMillis();
        String imageId = String.valueOf(new Date().getTime());
        System.out.println(imageId + " " + id);

        BufferedImage image;

        if (type == Params.CompositionType.STANDARD) {
            System.out.println("Composition.TYPE = STANDARD");
            image = createStandard();
        }
        else {
            System.out.println("Composition.TYPE = COMPOSITE");
            image = createComposite();
        }

        if (image != null) {
            File file = new File(
                    Constants.DIR_OUT + "\\" + imageId + "-" + id + ".jpg");
            ImageIO.write(image, "jpg", file);
            System.out.println((System.currentTimeMillis() - startTime) / 1000);
            System.out.println();
        }
    }

    BufferedImage paint(BufferedImage canvas, BufferedImage source) {

        int[][] sortedPixels = PixelSorter.sort(source, PixelSorter.SortType.BRIGHTNESS, PixelSorter.SortDir.DESC);
        canvas = paint(canvas, source, sortedPixels);

        return canvas;
    }

    Pictures getPictures() {

        return pictures;
    }

    private BufferedImage createStandard() throws Exception {

        Path path = pictures.rnd();
        if (path == null) {
            return null;
        }
        BufferedImage image = ImageIO.read(new File(path.toString()));

        Palette palette;
        Random rnd = new Random();
        if (rnd.nextBoolean())
        {
            System.out.println("Palette.OTHER_IMAGE");
            palette = new Palette(ImageIO.read(new File(pictures.rnd().toString())), 5, Params.PaletteSort.DESC);
        }
        else {
            System.out.println("Palette.SAME_IMAGE");
            palette = new Palette(image, 5, Params.PaletteSort.DESC);
        }

        int[][] sortedPixels = PixelSorter.sort(image, PixelSorter.SortType.BRIGHTNESS, PixelSorter.SortDir.DESC);

        return paint(image, palette, sortedPixels);
    }

    private BufferedImage createComposite() throws Exception {

        Composite composite = new Composite(this, 3);
        BufferedImage image = composite.paint(composite.getMaxWidth(), composite.getMaxHeight());

        int[][] sortedPixels = PixelSorter.sort(image, PixelSorter.SortType.BRIGHTNESS, PixelSorter.SortDir.DESC);
        Palette palette = new Palette(image, 5, Params.PaletteSort.DESC);
        return paint(image, palette, sortedPixels);
    }

    private BufferedImage paint(BufferedImage image, Palette palette, int[][] sortedPixels) {

        int w = image.getWidth();
        int h = image.getHeight();

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

        return image;
    }

    private BufferedImage paint(BufferedImage canvas, BufferedImage layer, int[][] sortedPixels) {

        int w = canvas.getWidth();
        int h = canvas.getHeight();

        int current = 0;
        int count = w * h / 2;
        for (int i = 0; i < sortedPixels.length; i++) {
            for (int j = 0; j < sortedPixels[i].length; j++) {
                int row = sortedPixels[i][j] / w;
                int col = sortedPixels[i][j] % w;
                canvas.setRGB(col, row, layer.getRGB(col, row));
            }
            current += sortedPixels[i].length;
            if (current > count) {
                break;
            }
        }

        return canvas;
    }
}
