import greenfoot.*;
import java.util.*;

public class PrepTable extends Actor
{
    Burger burger;
    Button bunsButton;
    Button lettuceButton;
    Button cheeseButton;
    Button checkButton;
    
    public PrepTable()
    {
        burger = new Burger();
        
        bunsButton = null;
        lettuceButton = null;
        cheeseButton = null;
        checkButton = null;
    }
    
    public void addTopping(String toppingType)
    {
        if (burger != null)
        {
            // get a reference to the game
            Game game = (Game) getWorld();
            
            int toppingNum = -1;
            if (toppingType.equals("buns"))
            {
                toppingNum = 0;
            }
            else if (toppingType.equals("lettuce"))
            {
                toppingNum = 1;
            }
            else if (toppingType.equals("cheese"))
            {
                toppingNum = 2;
            }
            
            if (burger.toppings[toppingNum] == null)
            {
                burger.toppings[toppingNum] = new Topping(toppingType);
                game.addObject(burger.toppings[toppingNum], getX(), getY());
            }
        }
    }
    
    public void emptyTable()
    {
        // get a reference to the game
        Game game = (Game) getWorld();
        
        // remove the toppings from the burger
        for (int i = 0; i < burger.toppings.length; i++)
        {
            game.removeObject(burger.toppings[i]);
        }
        
        // remove the patty
        game.removeObject(burger.patty);
        
        // remove the burger itself
        game.removeObject(game.prepTable.burger);
        game.prepTable.burger = null;
    }
    
    protected void addedToWorld(World world)
    {
        // get a reference to the game
        Game game = (Game) world;
        
        // add an empty burger obj
        game.addObject(burger, getX(), getY());
    }
}
