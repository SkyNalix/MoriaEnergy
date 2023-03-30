package dev.moriaenergy;

import javax.swing.*;

import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.util.ArrayList;

public class Displayer extends JPanel {

	private final int level;
	private final Map map;
	int HEIGHT = 500, WIDTH = 500, cell_width, cell_height;


	Displayer( int level, Map map ) {
		this.level = level;
		this.map = map;
		setBackground( Color.black );
		updateSizes();

		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized( ComponentEvent e ) {
				super.componentResized( e );
				HEIGHT = e.getComponent().getHeight();
				WIDTH = e.getComponent().getWidth();
				updateSizes();
				repaint();
			}
		} );

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
			int nearest = -1;
			Point center;
			Cell min = null;

			for( Cell[] cells : map.array ) {
				for( Cell cell : cells ) {
					if( cell != null ){
						center = cell.distFromPoint(cell_width,cell_height,getMousePosition());
						int distance =  ((int) getMousePosition().distance(center));
						if(min == null || nearest > distance){
							min = cell;
							nearest = distance;
						}
					}
				}
			}
			//rayon de l'hexagone = distance(centre,bord)
			int rayon = (int) Point.distance(0, 0, (cell_width/2f - cell_width/4f), cell_height/2f);

			if( min != null && nearest <= rayon ) {
				//si distance nearest > celle du rayon alors on est dans une zone vide
				List<Integer> before_rotation = new ArrayList<>( min.rotations );

				min.rotate();

				// on regarde ces ancients voisins, et on desactive si ils ne sont
				// plus alimentées
				for(Cell neighbor : min.getNeighbors(map, before_rotation)) {
					if( !neighbor.seekPower( map ) ) {
						neighbor.setEnabled( map, false );
					}
				}

				// on regarde si un des ces nouveaux voisins est alimenté
				boolean found = false;
				for(Cell neighbors : min.getNeighbors(map)) {
					if( neighbors.isEnabled() ) {
						found = true;
						break;
					}
				}
				min.setEnabled( map, found );
				min.update_rotations_images();

				map.updateWifi();
				repaint();
			}
			}
		});
	}

	void updateSizes() {
		cell_width = WIDTH / map.getW();
		cell_height = HEIGHT / map.getH();
		setPreferredSize( new Dimension(WIDTH+cell_width/2, HEIGHT+cell_height/2) );
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );
		g.setColor( Color.WHITE );
		g.setFont( new Font( null, Font.BOLD, 20 ) );
		g.drawString( "Level " + level, WIDTH/2-40,30 );

		for( Cell[] cells : map.array ) {
			for( Cell cell : cells ) {
				if( cell != null )
					cell.paint( g, cell_width, cell_height );
			}
		}

	}

}
