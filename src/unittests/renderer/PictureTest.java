package unittests.renderer;


import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.*;

import java.util.LinkedList;
import java.util.List;

public class PictureTest {

    private List<Sphere>  initializeBalls() {

         List<Sphere> balls = new LinkedList<>();

        //Material material = new Material().setKd(0.4).setKs(1).setShininess(50).setKt(0).setKr(0.5).setKs(0.5);
        Material material1 = new Material().setKd(0.4).setKs(1).setShininess(100).setKt(0).setKr(0.9);


        for(double i = 0,j = 6; i <500 ;i+=1,j+=0.04){
            double x = Math.cos(i);
            double y = Math.sin(i);
            balls.add((Sphere) new Sphere(j, new Point(i*y, i*x, (i*2 - 150))).setMaterial(material1).setEmission(new Color(255,0,0)));
        }
        return balls;
    }
    
    @Test
    public void pictureTest() {
        Scene scene = new Scene("final picture").setBackground(new Color(0,0,0));
        Camera camera = new Camera(new Point(0, -600, 10), new Vector(0, 1, 0), new Vector(0, 0, 1));
        camera.setVPSize(150, 150).setVPDistance(100);

        Material material = new Material().setKd(0.4).setKs(1).setShininess(50).setKt(0).setKr(0.5).setKs(0.5);
        Material material1 = new Material().setKd(0.4).setKs(1).setShininess(100).setKt(0).setKr(0);
        SpotLight light = new SpotLight(new Color(255, 255, 255), new Point(0, -50, 25), new Vector(0, 2, -1));
        SpotLight light2 = new SpotLight(new Color(255, 255, 255), new Point(0, 50, 25), new Vector(0, -2, -1));
        light.setKc(0).setKl(0.01).setKq(0.05);
        //light.setNarrowBeam(5);
        light2.setKc(0).setKl(0.01).setKq(0.05);
        //light2.setNarrowBeam(5);

        DirectionalLight directionalLight1 = new DirectionalLight(new Color(100, 100, 100), new Vector(0, 0, -1));
        DirectionalLight directionalLight2 = new DirectionalLight(new Color(100, 100, 100), new Vector(1, 0, 0));
        DirectionalLight directionalLight3 = new DirectionalLight(new Color(100, 100, 100), new Vector(-1, 0, 0));
        PointLight pointLight = new PointLight(new Color(255,255,255),new Point(200,50,-100));


        //scene.lights.add(light);
        scene.lightSources.add(directionalLight1);
        scene.lightSources.add(directionalLight2);
        scene.lightSources.add(directionalLight3);
        scene.lightSources.add(pointLight);



        Sphere sphere = new Sphere(130, new Point(0, 0, 220));

        Sphere sphere3 = new Sphere(100, new Point( 200, -100, 50));
        Sphere sphere4 = new Sphere(100, new Point(-200, -100, 50));

        sphere.setMaterial(material1).setEmission(new Color(102,178,255));

        //sphere.setEmission(new Color(255, 0, 0)).setMaterial(material1);
        sphere3.setEmission(new Color(169,50,50));
        sphere4.setEmission(new Color(50,50,169));

        Sphere sphere2 = new Sphere(70, new Point(0, 0, 0));
        sphere2.setEmission(new Color(100, 69, 0)).setMaterial(material);

        Polygon sqr1 = new Polygon(new Point(-10000, 10000, -10000),
                new Point(-10000, 10000, 10000),
                new Point(10000, 10000, 10000),
                new Point(10000, 10000, -10000));
        //.setEmission(new Color(255, 215, 0))
        sqr1.setMaterial(material1).setEmission(new Color(0,0,0));

        Polygon sqrt2 = new Polygon(new Point(-150, 100, -100),
                new Point(-150, 100, 100),
                new Point(-150, -100, 100),
                new Point(-150, -100, -100));
        //.setEmission(new Color(0, 0, 0))
        sqrt2.setMaterial(material);

        Polygon sqrt3 = new Polygon(new Point(150, -100, -100),
                new Point(150, -100, 100),
                new Point(150, 100, 100),
                new Point(150, 100, -100));
        //.setEmission(new Color(0, 0, 0))
        sqrt3.setMaterial(material);

        Polygon sqrt4 = new Polygon(new Point(150, -100, 150),
                new Point(-100, -100, 150),
                new Point(-100, 100, 150),
                new Point(100, 100, 150));
        //.setEmission(new Color(0, 0, 0))
        sqrt4.setMaterial(material);

        Polygon sqrt5 = new Polygon(new Point(100, -100, -150),
                new Point(-100, -100, -150),
                new Point(-100, 100, -150),
                new Point(100, 100, -150));
        //.setEmission(new Color(0, 0, 0));
        sqrt5.setMaterial(material);

        Plane pln = new Plane(new Point(100,-100,-150),new Vector(0,0,1));
        //pln.setEmission(new Color(255,255,255));
        //.setEmission(new Color(0, 0, 0));
        pln.setMaterial(material).setEmission(new Color(0,0,0));
        List<Sphere> balls = initializeBalls();
        scene.geometries.add(pln, sphere);
        for (Sphere item : balls) {
            scene.geometries.add(item);
        }

        camera.setImageWriter(new ImageWriter("final picture", 500, 500))
                .setRayTracer(new RayTracerBasic (scene))
                .renderImage()
                .writeToImage();
    }
}
