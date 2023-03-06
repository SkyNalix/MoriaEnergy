package dev.moriaenergy;

import java.awt.*;
import java.util.ArrayList;

public class Square extends Cell {

	public Square(Tile tile, int x, int y, ArrayList<Integer> rotations ) {
		if(rotations.size() > 4)
			throw new IllegalArgumentException("rotations size should not be more than 4 for squares ");
		this.tile = tile;
		this.x = x;
		this.y = y;
		this.rotations = rotations;
		this.connections = new Square[4];
	}

	@Override
	void paint( Graphics g, int width, int height ) {
		g.drawImage( Tile.valueOf( "SQUARE_" + (enabled ? "ON" : "OFF") ).getImage(),
					 x*width,y*height, width, height, null);
		if(tile != null) {
			g.drawImage( tile.getImage(), x*width, y*height, width, height,null );
		}
	}

	@Override
	void rotate() {

	}

}
