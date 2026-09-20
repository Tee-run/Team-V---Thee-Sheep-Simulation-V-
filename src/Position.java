public class Position {
    private double x;
    private double y;

    public Position (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Position genRand (int width, int height, int bufferX, int bufferY, int boxSize) {
        if (boxSize <= 0) {
            throw new IllegalArgumentException("boxSize must be positive but was " + boxSize);
        }
        if (width < boxSize || height < boxSize) {
            throw new IllegalArgumentException("Area " + width + "x" + height + " is smaller than one box of size " + boxSize);
        }
        int x = (int)(Math.random() * (width / boxSize)) * boxSize + bufferX;
        int y = (int)(Math.random() * (height / boxSize)) * boxSize + bufferY;
        return new Position(x, y);
    }

    // Whole pixel values, used for drawing and board checks
    public int getX() {
        return (int) Math.round(x);
    }

    public int getY() {
        return (int) Math.round(y);
    }

    // Exact values, used so slow speeds still move smoothly
    public double getExactX() {
        return x;
    }

    public double getExactY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double dist(Position other) {
        double xDist = this.x - other.x;
        double yDist = this.y - other.y;
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }

    //returns a vector showing how far away the target is horizontally and vertically
    public Position dir(Position target)
    {
        return new Position(target.x - this.x, target.y - this.y);
    }

    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}