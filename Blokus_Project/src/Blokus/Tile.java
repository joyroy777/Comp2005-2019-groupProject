package Blokus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tile extends JPanel
{
	private static final long serialVersionUID = 1L;
	public int u = 1, l = 1, d = 1, r = 1;
	public Color color = Game.NOCOLOR;
	public Polyomino identity = Polyomino.O0;

	public Tile(int size)
	{
		setSize(size, size);
		setPreferredSize(new Dimension(size, size));
		refresh();
	}

	public Tile(int size, Color color)
	{
		this(size);
		this.color = color;
		refresh();
	}

	public Tile(int size, int[] edges)
	{
		this(size);
		u = edges[0];
		l = edges[1];
		d = edges[2];
		r = edges[3];
		refresh();
	}

	public Tile(int size, Polyomino identity)
	{
		this(size);
		this.identity = identity;
		refresh();
	}

	public Tile(int size, Color color, int[] edges) { this(size, edges); this.color = color; refresh(); }
	public Tile(int size, Color color, Polyomino identity) { this(size, identity); this.color = color; refresh(); }
	public Tile(int size, int[] edges, Polyomino identity) { this(size, edges); this.identity = identity; refresh(); }
	public Tile(int size, Color color, int[] edges, Polyomino identity) { this(size, color, edges); this.identity = identity; refresh(); }

	public void refresh()
	{
		setBackground(color);
		setBorder(BorderFactory.createMatteBorder(u, l, d, r, color.darker()));
	}
}
