package jessevii.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyPressedListener implements KeyListener {
	@Override
	public void keyPressed(KeyEvent e) {
		if (Tetris.current != null) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					Tetris.current.rotate();
					break;
				case KeyEvent.VK_DOWN:
					if (Tetris.current.canMoveDown()) {
						Tetris.current.moveDown();
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (Tetris.current.canMoveRight()) {
						Tetris.current.moveRight();
					}
					break;
				case KeyEvent.VK_LEFT:
					if (Tetris.current.canMoveLeft()) {
						Tetris.current.moveLeft();
					}
					break;
				case KeyEvent.VK_SPACE:
					while (Tetris.current.canMoveDown()) {
						Tetris.current.moveDown();
					}
					break;
				default:
					return;
			}
			
			Tetris.paint();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Tetris.play.doClick();
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
