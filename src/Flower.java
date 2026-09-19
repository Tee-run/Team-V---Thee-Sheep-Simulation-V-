import java.awt.Color;

public class Flower extends Plant {

    Color fColour;
    public Flower()
    {
        float randomR = (float)Math.random() * 0.75f + 0.25f;
        float randomG = (float)Math.random() * 0.5f;
        float randomB = (float)Math.random() * 0.75f + 0.25f;

        fColour = new Color(randomR, randomG, randomB);
        this.name = "Flower";
        this.pColour = fColour;
        entityType = EntityType.flower;
    }
}
