import bagel.*;
import bagel.util.*;

public class Fire
{
	private final Image fireImg;
	private double posX;
	private double posY;
	private Rectangle fireHitbox;
	private DrawOptions options;
	
	public Fire(String imgSrc)
	{
		// Set initial coordinates to 0
		this.posX = this.posY = 0;
		this.fireImg = new Image(imgSrc);
		this.options = new DrawOptions();
	}
	
	
	// Before drawing the fire we always need to determine
	// the intersection point
	// NOTE: Adjust drawing point based on position of rectangle
	// Fire will be from
	public Point setAttackPoint(Sentient sentient, Enemy enemy)
	{
		if ((sentient.getPosX() <= enemy.getPosX()) &&
				(sentient.getPosY() <= enemy.getPosY()))
		{
			this.options.setRotation(Math.PI);
			Point newPoint = enemy.getHitbox().topLeft();
			newPoint = new Point(newPoint.x-this.fireImg.getWidth(),
					newPoint.y-this.fireImg.getHeight());
			return newPoint;
		}
		
		else if ((sentient.getPosX() <= enemy.getPosX()) &&
				(sentient.getPosY() > enemy.getPosY()))
		{
			this.options.setRotation(Math.PI/2);
			// return enemy.getHitbox().bottomLeft();
			Point newPoint = enemy.getHitbox().bottomLeft();
			newPoint = new Point(newPoint.x-this.fireImg.getWidth(), newPoint.y);
			return newPoint;
		}
		
		else if ((sentient.getPosX() > enemy.getPosX()) &&
				(sentient.getPosY() <= enemy.getPosY()))
		{
			this.options.setRotation(3*Math.PI/2);
			Point newPoint = enemy.getHitbox().topRight();
			newPoint = new Point(newPoint.x, newPoint.y-this.fireImg.getHeight());
			return newPoint;
		}
		
		else
		{
			this.options.setRotation(0);
			return enemy.getHitbox().bottomRight();
		}
	}
	
	// Direction of fire depends on the position of
	// Fae relative to the enemy
	public void drawFire(Fae fae, Enemy enemy)
	{
		if (enemy.checkAttackRange(fae.getPosX(), fae.getPosY()))
		{
			Point newPoint = setAttackPoint(fae, enemy);
			this.fireImg.drawFromTopLeft(newPoint.x, newPoint.y, this.options);
		}
	}
	
	// Getters and Setters
	
	public double getPosX()
	{
		return this.posX;
	}
	
	public void setPosX(double x)
	{
		this.posX = x;
	}
	
	public double getPosY()
	{
		return this.posY;
	}
	
	public void setPosY(double y)
	{
		this.posY = y;
	}
	
	public Rectangle getFireHitbox()
	{
		return this.fireHitbox;
	}
	
	public void setFireHitbox(Rectangle box)
	{
		this.fireHitbox = box;
	}
	
	public Image getFireImg()
	{
		return this.fireImg;
	}
}
