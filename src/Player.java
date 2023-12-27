
public class Player {
	
	private int money = 10000;
	private Game game;
	
	public Player(Game game) {
		this.game = game;
	}

	public int getMoney() {
		return money;
	}
	
	public void setMoney(int amount) {
		this.money = amount;
	}

	public void takeMoney(int amount) {
		this.money -= amount;
	}

	public void giveMoney(int amount) {
		this.money += amount;
	}
}
