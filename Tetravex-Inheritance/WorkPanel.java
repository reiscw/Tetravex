public class WorkPanel extends TetravexPanel {
	
	// present to avoid warning messages in Eclipse
	public static final long serialVersionUID = 1L;
	
	public WorkPanel(int size) {
		super(size);
		reset();
	}
		
	public void reset() {
		removeAll();
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				buttons[i][j] = new TetravexButton(null);
				add(buttons[i][j]);
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
		buttons[row][col].setSelected(!buttons[row][col].isSelected());
	}
}

	
