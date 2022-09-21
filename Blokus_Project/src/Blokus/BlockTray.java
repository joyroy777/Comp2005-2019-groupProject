package Blokus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BlockTray extends JPanel implements MouseListener
{
	private static final long serialVersionUID = 1L;
	private static final int defaultSize = 500;
	private static final Polyomino[][] defaultLayout =
	{
		{ Polyomino.L5, Polyomino.L5, Polyomino.L5, Polyomino.L5, Polyomino.V5, Polyomino.V5, Polyomino.V5 },
		{ Polyomino.P5, Polyomino.P5, Polyomino.O0, Polyomino.L5, Polyomino.T4, Polyomino.O0, Polyomino.V5 },
		{ Polyomino.P5, Polyomino.P5, Polyomino.F5, Polyomino.T4, Polyomino.T4, Polyomino.T4, Polyomino.V5 },
		{ Polyomino.P5, Polyomino.F5, Polyomino.F5, Polyomino.F5, Polyomino.O0, Polyomino.O4, Polyomino.O4 },
		{ Polyomino.I2, Polyomino.O0, Polyomino.X5, Polyomino.F5, Polyomino.Y5, Polyomino.O4, Polyomino.O4 },
		{ Polyomino.I2, Polyomino.X5, Polyomino.X5, Polyomino.X5, Polyomino.Y5, Polyomino.Y5, Polyomino.I3 },
		{ Polyomino.L4, Polyomino.L4, Polyomino.X5, Polyomino.W5, Polyomino.Y5, Polyomino.O0, Polyomino.I3 },
		{ Polyomino.L4, Polyomino.O0, Polyomino.W5, Polyomino.W5, Polyomino.Y5, Polyomino.T5, Polyomino.I3 },
		{ Polyomino.L4, Polyomino.W5, Polyomino.W5, Polyomino.T5, Polyomino.T5, Polyomino.T5, Polyomino.I4 },
		{ Polyomino.I5, Polyomino.N5, Polyomino.V3, Polyomino.V3, Polyomino.O1, Polyomino.T5, Polyomino.I4 },
		{ Polyomino.I5, Polyomino.N5, Polyomino.N5, Polyomino.V3, Polyomino.O0, Polyomino.O0, Polyomino.I4 },
		{ Polyomino.I5, Polyomino.O0, Polyomino.N5, Polyomino.Z5, Polyomino.Z5, Polyomino.Z4, Polyomino.I4 },
		{ Polyomino.I5, Polyomino.U5, Polyomino.N5, Polyomino.U5, Polyomino.Z5, Polyomino.Z4, Polyomino.Z4 },
		{ Polyomino.I5, Polyomino.U5, Polyomino.U5, Polyomino.U5, Polyomino.Z5, Polyomino.Z5, Polyomino.Z4 }
	};

	private int width = 14;
	private int height = 7;
	private Tile[][] tiles;
	private Polyomino[][] polys;
	private BlockInventory inventory;

	public BlockTray(BlockInventory inventory, int longEdgeSize, int quarterTurns)
	{
		// Create a temp copy of the default layout, so it can be rotated without issue
		Polyomino[][] layout = defaultLayout;
		for (int i = quarterTurns; i > 0; i--)
		{
			// Rotate the layout 90 degrees `quarterTurns` times
			Polyomino[][] result = new Polyomino[height][width];
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					result[y][width-1-x] = layout[x][y];
				}
			}

			// Set the new layout and swap width/height
			layout = result;
			int temp = width;
			width = height;
			height = temp;
		}

		setLayout(new GridLayout(height, width));
		this.inventory = inventory;

		// Define some variables for the loop to create the Blocks
		Color color, playerColor = inventory.color, backgroundColor = Game.NOCOLOR;
		Tile tile;
		int blockSize = longEdgeSize / (quarterTurns % 2 == 0 ? width : height);
		tiles = new Tile[width][height];
		polys = new Polyomino[width][height];
		int[] edges = new int[4];
		Polyomino poly;

		// Create a Block for each space in the layout, and put it on the tray
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				// Get the type of polyomino at that grid location
				poly = layout[x][y];

				// Check each direction. If it's not OOB, and it shares a polyomino type, 
				// then don't put a border between the two blocks. Otherwise, do.
				edges[0] = (y != 0        && poly == layout[x][y-1]) ? 0 : 1;
				edges[1] = (x != 0        && poly == layout[x-1][y]) ? 0 : 1;
				edges[2] = (y != height-1 && poly == layout[x][y+1]) ? 0 : 1;
				edges[3] = (x != width-1  && poly == layout[x+1][y]) ? 0 : 1;

				// Set the color. Any empty or used tile will be background 
				// colored, but will still be there (for structure)
				if (poly != Polyomino.O0 && inventory.isAvailable(poly))
				{
					color = playerColor;
				}
				else
				{
					color = backgroundColor;
				}

				tile = new Tile(blockSize, color, edges, poly);
				tile.addMouseListener(this);
				tiles[x][y] = tile;
				polys[x][y] = poly;
				add(tile);
			}
		}
	}

	public BlockTray(BlockInventory inventory, int longEdgeSize) { this(inventory, longEdgeSize, 0); }
	public BlockTray(BlockInventory inventory) { this(inventory, defaultSize, 0); }

	public void setInventory(BlockInventory inventory)
	{
		this.inventory = inventory;
		refresh();
	}

	public BlockInventory getInventory()
	{
		return inventory;
	}

	public void refresh()
	{
		Color playerColor = inventory.color;
		Color backgroundColor = Game.NOCOLOR;
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				if (inventory.isAvailable(polys[x][y])
				&&  polys[x][y] != Polyomino.O0)
				{
					tiles[x][y].color = playerColor;
				}
				else
				{
					tiles[x][y].color = backgroundColor;
				}
				tiles[x][y].refresh();
			}
		}
	}

	// Add MouseListener methods
	public void mouseClicked(MouseEvent evnt)
	{
		Tile tile = (Tile) evnt.getComponent();
		Polyomino poly = tile.identity;

		if (inventory.game.isSelected())
		{
			inventory.makeAvailable(inventory.game.selected);
			inventory.game.deselect();
		}
		else if (inventory.isAvailable(poly))
		{
			inventory.makeUnavailable(poly);
			inventory.game.selected = poly;
		}

		inventory.refreshGUI();
	}
	public void mouseEntered(MouseEvent evnt) {}
	public void mouseExited(MouseEvent evnt) {}
	public void mousePressed(MouseEvent evnt) {}
	public void mouseReleased(MouseEvent evnt) {}
}
