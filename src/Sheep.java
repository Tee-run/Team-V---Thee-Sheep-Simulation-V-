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
        //tempo just to allow for logic development. Replace with proper entity database at some point
        List<Entity> allFlowers = Board.entities.get(EntityType.flower.get());
    

        Entity closest = null;
        double closestDist = 99999;
        for (Entity flower : allFlowers) {

            double dist = this.pos.dist(flower.pos) ;
            if(dist < getPerception() && dist < closestDist)
            {
                closest = flower;
                closestDist = dist;
            }
        }
        if(closest != null)
        {
            //System.out.println("Closest flower @ " + closest.pos);
        }else{
            //System.out.println("No flowers in range");
        }
        return closest;


        
    }

    @Override 
    protected void Reproduce(Animal partAnimal)
    {
        super.Reproduce(partAnimal);
       Board.babyAnimals.add(new Sheep(null, 1, 60, 5, new Animal[]{this, partAnimal}));
        
    }


}
