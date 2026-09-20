
import java.awt.Color;
import java.awt.Graphics;



public abstract class Plant extends Entity {
    

    public Color pColour; 
    
    public Plant(){
        this.name = "Plant";
        this.pColour = Color.orange;
    }

    public Plant(String name, Color colour){
        this.name = name;
        this.pColour = colour;

    }

    @Override
    public void drawEntity(Graphics g) {
        g.setColor(pColour);
        g.fillOval(pos.getX(), pos.getY(), 40,40);
    }

}