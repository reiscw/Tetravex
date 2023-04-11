import java.awt.*;
import java.awt.image.*;
import java.awt.font.*;

public class Tile {
	
	private int[] numbers; 
	
	private static final Color BROWN = new Color(165, 42, 42);
	public static final int TILE_SIZE = 100;
	
	public Tile(int[] nums) {
		numbers = nums;
	}

	public int getNorth() {return numbers[0];}
	public int getEast() {return numbers[1];}
	public int getSouth() {return numbers[2];}
	public int getWest() {return numbers[3];}
	
	public void setNorth(int value) {numbers[0] = value;}
	public void setEast(int value) {numbers[1] = value;}
	public void setSouth(int value) {numbers[2] = value;}
	public void setWest(int value) {numbers[3] = value;}
	
	private Color getColor(int value) {
		switch (value) {
			case 0: return Color.BLACK;			// white lettering
			case 1: return BROWN;				// white lettering
			case 2: return Color.RED;			// white lettering
			case 3: return Color.ORANGE;		// white lettering
			case 4: return Color.YELLOW;		// black lettering
			case 5: return Color.GREEN;			// black lettering
			case 6: return Color.BLUE;			// white lettering
			case 7: return Color.MAGENTA;		// white lettering
			case 8: return Color.LIGHT_GRAY;	// black lettering
			case 9: return Color.WHITE;			// black lettering
		}
		return null;
	}
	
	private Color getTextColor(int value) {
		switch (value) {
			case 4:
			case 5:
			case 8:
			case 9: return Color.BLACK;
		}
		return Color.WHITE;
	}
	
	public BufferedImage drawTile() {
		
		// set up result value and get graphics reference
		BufferedImage tile = new BufferedImage(TILE_SIZE, TILE_SIZE, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = (Graphics2D)tile.getGraphics();
				
		// get vertices of colored triangles
		int[] northX = {0, TILE_SIZE, TILE_SIZE / 2};
		int[] northY = {0, 0, TILE_SIZE / 2};
		int[] eastX = {TILE_SIZE, TILE_SIZE / 2, TILE_SIZE};
		int[] eastY = {0, TILE_SIZE / 2, TILE_SIZE};
		int[] southX = {0, TILE_SIZE, TILE_SIZE / 2};
		int[] southY = {TILE_SIZE, TILE_SIZE, TILE_SIZE /2};
		int[] westX = {0, 0, TILE_SIZE / 2};
		int[] westY = {0, TILE_SIZE, TILE_SIZE / 2};
		
		// draw colored tiles
		graphic.setColor(getColor(numbers[0]));
		graphic.fillPolygon(northX, northY, 3);
		graphic.setColor(getColor(numbers[1]));
		graphic.fillPolygon(eastX, eastY, 3);
		graphic.setColor(getColor(numbers[2]));
		graphic.fillPolygon(southX, southY, 3);
		graphic.setColor(getColor(numbers[3]));
		graphic.fillPolygon(westX, westY, 3);
		
		// get coordinates for numbers
		int northNumberX = (int)(TILE_SIZE * 0.45);
		int northNumberY = (int)(TILE_SIZE * 0.25);
		int eastNumberX = (int)(TILE_SIZE * 0.80);
		int eastNumberY = (int)(TILE_SIZE * 0.57);
		int southNumberX = (int)(TILE_SIZE * 0.45);
		int southNumberY = (int)(TILE_SIZE * 0.90);
		int westNumberX = (int)(TILE_SIZE * 0.10);
		int westNumberY = (int)(TILE_SIZE * 0.57);
		
		
		// draw numbers
		
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		                         
		String north = "" + numbers[0];
		String east = "" + numbers[1];
		String south = "" + numbers[2];
		String west = "" + numbers[3];
		
		Font font = new Font("Serif", Font.PLAIN, (int)(TILE_SIZE * 0.2));
		FontRenderContext frc = graphic.getFontRenderContext();
		
		GlyphVector gvNorth = font.createGlyphVector(frc, north);
		GlyphVector gvEast = font.createGlyphVector(frc, east);
		GlyphVector gvSouth = font.createGlyphVector(frc, south);
		GlyphVector gvWest = font.createGlyphVector(frc, west);
		
		graphic.setColor(getTextColor(numbers[0]));
		graphic.drawGlyphVector(gvNorth, northNumberX, northNumberY);
		graphic.setColor(getTextColor(numbers[1]));
		graphic.drawGlyphVector(gvEast, eastNumberX, eastNumberY);
		graphic.setColor(getTextColor(numbers[2]));
		graphic.drawGlyphVector(gvSouth, southNumberX, southNumberY);
		graphic.setColor(getTextColor(numbers[3]));
		graphic.drawGlyphVector(gvWest, westNumberX, westNumberY);
		
		return tile;
		
	}
	
	public BufferedImage drawNegatedTile() {
		BufferedImage result = drawTile();
		for (int i = 0; i < result.getWidth(); i++) 
			for (int j = 0; j < result.getHeight(); j++) {
				// mathematics of negating each pixel
				int temp = result.getRGB(i, j);
				int r = (temp >> 16) & 0xFF;
				int g = (temp >> 8) & 0xFF;
				int b = temp & 0xFF;
				int newColor = 255 - r;
				newColor = (newColor << 8) + (255 - g);
				newColor = (newColor << 8) + (255 - b);
				result.setRGB(i, j, newColor);
			}
		return result;
	}
	
	public boolean equals(Tile other) {
		return getNorth() == other.getNorth() && getSouth() == other.getSouth() && getWest() == other.getWest() 
		       && getEast() == other.getEast();
	}
}
