public class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2(double angle){
        x = Math.sin(angle);
        y = Math.cos(angle);
    }

    public Vector2 normalize(){
        return new Vector2(x/this.magnitude(), y/this.magnitude());
    }
    public double magnitude(){
        return Math.sqrt(x*x + y*y);
    }
    public double sqrMagnitude(){
        return (x*x + y*y);
    }
    public void addVector(Vector2 gVector){
        x += gVector.x;
        y += gVector.y;
    }
    public void divide(double z){
        if(z != 0){
            x /= z;
            y /= z;
        }
    }
    public void multiply(double z){
        x *= z;
        y *= z;
    }
    public Vector2 returnMultiply(double z){
        return new Vector2(x * z, y * z);
    }
    public Vector2 returnDivide(double z){
        return new Vector2(x/z, y/z);
    }
    public double getAngle(){
        return Math.atan2(y,x);
    }
    public void multiplyVectors( Vector2 gvector){
        x *= gvector.x;
        y *= gvector.y;
    }
}
