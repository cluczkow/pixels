package com.luczkow.pixels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Chris on 1/31/2016.
 */
public class Palette {

    private Color[] colors;

    public Palette(BufferedImage image) {

        Random rnd = new Random();

        final int min = 3;
        final int max = 20;

        colors = new Color[rnd.nextInt(max - min) + min];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(image.getRGB(rnd.nextInt(image.getWidth()), rnd.nextInt(image.getHeight())));
        }

//        if (rnd.nextBoolean()) {
            Arrays.sort(colors, new BrightnessComparator());
//        }
    }

    public Color[] getColors() {

        return colors;
    }

    public class BrightnessComparator implements Comparator<Color> {

        @Override
        public int compare(Color color1, Color color2) {
            return (color1.getRed() + color1.getGreen() + color1.getBlue()) -
                    (color2.getRed() + color2.getGreen() + color2.getBlue());
        }
    }}
