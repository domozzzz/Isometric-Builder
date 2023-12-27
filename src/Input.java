import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Input implements KeyListener, MouseListener {
	
	private Game game;
	
	public Input(Game game) {
		this.game = game;
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W -> game.getLevel().up();
		case KeyEvent.VK_S -> game.getLevel().down();
		case KeyEvent.VK_A -> game.getLevel().left();
		case KeyEvent.VK_D -> game.getLevel().right();
		case KeyEvent.VK_0 -> game.getLevel().startScrolling();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
	
	}


	@Override
	public void mousePressed(MouseEvent e) {
		switch(e.getButton()) {
		case MouseEvent.BUTTON1 -> game.getLevel().click = true;
		case MouseEvent.BUTTON3 -> game.getLevel().remove = true;
		case MouseEvent.BUTTON2 -> game.getLevel().startScrolling();
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		switch(e.getButton()) {
		case MouseEvent.BUTTON1 -> game.getLevel().click = false;
		case MouseEvent.BUTTON3 -> game.getLevel().remove = false;
		case MouseEvent.BUTTON2 -> game.getLevel().stopScrolling();
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {	
		
	}


	@Override
	public void mouseExited(MouseEvent e) {

	}

}
