package dev.moriaenergy;

import javax.swing.*;

import java.awt.*;

public class Displayer extends JPanel {

	private final Map map;
	private int cell_width = 50, cell_height = 50;

	Displayer( Map map ) {
		this.map = map;
		setBackground( Color.black );
	}

	void udpate_size(int cell_width, int cell_height) {
		this.cell_width = cell_width;
		this.cell_height = cell_height;
		setPreferredSize( new Dimension(map.getW()*cell_width, map.getH()*cell_height) );
	}


	@Override
	public void paint( Graphics g ) {
		super.paint( g );
		for( Cell[] cells : map.array )
			for( Cell cell : cells )
				cell.paint( g, cell_width, cell_height );
	}

}
