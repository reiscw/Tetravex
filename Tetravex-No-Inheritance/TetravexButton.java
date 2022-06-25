import java.awt.*;
import javax.swing.*;

public class TetravexButton {
	
	// present to avoid warning messages in Eclipse
	public static final long serialVersionUID = 1L;
	
	private Tile tile;
	private JToggleButton button;
	
	public TetravexButton(Tile aTile) {
		tile = aTile;
		button = new JToggleButton();
		if (tile != null) {
			button.setIcon(new ImageIcon(tile.drawTile()));
			button.setPreferredSize(new Dimension(Tile.TILE_SIZE, Tile.TILE_SIZE));
			button.setSelectedIcon(new ImageIcon(tile.drawNegatedTile()));
		}
		else {
			button.setPreferredSize(new Dimension(Tile.TILE_SIZE, Tile.TILE_SIZE));
		}
	}
	
	public Tile getTile() {return tile;}
	public JToggleButton getButton() {return button;}
	
	public void setTile(Tile aTile) {
		tile = aTile;
		if (tile != null) {
			button.setIcon(new ImageIcon(tile.drawTile()));
			button.setPreferredSize(new Dimension(Tile.TILE_SIZE, Tile.TILE_SIZE));
			button.setSelectedIcon(new ImageIcon(tile.drawNegatedTile()));
		} else {
			button.setIcon(null);
			button.setPreferredSize(new Dimension(Tile.TILE_SIZE, Tile.TILE_SIZE));
			button.setSelectedIcon(null);
		}
	}
}
