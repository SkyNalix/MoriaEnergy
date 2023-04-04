package dev.moriaenergy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Main extends JFrame {

	Main() {
		super();
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setLocationRelativeTo( null );
		setMinimumSize( new Dimension(400, 400) );

		var frame = this;
		addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed( KeyEvent e ) {
				super.keyPressed( e );
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					frame.setVisible( false );
					frame.dispose();
				}
//				else if(e.getKeyCode() == KeyEvent.VK_M) {
//					try {
//						levelMakerPopup();
//					} catch( Exception ex ) {
//						throw new RuntimeException( ex );
//					}
//				}
			}
		} );
		try {
//			getContentPane().add(new LevelPlayer( 11 ));
//			getContentPane().add(new LevelMaker(null, Hexagon.class.getConstructors()[0]));
			levelMakerPopup();
		} catch( Exception e ) {
			throw new RuntimeException( e );
		}

		pack();
		setVisible( true );
	}

	private void levelMakerPopup() throws Exception {

		JTextField xField = new JTextField(5);
		JTextField yField = new JTextField(5);

		xField.setText( "3" );
		yField.setText("4");

		JPanel initInputPanel = new JPanel();
		initInputPanel.add(new JLabel("width:"));
		initInputPanel.add(xField);
		initInputPanel.add(Box.createHorizontalStrut(15)); // a spacer
		initInputPanel.add(new JLabel("height:"));
		initInputPanel.add(yField);
		initInputPanel.add(new JLabel("Form:"));
		JComboBox<String> formComboBox = new JComboBox<>( new String[]{ "Square", "Hexagon" } );
		initInputPanel.add( formComboBox );

		int result = JOptionPane.showConfirmDialog(null, initInputPanel,
				  "Please enter map's height and width", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			Constructor<?> formConstructor = null;
			switch( formComboBox.getItemAt( formComboBox.getSelectedIndex() ) ) {
				case "Square" -> formConstructor = Square.class.getConstructors()[0];
				case "Hexagon" -> formConstructor = Hexagon.class.getConstructors()[0];
			}

			Map map = new Map( Integer.parseInt( yField.getText() ), Integer.parseInt( xField.getText()));
			for( int i = 0; i < map.getH(); i++ )
				for( int j = 0; j < map.getW(); j++ )
					map.array[i][j] = (Cell)
							  formConstructor.newInstance( null, j, i, new ArrayList<>() );

			getContentPane().removeAll();
			getContentPane().add(new LevelMaker(map, formConstructor));
			revalidate();
			pack();
		}
	}

	public static void main( String[] args ) {
		new Main();
	}

}
