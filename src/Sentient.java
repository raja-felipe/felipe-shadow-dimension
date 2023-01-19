import bagel.*;
import bagel.util.*;

// Concerns all alive and moving objects in the game (player, demon, navec)
public abstract class Sentient implements DealsDamage
{
	private double posX;
	private double posY;
	private double width;
	private double height;
	private final int DAMAGE;
	private Rectangle hitbox;
	private boolean looksRight;
	private boolean isInvincible;
	private boolean isAttacking;
	private HealthBar healthbar;
	int currInvDuration;
	private final static int INVINCIBLE_DURATION = 3000*60/1000;
	
	Sentient(double posX, double posY, int dmg)
	{
		this.DAMAGE = dmg;
		this.posX = posX;
		this.posY = posY;
		this.isInvincible = false;
		this.isAttacking = false;
		this.currInvDuration = 0;
	}
	
	// Drawing Sentients also varies per subclass
	public abstract void drawSentient();
	// Each sentient class attacks differently
	public abstract void attack(Sentient sentient);
	// Need this function to move track movement of units
	public abstract void moveSentient(Input input, Insentient[] insentientList, Level level);
	public abstract String toString();
	
	// Overload to check bounds of level
	public boolean checkCollision(Level level)
	{
		int i=0;
		int j=0;
		boolean collided = false;
		Insentient[] newInsentients = new Insentient[level.getMaxEntries()];
		
		// Check level bounds first
		Point sentientPoint = this.getHitbox().topLeft();
		if (sentientPoint.y < level.getLevelBounds().top() 
				|| sentientPoint.y > level.getLevelBounds().bottom()
				|| sentientPoint.x < level.getLevelBounds().left() 
				|| sentientPoint.x > level.getLevelBounds().right())
		{
			collided = true;
			return collided;
		}
		
		// Then check with all insentient units
		while (level.getInsentientUnits()[i] != null)
		{
			Insentient curr = level.getInsentientUnits()[i];
			
			if (this.hitbox.intersects(curr.getHitbox()) && 
					curr.toString().equals(Sinkhole.SINKHOLE_TEXT))
			{
				if (this.toString().equals(Fae.FAE_TEXT))
				{
					((Sinkhole)curr).inflictDamage(this);
				}
				
				else
				{
					newInsentients[j] = curr;
					j++;
				}
			}
			
			else if (this.hitbox.intersects(curr.getHitbox()))
			{
				collided = true;
				newInsentients[j] = curr;
				j++;
			}
			
			else
			{
				newInsentients[j] = curr;
				j++;
			}
			i++;
		}
		
		// Reset the insentient units in case sinkholes were collided with
		level.setInsentientUnits(newInsentients);
		return collided;
	}
	
	// Receiving damage varies because we print to log also
	// public void receiveDamage(Sentient sentient);
	public void recieveDamage(DealsDamage damager)
	{
		if (damager.toString() != Sinkhole.SINKHOLE_TEXT)
		{
			this.setIsInvincible(true);
		}
		this.getHealthbar().setHealth(this.getHealthbar().getHealth()-damager.getDamage());
		// Need to print out the damage dealt
		System.out.println(" "+this.toString()+"'s current health: "+
				this.getHealthbar().getHealth()+"/"+this.getHealthbar().getMaxHealth());
	}
	
	// Do this to always check & increment the
	// invincibility duration
	public void checkInvincibleState()
	{
		if (this.currInvDuration == INVINCIBLE_DURATION)
		{
			this.currInvDuration = 0;
			this.isInvincible = false;
		}
		
		else if (this.isInvincible)
		{
			this.currInvDuration++;
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
	
	public Rectangle getHitbox()
	{
		return this.hitbox;
	}
	
	public void setHitbox(Rectangle hitbox)
	{
		this.hitbox = hitbox;
	}
	
	public boolean getLooksRight()
	{
		return this.looksRight;
	}
	
	public void setLooksRight(boolean looksRight)
	{
		this.looksRight = looksRight;
	}
	
	public boolean getIsInvincible()
	{
		return this.isInvincible;
	}
	
	public void setIsInvincible(boolean isInvincible)
	{
		this.isInvincible = isInvincible;
	}
	
	public boolean getIsAttacking()
	{
		return this.isAttacking;
	}
	
	public void setIsAttacking(boolean isAttacking)
	{
		this.isAttacking = isAttacking;
	}
	
	public HealthBar getHealthbar()
	{
		return this.healthbar;
	}
	
	public void setHealthBar(HealthBar healthbar)
	{
		this.healthbar = healthbar;
	}
	
	public int getCurrInvDuration()
	{
		return this.currInvDuration;
	}
	
	public void setCurrInvDuration(int currInvDuration)
	{
		this.currInvDuration = currInvDuration;
	}
	
	public int getInvincibleDuration()
	{
		return INVINCIBLE_DURATION;
	}
	
	public int getDamage()
	{
		return this.DAMAGE;
	}
	
	public double getWidth()
	{
		return this.width;
	}
	
	public void setWidth(double width)
	{
		this.width = width;
	}
	
	public double getHeight()
	{
		return this.height;
	}
	
	public void setHeight(double height)
	{
		this.height = height;
	}
}
