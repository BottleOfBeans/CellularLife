import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

import org.w3c.dom.css.RGBColor;

public class Particle extends GameWindow{

    //Visual Effects
    double radius = 5;

    //Game Speed Effects    
    double tickSpeed = 3;
    double speed = 10;


    //Equation Variables
    double effectradius = 67;
    double rotationProportional = -4; // Beta
    double fixedRotation = 47; // Alpha
    

    public Color color = Color.white;
    public int colorCode;
    public Vector2 changeVector;
    public Vector2 currentPos;
    public double diameter;
    public double effectDiameter;

    public Particle(Vector2 changeVector, Vector2 currentPos){

        this.changeVector = changeVector;
        changeVector = changeVector.normalize();

        this.currentPos = currentPos;

        diameter = radius*2;
        effectDiameter = diameter + effectradius * 2;

        this.color = Color.WHITE;

    }

    public void updateLocation(double deltaTime){
        currentPos.addVector(changeVector.returnMultiply(speed).returnDivide(deltaTime/tickSpeed));
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

    public int sign(double number){
        if(number > 0){
            return 1;
        }
        else if(number == 0){
            return 0;
        }else{
            return -1;
        }
    }

    public void updateColor(double number){
        number = Math.abs(number*50) ;
        int RedValue = (int) Math.min(number, 255);
        int BlueValue = (int) Math.max(0, 255-number/2);
        int GreenValue = (int) Math.min(Math.max(0, number - 255/3),255);

        this.color = new Color(RedValue, GreenValue, BlueValue);
    }

    public void updateParticle(ArrayList<Particle> particles){
        double nNeighbors = 0;
        double sNeighbors = 0;

        for(Particle p : particles){
            Vector2 difference = new Vector2(this.currentPos.x - p.currentPos.x , this.currentPos.y - p.currentPos.y);
            if(difference.magnitude() < effectDiameter){
                double angle = Math.atan2(difference.x, difference.y);
                if(angle >= 180 && angle <= 360 || angle <=90 && angle > 0){
                    nNeighbors ++;
                }else{
                    sNeighbors ++;
                }
            }
        }

        double N = nNeighbors + sNeighbors;

        double newAngleRad = Math.toRadians(fixedRotation) + rotationProportional * N * sign(nNeighbors - sNeighbors);
        
        double newX = Math.cos(newAngleRad) * changeVector.x - Math.sin(newAngleRad) * changeVector.y;
        double newY = Math.sin(newAngleRad) * changeVector.x + Math.cos(newAngleRad) * changeVector.y;

        changeVector = new Vector2(newX, newY);
        updateColor(N);
    }                    
} 
