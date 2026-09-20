import java.util.List;
public class Sheep extends Animal{


        public Sheep(String name, double speed, double perception, int lifeSpan, Animal[] parents)
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
    protected Animal createChild(double speed, double perception, int lifeSpan, Animal[] parents)
    {
        return new Sheep(null, speed, perception, lifeSpan, parents);
    }


}
