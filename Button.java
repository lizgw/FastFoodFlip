import greenfoot.*;

public class Button extends Actor
{
    /*
     * What the button does.
     * 0 - buns buton
     * 1 - lettuce button
     * 2 - cheese button
     * 3 - checkmark button
     * 4 - play btn
     * 5 - instructions btn
     * 6 - back button
     * 7 - play again button
     */
    int purpose;
    
    public Button(int btnPurpose)
    {
        purpose = btnPurpose;
        buttonInit();
    }
    
    public void act() 
    {
        if (Greenfoot.mouseClicked(this))
        {
            btnClick();
        }
    }
    
    private void btnClick()
    {
        // things in Game don't exist in the MainMenu so only use these vars if needed
        Game game = null;
        PrepTable table = null;
        
        if (purpose < 4)
        {
            // get a reference to the game & the prepTable
            game = (Game) getWorld();
            table = game.prepTable;
        }
        
        switch(purpose)
        {
            case 0:
                // buns button
                if (game.stillRunning)
                {
                    table.addTopping("buns");
                }
                break;
            case 1:
                // lettuce button
                if (game.stillRunning)
                {
                    table.addTopping("lettuce");
                }
                break;
            case 2:
                // cheese button
                if (game.stillRunning)
                {
                    table.addTopping("cheese");
                }
                break;
            case 3:
                // checkmark button:
                if (game.stillRunning)
                {
                    checkmarkButton();
                }
                break;
            case 4: // fall through
            case 7: 
                // play button
                Greenfoot.setWorld(new Game());
                break;
            case 5:
                // instructions button
                Greenfoot.setWorld(new InstructionsMenu());
                break;
            case 6:
                // back button
                Greenfoot.setWorld(new MainMenu());
                break;
            default:
                System.out.println("Pick a different purpose.");
                break;
        }
    }
    
    private void buttonInit()
    {
        // pick the right image
        switch(purpose)
        {
            case 0:
                // buns button
                setImage("btn-buns.png");
                break;
            case 1:
                // lettuce button
                setImage("btn-lettuce.png");
                break;
            case 2:
                // cheese button
                setImage("btn-cheese.png");
                break;
            case 3:
                // checkmark button
                setImage("checkmark.png");
                break;
            case 4:
                // play button
                setImage("btn-play.png");
                break;
            case 5:
                // instructions button
                setImage("btn-instructions.png");
                break;
            case 6:
                // back button
                setImage("btn-back.png");
                break;
            case 7:
                // play again button
                setImage("btn-playAgain.png");
                break;
            default:
                System.out.println("Pick a different purpose.");
                break;
        }
    }
    
    private void checkmarkButton()
    {
        // get a reference to the game
        Game game = (Game) getWorld();
        
        // get the current order if there is one
        if (game.orders.size() > 0)
        {
            Order currentOrder = game.orders.get(0);
            Burger currentBurger = game.prepTable.burger;
            
            // if the burger isn't empty
            if (currentBurger.patty != null || currentBurger.toppings[0] != null ||
                currentBurger.toppings[1] != null || currentBurger.toppings[2] != null)
            {
                // check accuracy to determine the number of points to add
                int pointsToAdd = currentOrder.matchOrderScore(currentBurger);
                game.score += pointsToAdd;
                
                // separate the negative sign so it can be left of the dollar sign
                String negSign = "";
                if (game.score < 0)
                {
                    negSign = "-";
                }
                
                // update the scoreText
                game.scoreText.updateText(negSign + "$" + Math.abs(game.score));
                
                // remove everything from the prepTable
                game.prepTable.emptyTable();
                
                // remove the current/first order & update the order locations
                game.orders.get(0).removeSelf();
                
                // add a new empty burger to the prepTable
                game.prepTable.burger = new Burger();
                game.addObject(game.prepTable.burger, game.prepTable.getX(), game.prepTable.getY());
            }
        }
    }
}
