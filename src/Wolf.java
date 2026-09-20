import java.awt.*;
import java.util.List;

public class Wolf extends Animal {
    public Wolf(String name, double speed, double perception, int lifeSpan, Animal[] parents)
    {
        super(name, speed, perception, lifeSpan, parents);
        setColour(Color.gray);
        entityType = EntityType.wolf;

    }  

    @Override
    public Entity LookForFood()
    {

    List<Entity> allSheep = Board.entities.get(EntityType.sheep.get());
    return findClosest(allSheep).orElse(null);

    }
    
    @Override
    protected Animal createChild(double speed, double perception, int lifeSpan, Animal[] parents)
    {
        return new Wolf(null, speed, perception, lifeSpan, parents);
    }

}
