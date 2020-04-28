public interface BreakoutConstants {
	/**
	 * Width and height of application window, in pixels.
	 */
	public static final int APPLICATION_WIDTH = 420;
	public static final int APPLICATION_HEIGHT = 600;

	/**
	 * Dimensions of game board, in pixels
	 */
	public static final int BOARD_WIDTH = APPLICATION_WIDTH;
	public static final int BOARD_HEIGHT = APPLICATION_HEIGHT;

	//BRICKS:
	
	/**
	 * Number of bricks in each row
	 */
	public static final int NBRICKS_PER_ROW = 10;

	/** 
	 * Number of rows of bricks
	 */
	public static final int NBRICK_ROWS = 10;

	/**
	 * Separation between neighboring bricks, in pixels
	 */
	public static final int BRICK_SEP = 4;

	/**
	 * Width of each brick, in pixels
	 */
	public static final double BRICK_WIDTH = 
		(BOARD_WIDTH - (NBRICKS_PER_ROW + 1.0) * BRICK_SEP) / NBRICKS_PER_ROW;

	/**
	 * Height of each brick, in pixels
	 */
	public static final int BRICK_HEIGHT = 8;

	/**
	 * Offset of the top brick row from the top, in pixels
	 */
	public static final int BRICK_Y_OFFSET = 70;
	
	//PADDLE:

	/**
	 * Dimensions of the paddle
	 */
	public static final int PADDLE_WIDTH = 60;
	public static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	public static final int PADDLE_Y_OFFSET = 30;
		
	//BALL:
	
	/**
	 * Radius of the ball in pixels
	 */
	public static final int BALL_RADIUS = 10;
	
	/**
	 * Animation delay or pause time between ball moves (ms)
	 */ 
	public static final int DELAY = 5;

}
