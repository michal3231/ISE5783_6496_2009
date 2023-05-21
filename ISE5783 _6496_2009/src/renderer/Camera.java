package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Camera {

	
		// TODO Auto-generated constructor stub
		 private Point position;//camera location
		 private Vector vTo;
		 private Vector vUp;
		 private Vector vRight;
		 private double height;
		 private double width;
		 private double distance;
		
		 public Camera(Point position,Vector To,Vector Up) {
			 if (To.dotProduct(Up) != 0)//if the vectors not vertical
			    	 throw new IllegalArgumentException("vTo and vUp must be orthogonal");
		        this.position = position;
		        this.vTo = To.normalize();
		        this.vUp = Up.normalize();		    
		        //Calculate the perpendicular vector between the two vectors by vector multiplication
		        //מחשבים את הווקטור הניצב בין שני הווקטורים עי מכפלה ווקטורית
		        this.vRight = this.vTo.crossProduct(this.vUp).normalize();
		        
	    }
		 
		 public Camera setVPSize(double width, double height) {
			 	this.width = width;
		        this.height = height;
		        return this;
		 }
		 
		 public Camera setVPDistance(double distance) {
			 this.distance = distance;
		     return this;
		 }
		 
		 //יוצר את הקרן של המצלמה בעזרת שליחת נקודת מיקום של המצלמה ווקטור כיוון לאחר שהורדנו את מיקום המצלמה מהווקטור  
		 public Ray constructRay(int nX, int nY, int j, int i) {
			 return new Ray(position, findPixelLocation(nX, nY, j, i).subtract(position));
		 }
		 
		    private Point findPixelLocation(int nX, int nY, int j, int i) {

		        double rY = height / nY;
		        double rX = width / nX;

		        double yI = -(i - (nY - 1d) / 2) * rY;
		        double jX = (j - (nX - 1d) / 2) * rX;
		        Point pIJ = position.add(vTo.scale(distance));

		        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));
		        if (jX != 0) pIJ = pIJ.add(vRight.scale(jX));
		        return pIJ;
		    }
		    
		    
		 
		 
}
