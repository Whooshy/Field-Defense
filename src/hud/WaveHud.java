package hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import level.World;
import object.Entity;

public class WaveHud extends Entity{

	public WaveHud(float x, float y, float sizeX, float sizeY) {
		super(x, y, sizeX, sizeY);
	}

	public void logic() {
		
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier New", Font.BOLD, 30));
		g.drawString("Wave: " + World.wave, (int) x, (int) y);
	}
	
}
