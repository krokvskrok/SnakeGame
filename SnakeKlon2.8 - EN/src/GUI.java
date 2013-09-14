import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class GUI {
	
	static JFrame f1;
	static JPanel p1;
	private Snake snake;
	private SnakeGame snakeGame;
	private Food food;
	private Font fontMenu;
	private Font fontHeader;
	
	public GUI() {
    	snakeGame = new SnakeGame();
    	snakeGame.setSnakeGame(snakeGame);
	}
	

	public void createGameWindow() {
		
		f1 = new JFrame("Snake");
		f1.setSize(675, 395);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		f1.setLocationRelativeTo(null);
		p1 = new Draw();
		p1.setLayout(null);
		f1.add(p1);
		
		//Create Fonts
	    fontMenu = new Font( Font.SANS_SERIF, Font.PLAIN, 12 );
	    fontHeader = new Font( Font.SANS_SERIF, Font.BOLD, 16 );
		
		f1.requestFocus();	
		f1.repaint();
		f1.setVisible(true);

		f1.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(snakeGame.isStarted()==true) {
					
					//Pause Menu
					if(snakeGame.isPause()==true){
						
						checkPauseMenuInputs(e);
						
					//Current Game	
					}else if(snakeGame.isPause()==false) {

						checkCurrentGameInputs(e);
						
					}
					
				//Game Over Menu	
				}else if(snakeGame.isFinished()==true) {
					
					checkGameOverInputs(e);
					
				//Start Menu	
				}else if(snakeGame.isStartmenu()==true) {

					checkStartMenuInputs(e);
					
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
		});	

	}
	

	class Draw extends JPanel {
		
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			
			if(snakeGame.isStarted() == true) {
				
				if(snakeGame.isPause()==true) {
					
					drawPauseMenu(g2);
					
				}else if(snakeGame.isPause()==false) {

					drawCurrentGame(g2);
					
				}
				
			}else if(snakeGame.isFinishedBlink1() == true){

				drawBlinkAnimation1(g2);
				
			}else if(snakeGame.isFinishedBlink2()==true) {

				drawBlinkAnimation2(g2);
				
			}else if(snakeGame.isFinished() == true) {
				
				drawGameOver(g2);
				
			}else if(snakeGame.isStartmenu()==true) {

				drawStartMenu(g2);
				
			}
				
		}
		
	}
	
	// -----------KeyListener-Methods---------------------
	
	public void checkPauseMenuInputs(KeyEvent e) {
		//ESC
		if(e.getKeyCode()==27) {
			snakeGame.setPause(false);
		//Up	
		}else if(e.getKeyCode()==38) {
			if(snakeGame.getMenuSelection().equals("Exit game")) {
				snakeGame.setMenuSelection("Back to main menu");
				GUI.f1.repaint();
			}else if(snakeGame.getMenuSelection().equals("Back to main menu")) {
				snakeGame.setMenuSelection("Restart game");
				GUI.f1.repaint();
			}
		//Down	
		}else if(e.getKeyCode()==40) {
			if(snakeGame.getMenuSelection().equals("Restart game")) {
				snakeGame.setMenuSelection("Back to main menu");
				GUI.f1.repaint();
			}else if(snakeGame.getMenuSelection().equals("Back to main menu")) {
				snakeGame.setMenuSelection("Exit game");
				GUI.f1.repaint();
			}	
		//Enter	
		}else if(e.getKeyCode()==10) {
			switch(snakeGame.getMenuSelection()) {
			case "Restart game":
				snakeGame.restartGame();
				break;
			case "Exit game":
				System.exit(0);
				break;
			case "Back to main menu":
				snakeGame.setPause(false);
				snakeGame.setStarted(false);
				snakeGame.restartGame();
				snakeGame.setStartmenu(true);
				snakeGame.setMenuSelection("Start game");
				GUI.f1.repaint();
				break;
			}
		}
	}
	
	public void checkCurrentGameInputs(KeyEvent e) {
		if(e.getKeyCode()==38) {
			if(snake.isSnakeDown()==false) {
				snake.setSnakeDown(false);
				snake.setSnakeRight(false);
				snake.setSnakeLeft(false);
				snake.setSnakeUp(true);
			}
		}else if(e.getKeyCode()==40) {
			if(snake.isSnakeUp()==false) {
				snake.setSnakeUp(false);
				snake.setSnakeRight(false);
				snake.setSnakeLeft(false);
				snake.setSnakeDown(true);
			}
		}else if(e.getKeyCode()==37) {
			if(snake.isSnakeRight()==false) {
				snake.setSnakeUp(false);
				snake.setSnakeRight(false);
				snake.setSnakeDown(false);
				snake.setSnakeLeft(true);
			}
		}else if(e.getKeyCode()==39) {
			if(snake.isSnakeLeft()==false) {
				snake.setSnakeUp(false);
				snake.setSnakeLeft(false);
				snake.setSnakeDown(false);
				snake.setSnakeRight(true);
			}
		}else if(e.getKeyCode()==27) {
				snakeGame.setPause(true);	
		}
	}
	
	public void checkGameOverInputs(KeyEvent e) {
		//Up
		if(e.getKeyCode()==38) {
			if(snakeGame.getMenuSelection().equals("Exit game")) {
				snakeGame.setMenuSelection("Restart game");
				GUI.f1.repaint();
			}	
		//Down	
		}else if(e.getKeyCode()==40) {
			if(snakeGame.getMenuSelection().equals("Restart game")) {
				snakeGame.setMenuSelection("Exit game");
				GUI.f1.repaint();
			}	
		//Enter
		}else if(e.getKeyCode()==10) {
			switch(snakeGame.getMenuSelection()) {
			case "Restart game":
				snakeGame.restartGame();
				snakeGame.startGame(snakeGame);
				break;
			case "Exit game":
				System.exit(0);
				break;
			}
		}
	}
	
	public void checkStartMenuInputs(KeyEvent e) {
		//Up
		if(e.getKeyCode()==38) {
			if(snakeGame.getMenuSelection().equals("Exit game")) {
				snakeGame.setMenuSelection("Difficulty");
				GUI.f1.repaint();
			}else if(snakeGame.getMenuSelection().equals("Difficulty")) {
				snakeGame.setMenuSelection("Start game");
				GUI.f1.repaint();
			}		
		//Down	
		}else if(e.getKeyCode()==40) {
			if(snakeGame.getMenuSelection().equals("Start game")) {
				snakeGame.setMenuSelection("Difficulty");
				GUI.f1.repaint();
			}else if(snakeGame.getMenuSelection().equals("Difficulty")) {
				snakeGame.setMenuSelection("Exit game");
				GUI.f1.repaint();
			}	
		//Select Difficult right
		}else if(e.getKeyCode()==39) {
			if(snakeGame.getMenuSelection().equals("Difficulty") && snakeGame.getDifficultyLevel()!=2) {
				snakeGame.setDifficultyLevel(snakeGame.getDifficultyLevel()+1);
				GUI.f1.repaint();
			}
		//Select Difficult left	
		}else if(e.getKeyCode()==37) {
			if(snakeGame.getMenuSelection().equals("Difficulty") && snakeGame.getDifficultyLevel()!=0) {
				snakeGame.setDifficultyLevel(snakeGame.getDifficultyLevel()-1);
				GUI.f1.repaint();
			}
		}
		//Enter
		else if(e.getKeyCode()==10) {
		switch(snakeGame.getMenuSelection()) {
		case "Start game":
			snakeGame.startGame(snakeGame);
			break;
		case "Exit game":
			System.exit(0);
			break;
		}
	}
	}
	
	// -----------Draw-Methods---------------------

	public void drawPauseMenu(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, 400, 400);
		g2.setColor(Color.WHITE);
		g2.setFont(fontHeader);
		g2.drawString("Pause menu", 100, 100);
		g2.setFont(fontMenu);
		g2.drawString("Restart game", 100, 200);
		g2.drawString("Back to main menu", 100, 250);
		g2.drawString("Exit game", 100, 300);
		
		switch (snakeGame.getMenuSelection()) {
		  case "Restart game":
			g2.fillOval(70, 190, 10, 10);
			break; 
		  case "Back to main menu":
			g2.fillOval(70, 240, 10, 10);
			break; 
		  case "Exit game":
				g2.fillOval(70, 290, 10, 10);
				break; 	
		}	
	}
	
	public void drawCurrentGame(Graphics2D g2) {
		// -------------------------------------------------------------------------------------------
		// Draw current game (left part of the game)
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 400, 400);
		//draw walls
		g2.setColor(Color.BLACK);
		g2.fillRect(395, 0, 10, 400);
		g2.fillRect(0, 0, 10, 400);		
		g2.fillRect(0, 0, 400, 10);
		g2.fillRect(0, 350, 400, 10);
		
	
		//draw snake
		for(int i=1; i<=snake.getList().size(); i++) { 
			g2.fillRect(snake.getList().get(i-1).x,snake.getList().get(i-1).y, 10, 10);
		}
		
		
		
		if(food.isFoodPlaced() == true) {
			g2.setColor(Color.GREEN);
			g2.fillRect(food.getFoodX(), food.getFoodY(), 10, 10);
		}
		
		
		// -------------------------------------------------------------------------------------------
		
		// -------------------------------------------------------------------------------------------
		// draw game-info (right)
		
		
		g2.setColor(Color.BLACK);
		g2.setFont(fontMenu);
		if(snakeGame.getMinutes()<10 && snakeGame.getSeconds()<10) {								//05:05
			
			g2.drawString("0"+snakeGame.getMinutes()+":0"+snakeGame.getSeconds(), 420, 50);
			
		}else if(snakeGame.getMinutes()<10 && snakeGame.getSeconds()>9) {							//05:55
			
			g2.drawString("0"+snakeGame.getMinutes()+":"+snakeGame.getSeconds(), 420, 50);
			
		}else if(snakeGame.getMinutes()>9 && snakeGame.getSeconds()>9) {							//55:55
			
			g2.drawString(snakeGame.getMinutes()+":"+snakeGame.getSeconds(), 420, 50);
			
		}else if(snakeGame.getMinutes()>9 && snakeGame.getSeconds()<10) {							//55:05
			
			g2.drawString(snakeGame.getMinutes()+":0"+snakeGame.getSeconds(), 420, 50);
			
		}
		
		g2.drawString("Score: "+snakeGame.getScore(), 420, 100);
		g2.drawString("Max score left: "+snakeGame.getMaxScoreLeft(), 420, 150);
		
		
		// -------------------------------------------------------------------------------------------
	}
	
	public void drawBlinkAnimation1(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 400, 400);
		g2.setColor(Color.BLACK);
		//draw walls
		g2.setColor(Color.BLACK);
		g2.fillRect(395, 0, 10, 400);
		g2.fillRect(0, 0, 10, 400);		
		g2.fillRect(0, 0, 400, 10);
		g2.fillRect(0, 350, 400, 10);
	

		//draw snake
		for(int i=1; i<=snake.getList().size(); i++) { 
			g2.fillRect(snake.getList().get(i-1).x,snake.getList().get(i-1).y, 10, 10);
		}
		

		if(food.isFoodPlaced() == true) {
			g2.setColor(Color.GREEN);
			g2.fillRect(food.getFoodX(), food.getFoodY(), 10, 10);
		}
	}
	
	public void drawBlinkAnimation2(Graphics2D g2) {
		//draw walls
		g2.setColor(Color.BLACK);
		g2.fillRect(395, 0, 10, 400);
		g2.fillRect(0, 0, 10, 400);		
		g2.fillRect(0, 0, 400, 10);
		g2.fillRect(0, 350, 400, 10);
		
		// for blinking animation
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 400, 400);
		
		//draw walls
		g2.setColor(Color.BLACK);
		g2.fillRect(395, 0, 10, 400);
		g2.fillRect(0, 0, 10, 400);		
		g2.fillRect(0, 0, 400, 10);
		g2.fillRect(0, 350, 400, 10);
	}
	
	public void drawGameOver(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, 400, 400);
		g2.setColor(Color.WHITE);
		g2.setFont(fontHeader);
		g2.drawString("Game over! \n Your score: "+snakeGame.getScore(), 100, 100);
		g2.setFont(fontMenu);
		g2.drawString("Restart game", 100, 200);
		g2.drawString("Exit game", 100, 250);
		
		switch (snakeGame.getMenuSelection()) {

		  case "Restart game":
			g2.fillOval(70, 190, 10, 10);
			break; 
		  case "Exit game":
			g2.fillOval(70, 240, 10, 10);
			break;
		  case "Difficulty":
			  g2.fillOval(70, 290, 10, 10);
			  break;
		}	
	}
	
	public void drawStartMenu(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, 400, 400);
		g2.setColor(Color.WHITE);
		g2.setFont(fontHeader);
		g2.drawString("Snake", 100, 100);
		g2.setFont(fontMenu);
		g2.drawString("Start game", 100, 200);
		g2.drawString("Difficulty:", 100, 250);
		g2.drawString("Exit game", 100, 300);
		g2.drawString("Easy", 205, 250);
		g2.drawString("Normal", 255, 250);
		g2.drawString("Hard", 305, 250);

		
		switch (snakeGame.getMenuSelection()) {

		  case "Start game":
			g2.fillOval(70, 190, 10, 10);
			break; 
		  case "Difficulty":
			g2.fillOval(70, 240, 10, 10);
			break; 
		  case "Exit game":
				g2.fillOval(70, 290, 10, 10);
				break; 
		}	
		
		
		switch(snakeGame.getDifficultyLevel()) {
		case 0:
			g2.drawRect(202, 239, 31, 14);		
			break;
		case 1:
			g2.drawRect(252, 239, 45, 14);
			break;
		case 2:
			g2.drawRect(302, 239, 32, 14);
			break;

		}
	}

	public SnakeGame getSnakeGame() {
		return snakeGame;
	}


	public void setSnakeGame(SnakeGame snakeGame) {
		this.snakeGame = snakeGame;
	}


	public Food getFood() {
		return food;
	}


	public void setFood(Food food) {
		this.food = food;
	}
	
	public Snake getSnake() {
		return snake;
	}


	public void setSnake(Snake snake) {
		this.snake = snake;
	}
	

}
