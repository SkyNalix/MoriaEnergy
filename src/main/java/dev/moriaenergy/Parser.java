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
        String form;
        if(options[2].equals( "S" )) {
            c = Square.class.getConstructors()[0];
            form = "SQUARE";
        } else {
            c = Hexagon.class.getConstructors()[0];
            form = "HEX";
        }

        Map map = new Map(H,W);

        int y = 0;
        while(scan.hasNextLine()){
            str = scan.nextLine();
            convertLine(map, str , y, c, form);
            y++;
        }
        return map;
    }

    public static void convertLine( Map map, String str, int index, Constructor<?> c, String form ) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        String[] tab = str.split(" ");
        int counter = 0;
        List<Integer> rotations = new ArrayList<>();
        for( String s : tab ) {
            switch( s ) {
                case "." -> {
                    map.array[index][counter] = (Cell)
                              c.newInstance( null, counter, index, rotations );
                    rotations.clear();
                    counter++;
                }
                case "S" -> {
                    Tile tile = Tile.valueOf(form+"_S");
                    map.array[index][counter] = (Cell)
                              c.newInstance( tile, counter, index, rotations );
                    rotations.clear();
                    counter++;
                }
                case "L", "W" -> {
                    Tile tile = Tile.valueOf( String.format( "%s_%s_OFF", form, s ) );
                    map.array[index][counter] = (Cell)
                              c.newInstance( tile, counter, index, rotations );
                    rotations.clear();
                    counter++;
                }
                default -> {
                        int val = Integer.parseInt( s );
                        rotations.add( val );
//                      chercherVoisin( map, val, map.array[index][counter] );
                }
            }
        }
    }

/*
    public static void chercherVoisin( Map plat, int valeur, Cell cell ){

        int x = cell.x;
        int y = cell.y;
        switch(valeur){
            case 0: 
                    cell.voisins.add(plat.array[x-1][y]); //dessus
                    plat.array[x-1][y].voisins.add(cell);
                    break;
            case 1:
                    //voisin droite
                    cell.voisins.add(plat.array[x][y+1]);
                    plat.array[x][y+1].voisins.add(cell);
                    break;
            case 2: 
                    //voisin en bas a droite
                    cell.voisins.add(plat.array[x+1][y+1]);
                    plat.array[x+1][y+1].voisins.add(cell);
                    break;
            case 3:
                    cell.voisins.add(plat.array[x+1][y]); //dessous
                    plat.array[x+1][y].voisins.add(cell);
                    break;

            case 4: 
                    cell.voisins.add(plat.array[x+1][y-1]);  // en bas gauche = diagonal
                    plat.array[x+1][y-1].voisins.add(cell);
                    break;

            case 5: 
                    // voisins gauche
                    cell.voisins.add(plat.array[x][y-1]);
                    plat.array[x][y-1].voisins.add(cell);

            default:
            break;
        }
    }
*/

}
