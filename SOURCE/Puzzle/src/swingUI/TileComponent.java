package swingUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import core.NumberTile;
import core.NumberTilesField;

public class TileComponent extends JLabel {
	private int cislo;
	private String FILE = "/1.png";;

	/** Number of row in the field. */
	private int row;
	//
	// /** Number of column in the field. */
	private int column;
	//
	// /** Tile in the field which represents the GUI conponent. */
	private NumberTile tile;
	private NumberTilesField playingfield;

	// Collections.shuffle(buttons);
	public TileComponent(NumberTile tile) {
		this.tile = tile;
		this.cislo = tile.getValue();
		if (cislo > 0) {
			this.FILE = "/" + cislo + ".png";
			ImageIcon PUZZLE_ICON = new ImageIcon(TileComponent.class.getResource(FILE));
			setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			setOpaque(true);
			setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			setSize(new Dimension(20, 20));
			setFont(new Font("Dialog", Font.BOLD, 11));
			Image img = PUZZLE_ICON.getImage();
			Image newimg = img.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
			PUZZLE_ICON = new ImageIcon(newimg);
			setIcon(PUZZLE_ICON);
		} else {
			setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			setOpaque(true);
			setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			setSize(new Dimension(20, 20));
			setFont(new Font("Dialog", Font.BOLD, 11));
		}

	}

	public void setImage() {
		if (this.cislo > 0) {
			this.FILE = "/" + this.cislo + ".png";
			ImageIcon PUZZLE_ICON = new ImageIcon(TileComponent.class.getResource(FILE));
			Image img = PUZZLE_ICON.getImage();
			setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			Image newimg = img.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
			PUZZLE_ICON = new ImageIcon(newimg);
			setIcon(PUZZLE_ICON);
		} else {
			setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			setIcon(null);
		}
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public NumberTile getTile() {
		return tile;
	}

	public void setTile(NumberTile tile) {
		this.tile = tile;
	}

	public int getCislo() {
		return cislo;
	}

	public void setCislo(int cislo) {
		this.cislo = cislo;
	}
}
