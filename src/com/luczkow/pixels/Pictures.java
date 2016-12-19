package com.luczkow.pixels;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

/**
 * Created by chris on 7/31/2016.
 */
public class Pictures {

	private final static int SIZE_ID = 12;
	
	public Path rnd() throws Exception {
		
		Random rnd = new Random();
		Path path = Paths.get(Constants.DIR_IN);
		String[] files = new File(path.toString()).list();

		return Paths.get(path + "\\" + files[rnd.nextInt(files.length)]);
	}
}
