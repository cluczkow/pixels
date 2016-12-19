package com.luczkow.pixels;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 7/31/2016.
 */
public class Composer {

    public void compose(Pictures pictures) throws Exception {

        int count = 20;
        List<BufferedImage> bufferedImages = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String path = pictures.rnd().toString();
            System.out.println(path);
            bufferedImages.add(ImageIO.read(new File(path)));
        }

//        for (int i = 1; i < count; i++) {
//            for (int x = 0; x < bufferedImages.get(i).getWidth(); x++) {
//                for (int y = 0; y < bufferedImages.get(i).getHeight(); y++) {
//                    bufferedImages.get(0).setRGB(x, y, bufferedImages.get(i).getRGB(x, y));
//                    if (x % (i + 1) == 0) {
//                        try {
//                            bufferedImages.get(0).setRGB(x, y, bufferedImages.get(i).getRGB(x, y));
//                        }
//                        catch (Exception e) {
//                            String s = e.toString();
//                        }
//                    }
//                }
//            }
//        }

        for (int i = 0; i < bufferedImages.size(); i++) {
            System.out.println(bufferedImages.get(i).getWidth() + " - " + bufferedImages.get(i).getHeight());
            if (bufferedImages.get(i).getWidth() < bufferedImages.get(i).getHeight()) {
                bufferedImages.set(i, ImageUtil.rotate(bufferedImages.get(i)));
            }
        }

        for (int x = 0; x < bufferedImages.get(0).getWidth(); x++) {
            for (int y = 0; y < bufferedImages.get(0).getHeight(); y++) {
                int n = y % count;
                if (n > 0) {
                    try {
                        bufferedImages.get(0).setRGB(x, y, bufferedImages.get(n).getRGB(x, y));
                    }
                    catch (Exception e) {
                        String s = e.toString();
                    }
                }
            }
        }

        File file = new File(Constants.DIR_OUT + "\\saved.png");
        ImageIO.write(bufferedImages.get(0), "png", file);
    }
}
