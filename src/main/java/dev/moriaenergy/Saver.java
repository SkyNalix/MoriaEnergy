package dev.moriaenergy;

import java.io.FileWriter;
import java.io.IOException;

public class Saver {

	public static void save(Map map, String name) {
		if(map == null || name == null)
			return;

		FileWriter writer;
		try {
			writer = new FileWriter("src/main/resources/custom levels/" + name);
		} catch( IOException e ) {
			return;
		}

		String to_write = map.getH() + " " + map.getW() + " ";
		if(map.array[0][0].getMaxNeighbors() == 4)
			to_write += "S\n";
		else
			to_write += "H\n";

		for (Cell[] cells : map.array) {
		    for(Cell cell : cells) {
				String tile_str = cell.tile == null ? "." : cell.tile.toString();
				String rotations_str = "";
				for( int i = 0; i < cell.rotations.size(); i++ ) {
					rotations_str += cell.rotations.get(i) + " ";
				}
				to_write += tile_str + " " + rotations_str;
			}
			to_write += "\n";
		}

		try {
			writer.write(to_write);
			writer.close();
		} catch( IOException ignored ) {}
	}

}
