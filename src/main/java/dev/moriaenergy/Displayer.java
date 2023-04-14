package dev.moriaenergy;

import dev.moriaenergy.mouseadapters.MyMouseAdapter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Displayer extends JPanel {

	private final Map map;
	private int cell_width = 50, cell_height = 50;

	public MyMouseAdapter mouseAdapter = null;

	Displayer( Map map ) {
		this.map = map;
		setBackground( Color.black );

		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized( ComponentEvent e ) {
				cell_width = getWidth() / map.getW();
				cell_height = getHeight() / map.getH();
				if(mouseAdapter != null)
					mouseAdapter.updateDimensions(cell_width, cell_height);
				repaint();
			}
		} );
	}

	public void setMouseAdapter( MyMouseAdapter mouseAdapter ) {
		if(this.mouseAdapter != null)
			removeMouseListener( this.mouseAdapter );
		this.mouseAdapter = mouseAdapter;
		mouseAdapter.updateDimensions( cell_width, cell_height );
		addMouseListener( this.mouseAdapter );
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );
		for( Cell[] cells : map.array )
			for( Cell cell : cells )
				cell.paint( g, cell_width, cell_height );
	}

}
