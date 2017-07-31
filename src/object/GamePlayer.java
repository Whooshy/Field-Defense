package object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import engine.Engine;
import level.World;
import utilities.Images;
import utilities.Reference;

public class GamePlayer extends Entity{
	
	public float frame = 0;
	
	public static int direction = 0;
	public float speed = 1.5f;
	
	public int audioFrame = 0;
	
	public GamePlayer(float x, float y, float sizeX, float sizeY) {
		super(x, y, sizeX, sizeY);
	}

	public void logic() {
		for(int x = 0; x < World.tiles.length; x++) {
			for(int y = 0; y < World.tiles[0].length; y++) {
				if(!World.cav) {
					if(topBounds().intersects(World.tiles[x][y].bounds()) && World.tiles[x][y].solid) {
						if(direction == 7) direction = 3;
					}
					if(leftBounds().intersects(World.tiles[x][y].bounds()) && World.tiles[x][y].solid) {
						if(direction == 5) direction = 2;
					}
					if(rightBounds().intersects(World.tiles[x][y].bounds()) && World.tiles[x][y].solid) {
						if(direction == 4) direction = 1;
					}
					if(bottomBounds().intersects(World.tiles[x][y].bounds()) && World.tiles[x][y].solid) {
						if(direction == 6) direction = 0;
					}
					if(topBounds().intersects(World.tiles[x][y].bounds()) && World.tiles[x][y].id == Tile.Sinkhole) { 
						World.cav = true;
					}
				}
				if(World.cav) {
					if(topBounds().intersects(World.caveTiles[x][y].bounds()) && World.caveTiles[x][y].solid) {
						if(direction == 7) direction = 3;
					}
					if(leftBounds().intersects(World.caveTiles[x][y].bounds()) && World.caveTiles[x][y].solid) {
						if(direction == 5) direction = 2;
					}
					if(rightBounds().intersects(World.caveTiles[x][y].bounds()) && World.caveTiles[x][y].solid) {
						if(direction == 4) direction = 1;
					}
					if(bottomBounds().intersects(World.caveTiles[x][y].bounds()) && World.caveTiles[x][y].solid) {
						if(direction == 6) direction = 0;
					}
					if(topBounds().intersects(World.caveTiles[x][y].bounds()) && World.caveTiles[x][y].id == Tile.Rope) { 
						World.cav = false;
					}
				}
				
				if(topBounds().intersects(World.topEdge)) {
					if(direction == 7) direction = 3;
				}
				if(leftBounds().intersects(World.leftEdge)) {
					if(direction == 5) direction = 2;
				}
				if(rightBounds().intersects(World.rightEdge)) {
					if(direction == 4) direction = 1;
				}
				if(bottomBounds().intersects(World.bottomEdge)) {
					if(direction == 6) direction = 0;
				}
			}
		}
	}

	public void draw(Graphics2D g) {
		try {} catch (Exception e) {
			e.printStackTrace();
		}
		
		switch(direction) {
		case 0:
			g.drawImage(Images.player[0], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			velX = 0;
			velY = 0;
			break;
		case 1:
			g.drawImage(Images.player[1], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			velX = 0;
			velY = 0;
			break;
		case 2:
			g.drawImage(Images.player[2], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			velX = 0;
			velY = 0;
			break;
		case 3:
			g.drawImage(Images.player[3], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			velX = 0;
			velY = 0;
			break;
		case 4:
			g.drawImage(Images.player[4 + (int) frame], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			frame += 0.04f;
			if(frame >= 2) frame = 0;
			
			audioFrame++;
			
			if(audioFrame == 1) {
			}
			
			if(audioFrame >= 70) audioFrame = 0;
			
			velX = speed;
			break;
		case 5:
			g.drawImage(Images.player[7 - (int) frame], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			frame += 0.04f;
			if(frame >= 2) frame = 0;
			
			audioFrame++;
			
			if(audioFrame == 1) {
			}
			
			if(audioFrame >= 70) audioFrame = 0;
			
			velX = -speed;
			break;
		case 6:
			g.drawImage(Images.player[8 + (int) frame], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			frame += 0.02f;
			if(frame >= 2) frame = 0;
			
			audioFrame++;
			
			if(audioFrame == 1) {
			}
			
			if(audioFrame >= 70) audioFrame = 0;
			
			velY = speed;
			break;
		case 7:
			g.drawImage(Images.player[11 - (int) frame], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			frame += 0.02f;
			if(frame >= 2) frame = 0;
			
			audioFrame++;
			
			if(audioFrame == 1) {
				
			}
			
			if(audioFrame >= 70) audioFrame = 0;
			
			velY = -speed;
			break;
		}
	}
	
	public Rectangle topBounds() {
		return new Rectangle((int) bounds().x + 10, (int) bounds().y + Reference.GRIDSIZE, Reference.GRIDSIZE - 20, 10);
	}
	
	public Rectangle leftBounds() {
		return new Rectangle((int) bounds().x, (int) bounds().y + Reference.GRIDSIZE + 10, Reference.GRIDSIZE / 2, 42);
	}
	
	public Rectangle rightBounds() {
		return new Rectangle((int) bounds().x + (Reference.GRIDSIZE / 2), (int) bounds().y + Reference.GRIDSIZE + 10, Reference.GRIDSIZE / 2, 42);
	}
	
	public Rectangle bottomBounds() {
		return new Rectangle((int) bounds().x + 10, (int) bounds().y + Reference.GRIDSIZE + 52, Reference.GRIDSIZE - 20, 12);
	}
}	
