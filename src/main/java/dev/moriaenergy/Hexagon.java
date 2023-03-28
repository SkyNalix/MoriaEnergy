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
		update_rotations_images();
	}

	@Override
	void paint( Graphics g, int width, int height ) {
		int x_pos = x*width, y_pos = y*height;
		x_pos -= x*(width/4);
		if(x%2 == 1) {
			y_pos += height/2;
		}
		g.drawImage( TileMap.HEX.getImage( isEnabled() ),
					 x_pos, y_pos, width, height, null);

		for (Image img : rotations_images) {
			g.drawImage( img, x_pos,y_pos, width, height,null );
		}

		if(tile != null) {
			g.drawImage( TileMap.valueOf( "HEX_" + tile ).getImage( isEnabled() ), x_pos, y_pos, width, height, null );
		}
	}

	@Override
	void rotate() {
		for(int i =0; i < this.rotations.size();i++){
			this.rotations.set(i, (this.rotations.get(i) + 1) % 6 );
			System.out.println(this.rotations.get(i));
		}
		this.update_rotations_images();
	}

	@Override
	void update_rotations_images() {
		if(rotations.size() == 0) return;
		rotations_images.clear();

		if(tile != null) {
			for (int r : rotations) {
				Image img = TileMap.HEX_SHORT_LINE.getImage( isEnabled() );
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
				TileMap line_tile = TileMap.HEX_SHORT_DIAG_LINE;
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
				rotations_images.add( Utils.rotate(
						  (BufferedImage) line_tile.getImage(isEnabled()),
						  angle ) );
			}
			// lignes en diagonale longue
			else if( r1 == (r2+2)%6 || r2 == (r1+2)%6 ) {
				TileMap line_tile = TileMap.HEX_LONG_DIAG_LINE;
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
				rotations_images.add( Utils.rotate(
						  (BufferedImage) line_tile.getImage(isEnabled()),
						  angle ) );
			} // lignes droite longue
			else if(r1 == (r2+3)%6 || r2 == (r1+3)%6) {
				TileMap line_tile = TileMap.HEX_LONG_LINE;
				if( ( r1 == 1 && r2 == 4 ) || ( r1 == 4 && r2 == 1 ) )
					angle = 60;
				if( ( r1 == 2 && r2 == 5 ) || ( r1 == 5 && r2 == 2 ) )
					angle = 2*60;
				rotations_images.add( Utils.rotate(
						  (BufferedImage) line_tile.getImage(isEnabled()),
						  angle ) );
			}
		}
	}

	@Override
	int[][] getNeighbors() {
		int[][] res = new int[rotations.size()][2];
		for( int i = 0; i < rotations.size(); i++ ) {
			int n = rotations.get(i);
			int rx = 0; int ry = 0;
			switch( n ) {
				case 0 -> {
					rx=x;
					ry= y - 1;
				}
				case 1 -> {
					rx=x+1;
					ry= y - (x%2==0 ? 1 : 0);
				}
				case 2 ->{
					rx=x+1;
					ry= y + (x%2==1 ? 1 : 0);
				}
				case 3 ->{
					rx=x;
					ry= y + 1;
				}
				case 4 -> {
					rx=x-1;
					ry= y + (x%2==1 ? 1 : 0);
				}
				case 5 ->{
					rx=x-1;
					ry= y - (x%2==0 ? 1 : 0);
				}
			}
			if(x==1 && y==2)
				System.out.printf( "%d, %d\n", rx, ry );
//			if(x%2==0) {
//				res[i][1]--;
//			}
			res[i] = new int[]{rx,ry};
		}
		return res;
	}

	public Point getCentreHexagone(int cell_width, int cell_height){
		//(x1,y1) = en haut à gauche, (x2,y2) = en bas à droite

		int x1 = this.x * cell_width -x*(cell_width/4);
		int y1 = this.y * cell_height;
		int x2 = (this.x + 1) * cell_width -x*(cell_width/4);
		int y2 = (this.y +1) * cell_height; 


		if(this.x % 2 == 1){
			y1 += cell_height/2;
			y2 += cell_height/2;
		}
		Point result = new Point((x1 + x2)/2, (y1 + y2) /2 ); 
		return result;
	}

	@Override
	Point distFromPoint(int cell_width, int cell_height,Point mouse_pos) {
		int x1 = this.x * cell_width -x*(cell_width/4);
		int y1 = this.y * cell_height;
		int x2 = (this.x + 1) * cell_width -x*(cell_width/4);
		int y2 = (this.y +1) * cell_height; 


		if(this.x % 2 == 1){
			y1 += cell_height/2;
			y2 += cell_height/2;
		}
		Point result = new Point((x1 + x2)/2, (y1 + y2) /2 ); 
		return result;	
		//return ((int) mouse_pos.distance(getCentreHexagone(cell_width, cell_height)));
	}	

}
