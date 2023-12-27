import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IO {
	
	public static BufferedImage loadImage(String filepath) {
	
		try {
			return ImageIO.read(Game.class.getResourceAsStream(filepath));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
