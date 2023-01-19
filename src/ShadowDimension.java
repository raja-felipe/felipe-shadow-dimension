import bagel.*;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2022
 *
 * Please enter your name below
 * Ramon Javier L. Felipe VI
 */

public class ShadowDimension extends AbstractGame {
	// Make classes for all of the needed objects
	private Fae fae;
	private Level0 level0;
	private Level1 level1;
	private final static String GAME_TITLE = "SHADOW DIMENSION";
	public final static int WINDOW_WIDTH = 1024;
    public final static int WINDOW_HEIGHT = 768;
  
    

    public ShadowDimension()
    {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        // Only initialize level0 for now, don't need level 1 yet
        this.fae = new Fae(0, 0, Fae.FAE_DAMAGE);
        this.level0 = new Level0(Level0.LEVEL_0_MAX, 
        		Level0.LEVEL_0_IMG, Level0.LEVEL_0_CSV, this.fae);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) 
    {
        // First check if the game was closed at each
    	// update loop
    	if (input.wasPressed(Keys.ESCAPE))
        {
            Window.close();
        }
    	
    	else if (!this.level0.getLevel1Next())
    	{
    		this.level0.playLevel(input, fae);
    	}
    	
    	else if (this.level0.getLevel1Next())
    	{   		
    		// Make sure to load the level
    		// and reset the coordinates
    		if (this.level1 == null)
    		{
    	        this.level1 = new Level1(Level1.LEVEL_1_MAX,
    	        		Level1.LEVEL_1_IMG, Level1.LEVEL_1_CSV, this.fae);
    		}
    		
    		this.level1.playLevel(input, fae);
    	}
    }
}