import bagel.*;
import bagel.util.*;

public class Demon extends Enemy
{
	public static final String DEMON_TEXT = "Demon";
	private static final String DEMON_FIRE = "res/demon/demonFire.png";
	private static final Image DEMON_LEFT = new Image("res/demon/demonLeft.png");
	private static final Image DEMON_RIGHT = new Image("res/demon/demonRight.png");
	private static final Image DEMON_INV_LEFT = new Image("res/demon/demonInvincibleLeft.png");
	private static final Image DEMON_INV_RIGHT = new Image("res/demon/demonInvincibleRight.png");
	public static final double DEMON_WIDTH = DEMON_LEFT.getWidth();
	public static final double DEMON_HEIGHT = DEMON_LEFT.getHeight();
	public static final int DEMON_DAMAGE = 10;
	private static final double DEMON_MAX_SPEED = 0.7;
	private static final double DEMON_MIN_SPEED = 0.3;
	public static final int DEMON_HEALTH = 40;
	private static final int DEMON_HEALTH_FONT = 15;
	private static final int DEMON_RANGE = 150;
	private double demon_bar_x;
	private double demon_bar_y;
	
	public Demon(int x, int y, int dmg)
	{
		super(x, y, dmg, DEMON_RANGE);
		// We can intitalise already where the healthbar
		// should be seen
		this.setWidth(DEMON_WIDTH);
		this.setHeight(DEMON_HEIGHT);
		this.demon_bar_x = this.getPosX();
		this.demon_bar_y = this.getPosY() - 6;
		this.setHitbox(new Rectangle(this.getPosX(), this.getPosY(), 
				this.getWidth(), this.getHeight()));
		this.setHealthBar(new HealthBar(this.demon_bar_x, this.demon_bar_y, 
				DEMON_HEALTH_FONT, DEMON_HEALTH));
		this.setLooksRight(this.getRandom().nextBoolean());
		this.setFire(new Fire(DEMON_FIRE));
		
		// Aggression of demons is always set to random
		this.setIsAggressive(this.getRandom().nextBoolean());
		
		// Initialize speed here
		if (this.getIsAggressive())
		{
			this.setSpeed(((this.getRandom().nextDouble())*(DEMON_MAX_SPEED-DEMON_MIN_SPEED)) 
					+ DEMON_MIN_SPEED);
		}
		
		else
		{
			this.setSpeed(0);
		}
	}
		
	@Override
	public void attack(Sentient sentient)
	{
		// Make sure to turn on attacking
		if (!sentient.getIsInvincible() && 
				(new Rectangle(this.getFire().setAttackPoint(sentient, this),
						this.getFire().getFireImg().getWidth(), 
						this.getFire().getFireImg().getHeight()).intersects(sentient.getHitbox())))
		{
			inflictDamage(sentient);
		}
		
		else
		{
			this.setIsAttacking(false);
		}
	}
	
	@Override
	public void inflictDamage(Sentient sentient) 
	{
		this.setIsAttacking(true);
		System.out.print(this.toString()+" inflicts "
				+this.getDamage()+" damage points on "+sentient.toString()+".");
		sentient.recieveDamage(this);
	}

	@Override
	// Drawing the sentient units for demons depends on direction
	// and whether the unit is invincible
	public void drawSentient()
	{
		if (this.getLooksRight() && this.getIsInvincible())
		{
			DEMON_INV_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		else if (this.getLooksRight() && !(this.getIsInvincible()))
		{
			DEMON_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		else if (!(this.getLooksRight()) && this.getIsInvincible())
		{
			DEMON_INV_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		else if (!(this.getLooksRight()) && !(this.getIsInvincible()))
		{
			DEMON_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		this.getHealthbar().setBarPosX(this.getPosX());
		this.getHealthbar().setBarPosY(this.getPosY()-6);
		this.getHealthbar().showHealth();
		
	}

	@Override
	public String toString() 
	{
		return DEMON_TEXT;
	}
		
	// Getters and Setters
	
	public double getDemonBarX()
	{
		return this.demon_bar_x;
	}
	
	public void setDemonBarX(double x)
	{
		this.demon_bar_x = x;
	}
	
	public double getDemonBarY()
	{
		return this.demon_bar_y;
	}
	
	public void setDemonBarY(double y)
	{
		this.demon_bar_y = y;
	}
}
