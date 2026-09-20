
import java.awt.Color;

public class Grass extends Plant{
    public Grass()
    {
        super("Grass", Color.green);
        entityType = EntityType.grass;
        this.nutrition = 0.6f;
    }
}