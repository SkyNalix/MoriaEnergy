package dev.moriaenergy;

import java.awt.*;

public abstract class Cell {

	public int x, y, rotation = 0;
	public Cell[] voisins;
	public Tile tile;
	public boolean on = false;

	abstract void paint( Graphics g );
	abstract void rotate();


}
