import java.awt.*;
import javax.swing.*;

public class TetravexButton extends JToggleButton {
	
	// present to avoid warning messages in Eclipse
	public static final long serialVersionUID = 1L;
	
	private Tile tile;
	
	public TetravexButton(Tile aTile) {
		super();
		tile = aTile;
		if (tile != null) {
			setIcon(new ImageIcon(tile.drawTile()));
			setPreferredSize(new Dimension(Tile.TILE_SIZE, Tile.TILE_SIZE));
			setSelectedIcon(new ImageIcon(tile.drawNegatedTile()));
		}
		else {
			setPreferredSize(new Dimension(Tile.TILE_SIZE, Tile.TILE_SIZE));
		}
	}
	
	public Tile getTile() {return tile;}
	
	public void setTile(Tile aTile) {
		tile = aTile;
		if (tile != null) {
			setIcon(new ImageIcon(tile.drawTile()));
			setPreferredSize(new Dimension(Tile.TILE_SIZE, Tile.TILE_SIZE));
			setSelectedIcon(new ImageIcon(tile.drawNegatedTile()));
		} else {
			setIcon(null);
			setPreferredSize(new Dimension(Tile.TILE_SIZE, Tile.TILE_SIZE));
			setSelectedIcon(null);
		}
	}
}
