package dev.moriaenergy;

import dev.moriaenergy.mouseadapters.CellEditorMouseAdapter;
import dev.moriaenergy.mouseadapters.RotatorMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;

public class LevelMaker extends JPanel {

	private final Map map;
	private final Displayer displayer;
	private final JPanel controller;
	int cell_width, cell_height;

	private MouseAdapter currentMouseAdapter = null;
	private final RotatorMouseAdapter rotatorMouseAdapter;
	private final CellEditorMouseAdapter rotationAdderMouseAdapter;

	public LevelMaker( Map map, Constructor<?> formConstructor ) {
		this.map = map;
		this.displayer = new Displayer(this.map);
		setLayout( new GridBagLayout() );
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.BOTH;
		constraint.gridwidth = 3;
		constraint.gridheight = 3;
		constraint.anchor = GridBagConstraints.FIRST_LINE_START;
		constraint.gridx = 0;
		constraint.gridy = 0;
		add(this.displayer, constraint);

		controller = new JPanel();
		controller.setLayout( new GridLayout(4,1) );
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

		constraint.fill = GridBagConstraints.BOTH;
		constraint.gridx = 3;
		constraint.gridy = 0;
		constraint.anchor = GridBagConstraints.FIRST_LINE_END;
		constraint.gridwidth = 1;
		constraint.gridheight = 3;
		add(controller,constraint);

		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized( ComponentEvent e ) {
				updateSizes();
				repaint();
			}
		} );
		rotatorMouseAdapter = new RotatorMouseAdapter( this.map, this );
		rotationAdderMouseAdapter = new CellEditorMouseAdapter( this.map, this, formConstructor );
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
		int height = getHeight();
		int width = getWidth();

		int displayer_width = width-200;

		cell_width = (displayer_width) / map.getW();
		cell_height = (height-cell_height/2) / map.getH();

		int offsetX = displayer.getX(), offsetY = displayer.getY();
		rotatorMouseAdapter.updateDimensions(cell_width, cell_height);
		rotatorMouseAdapter.updateOffset(offsetX, offsetY);
		rotationAdderMouseAdapter.updateDimensions(cell_width, cell_height);
		rotationAdderMouseAdapter.updateOffset(offsetX, offsetY);

		((GridBagLayout) getLayout()).getConstraints( displayer ).gridwidth = displayer_width;
		displayer.udpate_size(cell_width, cell_height);
	}

}
