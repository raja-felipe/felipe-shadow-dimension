import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import bagel.*;
import bagel.util.*;

public class Level1 extends Level
{
	private Enemy[] enemyUnits;
	public static final int LEVEL_1_MAX = 29;
	public static final String LEVEL_1_IMG = "res/background1.png";
	public static final String LEVEL_1_CSV = "res/level1.csv";
    private final static String INSTRUCTION_LEVEL_1_ONE = "PRESS SPACE TO START";
    private final static String INSTRUCTION_LEVEL_1_TWO = "PRESS A TO ATTACK";
    private final static String INSTRUCTION_LEVEL_1_THREE = "DEFEAT NAVEC TO WIN";
    private final static String VICTORY = "CONGRATULATIONS!";
	private int intensity;
	public static int MIN_SPEED = -3;
	public static int MAX_SPEED = 3;
	public static double SPEED_INCREASE = 1.5;
	
	public Level1(int maxEntries, String imgSrc, String levelCsv, Fae fae) 
	{
		super(maxEntries, imgSrc, levelCsv);
		this.setInsentientUnits(new Insentient[this.getMaxEntries()]);
		this.enemyUnits = new Enemy[this.getMaxEntries()];
		this.intensity = 0;
		readCSV(fae);	
	}
	
	/*
	 * Need to show:
	 * Instructions before you start
	 * Playing Screen as you play level 1
	 * VICTORY screen
	 */
	@Override
	public void playLevel(Input input, Fae fae)
	{
		// Instead print instructions of Level 1 concerning
		// ability to attack enemies and defeat Navec
		if (this.getLevelStart())
		{
    		instructionFont.drawString(INSTRUCTION_LEVEL_1_ONE, 
    				INSTRUCTION_ONE_POS_X, INSTRUCTION_ONE_POS_Y);
    		instructionFont.drawString(INSTRUCTION_LEVEL_1_TWO,
    				INSTRUCTION_TWO_POS_X, INSTRUCTION_TWO_POS_Y);
    		instructionFont.drawString(INSTRUCTION_LEVEL_1_THREE,
    				INSTRUCTION_THREE_POS_X, INSTRUCTION_THREE_POS_Y);

    		
    		// Signals the start of the game (Level 0)
    		if (input.wasPressed(Keys.SPACE))
    		{
    			this.setLevelStart(false);
    			this.setLevelGo(true); 
    		}
		}
		
		// Check for game overs
		else if (this.checkGameOver(fae))
		{
			this.drawGameOver(fae);
		}
		
		// This is the end screen
		else if (!this.getLevelStart() && this.getLevelGo() && this.getLevelDone())
		{
    		// Calculate y-coordinate of bottom left corner
    		double centerY = Window.getHeight()/2;
    		
    		// Calculate x-coordinate of bottom left corner
    		double centerX = (Window.getWidth()/2)-(msgFont.getWidth(VICTORY)/2);
    		
    		msgFont.drawString(VICTORY, centerX, centerY);
		}
		
		// This runs the level, and always checks if you win
		else if (!this.getLevelStart() && this.getLevelGo() && !this.getLevelDone())
    	{
    		this.changeSpeed(input);
    		fae.moveSentient(input, this.getInsentientUnits(), this);
    		
    		// Deal with all the enemies who can attack fae
    		int i = 0;
    		while (this.getEnemyUnits()[i] != null)
    		{
        		this.getEnemyUnits()[i].moveSentient(input, 
    					this.getInsentientUnits(), this);
    			this.getEnemyUnits()[i].attack(fae);
    			this.getEnemyUnits()[i].checkInvincibleState();
    			i++;
    		}
    		
    		// This concerns Fae attacking enemy units
    		if (fae.checkAttackMode(input))
    		{
    			i=0;
    			while (this.getEnemyUnits()[i] != null)
    			{
    				fae.attack(this.getEnemyUnits()[i]);
    				i++;
    			}
    		}
    		
    		// Check attack and invincibility cooldowns
    		// of Fae and draw the level
    		fae.attackTimer();
    		fae.checkInvincibleState();
     		
    		this.drawBackground(fae);
    		
    		// Always check if you won already
    		if (this.checkWin(fae))
    		{
    			this.setLevelDone(true);
    		}
    	}
	}
	
	@Override
	// Here we need to check if Navec died in this level
	public boolean checkWin(Fae fae) 
	{
		int i=0;
		while (this.enemyUnits[i] != null)
		{
			if (this.enemyUnits[i].toString() == Navec.NAVEC_TEXT)
			{
				return false;
			}
			i++;
		}
		return true;
	}
		
