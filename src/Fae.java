import bagel.*;
import bagel.util.*;

public class Fae extends Sentient implements DealsDamage
{
    public static final String FAE_TEXT = "Fae";
	private static final Image FAE_IDLE_LEFT = new Image("res/fae/faeLeft.png");
    private static final Image FAE_IDLE_RIGHT = new Image("res/fae/faeRight.png");
    private static final Image FAE_ATTACK_LEFT = new Image("res/fae/faeAttackLeft.png");
    private static final Image FAE_ATTACK_RIGHT = new Image("res/fae/faeAttackRight.png");
    public static final double FAE_WIDTH = FAE_IDLE_LEFT.getWidth();
    public static final double FAE_HEIGHT = FAE_IDLE_LEFT.getHeight();
    public static final int FAE_DAMAGE = 20;
    private static final int FAE_HEALTH_FONT_SIZE = 30;
	private static final int FAE_BAR_X = 20;
	private static final int FAE_BAR_Y = 25;
    public static final int FAE_MAX_HEALTH = 100;
    public static final int FAE_SPEED = 2;
    public static final int ATTACK_DURATION = 1000*60/1000;
    public static final int ATTACK_COOLDOWN = 2000*60/1000;
    private boolean canAttack;
    private int currAttackTime;
    private int currAttackCooldown;
	
	public Fae(int posX, int posY, int dmg)
	{
		super(posX, posY, dmg);
		this.setWidth(FAE_WIDTH);
		this.setHeight(FAE_HEIGHT);
		this.setHealthBar(new HealthBar(FAE_BAR_X, FAE_BAR_Y, 
				FAE_HEALTH_FONT_SIZE, FAE_MAX_HEALTH));
		this.setLooksRight(true);
		this.setHitbox(new Rectangle(this.getPosX(), this.getPosY(), 
				this.getWidth(), this.getHeight()));
		this.canAttack = true;
		this.currAttackTime = 0;
		this.currAttackCooldown = 0;
	}
	
	// This function turns on fae's attack animation
	public boolean checkAttackMode(Input input)
	{
		if (this.canAttack && input.isDown(Keys.A))
		{
			this.setIsAttacking(true);
			return true;
		}
		
		return false;
	}
	
	
	// Need function to count Fae's attack timer
	public void attackTimer()
	{
		// Signals end of the attack
		if (this.currAttackTime == ATTACK_DURATION)
		{
			this.currAttackTime = 0;
			this.setIsAttacking(false);
			this.setCanAttack(false);
			return;
		}
		
		// Signals end of attack cooldown
		else if (this.currAttackCooldown == ATTACK_COOLDOWN)
		{
			this.currAttackCooldown = 0;
			this.setCanAttack(true);
		}
		
		// Attack Cooldown ongoing
		else if (!this.getCanAttack())
		{
			this.currAttackCooldown++;
		}
		
		// Attack ongoing
		else if (this.getIsAttacking() && this.getCanAttack())
		{
			this.currAttackTime++;
		}
	}
	
	@Override
	public void attack(Sentient sentient) 
	{
		// TODO Auto-generated method stub
		if (this.getHitbox().intersects(sentient.getHitbox()) &&
				!sentient.getIsInvincible())
		{
			inflictDamage(sentient);
		}
	}
	
	@Override
	public void inflictDamage(Sentient sentient) 
	{
		System.out.print(this.toString()+" inflicts "
				+this.getDamage()+" damage points on "+sentient.toString()+".");
		sentient.recieveDamage(this);
	}
	
	
	@Override
	// For Fae we need to consider the input to base the movement
	public void moveSentient(Input input, Insentient[] insentientList, Level level) 
	{
		boolean newIsRight = false;
		
		// Checking each movement individually allows Fae to
		// not stop when colliding with walls;
		// she can slide off walls if one of the directions
		// are viable
		
        if (input.isDown(Keys.RIGHT))
        {
        	newIsRight = true;
        	moveHere(true, FAE_SPEED, insentientList, level);
        }
        
        if (input.isDown(Keys.LEFT))
        {
        	moveHere(true, -1*FAE_SPEED, insentientList, level);
        }
        
        if (input.isDown(Keys.UP))
        {
        	moveHere(false, -1*FAE_SPEED, insentientList, level);
        }
        
        if (input.isDown(Keys.DOWN))
        {
        	moveHere(false, FAE_SPEED, insentientList, level);
        }
                
        // Make sure to set the new isRight
        if (input.isDown(Keys.LEFT) || input.isDown(Keys.RIGHT)) 
        { 
        	this.setLooksRight(newIsRight); 
        }
	}
	
	public void moveHere(boolean isX, int amount, Insentient[] insentientList, Level level)
	{
		double newX = this.getPosX();
		double newY = this.getPosY();
		
		if (isX)
		{
			newX += amount;
		}
		
		else
		{
			newY += amount;
		}
		
		// Here we make a new rectangle that represents
		// where sentient unit would have moved, and test for collisions
		Rectangle testRectangle = new Rectangle(newX, newY,
				FAE_WIDTH, FAE_HEIGHT);
		Rectangle keep = this.getHitbox();
		
        this.setHitbox(testRectangle);
        
        if (this.checkCollision(level))
        {
        	this.setHitbox(keep);
        	return;
        }
        
        // If there are no collisions we can just push through with
        // the movement
        this.setPosX(newX);
        this.setPosY(newY);
        this.setHitbox(testRectangle);
	}
	
	// Drawing Fae depends on whether they're attacking
	@Override
	public void drawSentient()
	{
		if (this.getLooksRight() && this.getIsAttacking())
		{
			FAE_ATTACK_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		else if (this.getLooksRight() && !(this.getIsAttacking()))
		{
			FAE_IDLE_RIGHT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		else if (!(this.getLooksRight()) && this.getIsAttacking())
		{
			FAE_ATTACK_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		else if (!(this.getLooksRight()) && !(this.getIsAttacking()))
		{
			FAE_IDLE_LEFT.drawFromTopLeft(this.getPosX(), this.getPosY());
		}
		
		this.getHealthbar().showHealth();
	}
		
	@Override
	public String toString()
	{
		return FAE_TEXT;
	}
	
	
	// Getters and Setters
	public boolean getCanAttack()
	{
		return this.canAttack;
	}
	
	public void setCanAttack(boolean canAttack)
	{
		this.canAttack = canAttack;
	}
	
	public int getCurrAttacktime()
	{
		return this.currAttackTime;
	}
	
	public void setCurrAttackTime(int time)
	{
		this.currAttackTime = time;
	}
}
