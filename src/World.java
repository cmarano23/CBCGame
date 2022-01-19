import java.awt.*;
import java.util.ArrayList;

class World {
    //Instance Variables
    int height;
    int width;
    int numBalls;
    int numSquares;
    int numPlayers = 1;
    int score = 0;
    int level = 1;
    boolean collideWithPlayer = false;
    ArrayList<Ball> balls;
    ArrayList<Square> squares;
    Player player = new Player();
    Shot shot = new Shot();
    int ballsAlive = 1;

    //Constructor
    public World(int initWidth, int initHeight, int initNumBalls, int initNumSquares) {
        //Set variables
        width = initWidth;
        height = initHeight;
        numBalls = initNumBalls;
        numSquares = initNumSquares;
        balls = new ArrayList<>();
        squares = new ArrayList<>();
        shot = new Shot();
        //loop through to add balls to array list
        for(int i = 0; i < numBalls; i++) {
            balls.add(new Ball());
        }
        //loop through to add squares to array list
        for(int j = 0; j < numSquares; j++) {
            squares.add(new Square());
        }
    }

    //Method to draw the balls
    public void drawBalls(Graphics g) {
        for (int i = 0; i < numBalls; i++) {
            balls.get(i).draw(g, levelColor(level));
        }
    }

    //Method to update the balls' location
    public void updateBalls(double time) {
        for (int i = 0; i < numBalls; i++) {
            balls.get(i).update(this, time);
        }
    }

    //Method to draw the squares
    public void drawSquares(Graphics g) {
        for (int i = 0; i < numSquares; i++) {
            squares.get(i).draw(g, levelColor(level));
        }
    }

    //Method to update the squares' location
    public void updateSquares(double time) {
        for (int i = 0; i < numSquares; i++) {
            squares.get(i).update(this, time);
        }
    }

    //Method to draw Player
    public void drawPlayer(Graphics g) {
        for (int i = 0; i < numPlayers; i++) {
            player.draw(g);
        }
    }

    //Method to update the player's location
    public void updatePlayer(double time) {
        for (int i = 0; i < numPlayers; i++) {
            player.update(this, time);
        }
    }

    //Method to draw the shot
    public void drawShot(Graphics g) {
        for (int i = 0; i < 1; i++) {
            shot.draw(g);
        }
    }

    //Method to update the shot's location
    public void updateShot(double time) {
        for (int i = 0; i < 1; i++) {
            shot.update(this, time, player);
        }
    }

    //Method to check for new level and update accordingly
    public void updateNewLevel(Graphics g) {
        //if the player has destroyed all the balls (so there are 0 balls alive), advance to the next level
        if (ballsAlive == 0) {
            level++;
            //The new level will start with the same number of balls as the level number (level 2 starts with 2 balls, level 3 starts with 3 balls, etc.)
            for (int i = 0; i < level; i++) {
                balls.add(new Ball());
                ballsAlive++;
                numBalls++;
                //Only add one square each new round; square does not need to be destroyed to advance to next round
                if (i == 0) {
                    squares.add(new Square());
                    numSquares++;
                }
            }
        }
    }

    //Set the color of the balls and square to a color based on the level number
    public Color levelColor(int level) {
        if (level % 4 == 0) {
            return Color.yellow;
        } else if (level % 3 == 0) {
            return Color.cyan;
        } else if (level % 2 == 0) {
            return Color.green;
        } else {
            return Color.magenta;
        }
    }

    //Method to convert an integer to a String
    public String toString(int number) {
        String toReturn = "";
        toReturn += number;
        return toReturn;
    }

