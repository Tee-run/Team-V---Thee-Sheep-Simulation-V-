public enum EntityType {
    grass(0), flower(1), sheep(2), wolf(3);

    private final int index;

    private EntityType(int index) {
        this.index = index;
    }

    public int get() {
        return index;
    }
}