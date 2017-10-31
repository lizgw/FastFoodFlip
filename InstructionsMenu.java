import greenfoot.*;

public class InstructionsMenu extends World
{
    Button backBtn;
    
    public InstructionsMenu()
    {    
        // Create a new world with 800x600 cells with a cell size of 1x1 pixels.
        super(800, 600, 1);
        
        backBtn = new Button(6);
        addObject(backBtn, 600, 500);
    }
}
