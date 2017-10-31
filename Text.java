import greenfoot.*;
import java.awt.*;

public class Text extends Actor
{
    String text;
    int size;
    Color color;
    
    public Text(String txt)
    {
        text = txt;
        size = 52;
        color = Color.BLACK;
        
        // create the text image (msg, size, foreground, background)
        GreenfootImage g = new GreenfootImage(text, size, color, null);
        setImage(g);
    }
    
    public Text(String txt, Color col)
    {
        text = txt;
        size = 52;
        color = col;
        
        // create the text image (msg, size, foreground, background)
        GreenfootImage g = new GreenfootImage(text, size, color, null);
        setImage(g);
    }
    
    public Text(String txt, int fontSize)
    {
        text = txt;
        size = fontSize;
        color = Color.BLACK;
        
        // create the text image (msg, size, foreground, background)
        GreenfootImage g = new GreenfootImage(text, size, color, null);
        setImage(g);
    }
    
    // call to update the text to match the field
    public void updateText()
    {
        GreenfootImage g = new GreenfootImage(text, size, color, null);
        setImage(g);
    }
    
    public void updateText(String newString)
    {
        text = newString;
        
        GreenfootImage g = new GreenfootImage(text, size, color, null);
        setImage(g);
    }
}
