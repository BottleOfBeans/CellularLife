import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

public class Particle extends GameWindow{

    double G = 10;
    double effectradius = 100;
    double radius = 10;
    double CollisionOffset = 0.96;
    int amountOfColors = 2;
    double tickSpeed = 4;

    double[][] attractionForces = {
    // Formatted as columns are what is being effected, while the rows are what are being queryed.
    // R  G  Y          (0 is disabled, 1 is enabled)
      {0, 0, 0}, // R 
      {0, 1, 1}, // G
      {0, 0, 0}, // Y
    };

    double[][] attractionValues = {
        // R  G  Y          (Actual Values)
          {0, 0, 0}, // R 
          {0, -1, -3}, // G
          {0, 0, 0}, // Y
        };
    
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

        int i = rand.nextInt(0, amountOfColors);

        if(i == 0){
            this.color = Color.GREEN;
            colorCode = 1;
        }
        else if( i == 1){
            this.color = Color.YELLOW;
            colorCode = 2;
        }
        else{
            this.color = Color.RED;
            colorCode = 0;
        }

    }

    public void updateLocation(){
        currentPos.addVector(changeVector.returnDivide(FPS / tickSpeed));
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
            
            if(tempVector.magnitude() < (this.radius + p.radius)){
                changeVector = new Vector2(0, 0);
            }
            
            if(attractionForces[this.colorCode][p.colorCode]  == 1){
                if(tempVector.magnitude() <= effectDiameter){
                    tempVector = tempVector.normalize();
                    tempVector.multiply(attractionForces[this.colorCode][p.colorCode] * attractionValues[this.colorCode][p.colorCode] * -1);
                    if(!Double.isNaN(tempVector.x)){
                        //System.out.println("X: "+tempVector.x + "    Y:"+tempVector.y);
                        changeVector.addVector(tempVector);
                    }
                }
            }          
        }
    }                    
} 
