import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class movingBlockSIDE
{
    BBeanie app;
    int dx = 2;
    int left, right; // movement limits
    static Image image;
    int locx,locy;
    boolean active=true;
    Rectangle cb;
    static final int LEFT = 0;
    static final int RIGHT = 1;
    int state = RIGHT;
    int w,h;
    boolean side;

    movingBlockSIDE(BBeanie a,int x,int y,int l,int r, boolean isSide)
    {
        app=a;
        locx=x; locy=y;
        left=l;
        right=r;
        side=isSide;
         w=image.getWidth(null);
         h=image.getHeight(null);
        cb=new Rectangle(locx+(int)(0.1*w),locy+(int)(0.1*h),(int)(0.8*w),(int)(0.8*h));
    }

    static void setStaticImage(Image b)
    {
        image=b;
    }

    public Rectangle collisionBox()
    {
        cb=new Rectangle(locx+(int)(0.1*w),locy+(int)(0.1*h),(int)(0.8*w),(int)(0.8*h));
        return cb;
    }

    public int getX()
    {
        return locx;
    }

    public int getY()
    {
        return locy;
    }

    public void updateSprite()
    {
        // Simple state machine walks back and forth
        if (side)
        {
            locx += dx;
            if ((state == RIGHT) && (locx > right))
            {
                state = LEFT;
                dx = -dx;
            }
            else if ((state == LEFT) && (locx < left))
            {
            state = RIGHT;
            dx = -dx;
            }

    }
        
    }


    public void paint(Graphics gr)
    {
        gr.drawImage(image,locx-app.vleft,locy,app);
    }
}