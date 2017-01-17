package com.luczkow.pixels;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Created by chris luczkow on 1/14/2017.
 */
class Compositor {

    private Pictures pictures;

    Compositor(Pictures pictures, int depth) {
        this.pictures = pictures;
    }

    BufferedImage create(Pictures pictures) throws Exception {

        Path path = pictures.rnd();

        try (ImageInputStream inputStream = ImageIO.createImageInputStream(new FileInputStream(path.toFile()))) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(inputStream);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(inputStream);
                    int width = reader.getWidth(0);
                    int height = reader.getHeight(0);
                }
                finally {
                    reader.dispose();
                }
            }
        }

        return null;
    }
}
