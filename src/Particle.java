import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Particle extends GameWindow{

    double G = 10;
    double effectradius = 20;

    public double radius;
    public Vector2 changeVector;
    public Vector2 currentPos;
    public double diameter;
    public double effectDiameter;

    public Particle(Vector2 gchangeVector, Vector2 gcurrentPos){

        changeVector = gchangeVector;
        currentPos = gcurrentPos;

        radius = 10;
        diameter = radius*2;
        effectDiameter = diameter + effectradius * 2;
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

}
