package dev.moriaenergy;

import java.awt.*;
import java.awt.image.BufferedImage;
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
		update_rotations_images();
	}


	@Override
	void paint( Graphics g, int width, int height ) {
		String state = enabled ? "ON" : "OFF";
		int x_pos = x*width;
		int y_pos = y*height;
		g.drawImage( Tile.valueOf( "SQUARE_" + state ).getImage(),
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
				Image img = Tile.valueOf( "SQUARE_SHORT_LINE_" + state ).getImage();
				rotations_images.add(Utils.rotate((BufferedImage) img, r*90));
			}
			return;
		}
		int r1 = rotations.get(0);
		for( int r2 : rotations ) {
			if( r2 == -1 ) continue;
			// lignes en diagonale
			if( r1 == ( r2 + 1 ) % 4 || r2 == ( r1 + 1 ) % 4 ) {
				Tile line_tile = Tile.valueOf( "SQUARE_DIAG_LINE_" + state );
				int angle = 0;
				if( ( r1 == 1 && r2 == 2 ) || ( r1 == 2 && r2 == 1 ) )
					angle = 90;
				else if( ( r1 == 2 && r2 == 3 ) || ( r1 == 3 && r2 == 2 ) )
					angle = 2 * 90;
				else if( ( r1 == 3 && r2 == 0 ) || ( r1 == 0 && r2 == 3 ) )
					angle = 3 * 90;
				rotations_images.add( Utils.rotate( (BufferedImage) line_tile.getImage(), angle ) );
			} // lignes droite longue
			else if( r1 == ( r2 + 2 ) % 4 || r2 == ( r1 + 2 ) % 4 ) {
				Tile line_tile = Tile.valueOf( "SQUARE_LONG_LINE_" + state );
				int angle = 0;
				if( ( r1 == 1 && r2 == 3 ) || ( r1 == 3 && r2 == 1 ) )
					angle = 90;
				rotations_images.add( Utils.rotate( (BufferedImage) line_tile.getImage(), angle ) );
			}
		}
	}

}
