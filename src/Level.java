import bagel.*;
import bagel.util.*;

// Class that has the basic necessities of a level
public abstract class Level
{
	private Insentient[] insentientUnits;
	private Rectangle levelBounds;
	private final Image BACKGROUND_IMG;
	private final String LEVEL_CSV;
	public final int MAX_ENTRIES;
	public static final String TOP_LEFT = "TopLeft";
	public static final String BOTTOM_RIGHT = "BottomRight";
	private final static String FONT_STYLE = "res/frostbite.ttf";
    protected final static String GAME_OVER = "GAME OVER";
    protected final static int TITLE_POS_X = 260;
    protected final static int TITLE_POS_Y = 250;
    protected final static int INSTRUCTION_ONE_POS_X = TITLE_POS_X + 90;
    protected final static int INSTRUCTION_ONE_POS_Y = TITLE_POS_Y + 190;
    protected final static int INSTRUCTION_TWO_POS_X = INSTRUCTION_ONE_POS_X;
    protected final static int INSTRUCTION_TWO_POS_Y = INSTRUCTION_ONE_POS_Y + 50;
    protected final static int INSTRUCTION_THREE_POS_X = INSTRUCTION_ONE_POS_X;
    protected final static int INSTRUCTION_THREE_POS_Y = INSTRUCTION_TWO_POS_Y + 50;
    private boolean levelStart;
    private boolean levelGo;
    private boolean levelDone;
    protected Font instructionFont = new Font(FONT_STYLE, INSTRUCTION_SIZE);
    protected Font msgFont = new Font (FONT_STYLE, MSG_SIZE);
    private final static int INSTRUCTION_SIZE = 40;
    private final static int MSG_SIZE = 75;
	
	// Make sure to draw the image and put the bound coordinates
	public Level(int maxEntries, String imgSrc, String levelCsv)
	{
		// this.levelBounds = new Rectangle(topX, topY, botX-topX, botY-topY);
		this.BACKGROUND_IMG = new Image(imgSrc);
		this.LEVEL_CSV = levelCsv;
		this.MAX_ENTRIES = maxEntries;
		this.levelStart = true;
		this.levelGo = false;
		this.levelDone = false;
	}
	
	// Need this method to get and draw the units
	public abstract void drawBackground(Fae fae);
	public abstract void readCSV(Fae fae);
	public abstract void playLevel(Input input, Fae fae);
	public abstract boolean checkWin(Fae fae);
	
	// Draw Game Over Screen when possible
	public void drawGameOver(Fae fae)
	{
       	// Calculate y-coordinate of bottom left corner
    	double centerY = Window.getHeight()/2;
    		
    	// Calculate x-coordinate of bottom left corner
    	double centerX = (Window.getWidth()/2)-(msgFont.getWidth(GAME_OVER)/2);
    		
    	msgFont.drawString(GAME_OVER, centerX, centerY);
	}
	
	// Game Over Status is always the same for levels: check Fae health
	public boolean checkGameOver(Fae fae)
	{
		if (fae.getHealthbar().getHealth() == 0)
		{
			return true;
		}
		
		return false;
	}
	
	// Getters and Setters
	
	public Rectangle getLevelBounds()
	{
		return this.levelBounds;
	}
	
	public void setLevelBounds(Rectangle levelBounds)
	{
		this.levelBounds = levelBounds;
	}
	
	public Image getBackgroundImg()
	{
		return this.BACKGROUND_IMG;
	}
	
	public String getLevelCSV()
	{
		return this.LEVEL_CSV;
	}
	
	public int getMaxEntries()
	{
		return this.MAX_ENTRIES;
	}
	
	public Insentient[] getInsentientUnits()
	{
		return this.insentientUnits;
	}
	
	public void setInsentientUnits(Insentient[] units)
	{
		this.insentientUnits = units;
	}

	public boolean getLevelStart()
	{
		return this.levelStart;
	}
	
	public void setLevelStart(boolean setting)
	{
		this.levelStart = setting;
	}
	
	public boolean getLevelGo()
	{
		return this.levelGo;
	}
	
	public void setLevelGo(boolean setting)
	{
		this.levelGo = setting;
	}
	
	public boolean getLevelDone()
	{
		return this.levelDone;
	}
	
	public void setLevelDone(boolean setting)
	{
		this.levelDone = setting;
	}
}
