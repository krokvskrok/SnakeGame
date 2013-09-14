
public class Food {
	
	private int foodX = 0;												
	private int foodY = 0;
	private boolean foodPlaced = false;
	private Food food;
	

	public int getFoodX() {
		return foodX;
	}
	public void setFoodX(int essenX) {
		this.foodX = essenX;
	}
	public int getFoodY() {
		return foodY;
	}
	public void setFoodY(int essenY) {
		this.foodY = essenY;
	}
	public boolean isFoodPlaced() {
		return foodPlaced;
	}
	public void setFoodPlaced(boolean foodPlaced) {
		this.foodPlaced = foodPlaced;
	}
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}

}
