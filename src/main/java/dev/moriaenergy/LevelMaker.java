package dev.moriaenergy;

import javax.swing.*;
import java.awt.*;

public class LevelMaker extends JPanel {


	private Map map;
	int HEIGHT = 500, WIDTH = 500, cell_width, cell_height;

	public LevelMaker() {
		setBackground( Color.black );
		updateSizes();

		JTextField xField = new JTextField(5);
		JTextField yField = new JTextField(5);

		JPanel sizeInputPanel = new JPanel();
		sizeInputPanel.add(new JLabel("width:"));
		sizeInputPanel.add(xField);
		sizeInputPanel.add(Box.createHorizontalStrut(15)); // a spacer
		sizeInputPanel.add(new JLabel("height:"));
		sizeInputPanel.add(yField);

		int result = JOptionPane.showConfirmDialog(null, sizeInputPanel,
					 "Please enter map's height and width", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			map = new Map(Integer.parseInt(yField.getText()), Integer.parseInt(xField.getText()));
			updateSizes();
		}

	}

	void updateSizes() {
		if(map == null) return;
		cell_width = WIDTH / map.getW();
		cell_height = HEIGHT / map.getH();
		setPreferredSize( new Dimension( WIDTH + cell_width / 2, HEIGHT + cell_height / 2) );
	}

}
