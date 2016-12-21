package com.luczkow.pixels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by Chris on 1/31/2016.
 */
class Palette {

    private Color[] colors;

    Palette(BufferedImage image) {

        Random rnd = new Random();

        final int min = 3;
        final int max = 20;

        colors = new Color[rnd.nextInt(max - min) + min];

        System.out.println("Palette.COLOR_COUNT = " + colors.length);

        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(image.getRGB(rnd.nextInt(image.getWidth()), rnd.nextInt(image.getHeight())));
        }

        int n = rnd.nextInt(4);
        if (n > 0) {
            System.out.println("Palette.SORTED = true");
            if (n > 1) {
                System.out.println("Palette.SORT_ASC");
                Arrays.sort(colors, (a, b) ->
                        (a.getRed() + a.getGreen() + a.getBlue()) - (b.getRed() + b.getGreen() + b.getBlue()));
            }
            else {
                System.out.println("Palette.SORT_DESC");
                Arrays.sort(colors, (b, a) ->
                        (a.getRed() + a.getGreen() + a.getBlue()) - (b.getRed() + b.getGreen() + b.getBlue()));
            }
        }
        else {
            System.out.println("Palette.SORTED = false");
        }
    }

    Color[] getColors() {

        return colors;
    }
}
