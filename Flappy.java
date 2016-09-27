import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Flappy extends ImageSprite
{
	int dx = 0, dy = 0;
	public int dir = 0;
	public int jmp = 0;
	int state;
	Grid g;
	static final int STAND = 0;
	static final int JUMP = 1;
	static final int GRAVITY = 2;
	BBeanie app;
	static Image im_stand;
	static Image im_up;
	static Image im_down;
	boolean isTouch= false;
	boolean keyPressed=false;
	public Flappy(BBeanie a, Grid grid, int x, int y)
	{
		super(im_stand, x, y);
		g = grid;
		app = a;
		state = STAND;
	}

	static void setStaticImages(Image hs, Image hu, Image hd)
	{
		im_stand = hs;  // default image
		im_up = hu;     // when jumping upward
		im_down = hd;   // when jumping (falling?) downward
	}

	public int getX()
	{
		return (int)x;
	}

	public int width()
	{
		return width;
	}

	public void drawSprite(Graphics gr)
	{
		gr.drawImage(image,(int)x-app.vleft, (int)y, app);
	}

	public void updateSprite()
	{
			// Handle movement inputs
			if (dir == 1)
				dy = -12;
			else
				dy = 12;
			dx=20;
				
		

		// Then do the moving
		updatePosition();
	}

	public Rectangle collisionBox()
	{
		// We get better gameplay with a 90% collison box
		return new Rectangle((int)(x+0.05*width), (int)(y+0.05*height),
					(int)(0.9*width), (int)(0.9*height));
	}
	public void changeToStand()
	{
		state=STAND;
		setImage(im_stand);
	}
	public void updatePosition()
	{

		// First handle sideways movement

			if (dx > 0)
			{
				dx = g.moveRight(collisionBox(), dx);
			}
			else if (dx < 0)
			{
				dx = -g.moveLeft(collisionBox(), -dx);
			}
			if (dx != 0)
				x += dx;
			// Then look at vertical movement (if any)
			if (state == JUMP)
			{
				if (dy > 0)
				{
					dy = g.moveDown(collisionBox(), dy);
					setImage(im_down);
				}
				else if (dy < 0)
				{
					dy = -g.moveUp(collisionBox(), -dy);
				}
				if (dy != 0)
					y += dy;
				// Let gravity act (as acceleration)
				dy += GRAVITY;
				//
				// Fix that problem of the game crashing
				// after long falls - just impose a terminal
				// velocity (less than block size in the grid)
				if (dy > 19)
					dy = 19;
				// If we've landed on something, change state
				if (g.onGround(collisionBox()))
				{
					dy = 0;
					state = STAND;
					setImage(im_stand);
				}
				else if (g.atTop(collisionBox()))
				{
					dy = 0;
				}
			}


		else
			if (!g.onGround(collisionBox()))
				state = JUMP;



	}
}
