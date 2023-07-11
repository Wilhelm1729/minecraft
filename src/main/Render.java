package main;

public class Render {
	
	public int[][] frame;
	
	int WIDTH;
	int HEIGHT;
	
	
	public Render(int WIDTH, int HEIGHT, int[][] frame) {
		this.HEIGHT = HEIGHT;
		this.WIDTH = WIDTH;
		this.frame = frame;
		
		
	}
	
	public void render(int time) {
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				frame[x][y] = 0xFF00FF;
			}
		}
	}

}
