public class TetravexPuzzle {
	
	private int matrixSize;
	private Tile[][] tiles;
	
	public TetravexPuzzle(int size) {
		matrixSize = size;
		tiles = new Tile[matrixSize][matrixSize];
		// create a solved puzzle using random numbers
		for (int i = 0; i < matrixSize; i++) {
			for (int j = 0; j < matrixSize; j++) {
				int[] nums = new int[4];
				// top left
				if (i == 0 && j == 0) {
					nums[0] = (int)(Math.random()*10);
					nums[1] = (int)(Math.random()*10);
					nums[2] = (int)(Math.random()*10);
					nums[3] = (int)(Math.random()*10);
					tiles[i][j] = new Tile(nums); 
				// first row
				} else if (i == 0) {
					nums[0] = (int)(Math.random()*10);
					nums[1] = (int)(Math.random()*10);
					nums[2] = (int)(Math.random()*10);
					nums[3] = tiles[0][j-1].getEast();
					tiles[i][j] = new Tile(nums); 
				// first column
				} else if (j == 0) {
					nums[0] = tiles[i-1][0].getSouth();
					nums[1] = (int)(Math.random()*10);
					nums[2] = (int)(Math.random()*10);
					nums[3] = (int)(Math.random()*10);
					tiles[i][j] = new Tile(nums);
				// remaining tiles
				} else {
					nums[0] = tiles[i-1][j].getSouth();
					nums[1] = (int)(Math.random()*10);
					nums[2] = (int)(Math.random()*10);
					nums[3] = tiles[i][j-1].getEast();					
					tiles[i][j] = new Tile(nums);
				}				
			}
		}
	}

	// check solution needs to be rewritten
	public boolean checkSolution(Tile[][] candidateTiles) {
		if (candidateTiles.length != tiles.length || candidateTiles[0].length != tiles[0].length)
			return false;
		else {
			for (int i = 0; i < tiles.length; i++)
				for (int j = 0; j < tiles[0].length; j++) {
					if (candidateTiles[i][j] == null)
						return false;
					else if (!tiles[i][j].equals(candidateTiles[i][j]))
						return false;
				}
		}
		return true;
	}
	
	public Tile[][] getShuffledTiles() {
		
		// make a copy of the Tile array
		Tile[][] result = new Tile[matrixSize][matrixSize];
		for (int i = 0; i < matrixSize; i++) 
			for (int j = 0; j < matrixSize; j++) 
				result[i][j] = tiles[i][j];
		
		// shuffle the copy
		for (int i = 0; i < matrixSize; i++) {
			for (int j = 0; j < matrixSize; j++) {
				int row = (int) (Math.random()*matrixSize);
				int col = (int) (Math.random()*matrixSize);
				Tile temp = result[i][j];
				result[i][j] = result[row][col];
				result[row][col] = temp;
			}
		}
		return result;
	}
}
