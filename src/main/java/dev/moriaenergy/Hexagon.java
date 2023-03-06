package dev.moriaenergy;

import java.awt.*;
import java.util.ArrayList;

public class Hexagon extends Cell {

	public Hexagon( Tile tile, int x, int y, ArrayList<Integer> rotations ) {
		if(rotations.size() > 5)
			throw new IllegalArgumentException("rotations size should not be more than 5 for hexagons");
		this.tile = tile;
		this.x = x;
		this.y = y;
		this.rotations = rotations;
		this.connections = new Hexagon[5];
	}

	@Override
	void paint( Graphics g, int width, int height ) {
		int offset_x = x*width, offset_y = y*height;
		offset_x -= x*(width/4);
		if(x%2 == 1) {
			offset_y += height/2;
		}
		g.drawImage( Tile.valueOf( "HEX_" + (enabled ? "ON" : "OFF") ).getImage(),
					 offset_x,offset_y, width, height, null);
		if(tile != null) {
			g.drawImage( tile.getImage(), offset_x, offset_y, width, height,null );
		}
	}


	@Override
	void rotate() {

	}

}
