import java.awt.Rectangle;
import java.util.ArrayList;


public class Snake {
	
	private int snakeX = 100;
	private int snakeY = 100;
	private boolean snakeUp = false;
	private boolean snakeDown = false;
	private boolean snakeLeft = false;
	private boolean snakeRight = true;
	private  ArrayList<Rectangle> list = new ArrayList<Rectangle>();
	
	
	public  ArrayList<Rectangle> getList() {
		return list;
	}
	public  void setList(ArrayList<Rectangle> list) {
		this.list = list;
	}
	public int getSnakeX() {
		return snakeX;
	}
	public void setSnakeX(int snakeX) {
		this.snakeX = snakeX;
	}
	public int getSnakeY() {
		return snakeY;
	}
	public void setSnakeY(int snakeY) {
		this.snakeY = snakeY;
	}
	public boolean isSnakeUp() {
		return snakeUp;
	}
	public void setSnakeUp(boolean snakeUp) {
		this.snakeUp = snakeUp;
	}
	public boolean isSnakeDown() {
		return snakeDown;
	}
	public void setSnakeDown(boolean snakeDown) {
		this.snakeDown = snakeDown;
	}
	public boolean isSnakeLeft() {
		return snakeLeft;
	}
	public void setSnakeLeft(boolean snakeLeft) {
		this.snakeLeft = snakeLeft;
	}
	public boolean isSnakeRight() {
		return snakeRight;
	}
	public void setSnakeRight(boolean snakeRight) {
		this.snakeRight = snakeRight;
	}
	
	

}
