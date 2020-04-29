import acm.graphics.*; // GOval, GRect, etc.
import acm.program.*; // GraphicsProgram
import acm.util.*; // RandomGenerator
import java.applet.*; // AudioClip
import java.awt.*; // Color
import java.awt.event.*; // MouseEvent

public class Breakout extends GraphicsProgram implements BreakoutConstants {

	private GLabel gameOver;
	private GLabel score;
	private GLabel winner;
	private GLabel moveMouse;
	private GLabel lvl;
	private GRect brick;
	private GOval ball;
	private GRect paddle;
	double mouseX;
	double mouseY;
	double speed;
	double vx = 1;
	double vy = 1;
	int points = 0;
	int lv = 1;

	public void run() {
		addMouseListeners();
		addKeyListeners();

		lvl = new GLabel("Lv. " + lv);
		lvl.setFont("Courier-20");
		moveMouse = new GLabel("(Move your mouse first)");
		moveMouse.setFont("Courier-20");
		
		firstLevel();
	}

	// creates first level
	private void firstLevel() {
		add(lvl, BOARD_WIDTH - 5 - lvl.getWidth(), 20);
		bricks(Color.CYAN);
		paddle();
		score();
		ball();
	}

	// creates second level
	private void secondLevel() {
		bricks(Color.CYAN);
		bricks2();
		paddle();
		score();
		ball();
	}

	// creates third level
	private void thirdLevel() {
		bricks(Color.CYAN);
		bricks2();
		bricks3();
		paddle();
		score();
		ball();
	}

	// creates the brick wall (the first layer)
	private void bricks(Color c) {
		for (double j = BRICK_Y_OFFSET; j < 10 * (BRICK_HEIGHT + BRICK_SEP) + BRICK_Y_OFFSET; j = j + BRICK_HEIGHT
				+ BRICK_SEP) {

			// sets the colors of bricks
			if (j < 2 * (BRICK_HEIGHT + BRICK_SEP) + BRICK_Y_OFFSET) {
				c = Color.RED;
			} else if (j < 4 * (BRICK_HEIGHT + BRICK_SEP) + BRICK_Y_OFFSET) {
				c = Color.ORANGE;
			} else if (j < 6 * (BRICK_HEIGHT + BRICK_SEP) + BRICK_Y_OFFSET) {
				c = Color.YELLOW;
			} else if (j < 8 * (BRICK_HEIGHT + BRICK_SEP) + BRICK_Y_OFFSET) {
				c = Color.GREEN;
			} else {
				c = Color.cyan;
			}

			for (double i = BRICK_SEP; i < BOARD_WIDTH - BRICK_WIDTH; i = i + BRICK_WIDTH + BRICK_SEP) {

				// creates the brick
				brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				brick.setColor(c);
				add(brick, i, j);
			}
		}
	}

	// creates the brick wall (the second layer)
	private void bricks2() {
		for (double j = BRICK_Y_OFFSET; j < 10 * (BRICK_HEIGHT + BRICK_SEP) + BRICK_Y_OFFSET; j = j + BRICK_HEIGHT
				+ BRICK_SEP) {

			for (double i = BRICK_SEP; i < BOARD_WIDTH - BRICK_WIDTH; i = i + BRICK_WIDTH + BRICK_SEP) {
				brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				brick.setColor(Color.GRAY);
				add(brick, i, j);
			}
		}
	}

	// creates the brick wall (the third layer)
	private void bricks3() {
		for (double j = BRICK_Y_OFFSET; j < 10 * (BRICK_HEIGHT + BRICK_SEP) + BRICK_Y_OFFSET; j = j + BRICK_HEIGHT
				+ BRICK_SEP) {

			for (double i = BRICK_SEP; i < BOARD_WIDTH - BRICK_WIDTH; i = i + BRICK_WIDTH + BRICK_SEP) {
				brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				brick.setColor(Color.WHITE);
				add(brick, i, j);
			}
		}
	}

