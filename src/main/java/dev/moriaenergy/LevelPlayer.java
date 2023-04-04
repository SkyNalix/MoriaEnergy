package dev.moriaenergy;

import dev.moriaenergy.mouseadapters.RotatorMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LevelPlayer extends JPanel {

	private final Map map;
	private final Displayer displayer;
	int cell_width, cell_height;
	private final RotatorMouseAdapter rotatorMouseAdapter;

	public LevelPlayer(int level) throws Exception {
		map = Parser.parse( "level" + level );
		this.displayer = new Displayer(map);
		add(this.displayer);
		setPreferredSize( new Dimension( 400,400 ));
		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized( ComponentEvent e ) {
				updateSizes();
				repaint();
			}
		} );
		rotatorMouseAdapter = new RotatorMouseAdapter( map, this );
		updateSizes();
		addMouseListener(rotatorMouseAdapter);
	}

	void updateSizes() {
		if(map == null) return;
		cell_width = getWidth() / map.getW();
		cell_height = (getHeight()-cell_height/2) / map.getH();
		rotatorMouseAdapter.updateDimensions(cell_width, cell_height);
		displayer.udpate_size(cell_width,cell_height);
		setPreferredSize( new Dimension( getWidth(), getHeight() + cell_height/2) );
	}

}
