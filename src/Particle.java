import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class Particle extends GameWindow {

    // Visual Effects
    double radius = 8;
    double diameter;

    // Particle Mass;
    
    // Set Vectors;
    Vector2 GRAVITY = new Vector2(0,3);
    
    //Current Vectors
    Vector2 currentPos;
    Vector2 previousPos;
    Vector2 acceleration;

    


    
    public Particle(Vector2 currentPos) {
        // Taking in the Vector Inputs
        this.acceleration = new Vector2(0,0);
        this.currentPos = currentPos;
        this.previousPos = currentPos;
        diameter = radius*2;
    }

    // Updating location of particle
    public void updateLocation(double deltaTime) {

        Vector2 velocity = currentPos.returnSubtract(this.previousPos);
        this.previousPos = this.currentPos;

        this.currentPos.addVector(velocity);
        this.currentPos.addVector(acceleration.returnMultiply(deltaTime*deltaTime));
        
        acceleration = new Vector2(0,0);

    }

    public void accelerate(Vector2 acceleration){
        this.acceleration.addVector(acceleration);
    }

    public void applyGravity(){
        this.accelerate(GRAVITY);
    }

    public void update(double deltaTime){
        applyGravity();
        
        for(int i = 0; i < steps; i ++){
            applyCircularConstraint(CircularContainer);
            solveCollisions(particles);
        }

        this.updateLocation(deltaTime);
    }

    public void applyCircularConstraint(Ellipse2D circularConstraint){
        Vector2 dist = this.currentPos.returnSubtract(new Vector2(circularConstraint.getCenterX() - this.diameter, circularConstraint.getCenterY() - this.diameter));
        double distance = dist.magnitude();
        if(distance > circularConstraint.getHeight()/2){
            Vector2 n = dist.returnDivide(distance);
            this.currentPos.addVector(
                n.returnMultiply((distance - circularConstraint.getHeight()/2)*-1)
            );
        }
    }

    public void applyRectangularConstraint(Rectangle2D rectangularConstraint){
        if(this.currentPos.y > rectangularConstraint.getMaxY()){
            ;
        }  
    }

    public void solveCollisions(ArrayList<Particle> particles){
        for(Particle p : particles){
            if(p != this){
                Vector2 collisionAxis = this.currentPos.returnSubtract(p.currentPos);
                double distance = collisionAxis.magnitude();
                if(distance < this.radius + p.radius){
                    Vector2 tempVec = collisionAxis.normalize();
                    double change = this.radius + p.radius - distance;
                    this.currentPos.addVector(tempVec.returnMultiply(0.5 * change));
                    p.currentPos.subtractVector(tempVec.returnMultiply(0.5 * change));
                } 
            }
        }
    }

    // Various Get methods for location, object itself and effect areas.
    public Ellipse2D getParticle() {
        return new Ellipse2D.Double(currentPos.x - radius, currentPos.y-radius, diameter, diameter);
    }
}
