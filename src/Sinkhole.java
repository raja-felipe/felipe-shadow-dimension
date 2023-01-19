import bagel.Image;
import bagel.util.*;

public class Sinkhole extends Insentient implements DealsDamage
{
	public static final String SINKHOLE_TEXT = "Sinkhole";
	private static final int DAMAGE = 30;
	private static final Image SINKHOLE_IMG = new Image("res/sinkhole.png");
	public static final double SINKHOLE_WIDTH = SINKHOLE_IMG.getWidth();
	public static final double SINKHOLE_HEIGHT = SINKHOLE_IMG.getHeight();
	
	public Sinkhole(int x, int y)
	{
		super(x, y, SINKHOLE_IMG);
		this.setHitbox(new Rectangle(this.getPosX(), this.getPosY(),
				SINKHOLE_WIDTH, SINKHOLE_HEIGHT));
	}
	
	// We know sinkholes inflict damage so we need
	// to print that into the log as well
	@Override
	public void inflictDamage(Sentient sentient) 
	{
		System.out.print(this.toString()+" inflicts "
				+this.getDamage()+" damage points on "+sentient.toString()+".");
		sentient.recieveDamage(this);
	}

	@Override
	public void attack(Sentient sentient) 
	{
		if (this.getHitbox().intersects(sentient.getHitbox()))
		{
			inflictDamage(sentient);
		}
	}

	@Override
	public int getDamage() 
	{
		return DAMAGE;
	}
	
	@Override
	public String toString()
	{
		return SINKHOLE_TEXT;
	}
}
