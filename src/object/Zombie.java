package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import hud.BeaconHud;
import level.World;
import utilities.Images;
import utilities.Reference;

public class Zombie extends Entity {
	
	public int direction = 0;
	public int frame = 0;
	
	public int maxHealth = 5;
	
	public int health = 5;
	public int invFrame = 0;
	public int deadFrame = 0;
	
	public boolean isInvincible = true;
	public boolean invCooldown = false;
	
	public float speed = 1;
	public float finalSpeed = speed * (BeaconHud.power / 30);
	
	public Random random = new Random();
	
	public boolean activated = false;

	public Zombie(float x, float y, float sizeX, float sizeY) {
		super(x, y, sizeX, sizeY);
	}

	public void logic() {
		if((Reference.GRIDSIZE * Reference.WORLD_SIZE) / 2 <= x) {
			velX = -speed;
			direction = 5;
		}
		if((Reference.GRIDSIZE * Reference.WORLD_SIZE) / 2 >= y) {
			velY = speed;
			direction = 6;
		}
		if((Reference.GRIDSIZE * Reference.WORLD_SIZE) / 2 >= x) {
			velX = speed;
			direction = 4;
		}
		if((Reference.GRIDSIZE * Reference.WORLD_SIZE) / 2 <= y) {
			velY = -speed;
			direction = 7;
		}
		
		if(World.zombiesAwakened) {
			activated = true;
		}else {
			activated = false;
		}
		
		if(activated && !invCooldown) {
			isInvincible = false;
			x += velX;
			y += velY;
		}
		
		for(Entity e : World.projectiles) {
			if(e.bounds().intersects(bounds()) && !invCooldown) {
				health -= 1;
				invCooldown = true;
			}
			if(invCooldown) {
				invFrame++;
				if(invFrame >= 30) {
					invCooldown = false;
					isInvincible = false;
					invFrame = 0;
				}
			}
		}
		
		maxHealth = (4 + World.wave) * 2;
		speed = World.wave;
		
		if(health <= 0) {
			dead = true;
			deadFrame++;
			if(deadFrame >= 5) {
				dead = false;
				die();
			}
		}
	}

	public void draw(Graphics2D g) {
		switch(direction) {
		case 0:
			g.drawImage(Images.zombie[0], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			break;
		case 1:
			g.drawImage(Images.zombie[1], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			break;
		case 2:
			g.drawImage(Images.zombie[2], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			break;
		case 3:
			g.drawImage(Images.zombie[3], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			break;
		case 4:
			g.drawImage(Images.zombie[4 + (int) frame], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			frame += 0.1f;
			if(frame >= 2) frame = 0;
			break;
		case 5:
			g.drawImage(Images.zombie[7 - (int) frame], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			frame += 0.1f;
			if(frame >= 2) frame = 0;
			break;
		case 6:
			g.drawImage(Images.zombie[8 + (int) frame], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			frame += 0.05f;
			if(frame >= 2) frame = 0;
			break;
		case 7:
			g.drawImage(Images.zombie[11 - (int) frame], (int) x, (int) y, Reference.GRIDSIZE, Reference.GRIDSIZE * 2, null);
			frame += 0.05f;
			if(frame >= 2) frame = 0;
			break;
		}
	}
	
	public void die() {
		x = random.nextInt(Reference.GRIDSIZE * Reference.WORLD_SIZE);
		y = random.nextInt(Reference.GRIDSIZE * Reference.WORLD_SIZE);
		health = maxHealth;
		dead = false;
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
