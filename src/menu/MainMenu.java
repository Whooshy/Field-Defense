package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import object.Entity;

public class MainMenu extends Entity{

	public MainMenu(float x, float y, float sizeX, float sizeY) {
		super(x, y, sizeX, sizeY);
	}

	public void logic() {

	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y, (int) sizeX, (int) sizeY);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier New", Font.BOLD, 40));
		g.drawString("Field Defense", x + 480, y + 200);

		g.setFont(new Font("Courier New", Font.BOLD, 18));
		g.drawString("made by Trevor Strickland, within 48 hours", x + 415, y + 230);
		
		g.setFont(new Font("Courier New", Font.BOLD, 25));
		g.drawString("<Please press ENTER to play>", x + 425, y + 600);
	}
	
	

}
