package dev.moriaenergy;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JFrame {

	Main() throws Exception {
		super();
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setLocationRelativeTo( null );

		var frame = this;
		addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed( KeyEvent e ) {
				super.keyPressed( e );
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					frame.setVisible( false );
					frame.dispose();
				}
			}
		} );
		int level = 10;
		add(new Displayer(level, Parser.parse( "level" + level )));
//		add(new LevelMaker());
		pack();
		setVisible( true );
	}

	public static void main( String[] args ) throws Exception {
		new Main();
	}

}
