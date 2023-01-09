package com.cgvsu;

import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import javax.vecmath.Vector3f;

import com.cgvsu.math.Matrix;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;

    private Camera camera = new Camera(
            new Vector3f(0, 00, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;


    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }

    //Масштабирование
    @FXML
    public void handleScaleXPlus(ActionEvent actionEvent) {
        float[][] scaleMatrix = Matrix.getScale(1.01f, 1, 1);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(scaleMatrix));
    }

    @FXML
    public void handleScaleYPlus(ActionEvent actionEvent) {
        float[][] scaleMatrix = Matrix.getScale(1, 1.01f, 1);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(scaleMatrix));
    }

    @FXML
    public void handleScaleZPlus(ActionEvent actionEvent) {
        float[][] scaleMatrix = Matrix.getScale(1, 1, 1.01f);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(scaleMatrix));
    }

    @FXML
    public void handleScaleXMinus(ActionEvent actionEvent) {
        float[][] scaleMatrix = Matrix.getScale(0.99f, 1, 1);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(scaleMatrix));
    }

    @FXML
    public void handleScaleYMinus(ActionEvent actionEvent) {
        float[][] scaleMatrix = Matrix.getScale(1, 0.99f, 1);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(scaleMatrix));
    }

    @FXML
    public void handleScaleZMinus(ActionEvent actionEvent) {
        float[][] scaleMatrix = Matrix.getScale(1, 1, 0.99f);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(scaleMatrix));
    }


    //Перемещение
    @FXML
    public void handleMovementUp(ActionEvent actionEvent) {
        for (com.cgvsu.math.Vector3f vertex : mesh.vertices) {
            vertex.y += 0.1;
        }
    }

    @FXML
    public void handleMovementDown(ActionEvent actionEvent) {
        for (com.cgvsu.math.Vector3f vertex : mesh.vertices) {
            vertex.y -= 0.1;
        }
    }

    @FXML
    public void handleMovementLeft(ActionEvent actionEvent) {
        for (com.cgvsu.math.Vector3f vertex : mesh.vertices) {
            vertex.x += 0.1;
        }
    }

    @FXML
    public void handleMovementRight(ActionEvent actionEvent) {
        for (com.cgvsu.math.Vector3f vertex : mesh.vertices) {
            vertex.x -= 0.1;
        }
    }

    //Вращение
    @FXML
    public void handleRotationXPlus(ActionEvent actionEvent) {
        float angle = 10.0F;
        float[][] rotationMatrix = Matrix.getRotationX(angle);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(rotationMatrix));
    }

    @FXML
    public void handleRotationYPlus(ActionEvent actionEvent) {
        float angle = 10.0F;
        float[][] rotationMatrix = Matrix.getRotationY(angle);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(rotationMatrix));
    }

    @FXML
    public void handleRotationZPlus(ActionEvent actionEvent) {
        float angle = 10.0F;
        float[][] rotationMatrix = Matrix.getRotationZ(angle);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(rotationMatrix));
    }

    @FXML
    public void handleRotationXMinus(ActionEvent actionEvent) {
        float angle = -10.0F;
        float[][] rotationMatrix = Matrix.getRotationX(angle);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(rotationMatrix));
    }

    @FXML
    public void handleRotationYMinus(ActionEvent actionEvent) {
        float angle = -10.0F;
        float[][] rotationMatrix = Matrix.getRotationY(angle);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(rotationMatrix));
    }

    @FXML
    public void handleRotationZMinus(ActionEvent actionEvent) {
        float angle = -10.0F;
        float[][] rotationMatrix = Matrix.getRotationZ(angle);
        mesh.vertices.forEach(vertex -> vertex.applyMatrix(rotationMatrix));
    }

}