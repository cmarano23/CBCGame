import java.awt.*;
import java.util.Random;

public class Ball {
    //Instance Variables
    Pair position;
    Pair velocity;
    Pair acceleration;
    double radius;
    double dampening;
    boolean splitBall = false;

    //Constructor
    public Ball() {
        Random rand = new Random(new Random().nextInt(100));
        position = new Pair(500.0, 100.0);
        velocity = new Pair((rand.nextInt(1000) - 500), rand.nextInt(1000) - 500);
        acceleration = new Pair(0.0, 25.0);
        radius = 30;
        dampening = 1;
    }

    //Method to update the player's location
    public void update(World w, double time) {
        position = position.add(velocity.times(time));
        velocity = velocity.add(acceleration.times(time));
        bounce(w);
    }

    //Method to set the position of the ball
    public void setPosition(Pair p) {
        position = p;
    }

    //Method to get the radius of the ball
    public double getRadius(){
        return radius;
    }

    //Method to set the radius of the ball
    public void setRadius(double r){
        radius= r;
    }

    //Method to set the position of the ball
    public Pair getPosition() {
        return position;
    }

    //Method to set the velocity of the ball
    public void setVelocity(Pair v) {
        velocity = v;
    }

    //Method to set the acceleration of the ball
    public void setAcceleration(Pair a) {
        acceleration = a;
    }

    //Method to draw the ball
    public void draw(Graphics g, Color color) {
        Color c = g.getColor();
        g.setColor(color);
        g.fillOval((int)(position.x - radius), (int)(position.y - radius), (int)(2 * radius), (int)(2 * radius));
        g.setColor(c);
    }

    //Method to bounce the ball off of the walls
    private void bounce(World w) {
        Boolean bounced = false;
        if(position.x - radius < 0) {
            velocity.flipX();
            position.x = radius;
            bounced = true;
        }
        else if (position.x + radius > w.width) {
            velocity.flipX();
            position.x = w.width - radius;
            bounced = true;
        }
        if (position.y - radius < 0) {
            velocity.flipY();
            position.y = radius;
            bounced = true;
        }
        else if (position.y + radius > w.height) {
            velocity.flipY();
            position.y = w.height - radius;
            bounced = true;
        }
        if (bounced) {
            velocity = velocity.divide(dampening);
        }
    }
}