    //Method to draw the intro screen of the game
    public void drawIntro(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.red);
        g.drawString("Welcome To [INSERT NAME HERE]", width / 2, height / 2);
        g.drawString("Your game will begin shortly be ready", width / 2, (height / 2) + 20);
        g.setColor(c);
    }

    //Method to draw the score counter in the top right corner
    public void drawScore(int score, Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("Score: " + toString(score), width - 60, 20);
        g.setColor(c);
    }

    //Method to draw the level counter in the top left corner
    public void drawLevel(int level, Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("Level: " + toString(level), 10, 20);
        g.setColor(c);
    }

    //Method to draw the Game Over screen
    public void drawGameOver(int score, Graphics g) {
        //Draw only if an object has collided with the player
        if (collideWithPlayer) {
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.red);
            g.drawString("Game Over!", width / 2 - 28, height / 2 - 20);
            g.setColor(Color.black);
            g.drawString("Final Score: " + toString(score), width / 2 - 33, height / 2 + 10);
        }
    }

    public void checkCollision(Graphics g) {
        //Hit Detection for Balls
        for (int i = 0; i < numBalls; i++) {
            //Distance formula to calculate the distance between the shot and the balls
            double distance = Math.sqrt((Math.pow((balls.get(i).position.x - shot.position.x), 2)) + (Math.pow((balls.get(i).position.y - shot.position.y), 2)));
            //Check to see if that distance is less than the balls' radius + shot radius in order to detect contact
            if (distance <= (balls.get(i).radius + shot.radius) && shot.isReleased) {
                //If ball has not been previously split then split the balls creating two new ones
                if (!balls.get(i).splitBall) {
                    for (int j = 0; j < 2; j++) {
                        //Add new ball
                        balls.add(new Ball());
                        //Set radius to half
                        balls.get(numBalls).setRadius(balls.get(i).getRadius() / 2);
                        //Set position
                        balls.get(numBalls).setPosition(balls.get(i).getPosition());
                        //Identify as a split ball
                        balls.get(numBalls).splitBall = true;
                        //Increment numBalls and ballsAlive
                        numBalls++;
                        ballsAlive++;
                    }
                }
                //Once ball is hit, remove from screen
                balls.get(i).setPosition(new Pair(50000, 50000));
                balls.get(i).setVelocity(new Pair(0, 0));
                balls.get(i).setAcceleration(new Pair(0, 0));
                balls.get(i).setRadius(0);
                //Decrease ballAlive
                ballsAlive--;

                //Reset the shot to the player
                shot.setPosition(player.getPosition());
                shot.setVelocity(player.getVelocity());
                shot.isReleased = false;

                //Increase score by one for hitting a ball
                score++;
            }
            //Distance formula to calculate the distance between the player and the balls
            double distanceToPlayer = Math.sqrt((Math.pow((balls.get(i).position.x - player.position.x), 2)) + (Math.pow((balls.get(i).position.y - player.position.y), 2)));
            if (distanceToPlayer <= (balls.get(i).radius + player.radius)) {
                collideWithPlayer = true;
            }

        }
        //Hit detection for Squares
        for (int i = 0; i < numSquares; i++) {
            //Distance formula to calculate the distance between the shot and the squares
            double distance = Math.sqrt((Math.pow((squares.get(i).position.x - shot.position.x), 2)) + (Math.pow((squares.get(i).position.y - shot.position.y), 2)));
            //Check to see if that distance is less than the squares' width + shot radius in order to detect contact
            if (distance <= (squares.get(i).width + shot.radius) && shot.isReleased) {

                squares.get(i).setPosition(new Pair(50000, 50000));
                squares.get(i).setVelocity(new Pair(0, 0));
                squares.get(i).setAcceleration(new Pair(0, 0));
                squares.get(i).setWidth(0);

                shot.setPosition(player.getPosition());
                shot.setVelocity(player.getVelocity());
                shot.isReleased = false;
                //Increase score by 2 for hitting the square
                score += 2;
            }
            double distanceToPlayer = Math.sqrt((Math.pow((squares.get(i).position.x - player.position.x), 2)) + (Math.pow((squares.get(i).position.y - player.position.y), 2)));
            if (distanceToPlayer <= (squares.get(i).width + player.radius)) {
                collideWithPlayer = true;
            }

        }

    }
}

