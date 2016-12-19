package com.luczkow.pixels;

import java.awt.image.BufferedImage;

/**
 * Created by chris luczkow on 12/11/2016.
 */
public class ImageUtil {

    static public BufferedImage rotate(BufferedImage in) {

        BufferedImage out = new BufferedImage(in.getHeight(), in.getWidth(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < out.getWidth(); x++) {
            for (int y = 0; y < out.getHeight(); y++) {
                out.setRGB(x, y, in.getRGB(y, x));
            }
        }

        return out;
    }
}
