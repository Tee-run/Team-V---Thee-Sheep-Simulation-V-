//Factory pattern: the one place that knows how to create each animal, so Board, Sheep and Wolf don't repeat it
public class EntityFactory {

    //first generation, no parents and a random starting age
    public static Sheep createSheep()
    {
        Genes genes = GenesBuilder.sheep().build();
        return new Sheep(genes, new Animal[]{null, null}, (int) (Math.random() * genes.GetLifespan() * 0.75));
    }

    public static Wolf createWolf()
    {
        Genes genes = GenesBuilder.wolf().build();
        return new Wolf(genes, new Animal[]{null, null}, (int) (Math.random() * genes.GetLifespan() * 0.20));
    }

    //babies get genes from both parents and start at age 0
    public static Sheep createSheep(Animal parentA, Animal parentB)
    {
        Genes babyGenes = new Genes(parentA.GetGenes(), parentB.GetGenes());
        return new Sheep(babyGenes, new Animal[]{parentA, parentB}, 0);
    }

    public static Wolf createWolf(Animal parentA, Animal parentB)
    {
        Genes babyGenes = new Genes(parentA.GetGenes(), parentB.GetGenes());
        return new Wolf(babyGenes, new Animal[]{parentA, parentB}, 0);
    }
}