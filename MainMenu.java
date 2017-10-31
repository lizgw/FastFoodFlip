import greenfoot.*;

public class MainMenu extends World
{
    Text titleText;
    
    Button playBtn;
    Button howToPlayBtn;

    public MainMenu()
    {    
        // create a new world with 800x600 cells with a cell size of 1x1 pixels.
        super(800, 600, 1);
        
        titleText = new Text("Fast Food Flip");
        addObject(titleText, getWidth() / 2, 100);
        
        playBtn = new Button(4);
        addObject(playBtn, getWidth() / 2, 300);
        
        howToPlayBtn = new Button(5);
        addObject(howToPlayBtn, getWidth() / 2, 500);
    }
}
