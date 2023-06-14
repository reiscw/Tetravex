import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.font.*;

public class TetravexPanel extends JPanel {
	
	private TetravexButton[][] sourceButtons;
	private TetravexButton[][] destinationButtons;
	private JButton newGameButton;
	private JButton resetGameButton;
	private JButton shiftUpButton;
	private JButton shiftDownButton;
	private JButton shiftRightButton;
	private JButton shiftLeftButton;
	private JButton quitButton;
	private int size;
	private TetravexPuzzle puzzle;
	private static final int BUTTON_SIZE = 100;
	private static final int FONT_SIZE = 16;

	public TetravexPanel(int size) {
		// set size 
		this.size = size;
		// adjust size and set layout
		setPreferredSize(new Dimension ((2*size+4)*BUTTON_SIZE, (size+1)*BUTTON_SIZE));
		setLayout(null);
		// create puzzle
		puzzle = new TetravexPuzzle(size);
		// setup buttons
		buttonSetup();
	}
	
	public void buttonSetup() {
		sourceButtons = new TetravexButton[size][size];
		destinationButtons = new TetravexButton[size][size];
		Tile[][] shuffledTiles = puzzle.getShuffledTiles();
		// set up source buttons
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				final int r = i; // necessary for lambda expression
				final int c = j; // necessary for lambda expression
				sourceButtons[i][j] = new TetravexButton(shuffledTiles[i][j]);
				sourceButtons[i][j].setIcon(new ImageIcon(shuffledTiles[i][j].drawTile()));
				sourceButtons[i][j].setBounds(50 + j*BUTTON_SIZE, 50+i*BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
				add(sourceButtons[i][j]);
				sourceButtons[i][j].addActionListener(e -> {
					try {
						clickSource(r, c);
					} catch (Exception exc) {
						exc.printStackTrace();
					}
				});
			}
		}
		// set up destination buttons
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				final int r = i; // necessary for lambda expression
				final int c = j; // necessary for lambda expression
				destinationButtons[i][j] = new TetravexButton(null);
				destinationButtons[i][j].setBounds(BUTTON_SIZE * (size + 1) + j*BUTTON_SIZE, 50+i*BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
				add(destinationButtons[i][j]);
				destinationButtons[i][j].addActionListener(e -> {
					try {
						clickDestination(r, c);
					} catch (Exception exc) {
						exc.printStackTrace();
					}
				});
			}
		}
		// set up side buttons
		newGameButton = new JButton("New Game");
		resetGameButton = new JButton("Reset Game");
		shiftUpButton = new JButton("Shift Up");
		shiftDownButton = new JButton("Shift Down");
		shiftLeftButton = new JButton("Shift Left");
		shiftRightButton = new JButton("Shift Right");
		quitButton = new JButton("Quit Game");
		newGameButton.setBounds(50 + (2 * size + 1) * BUTTON_SIZE, 50, 150, 20);
		resetGameButton.setBounds(50 + (2 * size + 1) * BUTTON_SIZE, 90, 150, 20);
		shiftUpButton.setBounds(50 + (2 * size + 1) * BUTTON_SIZE, 130, 150, 20);
		shiftDownButton.setBounds(50 + (2 * size + 1) * BUTTON_SIZE, 170, 150, 20);
		shiftLeftButton.setBounds(50 + (2 * size + 1) * BUTTON_SIZE, 210, 150, 20);
		shiftRightButton.setBounds(50 + (2 * size + 1) * BUTTON_SIZE, 250, 150, 20);
		quitButton.setBounds(50 + (2 * size + 1) * BUTTON_SIZE, 290, 150, 20);
		add(newGameButton);
		add(resetGameButton);
		add(shiftUpButton);
		add(shiftDownButton);
		add(shiftLeftButton);
		add(shiftRightButton);
		add(quitButton);
		newGameButton.addActionListener(e -> {
			try {
				newGame();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		resetGameButton.addActionListener(e -> {
			try {
				resetGame();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		shiftUpButton.addActionListener(e -> {
			try {
				shiftUp();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		shiftDownButton.addActionListener(e -> {
			try {
				shiftDown();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		shiftLeftButton.addActionListener(e -> {
			try {
				shiftLeft();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		shiftRightButton.addActionListener(e -> {
			try {
				shiftRight();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		quitButton.addActionListener(e -> {
			try {
				quit();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
	}
	
	public void clickSource(int i, int j) {
		// check if a button has been clicked in source (if so, invalid, perform swap in destination)
		boolean source = false;
		int r = 0;
		int c = 0;
		for (r = 0; r < size; r++) {
			for (c = 0; c < size; c++) {
				if (!sourceButtons[r][c].isEnabled() && !(r == i && c == j)) {
					Tile one = sourceButtons[i][j].getTile();
					Tile two = sourceButtons[r][c].getTile();
					ImageIcon oneIcon = null;
					ImageIcon twoIcon = null;
					if (one != null) {
						oneIcon = new ImageIcon(one.drawTile());
					}
					if (two != null) {
						twoIcon = new ImageIcon(two.drawTile());
					}
					sourceButtons[r][c].setTile(one);
					sourceButtons[r][c].setIcon(oneIcon);
					sourceButtons[i][j].setTile(two);
					sourceButtons[i][j].setIcon(twoIcon);
					sourceButtons[r][c].setEnabled(true);
					sourceButtons[i][j].setEnabled(true);
					check();
					return;
				}
			}
		}
		// check if a button has been clicked in destination
		boolean destination = false;
		for (r = 0; r < size; r++) {
			for (c = 0; c < size; c++) {
				if (!destinationButtons[r][c].isEnabled()) {
					destination = true;
					break;
				}
			}
			if (destination) break;
		}
		if (destination) {
			// check if both tiles are null, if so, do nothing)
			if (sourceButtons[i][j].getTile() == null && destinationButtons[r][c].getTile() == null) {
				sourceButtons[i][j].setEnabled(true);
				destinationButtons[r][c].setEnabled(true);
				return;
			}
			// if not, perform the swap
			Tile s = sourceButtons[i][j].getTile();
			Tile d = destinationButtons[r][c].getTile();
			ImageIcon sIcon = null;
			ImageIcon dIcon = null;
			if (s != null) {
				sIcon = new ImageIcon(s.drawTile());
			}
			if (d != null) {
				dIcon = new ImageIcon(d.drawTile());
			}
			destinationButtons[r][c].setTile(s);
			destinationButtons[r][c].setIcon(sIcon);
			sourceButtons[i][j].setTile(d);
			sourceButtons[i][j].setIcon(dIcon);
			destinationButtons[r][c].setEnabled(true);
			sourceButtons[i][j].setEnabled(true);
			check();
		} else {
			// if not, turn the current button off
			sourceButtons[i][j].setEnabled(false);
		}
	}
	
	public void clickDestination(int i, int j) {
		// check if another tile has been clicked in destination (if so, perform swap in destination)
		int r = 0;
		int c = 0;
		for (r = 0; r < size; r++) {
			for (c = 0; c < size; c++) {
				if (!destinationButtons[r][c].isEnabled() && !(r == i && c == j)) {
					Tile one = destinationButtons[i][j].getTile();
					Tile two = destinationButtons[r][c].getTile();
					ImageIcon oneIcon = null;
					ImageIcon twoIcon = null;
					if (one != null) {
						oneIcon = new ImageIcon(one.drawTile());
					}
					if (two != null) {
						twoIcon = new ImageIcon(two.drawTile());
					}
					destinationButtons[r][c].setTile(one);
					destinationButtons[r][c].setIcon(oneIcon);
					destinationButtons[i][j].setTile(two);
					destinationButtons[i][j].setIcon(twoIcon);
					destinationButtons[r][c].setEnabled(true);
					destinationButtons[i][j].setEnabled(true);
					check();
					return;
				}
			}
		}
		// check if a tile has been clicked in source (if so, perform swap)
		for (r = 0; r < size; r++) {
			for (c = 0; c < size; c++) {
				if (!sourceButtons[r][c].isEnabled()) {
					// check if both tiles are null, if so, do nothing)
					if (sourceButtons[r][c].getTile() == null && destinationButtons[i][j].getTile() == null) {
						sourceButtons[r][c].setEnabled(true);
						destinationButtons[i][j].setEnabled(true);
						return;
					}
					// otherwise, do the swap
					Tile s = sourceButtons[r][c].getTile();
					Tile d = destinationButtons[i][j].getTile();
					ImageIcon sIcon = null;
					ImageIcon dIcon = null;
					if (s != null) {
						sIcon = new ImageIcon(s.drawTile());
					}
					if (d != null) {
						dIcon = new ImageIcon(d.drawTile());
					}
					destinationButtons[i][j].setTile(s);
					destinationButtons[i][j].setIcon(sIcon);
					sourceButtons[r][c].setTile(d);
					sourceButtons[r][c].setIcon(dIcon);
					destinationButtons[i][j].setEnabled(true);
					sourceButtons[r][c].setEnabled(true);
					check();
					return;
				}
			}
		}
		// if this is the only button selected, turn it off
		destinationButtons[i][j].setEnabled(false);
	}
	
	public void newGame() {
		puzzle = new TetravexPuzzle(size);
		resetGame();
	}
	
	public void resetGame() {
		Tile[][] shuffledTiles = puzzle.getShuffledTiles();
		// set up source buttons
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				sourceButtons[i][j].setTile(shuffledTiles[i][j]);
				sourceButtons[i][j].setIcon(new ImageIcon(shuffledTiles[i][j].drawTile()));
			}
		}
		// set up destination buttons
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				destinationButtons[i][j].setTile(null);
				destinationButtons[i][j].setIcon(null);
			}
		}
	}
	
	public void shiftUp() {
		// check if the entire first row is null
		for (int c = 0; c < size; c++) {
			if (destinationButtons[0][c].getTile() != null) {
				return;
			}
		}
		// perform the shift
		for (int r = 0; r < size - 1; r++) {
			for (int c = 0; c < size; c++) {
				destinationButtons[r][c].setTile(destinationButtons[r+1][c].getTile());
				destinationButtons[r][c].setIcon(destinationButtons[r+1][c].getIcon());
			}
		}
		// null out the bottom row
		for (int c = 0; c < size; c++) {
			destinationButtons[size - 1][c].setTile(null);
			destinationButtons[size - 1][c].setIcon(null);
		}
	}
	
	public void shiftDown() {
		// check if the entire last row is null
		for (int c = 0; c < size; c++) {
			if (destinationButtons[size - 1][c].getTile() != null) {
				return;
			}
		}
		// perform the shift
		for (int r = size - 1; r > 0; r--) {
			for (int c = 0; c < size; c++) {
				destinationButtons[r][c].setTile(destinationButtons[r-1][c].getTile());
				destinationButtons[r][c].setIcon(destinationButtons[r-1][c].getIcon());
			}
		}
		// null out the top row
		for (int c = 0; c < size; c++) {
			destinationButtons[0][c].setTile(null);
			destinationButtons[0][c].setIcon(null);
		}
	}
	
	public void shiftLeft() {
		// check if the entire first column is null
		for (int r = 0; r < size; r++) {
			if (destinationButtons[r][0].getTile() != null) {
				return;
			}
		}
		// perform the shift
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size - 1; c++) {
				destinationButtons[r][c].setTile(destinationButtons[r][c+1].getTile());
				destinationButtons[r][c].setIcon(destinationButtons[r][c+1].getIcon());
			}
		}
		// null out the last column
		for (int r = 0; r < size; r++) {
			destinationButtons[r][size - 1].setTile(null);
			destinationButtons[r][size - 1].setIcon(null);
		}
	}
	
	public void shiftRight() throws InterruptedException {
		// check if the entire last column is null
		for (int r = 0; r < size; r++) {
			if (destinationButtons[r][size-1].getTile() != null) {
				return;
			}
		}
		// perform the shift
		for (int r = 0; r < size; r++) {
			for (int c = size - 1; c > 0; c--) {
				destinationButtons[r][c].setTile(destinationButtons[r][c-1].getTile());
				destinationButtons[r][c].setIcon(destinationButtons[r][c-1].getIcon());
			}
		}
		// null out the firt column
		for (int r = 0; r < size; r++) {
			destinationButtons[r][0].setTile(null);
			destinationButtons[r][0].setIcon(null);
		}
	}
	
	public void quit() {
		System.exit(0);
	}
	
	public void check() {
		boolean success = checkSolution();
		if (success) {
			//partyDown();
			String successMessage = "Would you like to play again?";
			int result = JOptionPane.showConfirmDialog(null,  successMessage, "Congratulations!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				newGame();
			} else {
				System.exit(0);
			}
		}
	}
	
	public boolean checkSolution() {
		Tile[][] potentialSolution = new Tile[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (destinationButtons[i][j].getTile() == null) {
					return false;
				}
				potentialSolution[i][j] = destinationButtons[i][j].getTile();
			}
		}
		return puzzle.checkSolution(potentialSolution);
	}
	
	public void partyDown() throws InterruptedException {
		// flash the work panel
		for (int i = 0; i < 2500; i++) {
			int r = (int)(Math.random()*size);
			int c = (int)(Math.random()*size);
			boolean state = sourceButtons[r][c].isEnabled();
			sourceButtons[r][c].setEnabled(!state);
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Tetravex 2.2 by Christopher Reis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField sizeEntry = new JTextField();
		Object[] message = {"Enter your desired puzzle size (3-5): ", sizeEntry};
		int result = JOptionPane.showConfirmDialog(null,  message, "Choose your Tetravex size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		int size = 0;
		if (result == JOptionPane.OK_OPTION) {
			try {
				size = Integer.parseInt(sizeEntry.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (size < 3 || size > 5) {
				System.exit(0);
			}
			TetravexPanel panel = new TetravexPanel(size);
			frame.getContentPane().add(panel);
			frame.pack();
			frame.setVisible(true);
		}  else {
			System.exit(0);
		}
	}
	
}
