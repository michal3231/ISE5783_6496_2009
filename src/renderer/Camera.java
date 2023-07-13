package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

public class Camera {

    protected Point p0;
    protected Vector vUp;
    protected Vector vRight;
    protected Vector vTo;

    protected double width;
    protected double height;
    protected double distance;

    protected ImageWriter imageWriter;
    protected RayTracerBase rayTracerBase;

    /**
     * constructor that checks the parameters
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        if (!Util.isZero(vUp.dotProduct(vTo))){
            throw new IllegalArgumentException("the vectors are not vertical");
        }
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }


    public void checkIfFieldInitialized(){
        try {
            if (this.p0 == null)
                throw new MissingResourceException("P0 is not initialize", "Camera", "p0");
            if (this.vUp == null)
                throw new MissingResourceException("vUp is not initialize", "Camera", "vUp");
            if (this.vRight == null)
                throw new MissingResourceException("vRight is not initialize", "Camera", "vRight");
            if (this.vTo == null)
                throw new MissingResourceException("vTo is not initialize", "Camera", "vTo");
            if (this.imageWriter == null)
                throw new MissingResourceException("imageWriter is not initialize", "Camera", "imageWriter");
            if (this.rayTracerBase == null)
                throw new MissingResourceException("rayTracerBase is not initialize", "Camera", "rayTracerBase");
            if (this.width == 0)
                throw new MissingResourceException("width is not initialize", "Camera", "width");
            if (this.height == 0)
                throw new MissingResourceException("height is not initialize", "Camera", "height");
            if (this.distance == 0)
                throw new MissingResourceException("distance is not initialize", "Camera", "distance");
        }
        catch (MissingResourceException ex){
            throw new UnsupportedOperationException();
        }
    }


    /**
     * The function sends pixels for coloring
     * @return
     */
    public Camera renderImage(){
        checkIfFieldInitialized();

        for(int i = 0; i < this.imageWriter.getNx(); i++)
            for(int j = 0; j < this.imageWriter.getNy(); j++){
                imageWriter.writePixel(i ,j, castRay(i, j));
            }
        return this;
    }



    /**
     * create the ray goes through the pixel got at input and return its color
     * @param i
     * @param j
     * @return
     */
    protected Color castRay(int i, int j){
        Ray ray = constructRay(imageWriter.getNx(), imageWriter.getNy(), i, j);
            return rayTracerBase.traceRay(ray);
    }


    /**
     * The function to print a grid
     * @param interval
     * @param color
     */
    public void printGrid(int interval, Color color){
        if (this.imageWriter == null)
            throw new MissingResourceException("imageWriter is not initialize", "Camera", "imageWriter");

        //Write all pixels besides all the grid y axis
        for (int i = 0; i < this.imageWriter.getNy(); i = i + interval){
            for(int j = 0; j < this.imageWriter.getNx(); j++){
                    imageWriter.writePixel(j, i, color);
            }
        }

        ////Write all pixels besides all the grid x axis
        for (int i = 0; i < this.imageWriter.getNx(); i = i + interval){
            for(int j = 0; j < this.imageWriter.getNy(); j++){
                imageWriter.writePixel(i, j, color);
            }
        }
    }


    /**
     * write to the image calling the class
     */
    public void writeToImage(){

        if (this.imageWriter == null)
            throw new MissingResourceException("imageWriter is not initialize", "Camera", "imageWriter");

        this.imageWriter.writeToImage();
    }


    public double getHeight() {
        return height;
    }


    public Camera setHeight(double height) {
        this.height = height;
        return this;
    }


    public double getDistance() {
        return distance;
    }


    /**
     * setting the distance
     */
    public Camera setVPDistance(double distance) {
        if (Util.isZero(distance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }
        this.distance = distance;
        return this;
    }


    public double getWidth() {
        return width;
    }

    public Camera setWidth(double width) {
        this.width = width;
        return this;
    }

    public Camera setVPSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }


    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTraceBase(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }


    /**
     *
     * @param nX   no. of pixels in a row
     * @param nY   no. of pixels in a column
     * @param j
     * @param i
     * @return a ray begins from p0 to the object, goes through a center of a pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        Point pC = p0.add(vTo.scale(distance)); //the point in the center of the view plane -->Pc=P0+dVto

        double rY = height / nY; //height of one pixel
        double rX = width / nX;  //width of one pixel

        double yi = Util.alignZero(-( i - (nY - 1) / 2d) * rY) ; //distance to go from Pc-left or right
        double xj =Util.alignZero ((j - (nX - 1) / 2d) * rX); //distance to go from Pc-up or down

        //Pi,j-where we hit the view plane with the ray from P0

        Point Pij = pC;

        if (!Util.isZero(xj)) {
            Pij = Pij.add(vRight.scale(xj));
        }
        if (!Util.isZero(yi)) {
            Pij = Pij.add(vUp.scale(yi)); // Pij.add(_vUp.scale(-yi))
        }
        Vector vij = Pij.subtract(p0).normalize(); //direction vector

        return new Ray(p0, vij);
    }


    public RayTracerBase getRayTracerBase() {
        return rayTracerBase;
    }

    public Camera setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }
}
