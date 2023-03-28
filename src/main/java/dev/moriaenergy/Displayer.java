package dev.moriaenergy;

import javax.swing.*;
import javax.swing.text.Position;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int plusProche = -1;
				Point centre;
				Cell min = null;

				for( Cell[] cells : map.array ) {
					for( Cell cell : cells ) {
						if( cell != null ){
							centre = cell.distFromPoint(cell_width,cell_height,getMousePosition());
							int distance =  ((int) getMousePosition().distance(centre));
							if(min == null || plusProche > distance){
								min = cell;
								plusProche = distance;
							}
						}
							
							
					}
				}
				System.out.println(min.getClass());
				
				//rayon de l'hexagone = distance(centre,bord)
				int rayon = (int) Point.distance(0, 0, (cell_width/2 - cell_width/4), cell_height/2);
				  
				if(min instanceof Hexagon  &&  plusProche > rayon ){//si distance plusProche > celle du rayon alors on est dans une zone vide
					//ne rien faire
				}else{
					min.rotate();
					repaint();
				}
				
			
			}

			@Override
			public void mousePressed(MouseEvent e) {
				//System.out.println("pressed");
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				//System.out.println("released");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				//System.out.println("entered");
			}

			@Override
			public void mouseExited(MouseEvent e) {				
				//System.out.println("exited");
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
