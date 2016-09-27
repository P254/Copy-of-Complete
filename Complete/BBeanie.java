import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class BBeanie extends Applet implements Runnable
{
	HeroSprite hero;
	BadGuy badguy;
	Scenery scene[] = new Scenery[10];
	Burst bursts[] = new Burst[7];
	Grid grid;
	int score;
	boolean killed = false;
	Thread anim;
	Image buffer;
	Graphics bufgr;
	Image saved_i;
	int cut = 50;  // cutpoint in background scenery (to scroll)
	Font font;
	boolean intro = true; // init instruction screen
	int FRAME_DELAY = 50;
	public static final int VWIDTH = 800;
	public static final int VHEIGHT = 600;
	public static final int SCROLL = 30;  // Set edge limit for scrolling
	int vleft = 0;	// Pixel coord of left edge of viewable area
					// (used for scrolling)
	movingBlock movingBlock;

	public void init()
	{
		addMouseListener(new mseL());
		addKeyListener(new keyL());

		buffer = createImage(VWIDTH,VHEIGHT);
		bufgr = buffer.getGraphics();
		font = new Font("TimesRoman",Font.ITALIC,30);
		Image heros = getImage(getCodeBase(), "heros.gif");
		Image herou = getImage(getCodeBase(), "herou.gif");
		Image herod = getImage(getCodeBase(), "herod.gif");
		Image blockImage = getImage(getCodeBase(), "block.gif");
		saved_i = getImage(getCodeBase(), "back.gif");
		Image f1 = getImage(getCodeBase(), "flwrm.gif");
		Image f2 = getImage(getCodeBase(), "flwrl.gif");
		Image f3 = getImage(getCodeBase(), "flwrr.gif");
		Image b1 = getImage(getCodeBase(), "bfly1.gif");
		Image b2 = getImage(getCodeBase(), "bfly2.gif");
		Image burst = getImage(getCodeBase(), "burst.gif");
		Image guy = getImage(getCodeBase(), "guy.gif");
		Image block= getImage(getCodeBase(), "movingBlock.gif");

		MediaTracker t = new MediaTracker(this);
				t.addImage(heros, 0);
				t.addImage(herou, 0);
				t.addImage(herod, 0);
				t.addImage(blockImage, 0);
				t.addImage(saved_i, 0);
				t.addImage(f1, 0);
				t.addImage(f2, 0);
				t.addImage(f3, 0);
				t.addImage(b1, 0);
				t.addImage(b2, 0);
				t.addImage(burst, 0);
				t.addImage(guy, 0);
				try
				{
					t.waitForID(0);
				} catch (InterruptedException e)
			{}
		grid = new Grid(this, blockImage);
		// Store images with classes rather than a reference
		// in every object.
		HeroSprite.setStaticImages(heros,herou,herod);
		Flower.setStaticImages(f1,f2,f3);
		BFly.setStaticImages(b1,b2);
		Burst.setStaticImage(burst);
		BadGuy.setStaticImage(guy);
		movingBlock.setStaticImage(block);

		setLevel1();
		score = 0;
	}

	public void start()
	{
		anim = new Thread(this);
		anim.start();
	}

	public void stop()
	{
		anim.stop();
		anim = null;
	}

	public void setDead()
	{
		killed = true;
	}

	public void run()
	{
		int i;
		boolean isItTouched= false;
		while(true)
		{
			System.out.println(movingBlock.x + " " + (int)hero.getX() + " "+ (int)hero.y);
			if (!killed)
				hero.updateSprite();
			badguy.updateSprite();
			movingBlock.updateSprite();
			for (i = 0; i < scene.length; i++)
				scene[i].update();
			// Check for collisions (Note: we don't hit scenery)
			Rectangle cb = hero.collisionBox();
			for (i = 0; i < bursts.length; i++)
				if (bursts[i].isActive() && cb.intersects(bursts[i].collisionBox()))
					bursts[i].hit();
			if (cb.intersects(badguy.collisionBox()))
			{
				//setDead();

			}
			if (Math.abs((int)movingBlock.x -(int)hero.getX()) <30 && Math.abs((int)hero.y- 340) <100)
			{
				hero.isTouch=true;
				System.out.println("Happns");
			}
			if (cb.intersects(movingBlock.collisionBox()))
			{
				System.out.println("yo");
				isItTouched=true;
				hero.isTouch=true;

			}

			checkScrolling();
			repaint();
			try
			{
				Thread.sleep(FRAME_DELAY);
			} catch (InterruptedException e)
				{}
		}
	}

	void checkScrolling()
	{
		// Test if hero is at edge of view window and scroll appropriately
		if (hero.getX() < (vleft+SCROLL))
		{
			vleft = hero.getX()-SCROLL;
			if (vleft < 0)
				vleft = 0;
		}
		if ((hero.getX() + hero.width()) > (vleft+VWIDTH-SCROLL))
		{
			vleft = hero.getX()+hero.width()-VWIDTH+SCROLL;
			if (vleft > (grid.width()-VWIDTH))
				vleft = grid.width()-VWIDTH;
		}
	}

	public void setLevel1()
	{

		// Put in a ground level
		for (int i = 0; i < grid.MWIDTH; i++)
			grid.setBlock(i, grid.MHEIGHT-1);

		// Now place specific blocks (depends on current map size)
		/* new stuff */grid.setBlock(4,13);
		grid.setBlock(5,11);
		grid.setBlock(6,9); grid.setBlock(7,9);
		grid.setBlock(8,8);
		grid.setBlock(9,7);/* new stuff */
		grid.setBlock(10,13);
		grid.setBlock(11,13); grid.setBlock(11,12);
		grid.setBlock(12,13); grid.setBlock(12,12); grid.setBlock(12,11);
		grid.setBlock(13,13);

		grid.setBlock(17,10); grid.setBlock(18,10);
		grid.setBlock(19,8); grid.setBlock(20,8);


		grid.setBlock(22,13); grid.setBlock(24,13);
		grid.setBlock(25,11); grid.setBlock(26,11);
		grid.setBlock(23,9); grid.setBlock(24,9);
		grid.setBlock(25,7); grid.setBlock(26,7);
		grid.setBlock(22,5); grid.setBlock(23,5); grid.setBlock(24,5);
		grid.setBlock(20,8); grid.setBlock(19,8);

		grid.setBlock(30,11); grid.setBlock(31,11);
		grid.setBlock(32,10); grid.setBlock(33,9);

		grid.setBlock(32,7); grid.setBlock(32,4);
		grid.setBlock(31,6);

		grid.setBlock(34,7);
		for (int x=0;x<9;x++)
			grid.setBlock(35,5+x);


		grid.setBlock(39,13); grid.setBlock(38,13); grid.setBlock(39,12);
		// Setup foreground scenery
		scene[0] = new Flower(this,60,269*2+30,100,0);
		scene[1] = new Flower(this,90,269*2+30,100,20);
		scene[2] = new Flower(this,120,269*2+30,100,40);
		scene[3] = new Flower(this,650,269*2+30,120,30);
		scene[4] = new Flower(this,680,269*2+30,120,0);
		scene[5] = new BFly(this,70,120*2+30);
		scene[6] = new BFly(this,383,87*2+30);
		scene[7] = new BFly(this,400,80*2+30);
		scene[8] = new BFly(this,100,120*2+30);
		scene[9] = new Flower(this,100,269*2+30,100,0);
		// Setup up scoring bursts
		bursts[0] = new Burst(this,320,150*2+30);
		bursts[1] = new Burst(this,220,150*2+30);
		bursts[2] = new Burst(this,500,60*2+30);
		bursts[3] = new Burst(this,720,160*2+30);
		bursts[4] = new Burst(this,735,140*2+30);
		bursts[5] = new Burst(this,750,155*2+30);
		bursts[6] = new Burst(this,640,100*2+30);
		// And, the stars of our show...
		hero = new HeroSprite(this,grid,50,249);
		badguy = new BadGuy(this,540,249*2+30,520,620);
		movingBlock= new movingBlock(this,360,150*2+40,480,540);

	}

	private boolean lostGame()
	{
		return killed;
	}

	private boolean wonGame()
	{
		return (score == 10*bursts.length);
	}

	public void update(Graphics g)
	{
		paint(bufgr);
		g.drawImage(buffer,0,0,this);


	}

	public void paint(Graphics g)
	{
		int i;

		if (intro)
		{
			// startup screen
			g.setColor(Color.white);
			g.fillRect(0,0,400,300);
			g.setColor(Color.black);
			g.drawRect(0,0,399,299);
			g.setFont(font);
			g.drawString("BOUNCING BEANIE!",50,40);
			g.drawString("Use arrow keys to",50,100);
			g.drawString("move left and right,",40,130);
			g.drawString("space bar to jump.",60,160);
			g.drawString("Click applet to start game.",30,250);
			return;
		}
		/*
		 * Begin main paint
		 */
		g.setClip(0, 0, VWIDTH, VHEIGHT);
		cut = vleft>>1; // setting cut to half the main scroll factor
						// gives the parallax effect
		g.drawImage(saved_i, -100-cut, 0,VWIDTH,VHEIGHT, this);
		g.drawImage(saved_i, 400-cut, 0,VWIDTH,VHEIGHT, this);

		grid.paint(g);
		if (!killed)
			hero.drawSprite(g);
		badguy.drawSprite(g);
		for (i = 0; i < scene.length; i++)
			scene[i].paint(g);
		for (i = 0; i < bursts.length; i++)
			bursts[i].paint(g);
		movingBlock.drawSprite(g);
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString("Score:"+score,250,25);
		if (wonGame())
		{
			g.setColor(Color.black);
			g.setFont(font);
			g.drawString("You win!",100,100);
		}
		if (lostGame())
		{
			g.setColor(Color.black);
			g.setFont(font);
			g.drawString("You lose.",100,100);
		}
	}

	public void bumpScore(int p)
	{
		score += p;
	}

	private class mseL extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			requestFocus();
			intro = false;
		}
	}


	private class keyL extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{

			int key = e.getKeyCode();
			switch (key)
			{
				// Duplicate keys are defined to maintain
				// the original bindings (as well as provide
				// more sensible ones)
				//
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_J: hero.dir = 1;
									hero.keyPressed=true;
									break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_K: hero.dir = 2;
									hero.keyPressed=true;
									break;
				case KeyEvent.VK_SPACE:
				case KeyEvent.VK_A : hero.jmp = 1;
									hero.keyPressed=true;
									break;
				case KeyEvent.VK_Q : hero.jmp= 2;
									hero.keyPressed=true;
									break;
				case KeyEvent.VK_L: hero.jmp= 3;
									hero.keyPressed=true;
									break;
				default: hero.keyPressed=false;
									break;




			}
		}
		public void keyReleased(KeyEvent e)
		{
			int key = e.getKeyCode();
			if ((key == KeyEvent.VK_J)||(key == KeyEvent.VK_K)||
					(key == KeyEvent.VK_LEFT)||(key == KeyEvent.VK_RIGHT))
				hero.dir = 0;
		}
	}
}

