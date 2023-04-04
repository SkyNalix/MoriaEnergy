package dev.moriaenergy;

import dev.moriaenergy.mouseadapters.RotationAdderMouseAdapter;
import dev.moriaenergy.mouseadapters.RotatorMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;

public class LevelMaker extends JPanel {

	private Map map;
	private final Displayer displayer;
	private final JPanel controller;
	int cell_width, cell_height;

	private MouseAdapter currentMouseAdapter = null;
	private final RotatorMouseAdapter rotatorMouseAdapter;
	private final RotationAdderMouseAdapter rotationAdderMouseAdapter;

	public LevelMaker( Map map, Constructor<?> formConstructor ) {
		this.map = map;
		this.displayer = new Displayer(this.map);
		add(this.displayer);

		controller = new JPanel();
		controller.setBackground( Color.DARK_GRAY );
		JButton rotatorButton = new JButton("Rotating Mode");
		rotatorButton.addMouseListener(  new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				switchMouseAdapter( rotatorMouseAdapter );
			}
		} );

		JButton rotationAdderButton = new JButton("Rotations Adder mode");
		rotationAdderButton.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				switchMouseAdapter( rotationAdderMouseAdapter );
			}
		} );

		JButton saveButton = new JButton("Save");
		saveButton.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				String filename = JOptionPane.showInputDialog("Anyone there?", 42);
				if(filename != null && !filename.isBlank())
					Saver.save( map, filename.trim() + ".nrg" );
			}
		} );

		controller.add(rotatorButton);
		controller.add(rotationAdderButton);
		controller.add(saveButton);
		add(controller);

		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized( ComponentEvent e ) {
				updateSizes();
				repaint();
			}
		} );
		rotatorMouseAdapter = new RotatorMouseAdapter( this.map, this,null );
		rotationAdderMouseAdapter = new RotationAdderMouseAdapter( this.map, this, formConstructor );
		updateSizes();
		switchMouseAdapter( rotatorMouseAdapter );
		setVisible( true );
	}

	private void switchMouseAdapter(MouseAdapter newMode) {
		if(currentMouseAdapter != null)
			removeMouseListener( currentMouseAdapter );
		addMouseListener( newMode );
		currentMouseAdapter = newMode;
	}

	void updateSizes() {
		cell_width = (getWidth()-220) / map.getW();
		cell_height = (getHeight()-cell_height/2) / map.getH();

		int x = displayer.getX(), y = displayer.getY();
		rotatorMouseAdapter.updateDimensions(cell_width, cell_height);
		rotatorMouseAdapter.updateOffset(x, y);
		rotationAdderMouseAdapter.updateDimensions(cell_width, cell_height);
		rotationAdderMouseAdapter.updateOffset(x, y);

		displayer.udpate_size(cell_width, cell_height);
		controller.setPreferredSize( new Dimension(200, getHeight()-5) );
		setMinimumSize( new Dimension(600,400) );
		setPreferredSize( new Dimension( getWidth(), getHeight()) );
		setSize( 600,400 );
	}

}
