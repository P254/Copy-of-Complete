/*
 * Simplified version of Sprite, mcs - feb 2000
 * Modified to use (x,y) for top-left corner
 */

import java.awt.*;

class ImageSprite extends SpriteObject {
	protected Image image;

	ImageSprite (Image image, double x, double y) {
		super(x, y, 0, 0);
		setImage(image);
	}

	ImageSprite (Image image, double x, double y, int width, int height) {
		super(x, y, width, height);
		//System.out.println(width+" "+height);
		setImage(image,width,height);
	}


	public void setImage (Image img) {
		if ((image = img) == null)
			width = height = 0;
		else {
			width = img.getWidth(null);
			height = img.getHeight(null);
		}
	}

	public void setImage (Image img, int width, int height) {
		if ((image = img) == null)
			width = height = 0;
		else {
			width = width;
			height = height;
		}
	}

	public void drawSprite (Graphics g) {
		g.drawImage(image, (int)x, (int)y, null);
	}
}  // class ImageSprite

// EOF
