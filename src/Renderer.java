import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Renderer {

	Game game;
	
	public Renderer(Game game) {
		this.game = game;
	}
	
	public void render(BufferedImage image, int xpos, int ypos) {
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
					
				if (xpos + x < 0 || xpos + x >= Game.SCREEN_WIDTH) break;
						
				int pixel = (x + xpos) + (y + ypos) * Game.SCREEN_WIDTH;
				
				if (pixel >= 0 && pixel < game.getPixels().length) {
					if (image.getRGB(x, y) != 0x00000000) { //dont render transparent
						game.getPixels()[pixel] = image.getRGB(x, y);
					}
				}
			}
		}
	}

	public void clear() {
		//clear black
		Arrays.fill(game.getPixels(), 0xFF000000);
	}
}