	// Need this method to scale the speed of the monsters
	public void changeSpeed(Input input)
	{
		double scale=1;
		
		// Need to determine scale first
		if (input.wasPressed(Keys.K) && this.intensity > MIN_SPEED)
		{
			scale = 1.5;
			this.intensity--;
			String speedShow = String.format("Slowed down, Speed: %3d", this.intensity);
			System.out.println(speedShow);
			int i = 0;
			while (this.enemyUnits[i] != null)
			{
				Enemy curr = this.enemyUnits[i];
				curr.setSpeed(curr.getSpeed()/scale);
				i++;
			}
		}
		
		else if (input.wasPressed(Keys.L) && this.intensity < MAX_SPEED)
		{
			scale = 1.5;
			this.intensity++;
			String speedShow = String.format("Sped up, Speed: %3d", this.intensity);
			System.out.println(speedShow);
			int i = 0;
			while (this.enemyUnits[i] != null)
			{
				Enemy curr = this.enemyUnits[i];
				curr.setSpeed(curr.getSpeed()*scale);
				i++;
			}	
		}
	}

	// Need to edit this level to also read
	//and draw the enemies
	@Override
	public void drawBackground(Fae fae) 
	{
		int i = 0, j = 0;
		Enemy[] newEnemy = new Enemy[Level1.LEVEL_1_MAX];
		
		this.getBackgroundImg().draw(ShadowDimension.WINDOW_WIDTH/2.0,
				ShadowDimension.WINDOW_HEIGHT/2.0);
		
		while (this.getInsentientUnits()[i] != null)
		{
			this.getInsentientUnits()[i].drawInsentient();
			i++;
		}
		
		// Can use this to filter and check for dead enemies
		i = 0;
		while (this.enemyUnits[j] != null)
		{
			if (this.enemyUnits[j].getHealthbar().getHealth() > 0)
			{
				this.enemyUnits[j].drawSentient();
				this.enemyUnits[j].drawAttack(fae);
				newEnemy[i] = this.enemyUnits[j];
				i++;
				j++;	
			}
			
			else
			{
				j++;
			}
		}
		
		// Set new enemy list
		this.enemyUnits = newEnemy;
		
		// Make sure to draw Fae
		fae.drawSentient();
	}
	
	// This one gets enemies, trees, and sinkholes
	@Override
	public void readCSV(Fae fae) 
	{
        int topLeftX = 0;
        int topLeftY = 0;
        int bottomRightX = 0;
        int bottomRightY = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(this.getLevelCSV()))) 
        {
        	int i = 0;
        	int j = 0;
            String text;
            Insentient[] newInsentients = new Insentient[this.MAX_ENTRIES];
                
            while ((text = br.readLine()) != null) 
            {
            	// Separate the inputs in the line
            	String[] input = text.split(",");
                String objectType = input[0];
                int x = Integer.parseInt(input[1]);
                int y = Integer.parseInt(input[2]);
                    
                // Start checking for what entity
                // the values are for 
                if (objectType.equals(Fae.FAE_TEXT))
                {
                	fae.setPosX(x);
                    fae.setPosY(y);
                    fae.setHitbox(new Rectangle(x, y, Fae.FAE_WIDTH, Fae.FAE_HEIGHT));
                }
                    
                else if (objectType.equals(Tree.TREE_TEXT))
                {
                	newInsentients[i] = new Tree(x, y);
                	i++;
                }
                    
                else if (objectType.equals(Sinkhole.SINKHOLE_TEXT))
                {
                	newInsentients[i] = new Sinkhole(x, y);
                	i++;
                }
                
                else if (objectType.equals(Demon.DEMON_TEXT))
                {
                	this.enemyUnits[j] = new Demon(x, y, Demon.DEMON_DAMAGE);
                	j++;
                }
                
                else if (objectType.equals(Navec.NAVEC_TEXT))
                {
                	this.enemyUnits[j] = new Navec(x, y, Navec.NAVEC_DAMAGE);
                	j++;
                }
                    
                else if (objectType.equals(Level.TOP_LEFT))
                {
                	topLeftX = x;
                    topLeftY = y;
                }
                    
                else if (objectType.equals(Level.BOTTOM_RIGHT))
                {
                    bottomRightX = x;
                    bottomRightY = y;
                }
            }
            
            // After reading CSV you can make border of rectangle
            // and set the insentient units
            this.setInsentientUnits(newInsentients);
            this.setLevelBounds(new Rectangle(topLeftX, topLeftY, 
            		bottomRightX - topLeftX, bottomRightY -topLeftY));
        }
        
        catch (IOException e) 
        {
        	e.printStackTrace();
        }
	}
		
	// Getters and Setters
		
	public Enemy[] getEnemyUnits()
	{
		return this.enemyUnits;
	}
	
	public void setEnemyUnits(Enemy[] units)
	{
		this.enemyUnits = units;
	}
}
