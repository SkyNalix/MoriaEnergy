package dev.moriaenergy;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public abstract class Cell {

	protected int x, y;
	public List<Integer> rotations;
	final List<Image> rotations_images = new ArrayList<>();
	public Tile tile;
	private boolean enabled = false;

	abstract void paint( Graphics g, int width, int height);
	abstract void rotate();
	abstract void update_rotations_images();

	List<Cell> getNeighbors(Map map) {
		return getNeighbors( map, this.rotations );
	}
	abstract List<Cell> getNeighbors( Map map, List<Integer> rotations );

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled( Map map, boolean enabled ) {
		setEnabled( map, enabled, new LinkedList<>() );
	}
	private void setEnabled( Map map, boolean enabled, LinkedList<Cell> visited ) {
		if(visited.contains( this )) return;
		visited.addFirst(this);

		// (tile!=null && !enabled) => (tile != Tile.S)
		if( (tile == null || enabled) || tile != Tile.S)
			this.enabled = enabled;
		update_rotations_images();

		for(Cell neighbor : getNeighbors(map))
			map.array[neighbor.y][neighbor.x].setEnabled( map, this.enabled, visited );
	}

	boolean seekPower(Map map) {
		return seekPower( map, new LinkedList<>() );
	}

	private boolean seekPower(Map map, LinkedList<Cell> visited) {
		if(visited.contains( this ))
			return false;
		if(tile != null && tile == Tile.S)
			return true;
		visited.addFirst(this);
		for(Cell neighbor : getNeighbors(map))
			if(neighbor.seekPower(map, visited))
				return true;
		return false;
	}

	abstract Point distFromPoint(int x,int y,Point pos_mouse);

}
