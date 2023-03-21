package dev.moriaenergy;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Utils {

	public static BufferedImage rotate( BufferedImage image, double angle ) {
		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage rotated = new BufferedImage(width, height, image.getType());

		Graphics2D g2d = rotated.createGraphics();
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(angle), width / 2f, height / 2f);

		g2d.setTransform(at);
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		return rotated;
	}

}
