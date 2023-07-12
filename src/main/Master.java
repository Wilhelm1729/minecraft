package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Master extends Canvas implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = 200;
	private boolean running = false;
	private Thread thread;
	
	private int setTPS = 30;
	
	private BufferedImage display;
	private Render r;
	private int[][] frame;
	
	int time = 0;
	
	public Master() {
		display = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		frame = new int[WIDTH][HEIGHT];
		r = new Render(WIDTH, HEIGHT, frame);
	}
	
	public static void main(String[] args) {
		Master m = new Master();
		Dimension d = new Dimension(WIDTH * 2, HEIGHT * 2);
		m.setPreferredSize(d);
		m.setMaximumSize(d);
		m.setMinimumSize(d);
		
		JFrame f = new JFrame("Minecraft");
		f.setFocusable(true);
		f.add(m);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
		m.start();
	}
	
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		r.render(time);
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				display.setRGB(x, y, frame[x][y]);
			}
		}
		g.drawImage(display, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	private void tick() {
		time++;
	}
	
	public void run() {
		long timer = 0;
		int updates = 0;
		int tps = 0;
		long lastTick = System.nanoTime();
		long interval = 1000000000 / setTPS;
		
		while(running) {
			long startTime = System.nanoTime();
			if(startTime - lastTick > interval) {
				tick();
				tps++;
				lastTick = startTime;
			}
			render();
			updates++;
			long delta = System.nanoTime() - startTime;
			timer += delta;
			if(timer > 1000000000) {
				System.out.println("fps: " + updates + " tps: " + tps);
				tps = 0;
				updates = 0;
				timer = 0;
			}
		}
		
		
	}

}
