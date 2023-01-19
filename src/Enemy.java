import java.awt.geom.Point2D;
import java.util.Random;
import bagel.Input;
import bagel.util.Rectangle;

// All the enemies in the game have an attack range
// and check for that before attacking
public abstract class Enemy extends Sentient implements DealsDamage
{
	// Need these to mark directions of the monsters
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public static final int CHANGE_DIR = 2;
	private int range;
	private boolean isAggressive;
	private int direction;
	private int change;
	private double speed;
	private Random enemyRandom;
	private Fire fire;
	
	public Enemy(int x, int y, int dmg, int range)
	{
		super(x, y, dmg);
		this.range = range;
		this.enemyRandom = new Random();
		this.direction = Math.abs(this.enemyRandom.nextInt()%4);
		
		// Need to initially set the change direction
		// depending on initial direction
		if (this.direction == UP || this.direction == LEFT)
		{
			this.change = CHANGE_DIR;
		}
		
		else
		{
			this.change = -1*CHANGE_DIR;
		}
	}
		
	// All enemies have a range check before they attack
	public boolean checkAttackRange(double xSrc, double ySrc)
	{
		return Point2D.distance(this.getHitbox().centre().x, 
				this.getHitbox().centre().y, xSrc, ySrc) <= range;
	}
	
	// These functions have to be overridden in their subclasses
	@Override
	public abstract void drawSentient();
	@Override
	public abstract void inflictDamage(Sentient sentient);
	@Override
	public abstract void attack(Sentient sentient);
	@Override
	public abstract String toString();
	
	@Override
	public void moveSentient(Input input, Insentient[] insentientList, Level level) 
	{
		double newX = this.getPosX(), newY = this.getPosY();
		
		// Input does not matter, just check if enemy moves in correct direction
		if (this.direction == Enemy.UP)
		{
			newY -= this.getSpeed();
		}
		
		else if (this.direction == Enemy.DOWN)
		{
			newY += this.getSpeed();
		}
		
		else if (this.direction == Enemy.LEFT) 
		{
			newX -= this.getSpeed();
		}
		
		else if (this.direction == Enemy.RIGHT)
		{
			newX += this.getSpeed();
		}
		
        // Test if the movement is valid and does not collide
        Rectangle testRectangle = new Rectangle(newX, newY, 
        		this.getWidth(), this.getHeight());
        Rectangle keep = this.getHitbox();
        this.setHitbox(testRectangle);
        
        if (this.checkCollision(level))
        {
        	// Difference here is that at collision, enemy will face other way
        	this.direction += this.change;
        	this.change *= -1;
            
        	if (this.getDirection() == LEFT) 
            { 
            	this.setLooksRight(false); 
            }
        	
        	else if (this.getDirection() == RIGHT)
        	{
        		this.setLooksRight(true);
        	}
        	
        	this.setHitbox(keep);
        	return;
        }
        
        this.setPosX(newX);
        this.setPosY(newY);
        this.setHitbox(testRectangle);
	}
	
	public void drawAttack(Fae fae)
	{
		this.getFire().drawFire(fae, this);
	}
	
	// Getters and Setters
	
	public int getRange()
	{
		return this.range;
	}
	
	public void setRange(int range)
	{
		this.range = range;
	}
	
	public boolean getIsAggressive()
	{
		return this.isAggressive;
	}
	
	public void setIsAggressive(boolean setting)
	{
		this.isAggressive = setting;
	}
	
	public int getDirection()
	{
		return this.direction;
	}
	
	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	
	public int getChange()
	{
		return this.change;
	}
	
	public void setChange(int change)
	{
		this.change = change;
	}
	
	public double getSpeed()
	{
		return this.speed;
	}
	
	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
	
	public Random getRandom()
	{
		return this.enemyRandom;
	}
	
	public void setRandom(Random random)
	{
		this.enemyRandom = random;
	}
	
	public Fire getFire()
	{
		return this.fire;
	}
	
	public void setFire(Fire fire)
	{
		this.fire = fire;
	}
}
