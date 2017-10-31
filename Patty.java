import greenfoot.*;

public class Patty extends Actor
{
    int index; // keeps track of which patty on the grill it is
    int[] cookStatus; // holds 2 ints that represent how cooked each side of the patty is
    int currentSide;
    int cookTimer;
    boolean cooking; // whether or not the patty should cook
    boolean draggable; // it should only be draggable if it's on the grill
    int avgCookStatus; //average cook value from both sides
    
    // initial coordinates before dragging
    private int initX;
    private int initY;
    
    public Patty(int pattyIndex)
    {
        index = pattyIndex;
        cookStatus = new int[]{-1, -1};
        currentSide = 0;
        cookTimer = 0;
        cooking = true;
        draggable = true;
        
        initX = -1;
        initY = -1;
    }
    
    public void act()
    {
        if (((Game) getWorld()).stillRunning)
        {
            if (cooking)
            {
                // increment the timer
                cookTimer++;
                
                // cook the patty every 4 seconds
                if (cookTimer >= (cookStatus[currentSide] + 4) * 60)
                {
                    cookMore();
                    cookTimer = 0;
                }
            }
            
            checkClick();
            if (draggable)
            {
                checkDrag();
            }
        }
    }
    
    // store the initial position once its added to the world
    protected void addedToWorld(World world)
    {
        initX = getX();
        initY = getY();
    }
    
    private void cookMore()
    {
        cookStatus[currentSide]++;
        updateImage();
    }
    
    private void checkClick()
    {
        if (Greenfoot.mouseClicked(this))
        {
            flipPatty();
        }
    }
    
    private void flipPatty()
    {
        if (currentSide == 0)
        {
            currentSide = 1;
        }
        else
        {
            currentSide = 0;
        }
        updateImage();
        // reset the timer b/c now it's a different side
        cookTimer = 0;
    }
    
    private void updateImage()
    {
        switch(cookStatus[currentSide])
        {
            case -1:
                // frozen
                setImage("patty-1.png");
                break;
            case 0:
                // thawed
                setImage("patty0.png");
                break;
            case 1:
                // cooked perfectly
                setImage("patty1.png");
                break;
            case 2:
                // burnt
                setImage("patty2.png");
                break;
            default:
                // anything higher than 2 is still burnt
                setImage("patty2.png");
                break;
        }
    }
    
    private void checkDrag()
    {
        // if the patty is currently being dragged
        if (Greenfoot.mouseDragged(this))
        {
            // move the patty along with the mouse
            setLocation(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
        }
        
        // if the drag just ended
        if (Greenfoot.mouseDragEnded(this))
        {
            // get a reference to the world & the table
            Game game = (Game) getWorld();
            PrepTable table = game.prepTable;
            
            // if it's on the table & there isn't a patty already
            if (pattyOnTable() && table.burger.patty == null)
            {
                // transfer the patty to the table
                game.transferPatty(this);
            }
            else
            {
                // if it's not on the table, return it to the grill
                setLocation(initX, initY);
            }
        }
    }
    
    // helper method to determine whether or not the patty is w/i the bounds of the table
    private boolean pattyOnTable()
    {
        PrepTable table = ((Game) this.getWorld()).prepTable;
        
        return getX() >= table.getX() - table.getImage().getWidth() / 2 &&
               getY() >= table.getY() - table.getImage().getHeight() / 2;
    }
}
