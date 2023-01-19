import bagel.*;
import bagel.util.*;

public class HealthBar 
{
	private int fontSize;
	private double barPosX;
	private double barPosY;
	private int maxHealth;
	private int currHealth;
	private DrawOptions options;
	private Font font;
	private final int MID_HEALTH_BOUND = 65;
	private final int LOW_HEALTH_BOUND = 35;
	public static final String HEALTH_FONT_STYLE = "res/frostbite.ttf";
	
	public HealthBar(double navec_bar_x, double navec_bar_y, int fontSize, int maxHealth)
	{
		this.barPosX = navec_bar_x;
		this.barPosY = navec_bar_y;
		this.fontSize = fontSize;
		this.font = new Font(HEALTH_FONT_STYLE, this.fontSize);
		this.maxHealth = maxHealth;
		this.currHealth = maxHealth;
		this.options = new DrawOptions();
	}
	
	// Create a function to draw the text in the desired position
	public void showHealth()
	{
		int healthPercent = (int)(Math.round(100*
				((double)(this.currHealth)/this.maxHealth)));
		
		// Determine the color of the health based on the percentage
		if (healthPercent > this.MID_HEALTH_BOUND)
		{
			options.setBlendColour(0, 0.8, 0.2);
			this.font.drawString(String.valueOf(healthPercent)+"%", 
					this.barPosX, this.barPosY, this.options);
		}
		
		else if (healthPercent > this.LOW_HEALTH_BOUND)
		{
			options.setBlendColour(0.9, 0.6, 0);
			this.font.drawString(String.valueOf(healthPercent)+"%",
					this.barPosX, this.barPosY, this.options);
		}
		
		else
		{
			options.setBlendColour(1, 0, 0);
			this.font.drawString(String.valueOf(healthPercent)+"%",
					this.barPosX, this.barPosY, this.options);
		}
	}
	
	// Getters and setters
	public double getBarPosX()
	{
		return this.barPosX;
	}
	
	public void setBarPosX(double x)
	{
		this.barPosX = x;
	}
	
	public double getBarPosY()
	{
		return this.barPosY;
	}
	
	public void setBarPosY(double y)
	{
		this.barPosY = y;
	}
	
	public int getHealth()
	{
		return this.currHealth;
	}
	
	/* 
	 * Make sure the health just sets to zero if
	 * you go in the negatives 
	 */
	public void setHealth(int health)
	{
		if (health>0) { this.currHealth = health; }
		else { this.currHealth = 0; }
	}
	
	public int getMaxHealth()
	{
		return this.maxHealth;
	}
	
	public void setMaxHealth(int maxHealth)
	{
		this.maxHealth = maxHealth;
	}
	
	public Font getFont()
	{
		return this.font;
	}
		
	public void setFont(Font font)
	{
		this.font = font;
	}
}
