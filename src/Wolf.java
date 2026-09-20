import java.awt.*;
import java.util.List;

public class Wolf extends Animal {
    public Wolf(String name, float speed, float perception, int lifeSpan, Animal[] parents)
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
    protected void Reproduce(Animal partAnimal)
    {
        super.Reproduce(partAnimal);
       Board.babyAnimals.add(new Wolf(null, 1, 60, 5, new Animal[]{this, partAnimal}));
        
    }

}
