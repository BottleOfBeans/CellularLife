import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

public class Particle extends GameWindow{

    double G = 10;
    double effectradius = 20;
    double radius = 5;
    
    double[][] attractionForces = {
    // R  G  Y
      {0, 0, 0}, // R 
      {0, 0, 0}, // G
      {0, 0, 0}, // Y
    };
    double[] currentForce;
    
    public Color color = Color.white;
    public int colorCode;
    public Vector2 changeVector;
    public Vector2 currentPos;
    public double diameter;
    public double effectDiameter;

    public Particle(Vector2 changeVector, Vector2 currentPos){

        Random rand = new Random();

        this.changeVector = changeVector;
        this.currentPos = currentPos;

        diameter = radius*2;
        effectDiameter = diameter + effectradius * 2;

        int i = rand.nextInt(0, 3);

        if(i == 0){
            this.color = Color.GREEN;
            currentForce = attractionForces[1];
            colorCode = 1;
        }
        else if( i == 1){
            this.color = Color.YELLOW;
            currentForce = attractionForces[2];
            colorCode = 2;
        }
        else{
            this.color = Color.RED;
            currentForce = attractionForces[0];
            colorCode = 0;
        }

    }

    public void updateLocation(){
        currentPos.addVector(changeVector.returnDivide(FPS));
    }

    public Ellipse2D getParticle(){
        return new Ellipse2D.Double(currentPos.x-radius, currentPos.y-radius, diameter,diameter);
    }

    public Line2D directionLine(){
        return new Line2D.Double(currentPos.x, currentPos.y,changeVector.normalize().x * 30 + currentPos.x,changeVector.normalize().y * 30 +currentPos.y);
    }

    public Ellipse2D getEffectArea(){
        return new Ellipse2D.Double(currentPos.x - (effectradius + radius), currentPos.y - (effectradius + radius), effectDiameter, effectDiameter);
    }

    public void calculateAttraction(ArrayList<Particle> particles){
        for(Particle p : particles){
            Vector2 tempVector = new Vector2(this.currentPos.x - p.currentPos.x, this.currentPos.y - p.currentPos.y);
            if(tempVector.magnitude() < this.effectradius){
                changeVector.addVector(tempVector.normalize().returnMultiply(attractionForces[this.colorCode][p.colorCode]));

            }
        }                    
    }

}
