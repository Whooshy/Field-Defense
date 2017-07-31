package level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import engine.Engine;
import hud.BeaconHud;
import hud.Inventory;
import object.Entity;
import object.GamePlayer;
import object.Tile;
import object.Zombie;
import utilities.Images;
import utilities.Reference;

public class World {
	
	public static GamePlayer player;
	
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Entity> caveEntities = new ArrayList<Entity>();
	
	public static List<Entity> enemies = new ArrayList<Entity>();
	public static List<Entity> projectiles = new ArrayList<Entity>();
	
	public List<Entity> huds = new ArrayList<Entity>();
	
	public Random random = new Random();
	public BeaconHud bchud = new BeaconHud(((Reference.WORLD_SIZE * Reference.GRIDSIZE) / 2), ((Reference.WORLD_SIZE * Reference.GRIDSIZE) / 2) - 32, 0, 0);
	public Inventory inventory = new Inventory(20, 20, Engine.WIDTH - 40, Engine.HEIGHT - 40);
	
	public static Tile[][] tiles = new Tile[Reference.WORLD_SIZE][Reference.WORLD_SIZE];
	public static Tile[][] caveTiles = new Tile[Reference.WORLD_SIZE][Reference.WORLD_SIZE];
	
	public static boolean inv = false;
	public static boolean lmb = false;
	public static boolean rmb = false;
	public static boolean cav = false;
	
	private boolean playerState = false;
	
	public int loadCaveState = 0;
	
	public Rectangle cursor = new Rectangle(-2, -2, 1, 1);
	
	public static float breakTime = 0;
	
	public static boolean zombiesAwakened = false;
	
	public static int wave = 1;
	
	public static Rectangle topEdge = new Rectangle(0, 0, Reference.GRIDSIZE * Reference.WORLD_SIZE, Reference.GRIDSIZE * 15);
	public static Rectangle leftEdge = new Rectangle(0, 0, Reference.GRIDSIZE * 15, Reference.GRIDSIZE * Reference.WORLD_SIZE);
	public static Rectangle bottomEdge = new Rectangle(0, Reference.GRIDSIZE * Reference.WORLD_SIZE - (Reference.GRIDSIZE * 15), Reference.GRIDSIZE * Reference.WORLD_SIZE, Reference.GRIDSIZE * 15);
	public static Rectangle rightEdge = new Rectangle(Reference.GRIDSIZE * Reference.WORLD_SIZE - (Reference.GRIDSIZE * 15), 0, Reference.GRIDSIZE * 15, Reference.GRIDSIZE * Reference.WORLD_SIZE);
	
	public World() {
		for(int x = 0; x < Reference.WORLD_SIZE; x++) {
			for(int y = 0; y < Reference.WORLD_SIZE; y++) {
				tiles[x][y] = new Tile(x * Reference.GRIDSIZE, y * Reference.GRIDSIZE, Reference.GRIDSIZE, Reference.GRIDSIZE, Tile.Grass);
				caveTiles[x][y] = new Tile(x * Reference.GRIDSIZE, y * Reference.GRIDSIZE, Reference.GRIDSIZE, Reference.GRIDSIZE, Tile.Stone);
				entities.add(tiles[x][y]);
				caveEntities.add(caveTiles[x][y]);
				if(random.nextInt(25) == 0) {
					tiles[x][y].id = Tile.Flowers;
				}
				if(random.nextInt(10) == 0) {
					tiles[x][y].id = Tile.Tree;
				}
				if(random.nextInt(100) == 0) {
					tiles[x][y].id = Tile.RockClump;
				}
				
				if(random.nextInt(50) == 0) {
					caveTiles[x][y].id = Tile.IronOre;
				}
				if(random.nextInt(200) == 0) {
					caveTiles[x][y].id = Tile.GoldOre;
				}
			}
		}
		
		for(int i = 59; i <= 69; i++) {
			for(int j = 59; j <= 69; j++) {
				caveTiles[i][j].id = Tile.StoneFloor;
			}
		}
		
		caveTiles[64][64].id = Tile.Rope;
			
		tiles[Reference.WORLD_SIZE / 2][Reference.WORLD_SIZE / 2].id = Tile.Beacon;
		player = new GamePlayer(((Reference.WORLD_SIZE * Reference.GRIDSIZE) / 2) - 300, ((Reference.WORLD_SIZE * Reference.GRIDSIZE) / 2) - 300, Reference.GRIDSIZE, Reference.GRIDSIZE);
			
		entities.add(player);
		caveEntities.add(player);
		
		huds.add(bchud);
		
		for(int i = 0; i < 5; i++) {
			enemies.add(new Zombie(random.nextInt(Reference.GRIDSIZE * Reference.WORLD_SIZE), random.nextInt(Reference.GRIDSIZE * Reference.WORLD_SIZE), Reference.GRIDSIZE, Reference.GRIDSIZE * 2));
		}
	}
	
