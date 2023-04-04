package dev.moriaenergy.mouseadapters;

import dev.moriaenergy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class RotationAdderMouseAdapter extends MyMouseAdapter {

	private final Constructor<?> formConstructor;

	public RotationAdderMouseAdapter( Map map, JPanel panel, Constructor<?> formConstructor ) {
		super(map, panel);
		this.formConstructor = formConstructor;
	}

	@Override
	public void mouseClicked( MouseEvent e ) {
		Cell cell = getCellUnderMouse( e );
		if(cell == null)
			return;

		JTextField xField = new JTextField(5);
		JTextField yField = new JTextField(5);

		xField.setText( "3" );
		yField.setText("4");

		JPanel initInputPanel = new RotationEditorPanel( getCellUnderMouse( e ) );
		int result = JOptionPane.showConfirmDialog(
				  null,
				  initInputPanel,
				  "Add or remove connections",
				  JOptionPane.DEFAULT_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			panel.repaint();
		}
		map.enableSources();
		map.updateWifi();

	}

	private static class RotationEditorPanel extends JPanel {

		private final Cell cell;

		RotationEditorPanel(Cell cell) {
			this.cell = cell;
			setPreferredSize( new Dimension(150,300) );

			for( int i = 0; i < cell.getMaxNeighbors(); i++ ) {
				JButton button = new JButton( String.valueOf( i ) );
				button.setBackground( Color.red );
				Integer finalI = i;
				button.addMouseListener( new MouseAdapter() {
					@Override
					public void mouseClicked( MouseEvent e ) {
						if( cell.rotations.contains( finalI ) ) {
							cell.rotations.remove( finalI );
							e.getComponent().setBackground( Color.red );
						} else {
							cell.rotations.add( finalI );
							e.getComponent().setBackground( Color.green );
						}
						repaint();
					}
				} );
				add( button );
			}

			JButton sourceButton = new JButton("Source");
			sourceButton.addMouseListener( new MouseAdapter() {
				@Override
				public void mouseClicked( MouseEvent e ) {
					cell.tile = cell.tile != Tile.S ? Tile.S : null;
					repaint();
				}
			} );
			add(sourceButton);

			JButton lampeButton = new JButton("Lamp");
			lampeButton.addMouseListener( new MouseAdapter() {
				@Override
				public void mouseClicked( MouseEvent e ) {
					cell.tile = cell.tile != Tile.L ? Tile.L : null;
					repaint();
				}
			} );
			add(lampeButton);

			JButton wifiButton = new JButton("Wifi");
			wifiButton.addMouseListener( new MouseAdapter() {
				@Override
				public void mouseClicked( MouseEvent e ) {
					cell.tile = cell.tile != Tile.W ? Tile.W : null;
					repaint();
				}
			} );
			add(wifiButton);
		}

		@Override
		public void paint( Graphics g ) {
			super.paint( g );
			g.setColor( Color.BLACK );
			g.fillRect( 0,180,120,120 );
			cell.update_rotations_images();
			cell.paint( g, 10,190, 100, 100 );
		}

	}
}
