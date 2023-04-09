package dev.moriaenergy.mouseadapters;

import dev.moriaenergy.Cell;
import dev.moriaenergy.LevelPlayer;
import dev.moriaenergy.Map;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class RotatorMouseAdapter extends MyMouseAdapter {
	LevelPlayer level;
	public RotatorMouseAdapter( Map map, JPanel panel,LevelPlayer level) {
		super(map, panel);
		this.level = level;
	}

	@Override
	public void mouseClicked( MouseEvent e ) {
		Cell cell = getCellUnderMouse(e);
		if(cell == null)
			return;

		//si distance nearest > celle du rayon alors on est dans une zone vide
		List<Integer> before_rotation = new ArrayList<>( cell.rotations );

		cell.rotate();

		// on regarde ces ancients voisins, et on desactive si ils ne sont
		// plus alimentées
		for(Cell neighbor : cell.getNeighbors(map, before_rotation)) {
			if( !neighbor.seekPower( map ) ) {
				neighbor.setEnabled( map, false );
			}
		}

		// on regarde si un des ces nouveaux voisins est alimenté
		boolean found = false;
		for(Cell neighbors : cell.getNeighbors(map)) {
			if( neighbors.isEnabled() ) {
				found = true;
				break;
			}
		}
		cell.setEnabled( map, found );
		cell.update_rotations_images();
		map.updateWifi();
		panel.repaint();

		//on regarde si victoire
		if(this.map.victory()){
			System.out.println(" VICTOIRE ");
			//this.level.nextLevel();
		}
	}
}
