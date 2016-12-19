package com.luczkow;

import com.luczkow.pixels.Artist;
import com.luczkow.pixels.Composer;
import com.luczkow.pixels.Pictures;
import com.luczkow.pixels.Constants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;

import javax.imageio.ImageIO;

/**
 * Created by chris on 7/31/2016.
 */
public class Main {

	static Pictures pictures = new Pictures();
	
	public static void main(String[] args) throws Exception {

//		Composer composer = new Composer();
//		composer.compose(pictures);

		Artist artist = new Artist();
		artist.create();
	}
}
