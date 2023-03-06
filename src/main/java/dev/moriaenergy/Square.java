package dev.moriaenergy;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Square extends Cell {

	Square(Tile tile, int x, int y) {
		this.tile = tile;
		this.x = x;
		this.y = y;
		this.rotation = 0;
		this.voisins = new Square[4];
	}

	// src : https://blog.idrsolutions.com/image-rotation-in-java/
	private static BufferedImage rotateImage( Image image, int angle ) {
		final double rads = Math.toRadians(angle);
//		final double sin = Math.abs(Math.sin(rads));
//		final double cos = Math.abs(Math.cos(rads));
//		final int w = (int) Math.floor(image.getWidth(null) * cos + image.getHeight(null) * sin);
//		final int h = (int) Math.floor(image.getHeight(null) * cos + image.getWidth(null) * sin);

		int w = image.getWidth( null );
		int h = image.getWidth( null );

		BufferedImage rotatedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = AffineTransform.getRotateInstance( rads, w / 2, h / 2 );

		final AffineTransformOp rotateOp = new AffineTransformOp( at, AffineTransformOp.TYPE_BILINEAR);
		rotateOp.filter( (BufferedImage) image,rotatedImage);
		return rotatedImage;
	}

	@Override
	void paint( Graphics g ) {
		int X = tile.width*x;
		int Y = tile.height*y;
		int W = tile.width;
		int H = tile.height;

		g.drawImage( Tile.valueOf( "SQUARE_" + (on ? "ON" : "OFF") ).getImage(),
					 X,Y,W,H, null);
		BufferedImage buffImage = rotateImage(tile.getImage(), rotation*90);
		g.drawImage( buffImage, X, Y, W, H,null );
	}

	@Override
	void rotate() {

	}

}
