
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
        spriteFaceRight = false;
        facingRight = false;
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
    //how far a panicking sheep can warn the others and how likely each one is to join in
    private static double herdPanicRange = 300;
    private static double herdPanicChance = 0.8;

    @Override
    public void AlertAnimal(Animal hunter)
    {
        boolean wasFleeing = state == AnimalState.fleeing;
        super.AlertAnimal(hunter);

        //a shep that has just started running spreads the panic to the sheep near it
        if(!wasFleeing && state == AnimalState.fleeing)
        {
            SpreadPanic(hunter);
        }
    }

    private void SpreadPanic(Animal hunter)
    {
        Board.entities.get(Board.Ent.sheep.get()).stream()
            .filter(Entity::IsAlive)
            .filter(other -> other != this)
            .filter(other -> pos.dist(other.pos) < herdPanicRange)
            .forEach(other -> ((Sheep) other).JoinPanic(hunter));
    }

    //sheep starts fleeing too, and passes the panic on to the sheep near it
    private void JoinPanic(Animal hunter)
    {
        if(state == AnimalState.fleeing || Math.random() > herdPanicChance)
        {
            return;
        }
        state = AnimalState.fleeing;
        chasingAnimal = hunter;
        SpreadPanic(hunter);
    }

    public static Image getSheepImage() {
        try {
            return ImageIO.read(Sheep.class.getResource("/images/sheep.png"));
        } catch(IOException | IllegalArgumentException e) {
            return null;
        }
    }
}
