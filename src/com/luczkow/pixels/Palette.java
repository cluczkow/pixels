package com.luczkow.pixels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by Chris on 1/31/2016.
 */
class Palette {

    private Color[] colors;

    Palette(BufferedImage image, int count, Params.PaletteSort sort) {

        Random rnd = new Random();

        colors = new Color[count];

        System.out.println("Palette.COLOR_COUNT = " + colors.length);

        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(image.getRGB(rnd.nextInt(image.getWidth()), rnd.nextInt(image.getHeight())));
        }

        switch (sort) {
            case ASC:
                System.out.println("Palette.SORT_ASC");
                Arrays.sort(colors, (a, b) ->
                        (a.getRed() + a.getGreen() + a.getBlue()) - (b.getRed() + b.getGreen() + b.getBlue()));
                break;
            case DESC:
                System.out.println("Palette.SORT_DESC");
                Arrays.sort(colors, (b, a) ->
                        (a.getRed() + a.getGreen() + a.getBlue()) - (b.getRed() + b.getGreen() + b.getBlue()));
                break;
            default:
                System.out.println("Palette.SORT_NONE");
        }
    }

    Color[] getColors() {

        return colors;
    }
}
