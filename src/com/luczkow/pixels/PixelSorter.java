package com.luczkow.pixels;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by chris luczkow on 12/18/2016.
 */
class PixelSorter {

    private int slotCount;

    enum SortType {
        BRIGHTNESS
    }

    enum SortDir {
        ASC,
        DESC
    }

    static int[][] sort(BufferedImage image, SortType sortType, SortDir sortDir) {

        int[] counts = getValueCounts(image, sortType, sortDir);
        int[][] slots = allocateSlots(counts);

        fillSlots(slots, counts, image, sortDir);

        return slots;
    }

    static private int[] getValueCounts(BufferedImage image, SortType sortType, SortDir sortDir) {

        int slotCount = getSlotCount(sortType);
        int[] counts = new int[slotCount];

        int w = image.getWidth();
        int h = image.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Color color = new Color(image.getRGB(x, y));
                if (sortDir == SortDir.ASC) {
                    counts[getValue(sortType, color)]++;
                }
                else {
                    counts[slotCount - getValue(sortType, color) - 1]++;
                }
            }
        }

        return counts;
    }

    static private int[][] allocateSlots(int[] counts) {

        int[][] slots = new int[counts.length][];
        for (int i = 0; i < counts.length; i++) {
            slots[i] = new int[counts[i]];
        }

        return slots;
    }

    static private void fillSlots(int[][] slots, int[] counts, BufferedImage image, SortDir sortDir) {

        int w = image.getWidth();
        int h = image.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Color color = new Color(image.getRGB(x, y));
                int val = color.getRed() + color.getGreen() + color.getBlue();
                if (sortDir == SortDir.DESC) {
                    val = counts.length - val - 1;
                }
                slots[val][counts[val] - 1] = w * y + x;
                counts[val]--;
            }
        }
    }

    static private int getSlotCount(SortType sortType) {

        switch (sortType) {
            case BRIGHTNESS:
                return 766;
        }

        return 0;
    }

    static private int getValue(SortType sortType, Color color) {

        switch (sortType) {
            case BRIGHTNESS:
                return color.getRed() + color.getGreen() + color.getBlue();
        }

        return 0;
    }
}
