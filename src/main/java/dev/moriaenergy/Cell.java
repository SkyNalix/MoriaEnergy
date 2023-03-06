package dev.moriaenergy;

import java.awt.*;
import java.util.ArrayList;

public abstract class Cell {

	protected int x, y;
	public ArrayList<Integer> rotations;
	public Cell[] connections;
	public Tile tile;
	public boolean enabled = false;

	abstract void paint( Graphics g, int width, int height);
	abstract void rotate();


//	public String getVoisins(){
//		String str = "";
//		for(int i = 0; i < this.voisins.size();i++){
//			str += this.voisins.get(i).x + " ";
//		}
//		return str;
//	}
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
