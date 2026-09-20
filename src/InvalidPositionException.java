public class InvalidPositionException extends Exception {
    private final Position position;

    public InvalidPositionException(String message, Position position) {
        super(message + " at " + position);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
