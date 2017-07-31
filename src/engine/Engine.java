package engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import hud.HelpHud;
import hud.WarningHud;
import hud.WaveHud;
import level.World;
import menu.MainMenu;
import utilities.Images;
import utilities.Reference;

public class Engine extends Canvas implements Runnable, KeyListener, MouseListener{
	
	private Thread thread;
	
	public static int frameCount = 0;
	public static int FPS = 0;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 900;
	
	public static int state = 2;
	
	public static float camX, camY;
	public static float mouseX, mouseY;
	
	public Dimension size = new Dimension(WIDTH, HEIGHT);
	
	public static boolean isRunning = false;
	public static boolean genCaves = false;
	
	public Images imgs;
	
	public World world;
	public WarningHud whud;
	public WaveHud waveHud;
	public HelpHud helpHud;
	public MainMenu menu;
	
	public Engine() {
		JFrame frame = new JFrame("LD 39");
		
		this.setPreferredSize(size);
		
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		frame.add(this, BorderLayout.CENTER);
		this.addKeyListener(this);
		this.addMouseListener(this);
		
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		
		imgs = new Images();
		world = new World();
		menu = new MainMenu(0, 0, Engine.WIDTH, Engine.HEIGHT);
		whud = new WarningHud((Engine.WIDTH / 2) - 160, (Engine.HEIGHT / 2) - 200, 0, 0);
		waveHud = new WaveHud(20, 50, 0, 0);
		helpHud = new HelpHud(20, Engine.HEIGHT - 10, 0, 0);
		
		start();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		
		isRunning = true;
	}
	
	public void run() {
		long timer = System.currentTimeMillis();
		
		while(isRunning) {
			try {
				thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			frameCount++;
			
			update();
			
			if(System.currentTimeMillis() - timer >= 1000) {
				FPS = frameCount;
				frameCount = 0;
				timer += 1000;
			}
		}
	}
	
	public static void main(String[] args) {
		new Engine();
	}
	
	public void update() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		camX = world.player.getX() - (Engine.WIDTH / 2) + (Reference.GRIDSIZE / 2);
		camY = world.player.getY() - (Engine.HEIGHT / 2) + Reference.GRIDSIZE;
		
		Graphics graphics = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D) graphics;
		
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(state == 1) {
			if(genCaves) { 
				world = new World();
				genCaves = false;
			}
			g.translate(-camX, -camY);
			world.update(g);
			g.translate(camX, camY);
			
			if(world.zombiesAwakened) whud.update(g);
			waveHud.update(g);
			helpHud.update(g);
		}
		
