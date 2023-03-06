package dev.moriaenergy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum Tile {

	SQUARE_OFF(0,0,120,120),
	SQUARE_ON(0, 3*120,120,120),
	S_W_OFF(120,120, 120,120 ),
	S_W_ON(120,4*120, 120,120 ),
	S_L_OFF(2*120,120,120,120),
	S_L_ON(2*120,4*120,120,120),
	S_S_ON(0,4*120,120,120),
	S_SHORT_LINE_OFF(0,2*120, 120,120),
	S_DIAG_LINE_OFF(120,2*120,120,120),
	S_LONG_LINE_OFF(2*120,2*120, 120,120),
	S_SHORT_LINE_ON(0,5*120, 120,120),
	S_DIAG_LINE_ON(120,5*120,120,120),
	S_LONG_LINE_ON(2*120,5*120, 120,120),
	HEX_OFF(3*120, 0, 120, 104),
	HEX_ON(3*120,3*120, 120,104),
	H_W_OFF(4*120,120, 120,104),
	H_W_ON(4*120,4*120, 120,104),
	H_L_OFF(5*120,120, 120,104),
	H_L_ON(5*120,4*120, 120,104),
	H_S_ON(3*120,4*120,120, 104),
	H_SHORT_LINE_OFF(3*120,2*120, 120,104),
	H_SHORT_LINE_ON(3*120,5*120, 120,104),
	H_SHORT_DIAG_LINE_OFF(4*120,2*120,120,104),
	H_SHORT_DIAG_LINE_ON(4*120,5*120,120,104),
	H_LONG_DIAG_LINE_OFF(5*120,2*120,120,104),
	H_LONG_DIAG_LINE_ON(5*120,5*120,120,104),
	H_LONG_LINE_OFF(6*120,2*120, 120,104),
	H_LONG_LINE_ON(6*120,5*120, 120,104);

	final int x, y, width, height;

	Tile(int x, int y, int width, int height) {
		this.x = x; this.y = y; this.width = width; this.height = height;
	}

	Image getImage() {
		return tileMap.getSubimage( x, y, width, height );
	}


	private static BufferedImage tileMap;

	static {
		try {
			tileMap = ImageIO.read( new File( "src/main/resources/tuiles.png") );
		} catch( IOException e ) {
			throw new RuntimeException( e );
		}
	}

}
