import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class PuzzlePanel {
	
	// present to avoid warning messages in Eclipse
	public static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private TetravexButton[][] buttons;
	private int size;
	private TetravexPuzzle puzzle;
	private Tile[][] tileSource;
	
	public PuzzlePanel(int matrixSize) {
		size = matrixSize;
		buttons = new TetravexButton[size][size];
		panel = new JPanel();
		panel.setLayout(new GridLayout(size, size));
		puzzle = new TetravexPuzzle(size);
		tileSource = puzzle.getShuffledTiles();
		reset();
	}
	
	public JPanel getPanel() {return panel;}
	
	public void reset() {
		panel.removeAll();
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				buttons[i][j] = new TetravexButton(tileSource[i][j]);
				panel.add(buttons[i][j].getButton());
			}
		}
	}
		
	public TetravexPuzzle getPuzzle() {return puzzle;}

	public ArrayList<TetravexButton> selectedButtons() {
		ArrayList<TetravexButton> result = new ArrayList<TetravexButton>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (buttons[i][j].getButton().isSelected()) {
					result.add(buttons[i][j]);
				}
			}
		}
		return result;
	}
	
	public void clearAllButtons() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				buttons[i][j].getButton().setSelected(false);
			}
		}
	}
	
}
