package jessevii.main;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
	public Panel() {
		this.setBounds(0, 0, Tetris.instance.getWidth(), Tetris.instance.getHeight());
		this.setBackground(new Color(14, 14, 14));
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D)graphics;
		
		if (Tetris.current == null) {
			Tetris.drawGameOver(g);
		} else {
			TetrisBlock.drawAll(g);
		}
	}
}
