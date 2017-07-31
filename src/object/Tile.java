package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import hud.Inventory;
import level.World;
import utilities.Images;
import utilities.Reference;

public class Tile extends Entity {
	
	public static final int _Empty = 0;
	public static final int Grass = 1;
	public static final int Tree = 2;
	public static final int Flowers = 3;
	public static final int Beacon = 4;
	public static final int RockClump = 5;
	public static final int Sinkhole = 6;
	public static final int Stone = 7;
	public static final int IronOre = 8;
	public static final int GoldOre = 9;
	public static final int StoneFloor = 10;
	public static final int Rope = 11;
	public static final int Turret = 12;
	
	public int durability;
	public boolean solid;
	
	public boolean onCooldown = false;
	
	public float hardness = 0;
	
	public float frame = 0;
	
	public int id;

	public Tile(float x, float y, float sizeX, float sizeY, int id) {
		super(x, y, sizeX, sizeY);
		this.id = id;
	}

	public void logic() {
		if(id == Turret) {
			for(Entity e : World.enemies)
			if(e.bounds().intersects(sightRange()) && !onCooldown) {
				World.projectiles.add(new Projectile(x, y, Reference.GRIDSIZE, Reference.GRIDSIZE, Images.particle[0], (e.getX() - x) / 20, (e.getY() - y) / 20));
				onCooldown = true;
			}
			if(onCooldown) {
				frame++;
				if(frame >= 150) {
					frame = 0;
					try {} catch (Exception e1) {
						e1.printStackTrace();
					}
					onCooldown = false;
				}
			}
		}
	}

	public void draw(Graphics2D g) {
		switch(id) {
		case _Empty:
			break;
		case Grass:
			solid = false;
			hardness = 0;
			g.drawImage(Images.tiles[0], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		case Tree:
			solid = true;
			hardness = 2;
			if(Inventory.hasTier1Axe) hardness = 1;
			g.drawImage(Images.tiles[1], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		case Flowers:
			solid = false;
			hardness = 0;
			g.drawImage(Images.tiles[2], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		case Beacon:
			solid = true;
			hardness = 0;
			g.drawImage(Images.tiles[3], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		case RockClump:
			solid = true;
			hardness = 5;
			if(Inventory.hasTier1Pickaxe) hardness = 3;
			g.drawImage(Images.tiles[4], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		case Sinkhole:
			solid = false;
			hardness = 0;
			g.drawImage(Images.tiles[5], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		case Stone:
			solid = true;
			hardness = 6;
			if(Inventory.hasTier1Pickaxe) hardness = 3.5f;
			g.drawImage(Images.tiles[6], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		case IronOre:
			solid = true;
			hardness = 7;
			if(Inventory.hasTier1Pickaxe) hardness = 4;
			g.drawImage(Images.tiles[7], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		case GoldOre:
			solid = true;
			hardness = 10;
			if(Inventory.hasTier1Pickaxe) hardness = 5.5f;
			g.drawImage(Images.tiles[8], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		case StoneFloor:
			solid = false;
			hardness = 0;
			g.drawImage(Images.tiles[9], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		case Rope:
			solid = false;
			hardness = 0;
			g.drawImage(Images.tiles[10], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		case Turret:
			solid = true;
			hardness = 4;
			g.drawImage(Images.tiles[11], (int) x, (int) y, (int) sizeX, (int) sizeY, null);
			break;
		}
	}
	
	public Rectangle sightRange() {
		return new Rectangle((int) x - Reference.GRIDSIZE * 12, (int) y - Reference.GRIDSIZE * 12, Reference.GRIDSIZE * 24, Reference.GRIDSIZE * 24);
	}

}
