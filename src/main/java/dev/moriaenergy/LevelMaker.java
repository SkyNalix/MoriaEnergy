package dev.moriaenergy;

import dev.moriaenergy.mouseadapters.CellEditorMouseAdapter;
import dev.moriaenergy.mouseadapters.RotatorMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;

public class LevelMaker extends JPanel {

	public LevelMaker( Map map, Constructor<?> formConstructor ) {
		Displayer displayer = new Displayer( map );
		RotatorMouseAdapter rotatorMouseAdapter = new RotatorMouseAdapter( map, this );
		CellEditorMouseAdapter cellEditorMouseAdapter = new CellEditorMouseAdapter( map, this, formConstructor );
		setLayout( new GridBagLayout() );

		JPanel controller = new JPanel();
		controller.setLayout( new GridLayout( 4, 1, 10, 10) );
		JButton rotatorButton = new JButton("Rotating Mode");
		rotatorButton.addMouseListener(  new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				displayer.setMouseAdapter( rotatorMouseAdapter );
			}
		} );
		JButton rotationAdderButton = new JButton("Cell editor mode");
		rotationAdderButton.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				displayer.setMouseAdapter( cellEditorMouseAdapter );
			}
		} );
		JButton saveButton = new JButton("Save");
		saveButton.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				String filename = JOptionPane.showInputDialog("Save", "MyLevel");
				if(filename != null && !filename.isBlank())
					Saver.save( map, filename.trim() + ".nrg" );
			}
		} );

		JButton returnButton = new JButton("Return");
		returnButton.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				setVisible( false );
				Main.instance.switchTo(Main.instance.mainMenu);
			}
		} );
		controller.add( rotatorButton );
		controller.add( rotationAdderButton );
		controller.add( saveButton );
		controller.add( returnButton );
		Utils.configGirdBagLayout( this, displayer, controller );
		displayer.setMouseAdapter(rotatorMouseAdapter);
		setVisible( true );
	}

}
