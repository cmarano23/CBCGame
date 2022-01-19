class Pair {
    //Instance Variables
    public double x;
    public double y;

    //Constructor
    public Pair(double initX, double initY) {
        x = initX;
        y = initY;
    }

    //Method to add a value to the pair
    public Pair add(Pair toAdd) {
        return new Pair(x + toAdd.x, y + toAdd.y);
    }

    //Method to divide a value the pair
    public Pair divide(double denom) {
        return new Pair(x / denom, y / denom);
    }

    //Method to multiply a value times the pair
    public Pair times(double val) {
        return new Pair(x * val, y * val);
    }

    //Method to flip the x value of the pair
    public void flipX() {
        x = -x;
    }

    //Method to flip the y value of the pair
    public void flipY() {
        y = -y;
    }
}