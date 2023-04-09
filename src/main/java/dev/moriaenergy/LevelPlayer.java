package dev.moriaenergy;

import dev.moriaenergy.mouseadapters.RotatorMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelPlayer extends JPanel {


	public LevelPlayer(int level) throws Exception {
		setLayout( new GridBagLayout() );

		Map map = Parser.parse( "level" + level );
		Displayer displayer = new Displayer( map );
		add(displayer);

		JPanel controller = new JPanel();
		controller.setLayout( new GridLayout( 4, 1, 10, 10) );
		JButton returnButton = new JButton("Return");
		returnButton.addMouseListener(  new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				System.out.println( "TODO" );
			}
		} );
		controller.add( returnButton );

		Utils.configGirdBagLayout( this, displayer, controller );
		displayer.setMouseAdapter(new RotatorMouseAdapter( map, this ));
		setVisible( true );
	}

}
