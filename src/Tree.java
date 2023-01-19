import bagel.*;
import bagel.util.*;
// Tree Class is similar to wall,
// differs in design only
public class Tree extends Insentient
{
	public static final String TREE_TEXT = "Tree";
	private static final Image TREE_IMG = new Image("res/tree.png");
	public static final double TREE_WIDTH = TREE_IMG.getWidth();
	public static final double TREE_HEIGHT = TREE_IMG.getHeight();
	
	public Tree(int x, int y) 
	{
		super(x, y, TREE_IMG);
		this.setHitbox(new Rectangle(this.getPosX(), this.getPosY(),
				TREE_WIDTH, TREE_HEIGHT));
	}
	
	@Override
	public String toString()
	{
		return TREE_TEXT;
	}
}
