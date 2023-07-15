package main;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Player implements KeyListener, MouseMotionListener, MouseListener, FocusListener {
	
	public double px, py, pz, xrot, yrot;
	public double forward = 0, sideways = 0, rotation = 0;
	
	public boolean keyA, keyD, keyW, keyS;
	
	public boolean hasFocus;
	
	public int MouseX;
	public int MouseY;
	
	public Player() {
		px = 32.5;
		py = 32.5;
		pz = 32.5;
		xrot = 0;
		yrot = 0;
		System.out.println("Player init");
	}

	public void keyTyped(KeyEvent e) {
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				keyW = true;
				break;
			case KeyEvent.VK_S:
				keyS = true;
				break;
			case KeyEvent.VK_A:
				keyA = true;
				break;
			case KeyEvent.VK_D:
				keyD = true;
				break;
		}
		
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				keyW = false;
				break;
			case KeyEvent.VK_S:
				keyS = false;
				break;
			case KeyEvent.VK_A:
				keyA = false;
				break;
			case KeyEvent.VK_D:
				keyD = false;
				break;
		}
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
	}

	public void focusGained(FocusEvent e) {
		hasFocus = true;
	}

	public void focusLost(FocusEvent e) {
		hasFocus = false;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
