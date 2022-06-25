public class PuzzlePanel extends TetravexPanel {
	
	// present to avoid warning messages in Eclipse
	public static final long serialVersionUID = 1L;
	
	private TetravexPuzzle puzzle;
	private Tile[][] tileSource;
	
	public PuzzlePanel(int size) {
		super(size);
		puzzle = new TetravexPuzzle(size);
		tileSource = puzzle.getShuffledTiles();
		reset();
	}
	
	public void reset() {
		removeAll();
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				buttons[i][j] = new TetravexButton(tileSource[i][j]);
				add(buttons[i][j]);
			}
		}
	}
		
	public TetravexPuzzle getPuzzle() {return puzzle;}

}