		if(state == 2) {
			menu.update(g);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.PLAIN, 30));
		//g.drawString("FPS: " + FPS, 10, 40);
		
		graphics.dispose();
		bs.show();
	}

	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		
		if(k == e.VK_W) {
			world.player.direction = 7;
		}
		if(k == e.VK_S) {
			world.player.direction = 6;
		}
		if(k == e.VK_A) {
			world.player.direction = 5;
		}
		if(k == e.VK_D) {
			world.player.direction = 4;
		}
		
		if(k == e.VK_K) {
			world.saveWorld();
			world.saveCaves();
		}
		if(k == e.VK_L) {
			world.loadLevel();
		}
		
		if(k == e.VK_E) {
			world.inv = true;
		}
		
		if(k == e.VK_UP) {
			if(world.inv && world.inventory.selection > 0) {
				world.inventory.selection -= 1;
			}
		}
		
		if(k == e.VK_DOWN) {
			if(world.inv && world.inventory.selection < 5) {
				world.inventory.selection += 1;
			}
		}
		
		if(k == e.VK_ENTER) {
			if(world.inv && world.inventory.selection == 0 && world.inventory.woodAmount >= 2) {
				world.inventory.woodAmount -= 2;
				world.inventory.sticksAmount += 1;
			}
			if(world.inv && world.inventory.selection == 1 && world.inventory.stoneAmount >= 5 && world.inventory.sticksAmount >= 4) {
				world.inventory.stoneAmount -= 5;
				world.inventory.sticksAmount -= 4;
				world.inventory.pickaxeTier = 1;
			}
			if(world.inv && world.inventory.selection == 2 && world.inventory.stoneAmount >= 4 && world.inventory.sticksAmount >= 3) {
				world.inventory.stoneAmount -= 4;
				world.inventory.sticksAmount -= 3;
				world.inventory.axeTier = 1;
			}
			if(world.inv && world.inventory.selection == 3 && world.inventory.ironOreAmount >= 2) {
				world.inventory.ironOreAmount -= 2;
				world.inventory.ironAmount += 1;
			}
			if(world.inv && world.inventory.selection == 4 && world.inventory.goldOreAmount >= 2) {
				world.inventory.goldOreAmount -= 2;
				world.inventory.goldAmount += 1;
			}
			if(world.inv && world.inventory.selection == 5 && world.inventory.stoneAmount >= 10 && world.inventory.sticksAmount >= 2 && world.inventory.ironAmount >= 5) {
				world.inventory.stoneAmount -= 10;
				world.inventory.sticksAmount -= 2;
				world.inventory.ironAmount -= 5;
				world.inventory.turretAmount += 1;
			}
			if(state == 2) {
				world = new World();
				whud = new WarningHud((Engine.WIDTH / 2) - 160, (Engine.HEIGHT / 2) - 200, 0, 0);
				waveHud = new WaveHud(20, 50, 0, 0);
				state = 1;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		
		if(k == e.VK_W) {
			world.player.direction = 3;
		}
		if(k == e.VK_S) {
			world.player.direction = 0;
		}
		if(k == e.VK_A) {
			world.player.direction = 2;
		}
		if(k == e.VK_D) {
			world.player.direction = 1;
		}
		
		if(k == e.VK_E) {
			world.inv = false;
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)) { 
			world.lmb = true;
			world.rmb = false;
		}
		if(SwingUtilities.isRightMouseButton(e)) { 
			world.lmb = false;
			world.rmb = true;
		}
		
		mouseX = e.getX() + camX;
		mouseY = e.getY() + camY;
		
		if(world.inv) {
			if(mouseX >= world.inventory.getX() + 20 && mouseY <= world.inventory.getY() + 40 && mouseX <= world.inventory.getX() + 220 && mouseY >= world.inventory.getY() + 16) {
				world.inventory.itemSelection = 0;
			}
			if(mouseX >= world.inventory.getX() + 20 && mouseY <= world.inventory.getY() + 70 && mouseX <= world.inventory.getX() + 220 && mouseY >= world.inventory.getY() + 46) {
				world.inventory.itemSelection = 1;
			}
			if(mouseX >= world.inventory.getX() + 20 && mouseY <= world.inventory.getY() + 100 && mouseX <= world.inventory.getX() + 220 && mouseY >= world.inventory.getY() + 76) {
				world.inventory.itemSelection = 2;
			}
			if(mouseX >= world.inventory.getX() + 20 && mouseY <= world.inventory.getY() + 130 && mouseX <= world.inventory.getX() + 220 && mouseY >= world.inventory.getY() + 106) {
				world.inventory.itemSelection = 3;
			}
			if(mouseX >= world.inventory.getX() + 20 && mouseY <= world.inventory.getY() + 160 && mouseX <= world.inventory.getX() + 220 && mouseY >= world.inventory.getY() + 136) {
				world.inventory.itemSelection = 4;
			}
			if(mouseX >= world.inventory.getX() + 20 && mouseY <= world.inventory.getY() + 190 && mouseX <= world.inventory.getX() + 220 && mouseY >= world.inventory.getY() + 166) {
				world.inventory.itemSelection = 5;
			}
			if(mouseX >= world.inventory.getX() + 20 && mouseY <= world.inventory.getY() + 220 && mouseX <= world.inventory.getX() + 220 && mouseY >= world.inventory.getY() + 196) {
				world.inventory.itemSelection = 6;
			}
			if(mouseX >= world.inventory.getX() + 20 && mouseY <= world.inventory.getY() + 250 && mouseX <= world.inventory.getX() + 220 && mouseY >= world.inventory.getY() + 226) {
				world.inventory.itemSelection = 7;
			}
			if(mouseX >= world.inventory.getX() + 20 && mouseY <= world.inventory.getY() + 280 && mouseX <= world.inventory.getX() + 220 && mouseY >= world.inventory.getY() + 256) {
				world.inventory.itemSelection = 8;
			}
			if(mouseX >= world.inventory.getX() + 20 && mouseY <= world.inventory.getY() + 310 && mouseX <= world.inventory.getX() + 220 && mouseY >= world.inventory.getY() + 286) {
				world.inventory.itemSelection = 9;
			}
			if(mouseX >= world.inventory.getX() + 20 && mouseY <= world.inventory.getY() + 340 && mouseX <= world.inventory.getX() + 220 && mouseY >= world.inventory.getY() + 316) {
				world.inventory.itemSelection = 10;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		world.lmb = false;
		
		mouseX = -1;
		mouseY = -1;
	}
	
	
	
}
