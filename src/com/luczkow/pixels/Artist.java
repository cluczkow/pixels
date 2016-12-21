package com.luczkow.pixels;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

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

    void create() throws Exception {

        long startTime = System.currentTimeMillis();
        String imageId = String.valueOf(new Date().getTime());
        System.out.println(id + " " + imageId);

        BufferedImage image;

        Random rnd = new Random();
        if (rnd.nextBoolean()) {
            System.out.println("CompositionType.STANDARD");
            image = createStandard();
        }
        else {
            System.out.println("CompositionType.COMPOSITE");
            image = createComposite();
        }

        if (image != null) {
//            File file = new File(
//                    Constants.DIR_OUT + "\\" + id + "\\" + imageId + ".png");
            File file = new File(
                    Constants.DIR_OUT + "\\" + imageId + "-" + id + ".png");
            ImageIO.write(image, "png", file);
            System.out.println((System.currentTimeMillis() - startTime) / 1000);
            System.out.println();
        }
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
            palette = new Palette(ImageIO.read(new File(pictures.rnd().toString())));
        }
        else {
            System.out.println("Palette.SAME_IMAGE");
            palette = new Palette(image);
        }

        int[][] sortedPixels = PixelSorter.sort(image, PixelSorter.SortType.BRIGHTNESS, PixelSorter.SortDir.ASC);

        return paint(image, palette, sortedPixels);
    }

    private BufferedImage createComposite() throws Exception {

        int count = 10;
        List<BufferedImage> layers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String path = pictures.rnd().toString();
            System.out.println(path);
            layers.add(ImageIO.read(new File(path)));
        }

        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i).getWidth() < layers.get(i).getHeight()) {
                layers.set(i, ImageUtil.rotate(layers.get(i)));
            }
        }

        BufferedImage canvas = layers.get(0);
        Palette palette = new Palette(canvas);

        for (BufferedImage layer : layers) {
            int[][] sortedPixels = PixelSorter.sort(layer, PixelSorter.SortType.BRIGHTNESS, PixelSorter.SortDir.ASC);
            layer = paint(layer, palette, sortedPixels);
            if (!layer.equals(canvas)) {
                paint(canvas, layer, sortedPixels);
            }
        }

        return canvas;
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
