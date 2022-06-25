import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;


public class WorkPanel {
	
	// present to avoid warning messages in Eclipse
	public static final long serialVersionUID = 1L;
	
	public JPanel panel;
	public TetravexButton[][] buttons;
	public int size;
	
	
	public WorkPanel(int matrixSize) {
		size = matrixSize;
		buttons = new TetravexButton[size][size];
		panel = new JPanel();
		panel.setLayout(new GridLayout(size, size));
		reset();
	}
		
	public JPanel getPanel() {return panel;}
	
	public void reset() {
		panel.removeAll();
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				buttons[i][j] = new TetravexButton(null);
				panel.add(buttons[i][j].getButton());
			}
		}
	}
	
	public Tile[][] getTiles() {
		Tile[][] result = new Tile[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				result[i][j] = buttons[i][j].getTile();
			}
		}
		return result;
	}
	
	public void flash() {
		// pick a button to randomly flash (alter the selection state)
		int row = (int) (Math.random() * size);
		int col = (int) (Math.random() * size);
		buttons[row][col].getButton().setSelected(!buttons[row][col].getButton().isSelected());
	}
	
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