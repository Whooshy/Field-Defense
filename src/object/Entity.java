package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Entity {
	
	protected float x, y, sizeX, sizeY, velX, velY;
	protected boolean dead;
	
	protected Entity(float x, float y, float sizeX, float sizeY) {
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		velX = 0;
		velY = 0;
	}

	public void update(Graphics2D g) {
		logic();
		draw(g);
		
		x += velX;
		y += velY;
	}
	
	public abstract void logic();
	public abstract void draw(Graphics2D g);
	
	public Rectangle bounds() {
		return new Rectangle((int) x, (int) y, (int) sizeX, (int) sizeY);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getSizeX() {
		return sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public float getVelX() {
		return velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setSizeX(float sizeX) {
		this.sizeX = sizeX;
	}

	public void setSizeY(float sizeY) {
		this.sizeY = sizeY;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	

}
