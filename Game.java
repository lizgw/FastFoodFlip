import greenfoot.*;
import java.awt.Color;
import java.util.*;

public class Game extends World
{
    Grill grill;
    PrepTable prepTable;
    Text timerText;
    Text scoreText;
    
    // timer stuff
    boolean stillRunning;
    int frameRate;
    int timeLeft; // in seconds
    int frameCounter;
    
    int score; // the total score in (whole) $
    
    ArrayList<Order> orders;
    int totalSecondsElapsed; // used for calculating how often orders show up
    int orderTimer; // number of frames before the next order
    int orderVariance; // the number of frames that the order delay can vary (+/-)
    
    public Game()
    {    
        // Create a new world with 800x600 cells with a cell size of 1x1 pixels.
        super(800, 600, 1);
        
        // add grill obj on left
        grill = new Grill();
        addObject(grill, 200, 400);
        
        // add table on right
        prepTable = new PrepTable();
        addObject(prepTable, 600, 400);
        
        // add all the table buttons
        addTableButtons();
        
        // add timer text
        timerText = new Text("2:00");
        addObject(timerText, 60, 30);
        
        // setup the timer
        frameRate = 60;
        timeLeft = frameRate * 2;
        
        // setup the points
        score = 0;
        scoreText = new Text("$0");
        addObject(scoreText, 50, 80);
        
        // setup orders
        orders = new ArrayList<Order>();
        orderTimer = frameRate * 4; // start with a 4 sec delay before the first order
        
        // the game is still updating
        stillRunning = true;
        
        // fix layering order
        setPaintOrder(Text.class, Topping.class, Button.class, Patty.class, Order.class);
        
        // set order variance
        orderVariance = 2 * frameRate;
        
        // init seconds counter
        totalSecondsElapsed = 0;
    }
    
    public void act()
    {
        if (stillRunning)
        {
            updateTimer();
            handleOrders();
        }
    }
    
    // handles the movement of the patty from grill to prep table
    public void transferPatty(Patty p)
    {
        if (stillRunning)
        {
            // stop cooking it
            p.cooking = false;
            p.draggable = false;
            
            // change the img to a 150x15 rectangle of the right color (side view)
            GreenfootImage newPattyImage = new GreenfootImage(150, 15);
            newPattyImage.setColor(pickPattyColor(p));
            newPattyImage.fillRect(0, 0, 150, 15);
            p.setImage(newPattyImage);
            
            // put it in the right place
            p.setLocation(prepTable.burger.getX(), prepTable.burger.getY());
            
            // remove it from the grill
            grill.patties[p.index] = null;
            
            // put it on the table
            prepTable.burger.patty = p;
        }
    }
    
    // picks the color for the patty based on how cooked it is
    private Color pickPattyColor(Patty p)
    {
        // the color to return
        Color c;
        // an average of the cook values for both sides
        p.avgCookStatus = Math.round((p.cookStatus[0] + p.cookStatus[1]) / 2);
        
        switch(p.avgCookStatus)
        {
            case -1:
                c = new Color(239, 177, 171); // uncooked
                break;
            case 0:
                c = new Color(201, 177, 147); // thawed
                break;
            case 1:
                c = new Color(99, 87, 71); // perfect
                break;
            case 2: // fall through
            default:
                c = new Color(9, 7, 6); // burnt
                break;
        }
        
        return c;
    }
    
    public void addTableButtons()
    {
        prepTable.bunsButton = new Button(0);
        addObject(prepTable.bunsButton, 466, 250);
        
        prepTable.lettuceButton = new Button(1);
        addObject(prepTable.lettuceButton, 600, 250);
        
        prepTable.cheeseButton = new Button(2);
        addObject(prepTable.cheeseButton, 732, 250);
        
        prepTable.checkButton = new Button(3);
        addObject(prepTable.checkButton, 740, 540);
    }
    
    private void updateTimer()
    {
        // don't go into negative time
        if (timeLeft > 0)
        {
            frameCounter++;
            
            // if a second has passed
            if (frameCounter >= frameRate)
            {
                // reset the frame counter
                frameCounter = 0;
                
                // update the secondsElapsed var
                totalSecondsElapsed++;
                
                // subtract a second from the timer
                timeLeft--;
                
                // change the time into mins & seconds
                int minsLeft = timeLeft / 60;
                int secondsLeft = timeLeft % 60;
                
                // update the time display (min:sec)
                String timerDisplay = minsLeft + ":" + getLeadingZero(secondsLeft, 10) + secondsLeft;
                timerText.text = timerDisplay;
                timerText.updateText();
            }
        }
        else
        {
            endGame();
        }
    }
    
    // controls how often an order shows up
    private void handleOrders()
    {
        // only 4 orders can fit on the screen, so don't do anything if there's already 4
        if (orders.size() < 4)
        {
            orderTimer--;
            
            if (orderTimer <= 0)
            {
                // create a new order
                Order nextOrder = new Order();
                orders.add(nextOrder);
                
                // find the position
                int xPos = 170 * orders.size() + 70;
                addObject(nextOrder, xPos, 100);
                
                // reset the order timer to a new value (between base time - variance & base time + variance)
                int baseFrameDelay = calcOrderDelay();
                int maxDelay = baseFrameDelay + orderVariance;
                int minDelay = baseFrameDelay - orderVariance;
                orderTimer = Greenfoot.getRandomNumber(maxDelay - minDelay) + minDelay;
            }
        }
    }
    
    public void updateOrderLocations()
    {
        for (int i = 0; i < orders.size(); i++)
        {
            Order currentOrder = orders.get(i);
            
            // update the location of the order itself
            int newX = 170 * (i + 1) + 70;
            currentOrder.setLocation(newX, 100);
            
            // update the location of all the toppings
            for (int j = 0; j < currentOrder.toppings.length; j++)
            {
                if (currentOrder.toppings[j] != null)
                {
                    currentOrder.toppings[j].setLocation(currentOrder.getX(), currentOrder.getY());
                }
            }
            
            // update the location of the patty
            currentOrder.patty.setLocation(currentOrder.getX(), currentOrder.getY());
        }
    }
    
    private void endGame()
    {
        Text gameOverText = new Text("Game over! You earned " + scoreText.text, new Color(200, 200, 200));
        addObject(gameOverText, getWidth() / 2, getHeight() / 2);
        
        Button playAgainBtn = new Button(7);
        addObject(playAgainBtn, getWidth() / 2, getHeight() / 2 + 200);
        
        // stop all the timers
        stillRunning = false;
    }
    
    // add an extra zero if a value needs it (ex: 1:03 vs 1:3)
    public String getLeadingZero(double variable, double value)
    {
        String extraZero = "";
        if (variable < value)
        {
            extraZero = "0";
        }
        return extraZero;
    }
    
    // figure out how long to wait between each order in frames
    private int calcOrderDelay()
    {
        int frameDelay = 0;
        if (totalSecondsElapsed >= 120)
        {
            frameDelay = 2;
        }
        else if (totalSecondsElapsed >= 90)
        {
            frameDelay = 3;
        }
        else if (totalSecondsElapsed >= 60)
        {
            frameDelay = 4;
        }
        else if (totalSecondsElapsed >= 45)
        {
            frameDelay = 5;
        }
        else if (totalSecondsElapsed >= 30)
        {
            frameDelay = 6;
        }
        else if (totalSecondsElapsed >= 15)
        {
            frameDelay = 7;
        }
        else if (totalSecondsElapsed >= 0)
        {
            frameDelay = 8;
        }
        
        return frameDelay * 60;
    }
}
