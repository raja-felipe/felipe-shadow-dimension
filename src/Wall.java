import bagel.Image;
import bagel.util.*;
// Just keep as an insentient unit
// that collides and inhibits the movement of Fae
public class Wall extends Insentient
{
	public static final String WALL_TEXT = "Wall";
	private static final Image WALL_IMG = new Image("res/wall.png");
	public static final double WALL_WIDTH = WALL_IMG.getWidth();
	public static final double WALL_HEIGHT = WALL_IMG.getHeight();
	
	/*
	 * Ensures coordinates of wall are initialized
	 * before they are set
	 */
	public Wall(int x, int y)
	{
		super(x, y, WALL_IMG);
		this.setHitbox(new Rectangle(this.getPosX(), this.getPosY(),
				WALL_WIDTH, WALL_HEIGHT));
	}
	
	@Override
	public String toString()
	{
		return WALL_TEXT;
	}
}
