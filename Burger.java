import greenfoot.*;
import java.util.*;

public class Burger extends Actor
{
    Patty patty;
    Topping[] toppings; // a list of toppings. 0 = buns, 1 = lettuce, 2 = cheese
    
    public Burger()
    {
        patty = null;
        toppings = new Topping[3];
        
        // use a blank image
        GreenfootImage img = new GreenfootImage(1, 1);
        setImage(img);
    }
}
