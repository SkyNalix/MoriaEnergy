package dev.moriaenergy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Displayer extends JPanel {

	int height = 900, width = 900;
	private int level = 1;

	Displayer() {
		setBackground( Color.black );
		setPreferredSize( new Dimension(width, height) );

		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized( ComponentEvent e ) {
				super.componentResized( e );
				height = e.getComponent().getHeight();
				width = e.getComponent().getWidth();
				setPreferredSize( new Dimension(width, height) );
				repaint();
			}
		} );
	}

	private static final char SHAPE = 'S';
	private static final Cell[][] plot = new Cell[][] {
			  {new Square(Tile.S_L_OFF ,0,0),new Square(Tile.S_W_OFF ,1,0),null},
			  {new Square( Tile.S_DIAG_LINE_ON,0,1 ),new Square(Tile.S_L_OFF ,1,1),null},
			  {null,null,null},
	};



	@Override
	public void paint( Graphics g ) {
		super.paint( g );
		g.setColor( Color.WHITE );
		g.setFont( new Font( null, Font.BOLD, 20 ) );
		g.drawString( "Level " + level, width/2-40,30 );
//		for(Tile tile : Tile.values()) {
//			g.drawImage( tile.getImage(), tile.x, tile.y, tile.width, tile.height,null );
//		}

		for( Cell[] cells : plot ) {
			for( Cell cell : cells ) {
				if( cell != null )
					cell.paint( g );
			}
		}

	}

}
