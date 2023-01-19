import bagel.*;
import bagel.util.*;

// Concerns all block-type of objects (sinkholes, walls, trees)
// Similar to sentient but does not move
public class Insentient 
{
	private int posX;
	private int posY;
	private Rectangle hitbox;
	private final Image insentientImg;
	
	public Insentient(int x, int y, Image image)
	{
		this.posX = x;
		this.posY = y;
		this.insentientImg = image;
	}
	
	public String toString()
	{
		return "Insentient";
	}
	
	public void drawInsentient()
	{
		this.insentientImg.drawFromTopLeft(this.posX, this.posY);
	}
	
	// Getters and Setters
	
	public int getPosX()
	{
		return this.posX;
	}
	
	public void setPosX(int posX)
	{
		this.posX = posX;
	}
	
	public int getPosY()
	{
		return this.posY;
	}
	
	public void setPosY(int posY)
	{
		this.posY = posY;
	}
	
	public Rectangle getHitbox()
	{
		return this.hitbox;
	}
	
	public void setHitbox(Rectangle hitbox)
	{
		this.hitbox = hitbox;
	}
	
	public Image getInsentientImg()
	{
		return this.insentientImg;
	}
}