	public void saveWorld() {
		File saveFile = new File("save1-world.asf");
		if(!saveFile.exists()) {
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		FileWriter writer;
		try {
			writer = new FileWriter(saveFile);
			BufferedWriter bw = new BufferedWriter(writer);
				
			for(int x = 0; x < tiles.length; x++) {
				for(int y = 0; y < tiles[0].length; y++) {
					bw.write("" + tiles[x][y].id);
					bw.newLine();
				}
			}
				
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveCaves() {
		File saveFile = new File("save1-caves.asf");
		if(!saveFile.exists()) {
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		FileWriter writer;
		try {
			writer = new FileWriter(saveFile);
			BufferedWriter bw = new BufferedWriter(writer);
				
			for(int x = 0; x < caveTiles.length; x++) {
				for(int y = 0; y < caveTiles[0].length; y++) {
					bw.write("" + caveTiles[x][y].id);
					bw.newLine();
				}
			}
				
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadLevel() {
		File saveFile = new File("save1-world.asf");
		int[][] ids = getIDs(saveFile);
		File saveFile2 = new File("save1-caves.asf");
		int[][] ids2 = getIDs(saveFile2);
		for(int x = 0; x < ids.length; x++) {
			for(int y = 0; y < ids[0].length; y++) {
				tiles[x][y].id = ids[x][y];
				caveTiles[x][y].id = ids2[x][y];
			}
		}
	}
	
	public int[][] getIDs(File file){
		int[][] ids = new int[0][0];
		
		try {
			ids = new int[tiles.length][tiles[0].length];
			
			Scanner s = new Scanner(file);
			
			for(int x = 0; x < ids.length; x++) {
				for(int y = 0; y < ids[0].length; y++) {
					ids[x][y] = s.nextInt();
					s.nextLine();
					System.out.println("" + ids[x][y]);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ids;
	}
	
	public void update(Graphics2D g) {
		cursor = new Rectangle((int) Engine.mouseX, (int) Engine.mouseY, 1, 1);
		Rectangle loadBounds = new Rectangle((int) player.getX() - (Engine.WIDTH / 2), (int) player.getY() - (Engine.HEIGHT / 2), Engine.WIDTH + 100, Engine.HEIGHT + 100);
		
		if(!cav) {
			playerState = false;
			for(Entity e : entities) {
				if(e.bounds().intersects(loadBounds)) e.update(g);
			}
		}
		
		if(zombiesAwakened) for(Entity e : enemies) {
			e.logic();
			if(e.bounds().intersects(loadBounds)){
				e.draw(g);
			}
		}
		
		if(cav) {
			if(!playerState) {
				player.setX((Reference.WORLD_SIZE / 2) * Reference.GRIDSIZE + (Reference.GRIDSIZE * 3));
				player.setY((Reference.WORLD_SIZE / 2) * Reference.GRIDSIZE);
				playerState = true;
			}
			for(Entity e : caveEntities) {
				if(e.bounds().intersects(loadBounds)) e.update(g);
			}
			g.drawImage(Images.cave_vignette, (int) player.getX() - (Engine.WIDTH / 2) + (Reference.GRIDSIZE / 2) - 5, (int) player.getY() - (Engine.HEIGHT / 2) + Reference.GRIDSIZE - 5, Engine.WIDTH + 10, Engine.HEIGHT + 10, null);
		}
		
		for(Entity e : projectiles) {
			e.update(g);
		}
		
		if(inv && !entities.contains(inventory)) {
			entities.add(inventory);
			caveEntities.add(inventory);
			inventory.setX(Engine.camX + 20);
			inventory.setY(Engine.camY + 20);
		}else if(!inv && entities.contains(inventory)) {
			entities.remove(inventory);
			caveEntities.remove(inventory);
		}
		
		g.setColor(Color.WHITE);
		g.draw(cursor);
		
		for(Entity e : huds) {
			e.update(g);
		}
		
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[0].length; y++) {
				if(lmb && !cav && cursor.intersects(tiles[x][y].bounds()) && tiles[x][y].solid && tiles[x][y].id != Tile.Beacon) {
					breakTime += (1 / tiles[x][y].hardness);
					//System.out.println(breakTime);
					if(breakTime >= 0 && breakTime < 10) {
						g.drawImage(Images.breaksheet_1.getSubimage(0, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 10 && breakTime < 20) {
						g.drawImage(Images.breaksheet_1.getSubimage(8, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 20 && breakTime < 30) {
						g.drawImage(Images.breaksheet_1.getSubimage(16, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 30 && breakTime < 40) {
						g.drawImage(Images.breaksheet_1.getSubimage(24, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 40 && breakTime < 50) {
						g.drawImage(Images.breaksheet_1.getSubimage(32, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 50 && breakTime < 60) {
						g.drawImage(Images.breaksheet_1.getSubimage(40, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 60 && breakTime < 70) {
						g.drawImage(Images.breaksheet_1.getSubimage(48, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 70 && breakTime < 80) {
						g.drawImage(Images.breaksheet_1.getSubimage(56, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 80) {
						if(tiles[x][y].id == Tile.Tree) {
							inventory.woodAmount += random.nextInt(2) + 1;
							inventory.appleAmount += random.nextInt(2);
							tiles[x][y].id = Tile.Grass;
						}
						if(tiles[x][y].id == Tile.RockClump) tiles[x][y].id = Tile.Sinkhole;
						if(caveTiles[x][y].id == Tile.Turret) {
							//System.out.println("Hello!");
							inventory.turretAmount += 1;
							caveTiles[x][y].id = Tile.StoneFloor;
						}
						breakTime = 0;
					}
				}else if(lmb && cav && cursor.intersects(caveTiles[x][y].bounds()) && caveTiles[x][y].solid && caveTiles[x][y].id != Tile.Beacon) {
					breakTime += (1 / caveTiles[x][y].hardness);
					//System.out.println(breakTime);
					if(breakTime >= 0 && breakTime < 10) {
						g.drawImage(Images.breaksheet_1.getSubimage(0, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 10 && breakTime < 20) {
						g.drawImage(Images.breaksheet_1.getSubimage(8, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 20 && breakTime < 30) {
						g.drawImage(Images.breaksheet_1.getSubimage(16, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 30 && breakTime < 40) {
						g.drawImage(Images.breaksheet_1.getSubimage(24, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 40 && breakTime < 50) {
						g.drawImage(Images.breaksheet_1.getSubimage(32, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 50 && breakTime < 60) {
						g.drawImage(Images.breaksheet_1.getSubimage(40, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 60 && breakTime < 70) {
						g.drawImage(Images.breaksheet_1.getSubimage(48, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 70 && breakTime < 80) {
						g.drawImage(Images.breaksheet_1.getSubimage(56, 0, 8, 8), (int) tiles[x][y].getX(), (int) tiles[x][y].getY(), Reference.GRIDSIZE, Reference.GRIDSIZE, null);
					}
					if(breakTime >= 80) {
						if(caveTiles[x][y].id == Tile.RockClump) caveTiles[x][y].id = Tile.Sinkhole;
						if(caveTiles[x][y].id == Tile.Stone) {
							//System.out.println("Hello!");
							inventory.stoneAmount += 1;
							caveTiles[x][y].id = Tile.StoneFloor;
						}
						if(caveTiles[x][y].id == Tile.IronOre) {
							//System.out.println("Hello!");
							inventory.ironOreAmount += 1;
							caveTiles[x][y].id = Tile.StoneFloor;
						}
						if(caveTiles[x][y].id == Tile.GoldOre) {
							//System.out.println("Hello!");
							inventory.goldOreAmount += 1;
							caveTiles[x][y].id = Tile.StoneFloor;
						}
						if(caveTiles[x][y].id == Tile.Turret) {
							//System.out.println("Hello!");
							inventory.turretAmount += 1;
							caveTiles[x][y].id = Tile.StoneFloor;
						}
						breakTime = 0;
					}
				}else if(!lmb){
					breakTime = 0;
				}
				
				if(rmb && cursor.intersects(tiles[x][y].bounds()) && inventory.turretAmount >= 1 && inventory.itemSelection == 10) {
					tiles[x][y].id = Tile.Turret;
					inventory.turretAmount -= 1;
				}
			}
		}
	}

}
