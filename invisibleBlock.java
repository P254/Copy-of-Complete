import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class invisibleBlock
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

    invisibleBlock(BBeanie a,int x,int y)
    {
        app=a;
        locx=x; locy=y;
        //left=l;
        //right=r;
        
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
            
            /*if ((state == RIGHT) && (locy > right))
            {
                state = LEFT;
                dx = -dx;
            }
            else if ((state == LEFT) && (locy < left))
            {
            state = RIGHT;
            dx = -dx;
            }*/

    }
        
    }


    public void paint(Graphics gr)
    {
        gr.drawImage(image,locx-app.vleft,locy,app);
    }
}