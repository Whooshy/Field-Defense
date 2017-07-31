package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Projectile extends Entity{
	
	public BufferedImage img;

	protected Projectile(float x, float y, float sizeX, float sizeY, BufferedImage img, float velX, float velY) {
		super(x, y, sizeX, sizeY);
		this.img = img;
		this.velX = velX;
		this.velY = velY;
	}

	public void logic() {

	}

	public void draw(Graphics2D g) {
		g.drawImage(img, (int) x, (int) y, (int) sizeX, (int) sizeY, null);
	}

	
}
