package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Render {
	
	public int[][] textures;
	public int[][] frame;
	public int[][][] map;
	
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
		
		System.out.print("Textures loaded \n");
		
		map = new int[64][64][64];
		for ( int x = 0; x < 64; x++) {
	        for ( int y = 0; y < 64; y++) {
	            for ( int z = 0; z < 64; z++) {
	            	int yd = (int) ((y - 32.5) * 0.4);
	            	int zd = (int) ((z - 32.5) * 0.4);
	                map[x][y][z] = (int)(Math.random() * 16) | 0;
	                if (Math.random() > Math.sqrt(Math.sqrt(yd * yd + zd * zd)) - 0.9)
	                    map[x][y][z] = 0;
	            }
	        }
	    }
		

		System.out.print("Map created \n");
		
	}
	
	public void render(int time) {
		/*
		double cos = Math.cos(time / 10d);
		double sin = Math.sin(time / 10d);
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				//frame[x][y] = textures[(x + time) & 255][(y + time) & 255];
				setPixel(x,y, ((int)(x + 40 * cos) / 10) & 15, ((int)(y + 40 * sin) / 10) & 15, time / 10);
			}
		}*/
		
	    double xRot = Math.sin(time / 200d * Math.PI * 2) * 0.4d
	            + Math.PI / 2;
	    double yRot = Math.cos(time / 200d * Math.PI * 2) * 0.4d;
	    double yCos = Math.cos(yRot);
	    double ySin = Math.sin(yRot);
	    double xCos = Math.cos(xRot);
	    double xSin = Math.sin(xRot);

	    double ox = 32.5 + time / 100d;
	    double oy = 32.5;
	    double oz = 32.5;
		
		for (int x = 0; x < WIDTH; x++) {
			double ___xd = (x - WIDTH / 2d) / HEIGHT;
			for (int y = 0; y < HEIGHT; y++) {
				double __yd = (y - HEIGHT / 2d) / HEIGHT;
				double __zd = 1;
				
				double ___zd = __zd * yCos + __yd * ySin;
				double _yd = __yd * yCos - __zd * ySin;

				double _xd = ___xd * xCos + ___zd * xSin;
				double _zd = ___zd * xCos - ___xd * xSin;
				
	            int col = 0;
	            double br = 255;
	            double ddist = 0;
				
	            double closest = 32;
	            // d = 1 is the horizontal surfaces
	            for (int d = 0; d < 3; d++) {
	            	double dimLength = (d == 0 ? _xd : (d == 1 ? _yd : _zd));
	            	
	            	double ll = 1 / (dimLength < 0 ? -dimLength : dimLength);
	            	double xd = _xd * ll;
	            	double yd = _yd * ll;
	            	double zd = _zd * ll; 
	            	
	            	double initial = (d == 0 ? ox - (int) ox : (d == 1 ? oy - (int) oy : oz - (int) oz));
	            	if (dimLength > 0)
	                    initial = 1 - initial;
	            	
	            	double dist = ll * initial;
	            	
	            	double xp = ox + xd * initial;
	            	double yp = oy + yd * initial;
	            	double zp = oz + zd * initial;
	            	
	            	if (dimLength < 0) {
	                    if (d == 0)
	                        xp--;
	                    if (d == 1)
	                        yp--;
	                    if (d == 2)
	                        zp--;
	                }
	            	
	            	while (dist < closest) {
	            		int block = map[(int)xp & 63][(int)yp & 63][(int)zp & 63];
	            		
	            		if (block > 0) {
	            			/*
	            			int u = (int)((zp + xp) * 16) & 15;
	            			int v = (int)((yp) * 16) & 15;
	            			if (d==1) {
	            				u = (int) (xp * 16) & 15;
	            				v = (int) (zp * 16) & 15;
	            				if (yd < 0) {
	            					//Use this places to distinguish between side and top
	            				}
	            			}*/
	            			
	            			int cc = 0xFF00FF; // textures[u + 16][v];
	            			if (cc > 0) {
	            				col = cc;
	            				ddist = 255 - (int)(dist / 32d * 255);
	                            br = 255 * (255 - ((d + 2) % 3) * 50) / 255d;
	                            closest = dist;
	            			}
	            		}
	                    xp += xd;
	                    yp += yd;
	                    zp += zd;
	                    dist += ll;
	            	}
	            }
	            int r = (int) (((col >> 16) & 0xff) * br * ddist / (double)(255 * 255));
	            int g = (int) (((col >> 8) & 0xff) * br * ddist / (double)(255 * 255));
	            int b = (int) (((col) & 0xff) * br * ddist / (double)(255 * 255));

	            frame[x][y] = (r << 16) + (g << 8) + b;
	            
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
