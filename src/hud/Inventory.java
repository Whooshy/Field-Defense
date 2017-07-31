package hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import engine.Engine;
import object.Entity;

public class Inventory extends Entity{

	public int woodAmount = 0;
	public int appleAmount = 0;
	public int stoneAmount = 0;
	public int goldOreAmount = 0;
	public int ironOreAmount = 0;
	public int sticksAmount = 0;
	public int pickaxeTier = 0;
	public int axeTier = 0;
	public int ironAmount = 0;
	public int goldAmount = 0;
	public int turretAmount = 1;
	
	public int selection = 0;
	public int itemSelection = 0;
	
	public static boolean hasTier1Pickaxe = false;
	public static boolean hasTier1Axe = false;
	
	public Inventory(float x, float y, float sizeX, float sizeY) {
		super(x, y, sizeX, sizeY);
	}

	public void logic() {

	}

	public void draw(Graphics2D g) {
		if(pickaxeTier == 1) { 
			hasTier1Pickaxe = true;
		}else {
			hasTier1Pickaxe = false;
		}
		if(axeTier == 1) { 
			hasTier1Axe = true;
		}else {
			hasTier1Axe = false;
		}
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y, (int) sizeX, (int) sizeY);
		
		g.setColor(Color.WHITE);
		g.drawRect((int) x, (int) y, (int) sizeX, (int) sizeY);
		
		g.setFont(new Font("Courier New", Font.PLAIN, 14));
		g.drawString("Use the arrows keys to navigate crafting, use the mouse to select items in the inventory section.", x + 450, y + 850);
		
		if(itemSelection == 0) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		g.drawString("Wood: x" + woodAmount, x + 20, y + 40);
		
		if(itemSelection == 1) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		g.drawString("Apple: x" + woodAmount, x + 20, y + 70);
		
		if(itemSelection == 2) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		g.drawString("Stone: x" + stoneAmount, x + 20, y + 100);
		
		if(itemSelection == 3) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		g.drawString("Iron Ore: x" + ironOreAmount, x + 20, y + 130);
		
		if(itemSelection == 4) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		g.drawString("Gold Ore: x" + goldOreAmount, x + 20, y + 160);
		
		if(itemSelection == 5) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		g.drawString("Sticks: x" + sticksAmount, x + 20, y + 190);
		
		if(itemSelection == 6) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		g.drawString("Tier " + pickaxeTier + " Pickaxe", x + 20, y + 220);
		
		if(itemSelection == 7) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		g.drawString("Tier " + axeTier + " Axe", x + 20, y + 250);
		
		if(itemSelection == 8) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		g.drawString("Iron: x" + ironAmount, x + 20, y + 280);
		
		if(itemSelection == 9) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		g.drawString("Gold: x" + goldAmount, x + 20, y + 310);
		
		if(itemSelection == 10) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		g.drawString("Turret: x" + turretAmount, x + 20, y + 340);
		
		if(selection == 0) {
			g.setColor(Color.BLUE);
		}else {
			g.setColor(Color.WHITE);
		}
		
		g.setFont(new Font("Courier New", Font.PLAIN, 18));
		
		if(selection == 0) {
			g.setColor(Color.BLUE);
			g.drawString("Ingredients: 2 Wood", x + 50, y + (Engine.HEIGHT) - 60);
		}else {
			g.setColor(Color.WHITE);
		}
		g.drawString("Sticks", x + (Engine.WIDTH / 2), y + 35);
		if(selection == 1) {
			g.setColor(Color.BLUE);
			g.drawString("Ingredients: 4 Sticks, 5 Stone", x + 50, y + (Engine.HEIGHT) - 60);
		}else {
			g.setColor(Color.WHITE);
		}
		g.drawString("Tier I Pickaxe", x + (Engine.WIDTH / 2), y + 55);
		if(selection == 2) {
			g.setColor(Color.BLUE);
			g.drawString("Ingredients: 3 Sticks, 4 Stone", x + 50, y + (Engine.HEIGHT) - 60);
		}else {
			g.setColor(Color.WHITE);
		}
		g.drawString("Tier I Axe", x + (Engine.WIDTH / 2), y + 75);
		if(selection == 3) {
			g.setColor(Color.BLUE);
			g.drawString("Ingredients: 2 Iron Ore", x + 50, y + (Engine.HEIGHT) - 60);
		}else {
			g.setColor(Color.WHITE);
		}
		g.drawString("Iron", x + (Engine.WIDTH / 2), y + 95);
		if(selection == 4) {
			g.setColor(Color.BLUE);
			g.drawString("Ingredients: 2 Gold Ore", x + 50, y + (Engine.HEIGHT) - 60);
		}else {
			g.setColor(Color.WHITE);
		}
		g.drawString("Gold", x + (Engine.WIDTH / 2), y + 115);
		if(selection == 5) {
			g.setColor(Color.BLUE);
			g.drawString("Ingredients: 5 Iron, 10 Stone, 2 Sticks", x + 50, y + (Engine.HEIGHT) - 60);
		}else {
			g.setColor(Color.WHITE);
		}
		g.drawString("Turret", x + (Engine.WIDTH / 2), y + 135);
	}

}
