package com.ndominkiewicz.frontend.service.severalVariables;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;

public class Displayer extends Application {

    public static MeshView meshView;

    @Override
    public void start(Stage stage) {
        Group root = new Group(meshView);

        javafx.scene.PointLight light = new javafx.scene.PointLight(Color.WHITE);
        light.setTranslateX(100);
        light.setTranslateY(-200);
        light.setTranslateZ(-200);

        javafx.scene.AmbientLight ambient = new javafx.scene.AmbientLight(Color.color(0.3, 0.3, 0.3));

        root.getChildren().addAll(light, ambient);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateX(-300);
        camera.setTranslateY(-50);
        camera.setTranslateZ(-50);

        Scene scene = new Scene(root, 800, 600, true);
        scene.setCamera(camera);

        stage.setScene(scene);
        stage.setTitle("Wykres");
        stage.show();
    }


    public static MeshView createSurface() {
        TriangleMesh triangleMesh = new TriangleMesh();
        int size = 50;
        float min = -2f, max = 2f;
        float step = (max - min) / (size -1);

        for (int i = 0; i < size; i++) {
            float y = min + i * step;
            for (int j = 0; j < size; j++) {
                float x = min + j * step;
                float z = (float) (10 * x * x + 12 * x * y + 10 * y * y);
                triangleMesh.getPoints().addAll(x * 40, -z * 0.1f, y * 40);
            }
        }

        triangleMesh.getTexCoords().addAll(0, 0);

        for (int y = 0; y < size - 1; y++) {
            for (int x = 0; x < size - 1; x++) {
                int p0 = y * size + x;
                int p1 = p0 + 1;
                int p2 = p0 + size;
                int p3 = p2 + 1;

                triangleMesh.getFaces().addAll(p0,0, p2,0, p1,0);
                triangleMesh.getFaces().addAll(p1,0, p2,0, p3,0);
            }
        }

        MeshView mv = new MeshView(triangleMesh);
        mv.setMaterial(new PhongMaterial(Color.ORANGE));
        return mv;
    }
}

