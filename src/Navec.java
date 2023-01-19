import bagel.*;
import bagel.util.*;

public class Navec extends Enemy
{
	public static final String NAVEC_TEXT = "Navec";
	private static final String NAVEC_FIRE = "res/navec/navecFire.png";
	private static final Image NAVEC_RIGHT = new Image("res/navec/navecRight.png");
	private static final Image NAVEC_LEFT = new Image("res/navec/navecLeft.png");
	private static final Image NAVEC_INV_RIGHT = new Image("res/navec/navecInvincibleRight.png");
	private static final Image NAVEC_INV_LEFT = new Image("res/navec/navecInvincibleLeft.png");
	public static final double NAVEC_WIDTH = NAVEC_LEFT.getWidth();
	public static final double NAVEC_HEIGHT = NAVEC_LEFT.getHeight();
	private static final int NAVEC_RANGE = 200;
	private static final int NAVEC_HEALTH = Demon.DEMON_HEALTH*2;
	private static final int NAVEC_HEALTH_FONT = 15;
	public static final int NAVEC_DAMAGE = 2*Demon.DEMON_DAMAGE;
	private double navec_bar_x;
	private double navec_bar_y;
	private static final double NAVEC_MAX_SPEED = 0.7;
	private static final double NAVEC_MIN_SPEED = 0.3;
	
	public Navec(int x, int y, int dmg)
	{
		// Make sure to always set aggression to true
		// Other attributes are similar to Demon
		super(x, y, dmg, NAVEC_RANGE);
		this.setWidth(NAVEC_WIDTH);
		this.setHeight(NAVEC_HEIGHT);
		this.navec_bar_x = this.getPosX();
		this.navec_bar_y = this.getPosY() - 6;
		this.setFire(new Fire(NAVEC_FIRE));
		this.setHitbox(new Rectangle(this.getPosX(), this.getPosY(), 
				this.getWidth(), this.getHeight()));
		this.setHealthBar(new HealthBar(this.navec_bar_x, this.navec_bar_y, 
				NAVEC_HEALTH_FONT, NAVEC_HEALTH));
		this.setLooksRight(this.getRandom().nextBoolean());
		this.setIsAggressive(true);
		this.setSpeed((this.getRandom().nextDouble() * 
				(NAVEC_MAX_SPEED-NAVEC_MIN_SPEED)) + NAVEC_MIN_SPEED);
	}
	
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
	// Similar to demons, drawing of Navec depends on direction
	// and whether they're invincible
	public void drawSentient()
	{
		if (this.getLooksRight() && this.getIsInvincible())
		{
			NAVEC_INV_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		else if (this.getLooksRight() && !(this.getIsInvincible()))
		{
			NAVEC_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		else if (!(this.getLooksRight()) && this.getIsInvincible())
		{
			NAVEC_INV_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		else if (!(this.getLooksRight()) && !(this.getIsInvincible()))
		{
			NAVEC_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		this.getHealthbar().setBarPosX(this.getPosX());
		this.getHealthbar().setBarPosY(this.getPosY()-6);
		this.getHealthbar().showHealth();
	}
	
	@Override
	public String toString() 
	{
		return NAVEC_TEXT;
	}
	
	// Getters and Setters
		
	public double getNavecBarX()
	{
		return this.navec_bar_x;
	}
	
	public void setNavecBarX(double x)
	{
		this.navec_bar_x = x;
	}
	
	public double getNavecBarY()
	{
		return this.navec_bar_y;
	}
	
	public void setNavecBarY(double y)
	{
		this.navec_bar_y = y;
	}
}
