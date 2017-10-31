import greenfoot.*;

public class Topping extends Actor
{
    String type;
    
    public Topping(String toppingType)
    {
        type = toppingType;
        pickImage(type);
    }
    
    private void pickImage(String type)
    {
        if (type.equals("buns"))
        {
            setImage("buns.png");
        }
        else if (type.equals("cheese"))
        {
            setImage("cheese.png");
        }
        else if (type.equals("lettuce"))
        {
            setImage("lettuce.png");
        }
    }
    
    public boolean equals(Topping other)
    {
        if (other != null && type.equals(other.type))
        {
            return true;
        }
        return false;
    }
}
