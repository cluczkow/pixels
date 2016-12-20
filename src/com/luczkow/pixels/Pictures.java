package com.luczkow.pixels;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by chris on 7/31/2016.
 */
class Pictures {

	private String path;

	private Pictures() {}

	Pictures(String path) {
		this.path = path;
	}

	Path rnd() throws Exception {

		Random rnd = new Random();
		String[] files = new File(path).list();

		if (files != null && files.length > 0) {
			return Paths.get(path + "\\" + files[rnd.nextInt(files.length)]);
		}
		else {
			return null;
		}
	}
}
