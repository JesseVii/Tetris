package jessevii.main;

import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame {
	public static int score;
	public static JButton play;
	public static TetrisBlock current;
	public static Tetris instance;
	
	public static void main(String[] args) {
		//Calls the constructor and creates the JFrame
		SwingUtilities.invokeLater(() -> new Tetris());
	}
	
	//Initialize the JFrame
	public Tetris() {
		instance = this;
		this.setTitle("Tetris");
		this.setSize(405, 618);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFocusable(true);
		this.addKeyListener(new KeyPressedListener());
		
		play = new JButton("Play");
		play.setFont(new Font("Arial", Font.BOLD, 45));
		play.setForeground(Color.WHITE);
		play.setBackground(new Color(30, 30, 30));
		play.setBounds(100, 400, 200, 100);
		play.setVisible(false);
		play.addActionListener(e -> {
			score = 0;
			createGame();
			play.setVisible(false);
		});
		this.add(play);
		
		this.add(new Panel());
	}
	
	/**
	 * Calls repaint
	 */
	public static void paint() {
		instance.repaint();
	}
	
	/**
	 * Draws the game over screen.
	 */
	public static void drawGameOver(Graphics2D g) {
		g.setColor(new Color(14, 14, 14, 220));
		g.fillRect(0, 0, instance.getWidth(), instance.getHeight());
		
		Utils.drawCenterString(g, "-:255, 0, 0:-Game over", 100, 50);
		Utils.drawCenterString(g, "-:33, 148, 166:-Score: -:21, 237, 75:-" + score, 150, 50);

		Utils.drawCenterString(g, "-:213, 135, 237:-Controls", 220, 30);

		Utils.drawCenterString(g, "-:33, 148, 166:-Space: -:21, 237, 75:-Hard drop", 250, 20);
		Utils.drawCenterString(g, "-:33, 148, 166:-Up: -:21, 237, 75:-Rotate", 270, 20);
		Utils.drawCenterString(g, "-:33, 148, 166:-Down: -:21, 237, 75:-Soft drop", 290, 20);
		Utils.drawCenterString(g, "-:33, 148, 166:-Right: -:21, 237, 75:-Move right", 310, 20);
		Utils.drawCenterString(g, "-:33, 148, 166:-Left: -:21, 237, 75:-Move left", 330, 20);
	    
	    play.setVisible(true);
	}
	
	/**
	 * Creates the game
	 */
	public void createGame() {
		new Thread(() -> {
			while(true) {
				if (current == null || !current.canMoveDown()) {
					if (current != null) {
						Utils.playSound("Fall.wav");
						current.removeLayers();
					}

					current = new TetrisBlock(210, -20, null);
					if (!current.canMoveDown()) {
						Utils.playSound("GameOver.wav");
						current = null;
						TetrisBlock.blocks.clear();
						paint();
						break;
					}

					score++;
				}

				if (current.canMoveDown()) {
					current.moveDown();
					paint();
				}

				//Sleep some time before updating the block. The speed goes faster if the score is higher
				Utils.sleep(250 - (score / 5));
			}
		}).start();
	}
}
