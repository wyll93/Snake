import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;



public class Game extends Canvas implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int sizeSnake = 20;
	
	public int sizeScore = 30;
	
	public int sizeFontScore = 20;
	
	public int speed = 0, maxSpeed = 10;
	
	public int screenX = 480, screenY = 480;
	
	public Snake[] nodeSnake = new Snake[sizeSnake];

	public boolean left, right, up, down;
	
	public int score = 0;
	
	public int macaX = 0, macaY = 0;
	
	
	public Game() {
		this.setPreferredSize(new Dimension (screenX, screenY));
		for(int i = 0; i < nodeSnake.length; i++) {
			nodeSnake[i] = new Snake(0, 0);
		}
		
		this.addKeyListener(this);
	}

	public void tick() {
		
		for(int i = nodeSnake.length - 1; i > 0; i--){
			nodeSnake[i].x = nodeSnake[i-1].x;
			nodeSnake[i].y = nodeSnake[i-1].y;
		}
		
		if(nodeSnake[0].x + 10 < 0) {
			nodeSnake[0].x = screenX;
		}else if(nodeSnake[0].x >= screenX) {
			nodeSnake[0].x = -10;
		}
		
		if(nodeSnake[0].y + 10 < 0) {
			nodeSnake[0].y = screenY;
		}else if(nodeSnake[0].y >= screenY) {
			nodeSnake[0].y = -10;
		}
		
		
		if(right) {
			nodeSnake[0].x+=speed;
			
		}else if(up) {
			nodeSnake[0].y-=speed;
			
		}else if(down) {
			nodeSnake[0].y+=speed;
			
		}else if(left) {
			nodeSnake[0].x-=speed;
		}
		
		if(new Rectangle(nodeSnake[0].x, nodeSnake[0].y, 10, 10).intersects(new Rectangle(macaX, macaY, 10, 10))) {
			macaX = new Random().nextInt(screenX-10);
			macaY = new Random().nextInt(screenY-10);
			score ++;
			//System.out.println("Velocidade: " + speed);
			if(speed < maxSpeed) {
				speed++;
				//System.out.println("Velocidade atualizada: " + speed);
			}
			
			//debug
			//System.out.println("Score: " + score);
		}
	}
	// this function is responsible for to render graphics   
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		//render screen 
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, screenX, screenY);
		
		//render snake
		for(int i = 0; i < nodeSnake.length; i++) {
			g.setColor(Color.green);
			g.fillRect(nodeSnake[i].x, nodeSnake[i].y, 	10, 10);
		}
		
		//render apple
		g.setColor(Color.red);
		g.fillRect(macaX, macaY, 10, 10);
		
		//render score
		g.setColor(Color.BLUE);
		g.setFont(new Font("arial", Font.BOLD, sizeFontScore));
		g.drawString("Score: "+ score, sizeScore, sizeScore);
		// don't write code below this comment
		//this code is responsible to show graphics
		g.dispose();
		bs.show();
	}
	
	
	public static void main(String args[]) {
		// invoke class game to principal class
		Game game = new Game();
		
		//create new windows 
		JFrame frame = new JFrame("Snake");
		
		//add game in windows, this render and updates are processed in this windows
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		new Thread(game).start();
		
	}
	//this function is responsible to generate time variety 
	//while program run, this function receive the time in milliseconds
	//and execute the update function
	public void run() {
		
		//this function makes buttons are focused in game windows
		requestFocus();
		while(true) {
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			up = false;
			right = true;
			left = false;
			down = false;
		}else if(e.getKeyCode() == KeyEvent.VK_A) {
			up = false;
			right = false;
			left = true;
			down = false;
		}else if(e.getKeyCode() == KeyEvent.VK_W) {
			up = true;
			right = false;
			left = false;
			down = false;
		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			up = false;
			right = false;
			left = false;
			down = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
