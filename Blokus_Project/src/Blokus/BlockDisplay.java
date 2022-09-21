package Blokus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockDisplay extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final Color bgColor = Game.NOCOLOR;

	private Polyomino selected = Polyomino.O0;
	private Color color = Game.NOCOLOR;
	private Tile[][] grid = new Tile[5][5];
	private int cwRotations = 0;
	private boolean flipped = false;

	public BlockDisplay(int size)
	{
		setLayout(new GridLayout(5, 5));
		setSize(size, size);
		setPreferredSize(new Dimension(size, size));

		Tile tile;
		for (int y = 0; y < 5; y++)
		{
			for (int x = 0; x < 5; x++)
			{
				tile = new Tile(size, bgColor);
				grid[x][y] = tile;
				add(tile);
			}
		}
	}

	public void setPolyomino(Polyomino poly)
	{
		selected = poly;
		refresh();
	}

	public void setColor(Color color)
	{
		this.color = color;
		refresh();
	}

	public void setRotations(int rotations)
	{
		cwRotations = rotations;
		refresh();
	}

	public void rotate(int times)
	{
		cwRotations += times;
		cwRotations %= 4;
		refresh();
	}

	public void flip()
	{
		flipped = !flipped;
		refresh();
	}

	public void refresh()
	{
		// Create a copy of the Polyomino's shape, and rotate 
		// 90 degrees clockwise it the correct number of times
		int[][] target = selected.shape;
		for (int i = cwRotations; i > 0; i--)
		{
			int[][] rotated = new int[5][5];
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					rotated[y][4-x] = target[x][y];
				}
			}
			target = rotated;
		}

		// Flip it if needed
		if (flipped)
		{
			int[][] flipped = new int[5][5];
			for (int x = 0; x < 5; x++)
			{
				for (int y = 0; y < 5; y++)
				{
					flipped[x][y] = target[4-x][y];
				}
			}
			target = flipped;
		}

		int[] edges = new int[4];
		Tile tile;
		for (int x = 0; x < 5; x++)
		{
			for (int y = 0; y < 5; y++)
			{
				tile = grid[x][y];
				if (target[x][y] == 0)
				{
					tile.u = tile.l = tile.d = tile.r = 1;
				}
				else
				{
					tile.u = (y != 0 && target[x][y] == target[x][y-1]) ? 0 : 1;
					tile.l = (x != 0 && target[x][y] == target[x-1][y]) ? 0 : 1;
					tile.d = (y != 4 && target[x][y] == target[x][y+1]) ? 0 : 1;
					tile.r = (x != 4 && target[x][y] == target[x+1][y]) ? 0 : 1;
				}

				tile.color = (target[x][y] == 1) ? color : bgColor;

				tile.refresh();
			}
		}
	}
}
