package hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import engine.Engine;
import level.World;
import object.Entity;

public class BeaconHud extends Entity{
	
	public static float power = 100;
	public float powerMod = -0.0025f;
	
	public int audioFrame = 0;
	
	public boolean healing = false;
	public boolean attacked = false;

	public BeaconHud(float x, float y, float sizeX, float sizeY) {
		super(x, y, sizeX, sizeY);
	}

	public void logic() {
		if(!healing) power += powerMod;
		if(healing) power -= powerMod;
		
		if(power <= 0.99f && !attacked) {
			healing = true;
			World.zombiesAwakened = true;
		}
		if(power >= 100) {
			healing = false;
			World.wave += 1;
			World.zombiesAwakened = false;
		}
		
		if(power <= 0) {
			Engine.state = 2;
			power = 100;
			healing = false;
			World.zombiesAwakened = false;
		}
		
		for(Entity e : World.enemies) {
			if(e.bounds().intersects(World.tiles[64][64].bounds()) && !e.isDead()) {
				attacked = true;
				powerMod = 0.0125f;
				if(audioFrame == 0) {
					try {} catch (Exception e1) {
						e1.printStackTrace();
					}
					audioFrame++;
				}
				if(audioFrame >= 1) {
					audioFrame++;
					if(audioFrame > 100) {
						audioFrame = 0;
					}
				}
			}
			if(e.bounds().intersects(World.tiles[64][64].bounds()) && e.isDead()) {
				attacked = false;
				powerMod = -0.0075f;
			}
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Courier New", Font.BOLD, 24));
		g.drawString("" + (int) power + "/100", (int) x, (int) y);
	}
}
