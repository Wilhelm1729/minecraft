package main;

public class Level {


	public int[][][] map;
	Player p;
	private int newX;
	private int oldX;
	private int newY;
	private int oldY;
	public double halfPi = Math.PI/2d;
	
	public Level(Player p) {
		this.p = p;
		
		map = new int[64][64][64];
		for ( int x = 0; x < 64; x++) {
	        for ( int y = 0; y < 64; y++) {
	            for ( int z = 0; z < 64; z++) {
	            	int yd = (int) ((y - 32.5) * 0.4);
	            	int zd = (int) ((z - 32.5) * 0.4);
	                map[x][y][z] = (int)(Math.random() * 16) | 0;
	                if (Math.random() > Math.sqrt(yd * yd + zd * zd) - 0.8)
	                    map[x][y][z] = 0;
	            }
	        }
	    }
		
		System.out.print("Map created \n");
	}
	
	public void updateLevel() {
		updatePlayer();
	}
	
	double movementSpeed = 0.05, rotationSpeed = 0.015, friction = 0.9;
	
	public void updatePlayer() {
		
		newX = p.MouseX;
		newY = p.MouseY;
		if (p.hasFocus) {
			p.xrot += (newX-oldX)/200d;
			double d = -(newY-oldY)/200d;
			if (p.yrot+d < halfPi && p.yrot+d > -halfPi) {
				p.yrot += d;
			}
		}
		oldX = newX;
		oldY = newY;

		if (p.keyW) {
			p.forward += movementSpeed; 
		}
		if (p.keyS) {
			p.forward -= movementSpeed; 
		}
		if (p.keyD) {
			p.sideways += movementSpeed; 
		}
		if (p.keyA) {
			p.sideways -= movementSpeed; 
		}
		
		double sin = Math.sin(p.xrot);
		double cos = Math.cos(p.xrot);
		
		p.pz += cos * p.forward - sin * p.sideways;
		p.px += sin * p.forward + cos * p.sideways;
		p.xrot += p.rotation;
		
		p.forward *= friction;
		p.sideways *= friction;
		p.rotation *= 0.85;
	}

	
}
