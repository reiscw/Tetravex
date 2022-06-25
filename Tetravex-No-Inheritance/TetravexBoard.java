import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class TetravexBoard {
	
	// present to avoid warning messages in Eclipse
	public static final long serialVersionUID = 1L;
	
	private JFrame board;
	private PuzzlePanel source;
	private WorkPanel destination;
	private JToggleButton restart;
	private JToggleButton newGame;
	private int matrixSize;
	
	public TetravexBoard(int size) {
		board = new JFrame();
		matrixSize = size;
		newGame();
	}

	public PuzzlePanel getSource() {return source;}
	public WorkPanel getDestination() {return destination;}
	public JToggleButton getRestartButton() {return restart;}
	public JToggleButton getNewGameButton() {return newGame;}

	public void restart() {
		
		// reset the working panel to original state
		destination.reset();

		// reset the puzzle panel to original state
		source.reset();
		
		// reset the frame
		redraw();
		
	}
	
	public void newGame() {
		
		// initial setup of the puzzle
		source = new PuzzlePanel(matrixSize);
		destination = new WorkPanel(matrixSize);
		
		// reset the frame
		redraw();
	}

	public void redraw() {

		// clear the JFrame
		board.getContentPane().removeAll();
		
		// maximize the window
		board.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		// title setup
		board.setTitle("Tetravex");
		
		// adding panels
		board.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		board.add(destination.getPanel(), c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		board.add(source.getPanel(), c);
		
		// adding buttons
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		restart = new JToggleButton();
		restart.setText("Restart Game");
		restart.setPreferredSize(new Dimension(Tile.TILE_SIZE*4, Tile.TILE_SIZE/2));
		board.add(restart, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		newGame = new JToggleButton();
		newGame.setText("New Game");
		newGame.setPreferredSize(new Dimension(Tile.TILE_SIZE*4, Tile.TILE_SIZE/2));
		board.add(newGame, c);
		
		// turn the board on
		board.setVisible(true);
		board.validate();
	}
	
	public void gameCycle() throws InterruptedException {
		// check for the restart game button
		if (getRestartButton().isSelected()) {
			restart();
			return;
		}
	
		// check for the new game button
		if (getNewGameButton().isSelected()) {
			newGame();
			getNewGameButton().setSelected(false);
			return;
		}
	
		// check for pressed buttons in both panels
		ArrayList<TetravexButton> sourceButtons = getSource().selectedButtons();
		ArrayList<TetravexButton> destinationButtons = getDestination().selectedButtons();
		
		// if only one button is pressed in the puzzle panel, leave it
		if (sourceButtons.size() == 1 && destinationButtons.size() == 0)
			return;
			
		// if one button is pressed in the work panel, leave it
		if (sourceButtons.size() == 0 && destinationButtons.size() == 1)
			return;
		
		// if two or more buttons are pressed in the puzzle panel, 
		// clear all buttons
		if (sourceButtons.size() > 1) {
			getSource().clearAllButtons();
			getDestination().clearAllButtons();
			return;
		}
		
		// if three or more buttons are pressed in the work panel, 
		// clear all buttons
		if (destinationButtons.size() > 2) {
			getSource().clearAllButtons();
			getDestination().clearAllButtons();
			return;
		}
		
		// if one button is pressed in the puzzle panel and one in 
		// the work panel, check if the move is valid and do the move
		// if it is, otherwise clear
		if (sourceButtons.size() == 1 && destinationButtons.size() == 1) {
			TetravexButton source = sourceButtons.get(0);
			TetravexButton destination = destinationButtons.get(0);
			if (destination.getTile() == null && source.getTile() != null) {
				destination.setTile(source.getTile());
				source.setTile(null);
			} else if (destination.getTile() != null && source.getTile() == null) {
				source.setTile(destination.getTile());
				destination.setTile(null);
			}
			getSource().clearAllButtons();
			getDestination().clearAllButtons(); 
			return;	
		}
		
		// if two buttons are pressed in the work panel, check if the move is possible, 
		// and if so do it, otherwise clear
		if (destinationButtons.size() == 2) {
			if (destinationButtons.get(0).getTile() == null ^ destinationButtons.get(1).getTile() == null) {
				if (destinationButtons.get(0).getTile() == null) {
					destinationButtons.get(0).setTile(destinationButtons.get(1).getTile());
					destinationButtons.get(1).setTile(null);
				} else {
					destinationButtons.get(1).setTile(destinationButtons.get(0).getTile());
					destinationButtons.get(0).setTile(null);
				}
			}
			getSource().clearAllButtons();
			getDestination().clearAllButtons();
			return;
		}
	
		// check for validity of solution
		if (getSource().getPuzzle().checkSolution(getDestination().getTiles())) {
			partyDown();
			System.exit(0);
		}
		
		// quit if the user closes the window
		if (!board.isShowing()) {
			System.exit(0);
		}			
	}
	
	public void partyDown() throws InterruptedException {
		// flash the work panel
		for (int i = 0; i < 2500; i++) {
			getDestination().flash();
			Thread.sleep(1);
		}
	}

	public JFrame getBoard() {return board;}
}
