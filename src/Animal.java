import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class Animal extends Entity{

    protected enum AnimalState
    {
        lookingForFood, 
        chasingFood,
        lookingForMate,
        chasingMate;
    }

    private static String[] nameList = {"Mary", "Franky", "Franklin", "Georgie", "Susan", "Laura", "Daisy", "Rose","Cody","Cuddles", "Bill", "Shaun", "Katie", "Brutus", "Scar", "Fido", "Jet", };


    protected double hunger = 0.5; //Range from 1.0 (Creature full), (0.0) Creature dies)

    public double GetHunger()
    {
        return hunger;
    }

    private double speed = 1.0; //Tiles moved

    private double perception = 100.0; //Distance in pixels that the creature can see

    private int age = 0; // Increase by 1 every generation

    private int lifeSpan = 5;//Every generation after this, flip coin to see if survives

    private Color colour = Color.white;

    protected static double hungerToReproduce = 0.75; //Will look for a mate if hunger above this level;

    private ArrayList<Animal> children = new ArrayList<Animal>();
    private Animal[] parents = new Animal[2];

    private Entity targetEntity = null;

    private Position randPos;

    protected AnimalState state = AnimalState.lookingForFood;

    public AnimalState GetState(){
        return state;
    }


    


    public Animal()
    {
        
    }

    public Animal(String name, double speed, double perception, int lifeSpan, Animal[] parents)
    {
        if(name != null)
        {
            this.name = name;
        }else{
            this.name = nameList[(int)(Math.random() * nameList.length)];
        }
        
        this.speed = speed;
        this.perception = perception;
        this.lifeSpan = lifeSpan;
        this.parents = parents;
        this.pos = new Position(0, 0);
        this.hunger = 0.6;
        if(parents[0] != null)
        {
            this.pos.setX(parents[0].pos.getX());
            this.pos.setY(parents[0].pos.getY());
        }
        this.randPos = Position.genRand(Board.bWidth, Board.bHeight, 0, 100, 40);
    }
    public double getSpeed() { return speed; }
    public double getPerception() { return perception; }
    public int getAge() { return age; }
    public int getLifeSpan() { return lifeSpan; }
    public Color getColour() { return colour; }
    protected void setColour(Color colour) { this.colour = colour; }

    //Moves animal towards destination, returns true if it is at the destination already with buffer.
        public boolean Move(Position destination, int buffer)
    {
        Position direction = pos.dir(destination);
        double dx = direction.getExactX();
        double dy = direction.getExactY();
        if(Math.abs(dx) <= buffer && Math.abs(dy) <= buffer){return true;}
        if(Math.abs(dx) > Math.abs(dy))
        {
            pos.setX(pos.getExactX() + speed * Math.signum(dx));
        }else{
            pos.setY(pos.getExactY() + speed * Math.signum(dy));
        }
        return false;
    }

    int wait = 0;
    public void AnimalBehaviour()
    {
        if(!IsAlive()) return;
        hunger -= 0.0001;
        if(hunger < 0)
        {
            System.out.println(name + " Starved");
            hunger = 0;
            KillEntity();
            return;
        }
        
        switch(state){
            case AnimalState.lookingForFood:
            {
                LookingForFood();
                break;
            }
            case AnimalState.chasingFood:
            {
                ChasingFood();
                break;
            }case AnimalState.lookingForMate:
            {
                LookingForMate();
                break;
            }case AnimalState.chasingMate:
            {
                ChasingMate();
                break;
            }

        }     
    }

    protected void LookingForFood()
    {
        if(targetEntity == null || !targetEntity.IsAlive())
        {
            this.targetEntity = LookForFood();
            if(this.targetEntity != null)
            {
                state = AnimalState.chasingFood;
            }else
            {
                RoamRandomly();
            
            }
        
        }else{
            targetEntity = null;
            RoamRandomly();
        }

    }

    protected void ChasingFood()
    {
        //NEED TO FIX THIS AS, WHILST TECHINICALLY IT SHOULD NOT CRASH, SHEEP WILL HUNT INVISIBLE FLOWERS
        if(targetEntity == null || !targetEntity.IsAlive())
        {
           targetEntity = null;
            state = AnimalState.lookingForFood;
            return;
        }
        if(Move(targetEntity.pos, 10))
        {
            EatFood(targetEntity);
            if(hunger >= hungerToReproduce)
            {
                state = AnimalState.lookingForMate;
                //state = AnimalState.lookingForFood;
            }else{
                state = AnimalState.lookingForFood;
            }
        }
    }

    public void AttractMate(Entity ent)
    {
        targetEntity = ent;
        state = AnimalState.chasingMate;
    }
    
        protected void LookingForMate()
    {
        List<Entity> sameType = Board.entities.get(entityType.get());
        Optional<Entity> mate = findClosest(sameType, e -> ((Animal) e).GetState() == AnimalState.lookingForMate);

        if(mate.isPresent())
        {
            Animal partner = (Animal) mate.get();
            partner.AttractMate(this);
            targetEntity = partner;
            state = AnimalState.chasingMate;
        }else if(hunger < hungerToReproduce)
        {
            System.out.println(name + " is now to hungry for love :(");
            state = AnimalState.lookingForFood;
        }else
        {
            RoamRandomly();
        }
    }

    protected void RoamRandomly()
    {
        boolean moved = Move(randPos, 10);
            if (moved && (wait == 0 || hunger < 0.25))
            {   
                wait = 100;
                randPos = Position.genRand(Board.bWidth, Board.bHeight, 0, 100, 40);
            }
            else if (moved) {
                wait--;
            }
    }
    protected void ChasingMate()
{
    if(hunger < hungerToReproduce)
    {
        state = AnimalState.lookingForFood;
        targetEntity = null;
        return;
    }
    if(targetEntity == null || !targetEntity.IsAlive())
    {
        targetEntity = null;
        state = AnimalState.lookingForMate;
        return;
    }
    if(Move(targetEntity.pos, 10))
    {
        System.out.println("reproducing");
        Reproduce((Animal)targetEntity);
        System.out.println("baby Created");
        state = AnimalState.lookingForFood;
    }
}

    
        protected void Reproduce(Animal partner)
    {
        targetEntity = null;

        hunger = hunger - 0.4f;
        partner.hunger = partner.hunger - 0.4f;
        partner.targetEntity = null;

        double childSpeed = Genetics.inherit(speed, partner.speed, Genetics.MIN_SPEED, Genetics.MAX_SPEED);
        double childPerception = Genetics.inherit(perception, partner.perception, Genetics.MIN_PERCEPTION, Genetics.MAX_PERCEPTION);

        System.out.println(String.format("Baby born with speed %.2f and perception %.1f", childSpeed, childPerception));
        Board.babyAnimals.add(createChild(childSpeed, childPerception, lifeSpan, new Animal[]{this, partner}));
    }

    // Each species builds its own kind of child Animal decides the traits and the subclass decides the type
    protected abstract Animal createChild(double speed, double perception, int lifeSpan, Animal[] parents);


    // Generic search T can be any entity subtype, and the filter decides which candidates count
    protected <T extends Entity> Optional<T> findClosest(List<T> candidates, Predicate<T> filter)
{
    T closest = null;
    double closestDist = Double.MAX_VALUE;
    for (int i = 0; i < candidates.size(); i++)
    {
        T candidate = candidates.get(i);
        if (candidate == null || candidate == this || !candidate.IsAlive())
        {
            continue;
        }
        if (!filter.test(candidate))
        {
            continue;
        }
        double dist = pos.dist(candidate.pos);
        if (dist < perception && dist < closestDist)
        {
            closest = candidate;
            closestDist = dist;
        }
    }
    return Optional.ofNullable(closest);
}

    // Convenience version that accepts every candidate
    protected <T extends Entity> Optional<T> findClosest(List<T> candidates)
    {
    return findClosest(candidates, c -> true);
    }

    public abstract Entity LookForFood();

    
    public void EatFood(Entity entity)
    {
       
        hunger += entity.nutrition;
        
        if(hunger > 1.0)
        {
            hunger = 1.0;
        }
        System.out.println("Creature "+ name + " ate entity worth "+ entity.nutrition + " nutrition. Total hunger now: " + hunger);
        entity.KillEntity();
        targetEntity = null;

    }

    @Override
    public void drawEntity(Graphics g) {
        g.setColor(colour);
        g.fillRect(pos.getX(), pos.getY(), 40,40);

        


        g.setColor(Color.RED);
        g.drawString(name.toString(), pos.getX() + 5, pos.getY() + 5);
        g.setColor(Color.green);
        double rounded = Math.round(hunger * 100.0) / 100.0;

        g.drawString(Double.toString(rounded), pos.getX() + 5, pos.getY() + 20);

        g.setColor(Color.BLUE);
        g.drawString(state.toString(), pos.getX() + 5, pos.getY() + 35);
    }
}
