import java.awt.*;
import java.util.List;

public class Wolf extends Animal {
    public Wolf(String name, float speed, float perception, int lifeSpan, Animal[] parents)
    {
        super(name, speed, perception, lifeSpan, parents);
        this.colour = Color.gray;
        entityType = EntityType.wolf;

    }  

    @Override
    public Entity LookForFood() {
        List<Entity> allSheep = Board.entities.get(EntityType.sheep.get());

        Entity closest = null;

        double closestDist = 99999;
        for (Entity sheep : allSheep) {

            double dist = this.pos.dist(sheep.pos) ;
            if(dist < this.perception && dist < closestDist)
            {
                closest = sheep;
                closestDist = dist;
            }
        }

        if(closest != null)
        {
            // System.out.println("Closest sheep @ " + closest.pos);
        }else{
            // System.out.println("No sheep in range");
        }
        return closest;
    }
    
@Override 
    protected void Reproduce(Animal partAnimal)
    {
        super.Reproduce(partAnimal);
       Board.babyAnimals.add(new Wolf(null, 1, 60, 5, new Animal[]{this, partAnimal}));
        
    }

}
