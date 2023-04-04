package dev.moriaenergy;
import java.io.FileWriter;
import java.io.IOException;

public class Saver {

	public static boolean save(Map map, String name) {
		if(map == null || name == null)
			return false;

		FileWriter writer;
		try {
			writer = new FileWriter("src/main/resources/" + name);
		} catch( IOException e ) {
			return false;
		}

		String to_write = map.getH() + " " + map.getW() + " ";
		if(map.array[0][0] instanceof Square)
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
		} catch( IOException e ) {
			return false;
		}

		return true;
	}

}
