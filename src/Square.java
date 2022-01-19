import java.awt.*;
import java.util.Random;

public class Square {
    //Instance Variables
    Pair position;
    Pair velocity;
    Pair acceleration;
    double width;
    double dampening;
    Color color;

    //Constructor
    public Square() {
        Random rand = new Random(new Random().nextInt(100));
        position = new Pair(500.0, 100.0);
        velocity = new Pair((rand.nextInt(1000) - 500), rand.nextInt(1000) - 500);
        acceleration = new Pair(0.0, 25.0);
        width = 15;
        dampening = 1.3;
        color = new Color(255, 0, 255);
    }

    //Method to update the square's location
    public void update(World w, double time) {
        position = position.add(velocity.times(time));
        velocity = velocity.add(acceleration.times(time));
        bounce(w);
    }

    //Method to set the position of the square
    public void setPosition(Pair p) {
        position = p;
    }

    //Method to set the width of the square
    public void setWidth(double r){
        width = r;
    }

    //Method to set the velocity of the square
    public void setVelocity(Pair v) {
        velocity = v;
    }

    //Method to set the acceleration of the square
    public void setAcceleration(Pair a) {
        acceleration = a;
    }

    //Method to draw the Square
    public void draw(Graphics g, Color color) {
        Color c = g.getColor();
        g.setColor(color);
        g.fillRect((int)(position.x - width), (int)(position.y - width), (int)(2 * width), (int)(2 * width));
        g.setColor(c);
    }

    //Method to bounce the square off of the walls
    private void bounce(World w) {
        Boolean bounced = false;
        if(position.x - width < 0) {
            velocity.flipX();
            position.x = width;
            bounced = true;
        }
        else if (position.x + width > w.width) {
            velocity.flipX();
            position.x = w.width - width;
            bounced = true;
        }
        if (position.y - width < 0) {
            velocity.flipY();
            position.y = width;
            bounced = true;
        }
        else if (position.y + width > w.height) {
            velocity.flipY();
            position.y = w.height - width;
            bounced = true;
        }
        if (bounced) {
            velocity = velocity.divide(dampening);
        }
    }
}
