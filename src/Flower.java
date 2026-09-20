import java.awt.Color;

public class Flower extends Plant {

    public Flower()
    {
        float randomR = (float)Math.random() * 0.75f + 0.25f;
        float randomG = (float)Math.random() * 0.5f;
        float randomB = (float)Math.random() * 0.75f + 0.25f;

        this.name = "Flower";
        this.pColour = new Color(randomR, randomG, randomB);
        entityType = EntityType.flower;
    }
}
