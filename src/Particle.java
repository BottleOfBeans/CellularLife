import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Particle extends GameWindow {

    // Visual Effects
    double radius = 5;

    // Game Speed Effects
    double tickSpeed = 3;
    double speed = 10;

    // Equation Variables
    double effectradius = 55;
    double rotationProportional = -6; // Beta
    double fixedRotation = 120; // Alpha

    // Various Constructor Values
    public Color color = Color.white;
    public int colorCode;
    public Vector2 changeVector;
    public Vector2 currentPos;
    public double diameter;
    public double effectDiameter;

    public Particle(Vector2 changeVector, Vector2 currentPos) {

        // Taking in the changeVector

        this.changeVector = changeVector;

        // Normalizing the vector to allow for direction alone and independent speed
        // change
        changeVector = changeVector.normalize();

        this.currentPos = currentPos;

        // Setting diameter of object + diameter of effect radius
        diameter = radius * 2;
        effectDiameter = diameter + effectradius * 2;

        // Placeholder Color
        this.color = Color.WHITE;

    }

    // Updating location of particle
    public void updateLocation(double deltaTime) {
        // Updating location with regard to speed (multiplication) to time (deltaTime)
        // and a set tickspeed (tickspeed)
        currentPos.addVector(changeVector.returnMultiply(speed).returnDivide(deltaTime / tickSpeed));
    }

    // Various Get methods for location, object itself and effect areas.
    public Ellipse2D getParticle() {
        return new Ellipse2D.Double(currentPos.x - radius, currentPos.y - radius, diameter, diameter);
    }

    public Line2D directionLine() {
        return new Line2D.Double(currentPos.x, currentPos.y, changeVector.normalize().x * 30 + currentPos.x,
                changeVector.normalize().y * 30 + currentPos.y);
    }

    public Ellipse2D getEffectArea() {
        return new Ellipse2D.Double(currentPos.x - (effectradius + radius), currentPos.y - (effectradius + radius),
                effectDiameter, effectDiameter);
    }

    // Quick function that allows one to get the sign of a number
    public int sign(double number) {
        if (number > 0) {
            return 1;
        } else if (number == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    // Updating the color of the particle based on its surroundings
    public void updateColor(double number) {
        number = Math.abs(number * 50);
        int RedValue = (int) Math.min(number, 255);
        int BlueValue = (int) Math.max(0, 255 - number / 2);
        int GreenValue = (int) Math.min(Math.max(0, number - 255 / 5), 255);

        this.color = new Color(RedValue, GreenValue, BlueValue);
    }

    // Updating the direction of the particle based on surrounding particles
    public void updateParticle(ArrayList<Particle> particles) {
        double nNeighbors = 0; // North Neighbors
        double sNeighbors = 0; // South Neighbors

        // Looping through all of the particles
        for (Particle p : particles) {
            // Difference is the distance vector between the two particles
            Vector2 difference = new Vector2(this.currentPos.x - p.currentPos.x, this.currentPos.y - p.currentPos.y);

            // Checking if the distance is within the diameter
            if (difference.magnitude() < effectDiameter) {
                double angle = Math.atan2(difference.x, difference.y); // Getting the angle between the two objects
                if (angle >= 180 && angle <= 360 || angle <= 90 && angle > 0) {
                    nNeighbors++; // If within 180*-360* add to Northern neighbors
                } else {
                    sNeighbors++; // If within anything thelse add to Southern neighbors
                }
            }
        }

        double N = nNeighbors + sNeighbors; // The total number of neighbors that effect the experience

        /*
         * Using the equation (Delta Theta [Radians]) = alpha + beta * N * sign(N - S)
         * Alpha - fixedrotation
         * Beta - proportion of rotation
         * N - total number of surrounding bodies
         * sign (S-N) - the sign of the southern neighbors subtracted from north, allows
         * for turning in correct directions
         */

        double newAngleRad = Math.toRadians(fixedRotation) + rotationProportional * N * sign(sNeighbors - nNeighbors); // The
                                                                                                                       // Change
                                                                                                                       // Angle

        double newX = Math.cos(newAngleRad) * changeVector.x - Math.sin(newAngleRad) * changeVector.y; // Combined X
                                                                                                       // Vector
        double newY = Math.sin(newAngleRad) * changeVector.x + Math.cos(newAngleRad) * changeVector.y; // Conbined Y
                                                                                                       // Vector

        changeVector = new Vector2(newX, newY); // Updating the change Vector
        updateColor(N); // Updating the color of the particle
    }
}
