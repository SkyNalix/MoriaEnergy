package dev.moriaenergy;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
    public static Map parse( String file ) throws Exception {
        Scanner scan = new Scanner(new File("src/main/resources/" + file + ".nrg"));
        String str = scan.nextLine();
        String[] options = str.split( " " );
        int H = Integer.parseInt(options[0]);
        int W = Integer.parseInt(options[1]);

        Constructor<?> c;
        if(options[2].equals( "S" ))
            c = Square.class.getConstructors()[0];
        else
            c = Hexagon.class.getConstructors()[0];

        Map map = new Map(H,W);

        int ligneY = 0;
        while(scan.hasNextLine()){
            str = scan.nextLine();
            convertLine(map, str , ligneY, c);
            ligneY++;
        }

        // propagation de l'energie
        for( Cell[] cells : map.array) {
            for( Cell cell : cells ) {
                if(cell != null && cell.tile != null && cell.tile != Tile.S)
                    cell.setEnabled(map, true );
            }
        }
        map.updateWifi();
        return map;
    }

    public static void convertLine( Map map, String str, int index, Constructor<?> c) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        String[] tab = str.split(" ");
        int counter = 0;
        String tile_str = tab[0];
        List<Integer> rotations = new ArrayList<>();
        for( int i = 1; i < tab.length; i++ ) {
            String s = tab[i];
            switch( s ) {
                case ".","S","W","L" -> {
                    Tile tile = null;
                    if(!tile_str.equals( "." )) {
                        tile = Tile.valueOf( tile_str );
                    }
                    map.array[index][counter] = (Cell)
                              c.newInstance( tile, counter, index, rotations );
                    tile_str = s;
                    rotations = new ArrayList<>();
                    counter++;
                }
                default -> {
                    int val = Integer.parseInt( s );
                    rotations.add( val );
                }
            }
        }

        Tile tile = null;
        if(!tile_str.equals( "." )) {
            tile = Tile.valueOf( tile_str );
        }
        map.array[index][counter] = (Cell)
                  c.newInstance( tile, counter, index, rotations );
    }

}
