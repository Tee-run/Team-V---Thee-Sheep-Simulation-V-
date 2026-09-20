import java.util.List;
public class Sheep extends Animal{


    public Sheep(String name, float speed, float perception, int lifeSpan, Animal[] parents)
    {
        super(name, speed, perception, lifeSpan, parents);
        nutrition = 0.5;
        entityType = EntityType.sheep;
        

    }   

    @Override
    public Entity LookForFood()
    {
        
    List<Entity> allFlowers = Board.entities.get(EntityType.flower.get());
    return findClosest(allFlowers).orElse(null);

    }
    

   

    @Override 
    protected void Reproduce(Animal partAnimal)
    {
        super.Reproduce(partAnimal);
       Board.babyAnimals.add(new Sheep(null, 1, 60, 5, new Animal[]{this, partAnimal}));
        
    }


}
