package dev.moriaenergy.mouseadapters;

import dev.moriaenergy.Cell;
import dev.moriaenergy.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseAdapter extends MouseAdapter {

	protected final Map map;
	protected final JPanel panel;
	protected int cell_width, cell_height;
	public boolean changed = false;

	public MyMouseAdapter(Map map, JPanel panel ) {
		this.map = map;
		this.panel = panel;
	}

	public void updateDimensions(int cell_width, int cell_height) {
		this.cell_width = cell_width;
		this.cell_height = cell_height;
	}

	protected Cell getCellUnderMouse( MouseEvent e ) {
		double nearest = -1;
		Cell nearestCell = null;
		Point clickPos = new Point( e.getX(), e.getY());
		for( Cell[] cells : map.array ) {
			for( Cell cell : cells ) {
				Point center = cell.centerPoint(cell_width,cell_height);
				double distance =  clickPos.distance(center);
				if(nearestCell == null || nearest > distance){
					nearestCell = cell;
					nearest = distance;
				}
			}
		}
		return nearestCell;
	}

}
