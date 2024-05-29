import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Level {
	
	private int cols = 20;
	private int rows = 20;
	private int tileWidth = 40;
	private int tileHeight = 20;
	private int originX = 9;
	private int originY = 9;
	private BufferedImage[] tiles;
	private BufferedImage hiSquare;
	private int[] grid;
	private boolean scrolling = false;
	private int mouseStartY = 0;
	private int mouseStartX = 0;
	private int panX = 0;
	private int panY = 0;
	public boolean click = false;
	public boolean remove = false;
	
	private int selectedTile = 3;
	
	private final int TREE = 4;
	
	private Game game;
	private Player player;
	
	public Level(Game game) {
		
		this.game = game;
		
		init();
	}
	
	private void init() {
		tiles = new BufferedImage[5];
		tiles[0] = IO.loadImage("res/tile.png");
		tiles[1] = IO.loadImage("res/highlight.png");
		tiles[2] = IO.loadImage("res/cheat.png");
		tiles[3] = IO.loadImage("res/checkered.png");
		tiles[4] = IO.loadImage("res/tree.png");
		
		grid = new int[rows*cols];
		Arrays.fill(grid, 0);
		
		hiSquare = IO.loadImage("res/hiSquare.png");
	}
	
	public void tick() {
		int mouseX = game.getMouseX();
		int mouseY = game.getMouseY();
		
		if (scrolling) {
			if (mouseX > mouseStartX && panX < rows/2) {
				panX++;
			} else if (mouseX < mouseStartX && panX >= -rows/2) {
				panX--;
			}
			
			if (mouseY > mouseStartY && panY < cols/2) {
				panY++;
			} else if (mouseY < mouseStartY && panY >= -cols/2) {
				panY--;
			}
		}
		
		mouseStartX = mouseX;
		mouseStartY = mouseY;
	}

	public void render(Renderer renderer) {


		//render map
		for (int y = 0; y < cols; y++) {
			
			for (int x = 0; x < rows; x++) {
								
				int posx = (originX * tileWidth + (x - y) * tileWidth/2) - panX * tileWidth;
				int posy = (originY * tileHeight + (x + y) * tileHeight/2) - panY * tileHeight;
				
				int coord = grid[x + y*cols];
				
				//tree
				if (coord == 4) {
					posy -= tileHeight;
				}

				renderer.render(tiles[coord], posx, posy);
				
			}
		}
		
		//highlight hovered tile
		int mouseX = game.getMouseX();
		int mouseY = game.getMouseY();
		
		if (mouseX >= 0 && mouseX < Game.SCREEN_WIDTH
				&& mouseY >= 0 && mouseY < Game.SCREEN_HEIGHT) {
			
			int highlightedX = (mouseX / tileWidth) * tileWidth;
			int highlightedY = (mouseY / tileHeight) * tileHeight;
			
			int cellX = mouseX / tileWidth;
			int cellY = mouseY / tileHeight;
			
			int offsetX = mouseX % tileWidth;
			int offsetY = mouseY % tileHeight;
			
			int selectedX = (cellY - originY) + (cellX - originX);
			int selectedY = (cellY - originY) - (cellX - originX);
			
			//determine corner tiles
			int col = tiles[2].getRGB(offsetX, offsetY);
			
			if (col == 0xFFFF0000) {
				highlightedX -= tileWidth/2;
				highlightedY -= tileHeight/2;
				
				selectedX -= 1;
			}
			else if (col == 0xFF00FF00) {
				highlightedX -= tileWidth/2;
				highlightedY += tileHeight/2;
				
				
				selectedY += 1;
			}
			else if (col == 0xFF0000FF) { 
				highlightedX += tileWidth/2;
				highlightedY -= tileHeight/2;
				
				selectedY -= 1;
			}
			else if (col == 0xFFFFFF00) {
				highlightedX += tileWidth/2;
				highlightedY += tileHeight/2;
				
				
				selectedX += 1;	
			}
			
			int scrolledSelectedX = selectedX + panX + panY;
			int scrolledSelectedY = selectedY - panX + panY;
			
			if (scrolledSelectedX >= 0 && scrolledSelectedY >= 0
					&& scrolledSelectedX < rows && scrolledSelectedY < cols) {
				
				renderer.render(tiles[1], highlightedX, highlightedY);
				
				if (click && grid[(scrolledSelectedY) * rows + (scrolledSelectedX)] != selectedTile 
						&& player.getMoney() > 0) {
					
					grid[(scrolledSelectedY) * rows + scrolledSelectedX] = selectedTile;
					//player.takeMoney(100);


				} else if (remove) {
					
					if (grid[(scrolledSelectedY) * rows + (scrolledSelectedX)] != 0) {
						grid[(scrolledSelectedY) * rows + (scrolledSelectedX)] = 0;
						player.giveMoney(100);

					}
				}
			}
		}
		
		//render tilemenu
		for (int i = 0; i < tiles.length; i++) {
			
			if (i == TREE) {
				renderer.render(tiles[i], (i*tileWidth), 28*tileHeight);
				
			} else {
				renderer.render(tiles[i], (i*tileWidth), 29*tileHeight);
			}
		}
		
		for (int i = 0; i < tiles.length; i++) {
			
			if (game.getMouseX() < (i + 1)*tileWidth && game.getMouseX() >= i*tileWidth
					&& game.getMouseY() >= 29*tileHeight) {
				
				if (click) {
					selectedTile = i;
					break;
				}	
			}
		}
		renderer.render(hiSquare, selectedTile*tileWidth, 29*tileHeight);
	}

	public void up() {
		panY -= 1;
	}
	
	public void down() {
		panY += 1;
	}

	public void left() {
		panX -= 1;
	}
	
	public void right() {
		panX += 1;
	}

	public void startScrolling() {
		mouseStartX = game.getMouseX();
		mouseStartY = game.getMouseY();
		scrolling = true;
	}
	
	public void stopScrolling() {
		scrolling = false;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void click() {
		int mouseX = game.getMouseX() / tileWidth;
		int mouseY = game.getMouseY() / tileHeight;
		
		
		if (mouseX <= cols && mouseY > 0) {
			grid[mouseX + mouseY * cols] = 1;	
		}
	}

	public void addPlayer(Player player) {
		this.player = player;		
	}
}
