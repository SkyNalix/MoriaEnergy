package dev.moriaenergy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
