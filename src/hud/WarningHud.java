package hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import object.Entity;

public class WarningHud extends Entity {
	
	public WarningHud(float x, float y, float sizeX, float sizeY) {
		super(x, y, sizeX, sizeY);
	}

	public void logic() {
		
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("Courier New", Font.BOLD, 24));
		g.drawString("Zombies are approaching!", (int) x, (int) y);
	}

}
