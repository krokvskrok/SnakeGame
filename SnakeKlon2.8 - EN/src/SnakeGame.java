import java.awt.Rectangle;


public class SnakeGame implements Runnable {

	private boolean started = false;
	private boolean finishedBlink1 = false;
	private boolean finishedBlink2 = false;
	private boolean finished = false;														
	private int score = 0;
	private int maxScoreLeft = 0;
	private int seconds = 0;
	private int minutes = 0;
	private boolean pause = false;
	private boolean restart = false;
	private boolean startMenu = true;
	private int difficultyLevel=0;	//0=easy, 1=normal, 2=hard
	private  int difficultyLevel_thread;
	
	private String menuSelection = "Start game";

	private Snake snake;
	private SnakeGame snakeGame;
	private Food food;


	public void startGame(SnakeGame snakeGame) {										
		started = true;
		Thread t = new Thread(snakeGame);
		menuSelection = "Restart game";

		switch(snakeGame.getDifficultyLevel()) {
			case 0:
				snakeGame.setDifficultyLevel_thread(300);
				break;
			case 1:
				snakeGame.setDifficultyLevel_thread(200);
				break;
			case 2:
				snakeGame.setDifficultyLevel_thread(100);
				break;		
		}
		
		t.start();
	}
	
	public void restartGame() {
		snake.setSnakeX(100);
		snake.setSnakeY(100);
		snake.setSnakeLeft(false);
		snake.setSnakeUp(false);
		snake.setSnakeDown(false);
		snake.setSnakeRight(true);
		snakeGame.setFinished(false);
		snakeGame.setScore(0);
		snakeGame.setSeconds(0);
		snakeGame.setMinutes(0);
		snakeGame.setMaxScoreLeft(100);
		snakeGame.setPause(false);
		snake.getList().clear();
		snake.getList().add(new Rectangle(snake.getSnakeX(), snake.getSnakeY(), 10, 10));
	}


	public void finishGame() {															
		started = false;

		//create blink animation 
		for(int i=0; i<4; i++) {			
			finishedBlink1 = true;
			GUI.f1.repaint();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			finishedBlink1 = false;
			finishedBlink2 = true;
			GUI.f1.repaint();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			finishedBlink2 = false;
		}
		
		//display "game over" and score
		finishedBlink1 = false;
		finishedBlink2 = false;
		finished = true;
		menuSelection = "Restart game";											
		GUI.f1.repaint();
		
	}
	

	
	public void run() {
		
		snake = new Snake();
		food = new Food();
		food.setFood(food);
		App.gui.setFood(food);							
		App.gui.setSnake(snake);
		
		snake.getList().add(new Rectangle(snake.getSnakeX(), snake.getSnakeY(), 10, 10));

		long startTime = System.currentTimeMillis();
		long currentTime = 0;

		//game loop
		while(snakeGame.started==true) {
			
				if(snakeGame.isPause()==false) {

					//inputhandling and collisiondetecion for walls
					collisionWall();
					
					//place food if it's not placed
					placeFood();
					
					//refresh max score left
					if(snakeGame.getMaxScoreLeft()>0) {
						snakeGame.setMaxScoreLeft(snakeGame.getMaxScoreLeft()-1);
					}
				
					//snake collided with food?
					collisionFood();
						
					//snake collided with itself?
					collisionSnake();
					
					//draw snake correct -> add new head and remove last body-part
					snake.getList().add(new Rectangle(snake.getSnakeX(), snake.getSnakeY(), 10, 10));
					snake.getList().remove(0);
					
					//time-info
					currentTime = System.currentTimeMillis();
					if(currentTime - startTime >= 901) {						
						if(snakeGame.getSeconds()<59) {
							snakeGame.setSeconds(snakeGame.getSeconds()+1);
						}else if(snakeGame.getSeconds()>=59) {
							snakeGame.setSeconds(0);
							snakeGame.setMinutes(snakeGame.getMinutes()+1);
						}	
						startTime += currentTime - startTime + 100;
					}
				}
			
			GUI.f1.repaint();
			

			try {
				Thread.sleep(snakeGame.getDifficultyLevel_thread());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void collisionWall() {
		if(snake.isSnakeUp()==true) {
			if(snake.getSnakeY()<20) {
				finishGame();
			}else{
				snake.setSnakeY(snake.getSnakeY() - 15);
			}
			
		}else if(snake.isSnakeDown()==true) {
			if(snake.getSnakeY()>335) {
				finishGame();
			}else {
				snake.setSnakeY(snake.getSnakeY() + 15);
			}
		}else if(snake.isSnakeLeft()==true) {
			if(snake.getSnakeX()<20) {
				finishGame();
			}else {
				snake.setSnakeX(snake.getSnakeX() - 15);
			}
		}else if(snake.isSnakeRight()==true) {
			if(snake.getSnakeX()>370) {
				finishGame();
			}else {
				snake.setSnakeX(snake.getSnakeX() + 15);
			}
		}
	}
	
	
	
	public void collisionSnake() {
		for(int i=1; i<snake.getList().size()-1; i++) {
			
			if(i+1<snake.getList().size()) {
				if(snake.getList().get(0).intersects(snake.getList().get(i+1))) {
					finishGame();
				}
			}
		}
	}
	
	public void collisionFood() {
		if(Math.abs(food.getFoodX()-snake.getSnakeX())<=8  &&  Math.abs(food.getFoodY()-snake.getSnakeY())<=8) {
			food.setFoodPlaced(false);

			snake.getList().add(new Rectangle(snake.getSnakeX(), snake.getSnakeY(), 10, 10));

			snakeGame.score += snakeGame.getMaxScoreLeft();
		}
	}
	
	
	public void placeFood() {
		if(food.isFoodPlaced() == false) {
			food.setFoodX((int) (35+Math.random()*335));
			food.setFoodY((int) (35+Math.random()*315));
			food.setFoodPlaced(true);
			snakeGame.maxScoreLeft = 100;
		}
	}
	
	
	
	public Snake getSnake() {
		return snake;
	}


	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public SnakeGame getSnakeGame() {
		return snakeGame;
	}


	public void setSnakeGame(SnakeGame snakeGame) {
		this.snakeGame = snakeGame;
	}

	public boolean isFinishedBlink1() {
		return finishedBlink1;
	}


	public void setFinishedBlink1(boolean finishedBlink1) {
		this.finishedBlink1 = finishedBlink1;
	}


	public boolean isFinishedBlink2() {
		return finishedBlink2;
	}


	public void setFinishedBlink2(boolean finishedBlink2) {
		this.finishedBlink2 = finishedBlink2;
	}


	public boolean isPause() {
		return pause;
	}


	public void setPause(boolean pause) {
		this.pause = pause;
	}


	public boolean isRestart() {
		return restart;
	}


	public void setRestart(boolean restart) {
		this.restart = restart;
	}


	public String getMenuSelection() {
		return menuSelection;
	}


	public void setMenuSelection(String menuSelection) {
		this.menuSelection = menuSelection;
	}

	public int getMaxScoreLeft() {
		return maxScoreLeft;
	}

	public void setMaxScoreLeft(int maxScoreLeft) {
		this.maxScoreLeft = maxScoreLeft;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}


	public boolean isStartmenu() {
		return startMenu;
	}

	public void setStartmenu(boolean startmenu) {
		this.startMenu = startmenu;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public int getDifficultyLevel_thread() {
		return difficultyLevel_thread;
	}

	public void setDifficultyLevel_thread(int difficultyLevel_thread) {
		this.difficultyLevel_thread = difficultyLevel_thread;
	}

}
