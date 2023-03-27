import java.awt.geom.Ellipse2D;

public class Particle extends GameWindow{
    double G = 10;
    public double mass;
    public double radius;
    public Vector2 changeVector;
    public Vector2 currentPos;
    public double diameter;

    public Particle(double gmass, Vector2 gchangeVector, Vector2 gcurrentPos){

        mass = gmass;

        changeVector = gchangeVector;
        currentPos = gcurrentPos;

        radius = 10;
        diameter = radius*2;
    }

    public void updateLocation(){
        currentPos.addVector(changeVector.returnDivide(FPS));
    }

    public Ellipse2D getParticle(){
        return new Ellipse2D.Double(currentPos.x-radius, currentPos.y-radius, diameter,diameter);
    }


}
