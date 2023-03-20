package dev.moriaenergy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hexagon extends Cell {

	public Hexagon( Tile tile, int x, int y, ArrayList<Integer> rotations ) {
		if(rotations.size() > 6)
			throw new IllegalArgumentException("rotations size should not be more than 5 for hexagons");
		this.tile = tile;
		this.x = x;
		this.y = y;
		this.rotations = rotations;
		this.connections = new Hexagon[6];
		update_rotations_images();
	}

	@Override
	void paint( Graphics g, int width, int height ) {
		String state = enabled ? "ON" : "OFF";
		int x_pos = x*width, y_pos = y*height;
		x_pos -= x*(width/4);
		if(x%2 == 1) {
			y_pos += height/2;
		}
		g.drawImage( Tile.valueOf( "HEX_" + state ).getImage(),
					 x_pos,y_pos, width, height, null);

		for (Image img : rotations_images) {
			g.drawImage( img, x_pos,y_pos, width, height,null );
		}

		if(tile != null) {
			g.drawImage( tile.getImage(), x_pos, y_pos, width, height,null );
		}
	}

	@Override
	void rotate() {

	}

	@Override
	void update_rotations_images() {
		if(rotations.size() == 0) return;
		String state = enabled ? "ON" : "OFF";
		rotations_images.clear();

		if(tile != null) {
			for (int r : rotations) {
				Image img = Tile.valueOf( "HEX_SHORT_LINE_" + state ).getImage();
				rotations_images.add(Utils.rotate((BufferedImage) img, r*60));
			}
			return;
		}

		int r1 = rotations.get(0);
		for(int j = 1; j < rotations.size(); j++ ) {
			int r2 = rotations.get(j);
			int angle = 0;
			// lignes en diagonale courte
			if( r1 == (r2+1)%6 || r2 == (r1+1)%6 ) {
				Tile line_tile = Tile.valueOf( "HEX_SHORT_DIAG_LINE_"+ state );
				if( ( r1 == 1 && r2 == 2 ) || ( r1 == 2 && r2 == 1 ) )
					angle = 60;
				else if( ( r1 == 2 && r2 == 3 ) || ( r1 == 3 && r2 == 2 ) )
					angle = 2*60;
				else if( ( r1 == 3 && r2 == 4 ) || ( r1 == 4 && r2 == 3 ) )
					angle = 3*60;
				else if( ( r1 == 4 && r2 == 5 ) || ( r1 == 5 && r2 == 4 ) )
					angle = 4*60;
				else if( ( r1 == 5 && r2 == 0 ) || ( r1 == 0 && r2 == 5 ) )
					angle = 5*60;
				rotations_images.add( Utils.rotate( (BufferedImage) line_tile.getImage(), angle ) );
			}
			// lignes en diagonale longue
			else if( r1 == (r2+2)%6 || r2 == (r1+2)%6 ) {
				Tile line_tile = Tile.valueOf( "HEX_LONG_DIAG_LINE_"+ state );
				if( ( r1 == 1 && r2 == 3 ) || ( r1 == 3 && r2 == 1 ) )
					angle = 60;
				else if( ( r1 == 2 && r2 == 4 ) || ( r1 == 4 && r2 == 2 ) )
					angle = 2*60;
				else if( ( r1 == 3 && r2 == 5 ) || ( r1 == 5 && r2 == 3 ) )
					angle = 3*60;
				else if( ( r1 == 4 && r2 == 0 ) || ( r1 == 0 && r2 == 4 ) )
					angle = 4*60;
				else if( ( r1 == 5 && r2 == 1 ) || ( r1 == 1 && r2 == 5 ) )
					angle = 5*60;
				rotations_images.add( Utils.rotate( (BufferedImage) line_tile.getImage(), angle ) );
			} // lignes droite longue
			else if(r1 == (r2+3)%6 || r2 == (r1+3)%6) {
				Tile line_tile = Tile.valueOf( "HEX_LONG_LINE_"+ state );
				if( ( r1 == 1 && r2 == 4 ) || ( r1 == 4 && r2 == 1 ) )
					angle = 60;
				if( ( r1 == 2 && r2 == 5 ) || ( r1 == 5 && r2 == 2 ) )
					angle = 2*60;
				rotations_images.add( Utils.rotate( (BufferedImage) line_tile.getImage(), angle ) );
			}
		}
	}

}
