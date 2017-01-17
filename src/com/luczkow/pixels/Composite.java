package com.luczkow.pixels;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chris luczkow on 1/16/2017.
 */
class Composite {

    private List<Composite> layers = new ArrayList<>();
    private BufferedImage canvas;
    private Artist artist;
    private int maxWidth;
    private int maxHeight;
    private Path path;

    private static final int LAYER_COUNT = 2;

    Composite(Artist artist, int depth) throws Exception {

        this.artist = artist;

        if (depth > 0) {
            for (int i = 0; i < LAYER_COUNT; i++) {
                Composite composite = new Composite(artist, depth - 1);
                layers.add(composite);

                maxWidth = composite.getMaxWidth();
                maxHeight = composite.getMaxHeight();
            }
        }
        else {
            this.path = artist.getPictures().rnd();
            readDimensions(path);
            System.out.println(path);
        }
    }

    BufferedImage paint(int width, int height) throws Exception {

        BufferedImage canvas;

        if (path != null) {
            canvas = ImageIO.read(new File(path.toString()));
            if (canvas.getWidth() < canvas.getHeight()) {
                canvas = ImageUtil.rotate(canvas);
            }

            if (canvas.getWidth() < width || canvas.getHeight() < height) {
                throw new Exception("Invalid layer size");
            }
        }
        else {
            canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (Composite layer : layers) {
                artist.paint(canvas, layer.paint(width, height));
                layer.close();
            }
        }

        return canvas;
    }

    void close() {
        this.canvas = null;
        for (Composite layer : layers) {
            layer.close();
        }
    }

    int getMaxWidth() {
        return this.maxWidth;
    }

    int getMaxHeight() {
        return this.maxHeight;
    }

    private void readDimensions(Path path) throws Exception {

        try (ImageInputStream inputStream = ImageIO.createImageInputStream(new FileInputStream(path.toFile()))) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(inputStream);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(inputStream);
                    this.maxWidth = Math.max(reader.getWidth(0), reader.getHeight(0));
                    this.maxHeight = Math.min(reader.getWidth(0), reader.getHeight(0));
                }
                finally {
                    reader.dispose();
                }
            }
        }
    }
}
