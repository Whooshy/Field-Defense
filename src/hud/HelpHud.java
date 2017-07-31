package hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import level.World;
import object.Entity;

public class HelpHud extends Entity{

	public HelpHud(float x, float y, float sizeX, float sizeY) {
		super(x, y, sizeX, sizeY);
	}

	public void logic() {
		
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier New", Font.PLAIN, 15));
		if(World.wave == 1 && !World.zombiesAwakened) g.drawString("Use WASD to move, E to open inventory, LMB to collect resources. Get 2 turrets before the beacon runs out of power! Hint: Mine the rocks", x, y);
		if(World.wave == 1 && World.zombiesAwakened) g.drawString("They're here. Hopefully you have at least two turrets. When the beacon reaches full power, the zombies will dissapear.", x, y);
		if(World.wave > 1) g.drawString("That's all for me. Try to see how far you can get in this game. Pressing K and L will save/load the world, but not the player's inventory.", x, y);

		
	}

}
