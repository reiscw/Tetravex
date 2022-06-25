import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public abstract class TetravexPanel extends JPanel {
	
	// present to avoid warning messages in Eclipse
	public static final long serialVersionUID = 1L;
	
	protected TetravexButton[][] buttons;
	protected int size;
		
	public TetravexPanel(int matrixSize) {
		super();
		size = matrixSize;
		buttons = new TetravexButton[size][size];
		setLayout(new GridLayout(size, size));
	}
	
	public TetravexButton getButton(int row, int column) {return null;}
	
	public ArrayList<TetravexButton> selectedButtons() {
		ArrayList<TetravexButton> result = new ArrayList<TetravexButton>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (buttons[i][j].isSelected()) {
					result.add(buttons[i][j]);
				}
			}
		}
		return result;
	}
	
	public void clearAllButtons() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				buttons[i][j].setSelected(false);
			}
		}
	}
}
