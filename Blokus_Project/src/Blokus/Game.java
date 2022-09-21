package Blokus;
import java.awt.Color;

public class Game {
	public static final int PLAYER1 = 1;
	public static final int PLAYER2 = 2;
	public static final int PLAYER3 = 3;
	public static final int PLAYER4 = 4;
	public static final int NOPLAYER = 5;
	public static final int INVALID = 6;

	public static final Color P1COLOR = Color.BLUE;
	public static final Color P2COLOR = Color.YELLOW;
	public static final Color P3COLOR = Color.RED;
	public static final Color P4COLOR = Color.GREEN;
	public static final Color NOCOLOR = Color.GRAY;

	public static final int size = 20;

	private int[][] board;
	public Polyomino selected = Polyomino.O0;
	private GameMenu gui;
	public Color color = P1COLOR;
	public int active = PLAYER1;

	public Game(GameMenu gui)
	{
		this.gui = gui;
		board = new int[size][size];
		for (int x = 0; x < size; x++)
		{
			for (int y = 0; y < size; y++)
			{
				board[x][y] = NOPLAYER;
			}
		}
	}

	public int getStateAt(int x, int y)
	{
		if (x < 0 || y < 0 || x >= size || y >= size)
		{
			return INVALID;
		}

		return board[x][y];
	}

	public boolean isSelected(Polyomino poly)
	{
		if (poly == Polyomino.O0) { return false; }
		return poly == selected;
	}

	public boolean isSelected()
	{
		return selected != Polyomino.O0;
	}

	public void deselect()
	{
		selected = Polyomino.O0;
	}

	public void refreshGUI()
	{
		gui.refresh();
	}

	public void nextTurn()
	{
		switch (active)
		{
			case PLAYER1:	active = PLAYER2;
							color = P2COLOR;
							break;
			case PLAYER2:	active = PLAYER3;
							color = P3COLOR;
							break;
			case PLAYER3:	active = PLAYER4;
							color = P4COLOR;
							break;
			case PLAYER4:	active = PLAYER1;
							color = P1COLOR;
							break;
		}
	}
}
