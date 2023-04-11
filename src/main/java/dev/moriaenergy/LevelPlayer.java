package dev.moriaenergy;

import dev.moriaenergy.mouseadapters.RotatorMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelPlayer extends JPanel {

	private  Map map;
	private  Displayer displayer;
	private  RotatorMouseAdapter rotatorMouseAdapter;

	static int levelRef;

	public LevelPlayer(int level) throws Exception {
		levelRef = level;
		setLayout( new GridBagLayout() );

		map = Parser.parse( "level" + level );
//		map.multipleVictory();
		displayer = new Displayer( map );
		add(displayer);

		JPanel controller = new JPanel();
		controller.setLayout( new GridLayout( 4, 1, 10, 10) );
		JButton returnButton = new JButton("Return");
		returnButton.addMouseListener(  new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				setVisible( false );
				Main.instance.switchTo(Main.instance.mainMenu);
			}
		} );
		controller.add( returnButton );

		Utils.configGirdBagLayout( this, displayer, controller );
		rotatorMouseAdapter = new RotatorMouseAdapter( map, this );
		displayer.setMouseAdapter(rotatorMouseAdapter);
		setVisible( true );
	}

	public void nextLevel() {
		try{
			//LevelPlayer suivant = new LevelPlayer(levelRef+1);
			this.map = Parser.parse("level" + levelRef);
			this.displayer = new Displayer(map);
			rotatorMouseAdapter = new RotatorMouseAdapter( map, this );
			addMouseListener(rotatorMouseAdapter);
		} catch(Exception e){
			e.printStackTrace();
		}

	}

}
