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
		int size =  Math.min(cell_width, cell_height);
		this.cell_width = size;
		this.cell_height = size;
		setPreferredSize( new Dimension(map.getW()*size,
										map.getH()*size + size/2) );
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );
		for( Cell[] cells : map.array )
			for( Cell cell : cells )
				cell.paint( g, cell_width, cell_height );
	}

}
