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

		add(new Displayer(3, Parser.parse( "level3" )));
		pack();
		setVisible( true );
	}

	public static void main( String[] args ) throws Exception {
		new Main();
//		System.out.println(plat.debutParcour());
//		for(int i =0;i<plat.listeAAllumer.size();i++){
//			System.out.println(plat.listeAAllumer.get(i).x + " " + plat.listeAAllumer.get(i).allumer);
//		}
	}

}
