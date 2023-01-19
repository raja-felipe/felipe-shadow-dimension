import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import bagel.*;
import bagel.util.Rectangle;

public class Level0 extends Level
{
	public static final int LEVEL_0_MAX = 60;
	public static final String LEVEL_0_IMG = "res/background0.png";
	public static final String LEVEL_0_CSV = "res/level0.csv";
	private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final static String INSTRUCTION_LEVEL_0_ONE = "PRESS SPACE TO START";
    private final static String INSTRUCTION_LEVEL_0_TWO = "USE ARROW KEYS TO FIND GATE";
    private final static String LEVEL_COMPLETE_MSG = "LEVEL COMPLETE!";
    private final static int WIN_X = 950;
    private final static int WIN_Y = 670;
    private final int LEVEL_COMPLETE_TIME = 3000*60/1000;
    private int levelCompleteTimer;
    private boolean level1Next = false;
	
	public Level0(int maxEntries, String imgSrc, String levelCsv, Fae fae) 
	{
		super(maxEntries, imgSrc, levelCsv);
		this.setInsentientUnits(new Insentient[this.getMaxEntries()]);
		readCSV(fae);
	    this.level1Next = false;
	    this.levelCompleteTimer = 0;
	}
	
	/*
	 * Need to show:
	 * Title Screen before start
	 * Playing Screen as you play level 0
	 * Loading screen to next level
	 * Starting screen for level 1
	 */
	@Override
	public void playLevel(Input input, Fae fae)
	{		
		if (this.getLevelStart())
		{
    		msgFont.drawString(GAME_TITLE, TITLE_POS_X, TITLE_POS_Y);
    		instructionFont.drawString(INSTRUCTION_LEVEL_0_ONE,
    				INSTRUCTION_ONE_POS_X, INSTRUCTION_ONE_POS_Y);
    		instructionFont.drawString(INSTRUCTION_LEVEL_0_TWO,
    				INSTRUCTION_TWO_POS_X, INSTRUCTION_TWO_POS_Y);

    		
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
		
		else if (!this.getLevelStart() && this.getLevelGo() && this.getLevelDone() && !this.level1Next)
		{
    		// Calculate y-coordinate of bottom left corner
    		double centerY = Window.getHeight()/2;
    		
    		// Calculate x-coordinate of bottom left corner
    		double centerX = (Window.getWidth()/2)-(msgFont.getWidth(LEVEL_COMPLETE_MSG)/2);
    		
    		msgFont.drawString(LEVEL_COMPLETE_MSG, centerX, centerY);
    		
    		this.levelCompleteTimer++;
    		
    		if (this.levelCompleteTimer == LEVEL_COMPLETE_TIME)
    		{
    			this.level1Next = true;
    		}
		}
		
		
		// This runs the level, and always checks if you win
		else if (!this.getLevelStart() && this.getLevelGo() && !this.getLevelDone() && !this.level1Next)
		{
    		fae.moveSentient(input, this.getInsentientUnits(), this);
    		this.drawBackground(fae);
    		
    		if (this.checkWin(fae))
    		{
    			this.setLevelDone(true);
    		}
		}
	}
	
	@Override
	public boolean checkWin(Fae fae)
	{
		if ((fae.getPosX() >= WIN_X) && (fae.getPosY() >= WIN_Y)) 
		{ 
			System.out.println("WIN LEVEL 0");
			return true; 
		}
		return false;
	}
	
	// Check for win in level0 just means if player reaches
	// end point
	public boolean checkLevel0Win(Fae fae)
	{
		if ((fae.getPosX() >= WIN_X) && (fae.getPosY() >= WIN_Y)) 
		{ 
			System.out.println("WIN LEVEL 0");
			return true; 
		}
		return false;
	}
	
	@Override
	public void drawBackground(Fae fae) 
	{
		int i = 0;
		
		this.getBackgroundImg().draw(ShadowDimension.WINDOW_WIDTH/2.0,
				ShadowDimension.WINDOW_HEIGHT/2.0);
		
		while (this.getInsentientUnits()[i] != null)
		{
			this.getInsentientUnits()[i].drawInsentient();
			i++;
		}
		
		// Make sure to draw Fae
		fae.drawSentient();
	}
	
	// This one gets walls and sinkholes
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
                    
                else if (objectType.equals(Wall.WALL_TEXT))
                {
                	newInsentients[i] = new Wall(x, y);
                	i++;
                }
                    
                else if (objectType.equals(Sinkhole.SINKHOLE_TEXT))
                {
                	newInsentients[i] = new Sinkhole(x, y);
                	i++;
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
            // And the insentient units
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
	
	public boolean getLevel1Next()
	{
		return this.level1Next;
	}
	
	public void setLevel1Next(boolean setting)
	{
		this.level1Next = setting;
	}
}
