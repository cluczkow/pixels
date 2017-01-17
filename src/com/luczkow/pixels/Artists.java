package com.luczkow.pixels;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by chris luczkow on 12/19/2016.
 */
class Artists {

    void create() throws Exception {

        Path path = Paths.get(Constants.DIR_IN);
        String[] dirs = new File(path.toString()).list((current, name) ->
            new File(current, name).isDirectory()
        );

        List<Artist> artists = new ArrayList<>();
        if (dirs != null) {
            for (String dir : dirs) {
                artists.add(new Artist(dir));
            }
        }

        Random rnd = new Random();

        for (int i = 0; i < 20; i++) {
            Artist artist = artists.get(rnd.nextInt(artists.size()));
            artist.create(Params.CompositionType.STANDARD);
            artist.create(Params.CompositionType.COMPOSITE);
        }
    }
}
