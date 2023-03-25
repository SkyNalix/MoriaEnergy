package dev.moriaenergy;

import java.awt.*;
import java.util.ArrayList;

public abstract class Cell {

	protected int x, y;
	public ArrayList<Integer> rotations;
	final ArrayList<Image> rotations_images = new ArrayList<>();
	public Tile tile;
	private boolean enabled = false;

	abstract void paint( Graphics g, int width, int height);
	abstract void rotate();
	abstract void update_rotations_images();
	abstract int[][] getNeighbors();

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled( Map map, boolean enabled ) {
		if(this.enabled == enabled) return;
		if(tile != null && !enabled && tile != Tile.S)
			return;
		this.enabled = enabled;
		update_rotations_images();
		if(tile != null && tile == Tile.W) {
			for( int i = 0; i < map.array.length; i++ ) {
				for( int j = 0; j < map.array[i].length; j++ ) {
					if(i!=y && j!=x && map.array[i][j] != null
					   && map.array[i][j].tile != null
					   && map.array[i][j].tile.toString().endsWith( "_W" ))
						map.array[i][j].setEnabled( map, this.enabled );
				}
			}
		}
		for(int[] voisin : getNeighbors()) {
			int x = voisin[0], y = voisin[1];
			if( !map.isInBounds(voisin[0], voisin[1]) || map.array[y][x] == null)
				continue;
			map.array[y][x].setEnabled( map, this.enabled );
		}
	}


//
//	public void rotation(boolean s){
//		for(int i=0; i<this.rotation.size();i++ ){
//
//			int valeur = this.rotation.get(i);
//			int modulo = 4;
//			if(!s) modulo = 6;
//
//			valeur = (valeur + 1) % modulo;
//			this.rotation.set(i,valeur);
//		}
//	}
//
//    /*public void parcoursVoisin(){ // part du principe qu'on vient d'un endroit allumer
//            this.allumer = true;
//            for(int i=0;i<this.voisins.size();i++){
//                this.voisins.get(i).parcoursVoisin();
//            }
//    } */


}
