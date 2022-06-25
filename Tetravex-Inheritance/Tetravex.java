public class Tetravex {
	
	public static void main(String[] args) throws InterruptedException {

		int size = 4; // could be adjusted, but needs to be tested
		TetravexBoard board = new TetravexBoard(size);

		while (true) {
			board.gameCycle();
		}
	}
}
