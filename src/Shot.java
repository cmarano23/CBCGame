import java.awt.*;

public class Shot {
    //Instance Variables
    Pair position;
    Pair velocity;
    double radius;
    double dampening;
    Color color;
    boolean isReleased = false;

    //Constructor
    public Shot() {
        position = new Pair(50, 700);
        velocity = new Pair(200, 0);
        radius = 10;
        dampening = 1.3;
        color = Color.white;
    }

    //Method to update the shot's location
    public void update(World w, double time, Player player) {
        if (!isReleased) {
            position = player.position;
        } else {
            position = position.add(velocity.times(time));
        }
        checkOutOfBounds(w, player);
    }

    //Method to set Velocity
    public void setVelocity(Pair v) {
        velocity = v;
    }

    //Method to draw the shot
    public void draw(Graphics g) {
        Color c = g.getColor();

        if(!isReleased) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(randomColor());
        }
        g.fillOval((int) (position.x - radius), (int) (position.y - radius), (int) (2 * radius), (int) (2 * radius));
        g.setColor(c);
    }

    //Checks to see if shot is out of bounds
    private void checkOutOfBounds(World w, Player player) {
        //Boolean OOB (Out Of Bounds) to keep track of if shot is out of bounds
        Boolean OOB = false;
        if (position.x - radius < 0) {
            OOB = true;
        } else if (position.x + radius > w.width) {
            OOB = true;
        }
        if (position.y - radius < 0) {
            OOB = true;
        } else if (position.y + radius > w.height) {
            OOB = true;
        }
        if (OOB) {
            position = player.getPosition();
            velocity = player.getVelocity();
            isReleased = false;
        }
    }

    //Set shot position
    public void setPosition(Pair p){
        position= p;
    }

    //Make the ball technicolor
    public Color randomColor(){
        double randColor = Math.random();
        if (randColor < 0.2){
            return Color.green;
        } else if (randColor >= 0.2 && randColor < 0.4){
            return Color.BLUE;
        } else if (randColor >= 0.4 && randColor < 0.6){
            return Color.orange;
        } else if (randColor >= 0.6 && randColor < 0.8){
            return Color.MAGENTA;
        } else{
            return Color.RED;
        }
    }
}
