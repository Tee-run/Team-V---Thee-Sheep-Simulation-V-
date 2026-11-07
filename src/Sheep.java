
import java.awt.*;
import java.io.IOException;
import java.util.Comparator;

import javax.imageio.ImageIO;

public class Sheep extends Animal {

    public Sheep(Genes genes, Animal[] parents, int age)
    {
        super(genes, parents, age);
        nutrition = 0.5;
        entityType = Board.Ent.sheep;
        this.sprite = getSheepImage();
        sprintOnFlee = true;
    }

    @Override
    public Entity LookForFood()
    {
        // Flowers are preferred over grass.
        Entity closestFlower = findClosest(Board.entities.get(Board.Ent.flower.get()));
        if (closestFlower != null)
        {
            return closestFlower;
        }

        // If no flower is nearby, search for grass instead.
        return findClosest(Board.entities.get(Board.Ent.grass.get()));
    }

    @Override
    protected void Reproduce(Animal partAnimal)
    {
        System.out.println("Making baby sheep");

        Board.babyAnimals.add(EntityFactory.createSheep(this, partAnimal));

        super.Reproduce(partAnimal);
    }

    public static Image getSheepImage() {
        try {
            return ImageIO.read(Sheep.class.getResource("/images/sheep.png"));
        } catch(IOException | IllegalArgumentException e) {
            return null;
        }
    }
}

