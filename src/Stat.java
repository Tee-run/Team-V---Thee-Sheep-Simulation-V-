import java.util.stream.Collector;

public class Stat {
    public int numSheep;
    public int numWolves;
    public int numGrass;
    public int numFlowers;
    public int tic;
    public Traits sheepTraits;
    public Traits wolfTraits;

    public Stat(int numSheep, int numWolves, int numGrass, int numFlowers, int tic, Traits sheepTraits, Traits wolfTraits) {
        this.numSheep = numSheep;
        this.numWolves = numWolves;
        this.numGrass = numGrass;
        this.numFlowers = numFlowers;
        this.tic = tic;
        this.sheepTraits = sheepTraits;
        this.wolfTraits = wolfTraits;
    }


    public String toString() {
        return "[" + numSheep + ", " + numWolves + ", " + numGrass + ", " + numFlowers + ", " + tic + "]";
    }

    //average genes of a group of animals at one moment
    public static class Traits {
        public int count;
        public double speed;
        public double perception;

        public Traits(int count, double speed, double perception) {
            this.count = count;
            this.speed = speed;
            this.perception = perception;
        }

        public String toString() {
            return "[" + count + ", " + speed + ", " + perception + "]";
        }
    }

    //custom collector: each thread keeps its own totals which are added togeter at the end
    public static Collector<Animal, double[], Traits> averageTraits() {
        return Collector.of(
            () -> new double[3], //count, speed, perception
            (sums, animal) -> {
                sums[0] += 1;
                sums[1] += animal.GetGenes().GetSpeed();
                sums[2] += animal.GetGenes().GetPerception();
            },
            (a, b) -> {
                for (int i = 0; i < a.length; i++) {
                    a[i] += b[i];
                }
                return a;
            },
            sums -> {
                if (sums[0] == 0) {
                    return new Traits(0, 0, 0);
                }
                return new Traits((int) sums[0], sums[1] / sums[0], sums[2] / sums[0]);
            }
        );
    }
}
