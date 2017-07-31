package utilities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
	
	public static BufferedImage player_idleSheet;
	public static BufferedImage player_walk_sideSheet;
	public static BufferedImage player_walk_backForthSheet;
	
	public static BufferedImage zombie_idleSheet;
	public static BufferedImage zombie_walk_sideSheet;
	public static BufferedImage zombie_walk_backForthSheet;
	
	public static BufferedImage tilesheet_1;
	public static BufferedImage breaksheet_1;
	public static BufferedImage particlesheet_1;
	
	public static BufferedImage[] player = new BufferedImage[12];
	public static BufferedImage[] tiles = new BufferedImage[12];
	public static BufferedImage[] zombie = new BufferedImage[12];
	public static BufferedImage[] particle = new BufferedImage[1];
	
	public static BufferedImage cave_vignette;
	
	public Images() {
		try {
			player_idleSheet = ImageIO.read(getClass().getResource("/player/player_idle.png"));
			player_walk_sideSheet = ImageIO.read(getClass().getResource("/player/player_walk_side.png"));
			player_walk_backForthSheet = ImageIO.read(getClass().getResource("/player/player_walk_backfront.png"));
			
			zombie_walk_sideSheet = ImageIO.read(getClass().getResource("/enemies/zombie_walk_side.png"));
			zombie_walk_backForthSheet = ImageIO.read(getClass().getResource("/enemies/zombie_walk_backfront.png"));
			
			tilesheet_1 = ImageIO.read(getClass().getResource("/tiles/tilesheet_1.png"));
			breaksheet_1 = ImageIO.read(getClass().getResource("/particles/breakTile.png"));
			
			cave_vignette = ImageIO.read(getClass().getResource("/lighting/cave_vignette.png"));
			
			particlesheet_1 = ImageIO.read(getClass().getResource("/particles/particleSheet_1.png"));
			
			player[0] = player_idleSheet.getSubimage(0, 0, 16, 32);
			player[1] = player_idleSheet.getSubimage(16, 0, 16, 32);
			player[2] = player_idleSheet.getSubimage(32, 0, 16, 32);
			player[3] = player_idleSheet.getSubimage(48, 0, 16, 32);
			player[4] = player_walk_sideSheet.getSubimage(0, 0, 16, 32);
			player[5] = player_walk_sideSheet.getSubimage(16, 0, 16, 32);
			player[6] = player_walk_sideSheet.getSubimage(32, 0, 16, 32);
			player[7] = player_walk_sideSheet.getSubimage(48, 0, 16, 32);
			player[8] = player_walk_backForthSheet.getSubimage(0, 0, 16, 32);
			player[9] = player_walk_backForthSheet.getSubimage(16, 0, 16, 32);
			player[10] = player_walk_backForthSheet.getSubimage(32, 0, 16, 32);
			player[11] = player_walk_backForthSheet.getSubimage(48, 0, 16, 32);
			
			tiles[0] = tilesheet_1.getSubimage(0, 0, 16, 16);
			tiles[1] = tilesheet_1.getSubimage(16, 0, 16, 16);
			tiles[2] = tilesheet_1.getSubimage(32, 0, 16, 16);
			tiles[3] = tilesheet_1.getSubimage(48, 0, 16, 16);
			tiles[4] = tilesheet_1.getSubimage(0, 16, 16, 16);
			tiles[5] = tilesheet_1.getSubimage(16, 16, 16, 16);
			tiles[6] = tilesheet_1.getSubimage(32, 16, 16, 16);
			tiles[7] = tilesheet_1.getSubimage(48, 16, 16, 16);
			tiles[8] = tilesheet_1.getSubimage(0, 32, 16, 16);
			tiles[9] = tilesheet_1.getSubimage(16, 32, 16, 16);
			tiles[10] = tilesheet_1.getSubimage(32, 32, 16, 16);
			tiles[11] = tilesheet_1.getSubimage(48, 32, 16, 16);
			
			zombie[0] = null;
			zombie[1] = null;
			zombie[2] = null;
			zombie[3] = null;
			zombie[4] = zombie_walk_sideSheet.getSubimage(0, 0, 16, 32);
			zombie[5] = zombie_walk_sideSheet.getSubimage(16, 0, 16, 32);
			zombie[6] = zombie_walk_sideSheet.getSubimage(32, 0, 16, 32);
			zombie[7] = zombie_walk_sideSheet.getSubimage(48, 0, 16, 32);
			zombie[8] = zombie_walk_backForthSheet.getSubimage(0, 0, 16, 32);
			zombie[9] = zombie_walk_backForthSheet.getSubimage(16, 0, 16, 32);
			zombie[10] = zombie_walk_backForthSheet.getSubimage(32, 0, 16, 32);
			zombie[11] = zombie_walk_backForthSheet.getSubimage(48, 0, 16, 32);
			
			particle[0] = particlesheet_1.getSubimage(0, 0, 8, 8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
