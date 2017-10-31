import greenfoot.*;
import java.util.*;

public class Grill extends Actor
{
    Patty[] patties;
    
    public Grill()
    {
        patties = new Patty[4];
    }
    
    public void act() 
    {
        checkClick();
    }
    
    private void checkClick()
    {
        // add a patty if the grill is clicked
        if (Greenfoot.mouseClicked(this))
        {
            addPatty();
        }        
    }
    
    private void addPatty() 
    {
        // get a reference to the current world
        Game game = (Game) getWorld();
        
        if (game.stillRunning)
        {
            // find the next open grill space
            int pattyIndex = findNextGrillSpace();
            
            // if there's an open space, create & add the patty
            if (pattyIndex != -1)
            {
                Patty pat = new Patty(pattyIndex);
                patties[pattyIndex] = pat;
                game.addObject(pat, findPattyX(pattyIndex), findPattyY(pattyIndex));
            }
        }
    }
    
    private int findNextGrillSpace()
    {
        for (int currentSpace = 0; currentSpace < patties.length; currentSpace++)
        {
            if (patties[currentSpace] == null)
            {
                return currentSpace;
            }
        }
        
        // return -1 if there's no open spaces on the grill
        return -1;
    }
    
    /* these 2 methods find coordinates for the patty based on the index
     * 0 - (100, 300)
     * 1 - (300, 300)
     * 2 - (100, 500)
     * 3 - (300, 500)
     */
    
    private int findPattyX(int index)
    {
        switch(index)
        {
            case 0: // fall through
            case 2:
                return 100;
            case 1: // fall through
            case 3:
                return 300;
        }
        
        return 0; // if this happens there's been an error!
    }
    
    private int findPattyY(int index)
    {
        switch(index)
        {
            case 0: // fall through
            case 1:
                return 300;
            case 2: // fall through
            case 3:
                return 500;
        }
        
        return 0; // this shouldn't happen either
    }
}
