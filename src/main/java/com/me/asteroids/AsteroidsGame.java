package com.me.asteroids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AsteroidsGame extends Application {

    public static int WIDTH = 600;
    public static int HEIGHT = 400;
    private final double ASTEROID_SPAWN_RATE = 0.01;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Asteroids!");

        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        Scene scene = new Scene(pane);

        AtomicInteger points = new AtomicInteger();
        Text text = new Text(10, 20, "Points: " + points.get());
        pane.getChildren().add(text);

        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);

        List<Projectile> projectiles = new ArrayList<>();
        List<Asteroid> asteroids = new ArrayList<>();

        // initialize 5 asteroids for beginning of game
        for (int i = 0; i < 5; ++i) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH), rnd.nextInt(100));
            asteroids.add(asteroid);
        }

        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        scene.setOnKeyPressed(event -> pressedKeys.put(event.getCode(), Boolean.TRUE));
        scene.setOnKeyReleased(event -> pressedKeys.put(event.getCode(), Boolean.FALSE));

        new ProjectileTimer() {
            @Override
            public void handle() {
                if (pressedKeys.getOrDefault(KeyCode.SPACE, false)) {
                    // shoot; create the projectile, add to projectiles, accelerate, normalize movement speed, add to pane
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(),
                                                           (int) ship.getCharacter().getTranslateY());

                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);

                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));

                    pane.getChildren().add(projectile.getCharacter());
                }
            }
        }.start();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // ship controls
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false))
                    ship.turnLeft();

                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false))
                    ship.turnRight();

                if (pressedKeys.getOrDefault(KeyCode.UP, false))
                    ship.accelerate();

                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid))
                        stop(); // GAME OVER
                });

                // activate movement of ship, asteroids, and projectiles
                ship.move();
                asteroids.forEach(Character::move);
                projectiles.forEach(Character::move);

                // if a collision occurs, setAlive(false) for asteroids; The projectiles that destroyed the asteroid are collected into a list.
                List<Projectile> projectilesToRemove = projectiles.stream().filter(projectile -> {
                    List<Asteroid> collisions = asteroids.stream()
                            .filter(asteroid -> asteroid.collide(projectile))
                            .collect(Collectors.toList());

                    if (collisions.isEmpty())
                        return false;

                    collisions.forEach(collided -> collided.setAlive(false));

                    return true;
                }).collect(Collectors.toList());

                // Sets projectiles from above list to setAlive(false); Increments points.
                projectilesToRemove.forEach(projectile -> {
                    projectile.setAlive(false);
                    text.setText("Points: " + points.addAndGet(1000));
                });

                // remove projectiles and asteroids if isAlive == false
                projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
                projectiles.removeAll(projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .collect(Collectors.toList()));

                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                asteroids.removeAll(asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .collect(Collectors.toList()));

                // spawn more asteroids
                if (Math.random() < ASTEROID_SPAWN_RATE) {
                    Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
                    if (!asteroid.collide(ship)) {
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }

            }
        }.start();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}