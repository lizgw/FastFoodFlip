import greenfoot.*;
import java.awt.Color;

public class Order extends Burger
{
    public Order()
    {
        // add the patty and set its cook values
        patty = new Patty(-1); // -1 b/c it doesn't need to interact with the grill
        patty.cooking = false; // stop it from cooking
        
        // add the buns
        toppings[0] = new Topping("buns");
        
        // 50% chance of having lettuce
        if (Greenfoot.getRandomNumber(2) > 0)
        {
            toppings[1] = new Topping("lettuce");
        }
        
        // 50% chance of having cheese
        if (Greenfoot.getRandomNumber(2) > 0)
        {
            toppings[2] = new Topping("cheese");
        }
    }
    
    // returns the number of points that the player gets for the burger
    public int matchOrderScore(Burger burger)
    {
        int score = 0; // start with 0 & add/subtract
        
        // make sure the burger exists first
        if (burger != null)
        {
            // does it have buns? +1/-1
            if (burger.toppings[0] != null)
            {
                score++;
            }
            else
            {
                score -= 3;
            }
            
            // does it have a patty? +1/-1
            if (burger.patty != null)
            {
                score++;
                // is the patty cooked perfectly? +1/-1
                if (burger.patty.avgCookStatus == 1)
                {
                    score++;
                }
                else
                {
                    score--;
                }
            }
            else
            {
                score -= 3;
            }
        
            
            
            // see if the toppings match
            for (int i = 1; i <= 2; i++)
            {
                // does the order have the extra topping?
                if (toppings[i] != null)
                {
                    // do the match? +1/-1
                    if (toppings[i].equals(burger.toppings[i]))
                    {
                        score++;
                    }
                    else
                    {
                        score--;
                    }
                }
                else // if the order doesn't have the topping on it
                {
                    // subtract points for extra toppings
                    if (burger.toppings[i] != null)
                    {
                        score--;
                    }
                }
            }
        }
        
        return score; // in whole dollars
    }
    
    // create an image for the order based on the toppings when it's added to the world
    protected void addedToWorld(World game)
    {
        // cast the world to the right type
        game = (Game) game;
        
        // add the buns
        game.addObject(toppings[0], getX(), getY());
        
        // add the patty (perfectly cooked side view)
        GreenfootImage pattyImage = new GreenfootImage(150, 15);
        pattyImage.setColor(new Color(99, 87, 71)); // perfecly cooked color
        pattyImage.fillRect(0, 0, 150, 15);
        patty.setImage(pattyImage);
        game.addObject(patty, getX(), getY());
        
        // add the lettuce if needed
        if (toppings[2] != null)
        {
            game.addObject(toppings[2], getX(), getY());
        }
        
        // add the cheese if needed
        if (toppings[1] != null)
        {
            game.addObject(toppings[1], getX(), getY());
        }
        
        // finally, set the image of the order background
        setImage("order.png");
    }
    
    public void removeSelf()
    {
        // get a ref to the game
        Game game = (Game) getWorld();
        
        // remove all the toppings
        for (int i = 0; i < toppings.length; i++)
        {
            game.removeObject(toppings[i]);
        }
        
        // remove the patty
        game.removeObject(patty);
        
        // remove order from the world
        game.removeObject(this);
        
        // remove order from the list
        game.orders.remove(0);
        
        // move all the orders over to account for the one that just disappeared
        game.updateOrderLocations();
    }
}
