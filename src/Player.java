import java.awt.*;
import java.util.Random;

public class Player {
    //Instance Variables
    Pair position;
    Pair velocity;
    Pair acceleration;
    double radius;
    double dampening;

    //Constructor
    public Player() {
        position = new Pair(0, 700);
        velocity = new Pair(200, 0);
        acceleration = new Pair(0.0, 0.0);
        radius = 25;
        dampening = 1.3;
    }

    //Method to update the player's location
    public void update(World w, double time){
        position = position.add(velocity.times(time));
        bounce(w);
    }

    //Method to set the velocity of the player
    public void setVelocity(Pair v){
        velocity = v;
    }

    //Method to draw the player
    public void draw(Graphics g){
        Color c = g.getColor();
        //Head
        g.setColor(Color.WHITE);
        g.fillOval((int)(position.x - radius), (int)(position.y - radius), (int)(2*radius), (int)(2*radius));
        //Eyes
        g.setColor(Color.black);
        g.fillOval((int)(position.x - 15), (int)(position.y - 15), 10, 10);
        g.fillOval((int)(position.x + 5), (int)(position.y - 15), 10, 10);
        //mouth
        g.fillArc((int) position.x - 10, (int) position.y + 5, 20, 10, 0, -180);
    }

    //Method to bounce the player off of the walls
    private void bounce(World w){
        Boolean bounced = false;
        if (position.x - radius < 0){
            velocity.flipX();
            position.x = radius;
            bounced = true;
        }
        else if (position.x + radius > w.width){
            velocity.flipX();
            position.x = w.width - radius;
            bounced = true;
        }
        if (bounced){
            velocity = velocity.divide(dampening);
        }
    }

    //Method to get the player's current position
    public Pair getPosition() {
        return position;
    }

    //Method to get the player's current velocity
    public Pair getVelocity(){
        return velocity;
    }
}
