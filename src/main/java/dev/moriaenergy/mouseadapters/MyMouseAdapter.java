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
	public int offsetX = 0, offsetY = 0;

	public MyMouseAdapter(Map map, JPanel panel ) {
		this.map = map;
		this.panel = panel;
	}

	public void updateDimensions(int cell_width, int cell_height) {
		this.cell_width = cell_width;
		this.cell_height = cell_height;
	}
	public void updateOffset(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	protected Point getMousePoint( MouseEvent e ) {
		return new Point( e.getX() - offsetX, e.getY() - offsetY);
	}

	protected Cell getCellUnderMouse( MouseEvent e ) {
		int nearest = -1;
		Point center;
		Cell min = null;
		Point clickPos = getMousePoint(e);
		for( Cell[] cells : map.array ) {
			for( Cell cell : cells ) {
				center = cell.distFromPoint(cell_width,cell_height,clickPos);
				int distance =  ((int) clickPos.distance(center));
				if(min == null || nearest > distance){
					min = cell;
					nearest = distance;
				}
			}
		}
		//rayon de l'hexagone = distance(centre,bord)
		int rayon = (int) Point.distance(0, 0, (cell_width/2f - cell_width/4f), cell_height/2f);

		if( min != null && nearest <= rayon ) {
			return min;
		}
		return null;
	}

}
