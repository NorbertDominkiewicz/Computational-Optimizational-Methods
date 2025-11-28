package com.ndominkiewicz.backend.service.SeveralVariable;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;

public class SurfacePlotNewton extends Application {
    private double f(double x, double y) {
        return y*y*y + x*x - 9*x*y - 3*x + 2;
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        MeshView surface = createSurface();
        root.getChildren().add(surface);

        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(0);
        light.setTranslateY(-300);
        light.setTranslateZ(-300);
        root.getChildren().add(light);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-500);
        camera.setTranslateY(-50);
        camera.setTranslateX(-50);
        camera.setFarClip(2000);

        Scene scene = new Scene(root, 900, 700, true);
        scene.setCamera(camera);
        scene.setFill(Color.gray(0.1));

        final double[] last = {0, 0};
        scene.setOnMousePressed(e -> { last[0] = e.getSceneX(); last[1] = e.getSceneY(); });
        scene.setOnMouseDragged(e -> {
            double dx = e.getSceneX() - last[0];
            double dy = e.getSceneY() - last[1];
            surface.setRotationAxis(javafx.geometry.Point3D.ZERO.add(0,1,0));
            surface.setRotate(surface.getRotate() + dx*0.5);
            last[0] = e.getSceneX();
            last[1] = e.getSceneY();
        });

        stage.setScene(scene);
        stage.setTitle("Wykres 3D: f(x,y) = y^3 + x^2 - 9xy - 3x + 2");
        stage.show();
    }

    private MeshView createSurface() {
        int size = 120;
        float scale = 12f;

        TriangleMesh mesh = new TriangleMesh();

        double min = -5;
        double max = 5;

        for (int iy = 0; iy < size; iy++) {
            for (int ix = 0; ix < size; ix++) {

                double x = min + ix * (max - min) / (size - 1);
                double y = min + iy * (max - min) / (size - 1);
                double z = f(x, y);

                mesh.getPoints().addAll(
                        (float)(x * scale),
                        (float)(-z * 0.6f),
                        (float)(y * scale)
                );
            }
        }

        mesh.getTexCoords().addAll(0, 0);

        for (int y = 0; y < size - 1; y++) {
            for (int x = 0; x < size - 1; x++) {
                int p0 = y * size + x;
                int p1 = p0 + 1;
                int p2 = p0 + size;
                int p3 = p2 + 1;

                mesh.getFaces().addAll(p0, 0, p2, 0, p1, 0);
                mesh.getFaces().addAll(p1, 0, p2, 0, p3, 0);
            }
        }

        MeshView view = new MeshView(mesh);

        PhongMaterial material = new PhongMaterial(Color.CORNFLOWERBLUE);
        view.setMaterial(material);
        view.setDrawMode(DrawMode.FILL);

        return view;
    }

    public static void main(String[] args) {
        launch();
    }
}

