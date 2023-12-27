import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	public static int SCREEN_HEIGHT = 600;
	public static int SCREEN_WIDTH = 800;
	
	private int FPS = 60;
	private boolean running = false;
	private int[] pixels;
	private BufferedImage displayImage;
	private Renderer renderer;
	private Level level;
	private Input input;
	private Player player;
	
	public Game() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		displayImage = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		setPixels(((DataBufferInt)displayImage.getRaster().getDataBuffer()).getData());	
	}
	
	public void init() {
		level = new Level(this);
		renderer = new Renderer(this);
		input = new Input(this);
		player = new Player(this);
		
		level.addPlayer(player);
		this.addKeyListener(input);
		this.addMouseListener(input);
	}

	public void start() {
		running = true;
		new Thread(this).start();
	}
	
	public void stop() {
		running = false;
	}
	
	@Override
	public void run() {
	
		double i = 0;
		double lastTime = System.currentTimeMillis();
		while (running) {
			
			double currentTime = System.currentTimeMillis();
			i += (currentTime - lastTime) / (1000/FPS);
			lastTime = currentTime;

			
			if (i >= 1) {
				tick();
				render();
				i--;
			}
		}
	}
	


	private void tick() {
		level.tick();
	}
	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = displayImage.getGraphics();
		
		renderer.clear();
		level.render(renderer);
		
		g = bs.getDrawGraphics();
		g.drawImage(displayImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		bs.show();
	}
	
	public int getMouseX() {
	    return (int) (MouseInfo.getPointerInfo().getLocation().getX() - this.getLocationOnScreen().getX());
	}

	public int getMouseY() {
		return (int) (MouseInfo.getPointerInfo().getLocation().getY() - this.getLocationOnScreen().getY());
	}
	 
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		
		frame.setTitle("blubbzy");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		game.init();
		game.start();
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public Level getLevel() {
		return level;
	}
}

