package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Render {
	
	public int[][] textures;
	public int[][] frame;
	
	int WIDTH;
	int HEIGHT;
	
	
	public Render(int WIDTH, int HEIGHT, int[][] frame) {
		this.HEIGHT = HEIGHT;
		this.WIDTH = WIDTH;
		this.frame = frame;
		
		// Load textures
		
		BufferedImage tex = null;
		
		try {
			tex = ImageIO.read(new File("res/terrain.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		textures = new int[tex.getWidth()][tex.getHeight()];
		
		for(int i = 0; i < textures.length; i++) {
			for(int j = 0; j < textures[0].length; j++) {
				textures[i][j] = tex.getRGB(i, j);
			}
		}
		
		System.out.print("Textures loaded");
		
	}
	
	public void render(int time) {
		double cos = Math.cos(time / 10d);
		double sin = Math.sin(time / 10d);
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				//frame[x][y] = textures[(x + time) & 255][(y + time) & 255];
				setPixel(x,y, ((int)(x + 40 * cos) / 10) & 15, ((int)(y + 40 * sin) / 10) & 15, time / 10);
			}
		}
	}
	
	public void setPixel(int x, int y, int tx, int ty, int index) {
		index = index & 255;
		int col = textures[tx + 16 * (index & 15)][ty + 16 * (int)(index / 16)];
		if (col == -65316) // Transparent
			return;
			
		frame[x][y] = col;
					
					/*((int) ((double) ((col >> 16) & 0xff) * f) << 16)
					+ ((int) ((double) ((col >> 8) & 0xff) * f) << 8)
					+ ((int) ((double) ((col) & 0xff) * f));*/
	}

}
