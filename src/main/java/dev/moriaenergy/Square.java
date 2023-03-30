package dev.moriaenergy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Square extends Cell {

	public Square( Tile tile, int x, int y, ArrayList<Integer> rotations ) {
		if(rotations.size() > 4)
			throw new IllegalArgumentException("rotations size should not be more than 4 for squares ");
		this.tile = tile;
		this.x = x;
		this.y = y;
		this.rotations = rotations;
		update_rotations_images();
	}


	@Override
	void paint( Graphics g, int width, int height ) {
		int x_pos = x*width;
		int y_pos = y*height;
		g.drawImage( TileMap.SQUARE.getImage( isEnabled() ),
					 x_pos, y_pos, width, height, null);

		for (Image img : rotations_images) {
			g.drawImage( img, x_pos,y_pos, width, height,null );
		}
		if(tile != null) {
			g.drawImage( TileMap.valueOf( "SQUARE_" + tile )
								.getImage(isEnabled()), x_pos, y_pos, width, height,null );
		}
	}

	@Override
	void rotate() {
		this.rotations.replaceAll( integer -> ( integer + 1 ) % 4 );
	}

	@Override
	void update_rotations_images() {
		if(rotations.size() == 0) return;
		rotations_images.clear();

		if(tile != null) {
			for (int r : rotations) {
				Image img = TileMap.SQUARE_SHORT_LINE.getImage( isEnabled() );
				rotations_images.add(Utils.rotate((BufferedImage) img, r*90));
			}
			return;
		}
		int r1 = rotations.get(0);
		for( int r2 : rotations ) {
			if( r2 == -1 ) continue;
			// lignes en diagonale
			if( r1 == ( r2 + 1 ) % 4 || r2 == ( r1 + 1 ) % 4 ) {
				TileMap line_tile = TileMap.SQUARE_DIAG_LINE;
				int angle = 0;
				if( ( r1 == 1 && r2 == 2 ) || ( r1 == 2 && r2 == 1 ) )
					angle = 90;
				else if( ( r1 == 2 && r2 == 3 ) || ( r1 == 3 && r2 == 2 ) )
					angle = 2 * 90;
				else if( ( r1 == 3 && r2 == 0 ) || ( r1 == 0 && r2 == 3 ) )
					angle = 3 * 90;
				rotations_images.add( Utils.rotate(
						  (BufferedImage) line_tile.getImage(isEnabled()),
						  angle ) );
			} // lignes droite longue
			else if( r1 == ( r2 + 2 ) % 4 || r2 == ( r1 + 2 ) % 4 ) {
				TileMap line_tile = TileMap.SQUARE_LONG_LINE;
				int angle = 0;
				if( ( r1 == 1 && r2 == 3 ) || ( r1 == 3 && r2 == 1 ) )
					angle = 90;
				rotations_images.add( Utils.rotate(
						  (BufferedImage) line_tile.getImage(isEnabled()),
						  angle ) );
			}
		}
	}

	@Override
	List<Cell> getNeighbors(Map map, List<Integer> rotations ) {
		List<Pair<Integer, Point>> neighborsPositions = new ArrayList<>();
		for( int n : rotations ) {
			int neighbor_x = 0, neighbor_y = 0, neighbor_rotation = -1;
			switch( n ) {
				case 0 -> {
					neighbor_x = x;
					neighbor_y = y - 1;
					neighbor_rotation = 2;
				}
				case 1 -> {
					neighbor_x = x + 1;
					neighbor_y = y;
					neighbor_rotation = 3;
				}
				case 2 -> {
					neighbor_x = x;
					neighbor_y = y + 1;
					neighbor_rotation = 0;
				}
				case 3 -> {
					neighbor_x = x - 1;
					neighbor_y = y;
					neighbor_rotation = 1;
				}
			}
			neighborsPositions.add( new Pair<>(
					  neighbor_rotation, new Point( neighbor_x, neighbor_y ) ) );
		}
		List<Cell> neighbors = new ArrayList<>();
		for(Pair<Integer, Point> position : neighborsPositions) {
			int x = position.snd().x, y = position.snd().y;
			if( map.isInBounds( x, y )
				&& map.array[y][x] != null
				&& map.array[y][x].rotations.contains( position.fst()) ) {
				neighbors.add(map.array[y][x]);
			}
		}
		return neighbors;
	}

	@Override
	Point distFromPoint(int cell_width, int cell_height, Point mouse_pos) {
		int x1 = this.x * cell_width ;
		int y1 = this.y * cell_height;
		int x2 = (this.x + 1) * cell_width ;
		int y2 = (this.y +1) * cell_height;

		return new Point( ( x1 + x2) / 2, ( y1 + y2) / 2 );
	}

}
