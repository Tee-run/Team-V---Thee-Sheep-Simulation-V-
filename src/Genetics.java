public class Genetics {
    public static final double MIN_SPEED = 0.5;
    public static final double MAX_SPEED = 3.0;
    public static final double MIN_PERCEPTION = 30.0;
    public static final double MAX_PERCEPTION = 250.0;

    // Chance that any one trait mutates when a baby is born
    private static final double MUTATION_CHANCE = 0.2;
    // Biggest change a mutation can make, as a fraction of the trait value
    private static final double MUTATION_STRENGTH = 0.25;

    // Only static helpers live here, so nobody should create a Genetics object
    private Genetics() { }

    // The child copies this trait from one parent at random, may mutate it, then stays inside min and max
    public static double inherit(double parentA, double parentB, double min, double max)
    {
        double value = parentA;
        if (Math.random() >= 0.5)
        {
            value = parentB;
        }
        if (Math.random() < MUTATION_CHANCE)
        {
            double change = (Math.random() * 2 - 1) * MUTATION_STRENGTH;
            value = value * (1 + change);
        }
        return Math.max(min, Math.min(max, value));
    }
}
