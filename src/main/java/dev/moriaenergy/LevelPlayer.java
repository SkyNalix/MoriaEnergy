package dev.moriaenergy;

import dev.moriaenergy.mouseadapters.RotatorMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelPlayer extends QuittablePanel {

	private  Map map;
	private  Displayer displayer;
	private  RotatorMouseAdapter rotatorMouseAdapter;

	static String levelRef;

	public LevelPlayer(String level) throws Exception {
		levelRef = level;
		setLayout( new GridBagLayout() );

		map = Parser.parse( level );
		rotationAleatoire(map);
		displayer = new Displayer( map );
		add(displayer);

		JPanel controller = new JPanel();
		controller.setLayout( new GridLayout( 4, 1, 10, 10) );
		JButton returnButton = new JButton("Return");
		returnButton.addMouseListener(  new MouseAdapter() {
			@Override
			public void mouseClicked( MouseEvent e ) {
				quit();
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
			//rotatorMouseAdapter = new RotatorMouseAdapter( map, this );
			addMouseListener(rotatorMouseAdapter);
		} catch(Exception e){
			e.printStackTrace();
		}

	}


	@Override
	public void quit() {
		setVisible( false );
		Main.instance.switchTo(Main.instance.mainMenu);
	}

	public static void rotationAleatoire(Map map){

		for(int i =0;i<map.array.length;i++){
			for(int j =0;j<map.array[i].length;j++){
				
				Random rand = new Random();
				int valeur = rand.nextInt(6);

				for(int k=0;k<valeur;k++){
					Cell cell = map.array[i][j];
					if(cell != null){
						System.out.println("rotation aléatoire");
						List<Integer> before_rotation = new ArrayList<>( cell.rotations );
						cell.rotate();
						for(Cell neighbor : cell.getNeighbors(map, before_rotation)) {
							if( !neighbor.seekPower( map ) ) {
								neighbor.setEnabled( map, false );
							}
						}
				
						// on regarde si un des ces nouveaux voisins est alimenté
						boolean found = false;
						for(Cell neighbors : cell.getNeighbors(map)) {
							if( neighbors.isEnabled() ) {
								found = true;
								break;
							}
						}
						cell.setEnabled( map, found );
						cell.update_rotations_images();
					}
				}
			}
		}

	}

	public static void rotationAleatoire2(Map map){

	}

}