	// creates paddle down in the window
	public void paddle() {
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.BLACK);
		add(paddle);
	}

	// makes paddle moving => follow the mouse
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		paddle.setLocation(mouseX - PADDLE_WIDTH / 2, BOARD_HEIGHT - PADDLE_Y_OFFSET);
	}

	// creates and adds bouncingBall + add text to screen
	private void ball() {

		// creates and adds the ball to the center of the screen
		double x = (BOARD_WIDTH / 2);
		double y = (BOARD_HEIGHT / 2);
		ball = new GOval(x, y, BALL_RADIUS * 2, BALL_RADIUS * 2);
		ball.setColor(Color.BLUE);
		ball.setFilled(true);
		ball.setFillColor(Color.BLUE);
		add(ball);

		// creates and adds 'welcome text' -> "Click to start! ;)"
		GLabel start = new GLabel("Click to start! ;)");
		start.setFont("Courier-50");
		add(start, (BOARD_WIDTH - start.getWidth()) / 2, (BOARD_HEIGHT - start.getY()) / 2);

		add(moveMouse, (BOARD_WIDTH - moveMouse.getWidth()) / 2, BOARD_HEIGHT / 2 + start.getHeight() / 2);

		// when user clicks, game will start
		waitForClick();
		remove(start);
		remove(moveMouse);

		// at the beginning the score is set to 0
		points = 0;

		// at the beginning the movement of ball on x is 2 and on y is also 2
		vx = 2;
		vy = 2;

		// the ball will bounce when hits the side of the window
		while (true) {
			if (ball.getY() > (getHeight() - BALL_RADIUS * 2) || ball.getY() < 0) {
				vy = vy * -1;
			} else if (ball.getX() > (getWidth() - BALL_RADIUS * 2) || ball.getX() < 0) {
				vx = vx * -1;
			}

			checkCollision();
			checkScore();
			ball.move(vx, vy);

			// movement speed
			pause(DELAY);

			// stops and shows 'instructions' when the ball is under the paddle
			if ((ball.getY() + 2 * BALL_RADIUS - 9) > paddle.getY()) {
				gameOver = new GLabel("Game Over!");
				gameOver.setFont("Courier-50");
				add(gameOver, (BOARD_WIDTH - gameOver.getWidth()) / 2, (BOARD_HEIGHT - gameOver.getY()) / 2);

				GLabel finalScore = new GLabel("Your score is " + points);
				finalScore.setFont("Courier-30");
				add(finalScore, (BOARD_WIDTH - finalScore.getWidth()) / 2, BOARD_HEIGHT / 2 + finalScore.getHeight());

				GLabel restart = new GLabel("Click to restart the level");
				restart.setFont("Courier-30");
				add(restart, (BOARD_WIDTH - restart.getWidth()) / 2, BOARD_HEIGHT / 2 + restart.getHeight() * 2);

				vx = vy = 0;

				// after user clicks the level will reset
				waitForClick();

				if (lv == 1) {
					removeAll();
					add(lvl, BOARD_WIDTH - 5 - lvl.getWidth(), 20);
					add(ball);
					ball.setLocation((BOARD_WIDTH - ball.getWidth()) / 2, (BOARD_HEIGHT - ball.getHeight()) / 2);
					bricks(Color.CYAN);
				} else if (lv == 2) {
					removeAll();
					add(lvl, BOARD_WIDTH - 5 - lvl.getWidth(), 20);
					add(ball);
					ball.setLocation((BOARD_WIDTH - ball.getWidth()) / 2, (BOARD_HEIGHT - ball.getHeight()) / 2);
					bricks(Color.CYAN);
					bricks2();
				} else if (lv == 3) {
					removeAll();
					add(lvl, BOARD_WIDTH - 5 - lvl.getWidth(), 20);
					add(ball);
					ball.setLocation((BOARD_WIDTH - ball.getWidth()) / 2, (BOARD_HEIGHT - ball.getHeight()) / 2);
					bricks(Color.CYAN);
					bricks2();
					bricks3();
				}
				add(score);
				points = 0;
				add(paddle);
				vx = vy = 2;
			}
		}
	}

	// checking for collisions
	private void checkCollision() {

		if ((getElementAt(ball.getX(), ball.getY()) != null)
				|| (getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY()) != null)
				|| (getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2) != null)
				|| (getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2) != null)) {

			// when the ball hits the paddle or score label or level label it will bounce
			// off
			if ((getElementAt(ball.getX(), ball.getY()) == paddle)
					|| (getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY()) == paddle)
					|| (getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2) == paddle)
					|| (getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2) == paddle)
					|| (getElementAt(ball.getX(), ball.getY()) == score)
					|| (getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY()) == score)
					|| (getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2) == score)
					|| (getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2) == score)
					|| (getElementAt(ball.getX(), ball.getY()) == lvl)
					|| (getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY()) == lvl)
					|| (getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2) == lvl)
					|| (getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2) == lvl)) {

				// change of x movement depends on where the ball lands on paddle
				if (ball.getX() + BALL_RADIUS >= paddle.getX()
						&& ball.getX() + BALL_RADIUS < paddle.getX() + (PADDLE_WIDTH / 5)
						&& ball.getY() + 2 * BALL_RADIUS == paddle.getY()) {
					vx = -2;
				} else if (ball.getX() + BALL_RADIUS >= paddle.getX() + (PADDLE_WIDTH / 5)
						&& ball.getX() + BALL_RADIUS < paddle.getX() + (PADDLE_WIDTH / 5) * 2
						&& ball.getY() + 2 * BALL_RADIUS == paddle.getY()) {
					vx = -1;
					vy = 2;
				} else if (ball.getX() + BALL_RADIUS >= paddle.getX() + (PADDLE_WIDTH / 5) * 2
						&& ball.getX() + BALL_RADIUS < paddle.getX() + (PADDLE_WIDTH / 5) * 3
						&& ball.getY() + 2 * BALL_RADIUS == paddle.getY()) {
					vx = 0;
					vy = 3;
				} else if (ball.getX() + BALL_RADIUS >= paddle.getX() + (PADDLE_WIDTH / 5) * 3
						&& ball.getX() + BALL_RADIUS < paddle.getX() + (PADDLE_WIDTH / 5) * 4
						&& ball.getY() + 2 * BALL_RADIUS == paddle.getY()) {
					vx = 1;
					vy = 2;
				} else if (ball.getX() + BALL_RADIUS >= paddle.getX() + (PADDLE_WIDTH / 5) * 4
						&& ball.getX() + BALL_RADIUS <= paddle.getX() + (PADDLE_WIDTH / 5) * 5
						&& ball.getY() + 2 * BALL_RADIUS == paddle.getY()) {
					vx = 2;
				}

				// changes y movement when hits paddle
				vy = vy * -1;

			} else {

				// changes y movement when hits brick
				vy = vy * -1;

				// removes brick when hit
				if (getElementAt(ball.getX(), ball.getY()) != null) {
					remove(getElementAt(ball.getX(), ball.getY()));
					points++;
					score.setLabel("Score: " + points);
				} else if (getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY()) != null) {
					remove(getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY()));
					points++;
					score.setLabel("Score: " + points);
				} else if (getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2) != null) {
					remove(getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2));
					points++;
					score.setLabel("Score: " + points);
				} else if (getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2) != null) {
					remove(getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2));
					points++;
					score.setLabel("Score: " + points);
				}

				else if (getElementAt(ball.getX(), ball.getY()) != null
						&& getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY()) != null) {
					remove(getElementAt(ball.getX(), ball.getY()));
					points++;
					score.setLabel("Score: " + points);
					remove(getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY()));
					points++;
					score.setLabel("Score: " + points);
				} else if (getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY()) != null
						&& getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2) != null) {
					remove(getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY()));
					points++;
					score.setLabel("Score: " + points);
					remove(getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2));
					points++;
					score.setLabel("Score: " + points);
				} else if (getElementAt(ball.getX(), ball.getY()) != null
						&& getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2) != null) {
					remove(getElementAt(ball.getX(), ball.getY()));
					points++;
					score.setLabel("Score: " + points);
					remove(getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2));
					points++;
					score.setLabel("Score: " + points);
				} else {
					remove(getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2));
					points++;
					score.setLabel("Score: " + points);
					remove(getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2));
					points++;
					score.setLabel("Score: " + points);
				}
			}
		}
	}

	// checks score to change the level or write the final score when the game is
	// over
	private void checkScore() {
		if (lv == 1) {

			// after reaching 100 points it will change to 2nd level
			if (points == 100) {
				vx = vy = 0;
				lv++;

				winner = new GLabel("You won! Congrats!");
				winner.setFont("Courier-40");
				add(winner, (BOARD_WIDTH - winner.getWidth()) / 2, (BOARD_HEIGHT - winner.getY()) / 2);

				GLabel nextLv = new GLabel("Click to start the next level");
				nextLv.setFont("Courier-30");
				add(nextLv, (BOARD_WIDTH - nextLv.getWidth()) / 2, BOARD_HEIGHT / 2 + nextLv.getHeight());

				waitForClick();

				removeAll();
				lvl = new GLabel("Lv. " + lv);
				lvl.setFont("Courier-20");
				add(lvl, BOARD_WIDTH - 5 - lvl.getWidth(), 20);
				secondLevel();
			}
		} else if (lv == 2) {

			// after reaching 200 points it will change to 3rd level
			if (points == 200) {
				vx = vy = 0;
				lv++;

				winner = new GLabel("You won! Congrats!");
				winner.setFont("Courier-40");
				add(winner, (BOARD_WIDTH - winner.getWidth()) / 2, (BOARD_HEIGHT - winner.getY()) / 2);

				GLabel nextLv = new GLabel("Click to start the next level");
				nextLv.setFont("Courier-30");
				add(nextLv, (BOARD_WIDTH - nextLv.getWidth()) / 2, BOARD_HEIGHT / 2 + nextLv.getHeight());

				waitForClick();

				removeAll();
				lvl = new GLabel("Lv. " + lv);
				lvl.setFont("Courier-20");
				add(lvl, BOARD_WIDTH - 5 - lvl.getWidth(), 20);
				thirdLevel();
			}
		} else if (lv == 3) {

			// after reaching 300 points the game ends
			if (points == 300) {
				vx = vy = 0;
				lv++;

				winner = new GLabel("You won!");
				winner.setFont("Courier-60");
				add(winner, (BOARD_WIDTH - winner.getWidth()) / 2, (BOARD_HEIGHT - winner.getY()) / 2);

				GLabel theEnd = new GLabel("Congratulations for completing the game!");
				theEnd.setFont("Courier-20");
				add(theEnd, (BOARD_WIDTH - theEnd.getWidth()) / 2, BOARD_HEIGHT / 2 + theEnd.getHeight());
			}
		}
	}

	// adds score label to the left upper corner
	private void score() {
		score = new GLabel("Score: ", 5, 20);
		score.setFont("Courier-20");
		add(score);
	}

}