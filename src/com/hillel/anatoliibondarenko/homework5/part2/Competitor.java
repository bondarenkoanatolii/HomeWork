package com.hillel.anatoliibondarenko.homework5.part2;

public abstract class Competitor {
    final private String category;
    final private String name;
    final private double limitRun;
    final private double limitJump;
    private boolean isFailObstacle;

    public Competitor(String category, String name, double limitRun, double limitJump) {
        this.category = category;
        this.name = name;
        this.limitRun = limitRun;
        this.limitJump = limitJump;
    }

    public boolean isFailObstacle() {
        return isFailObstacle;
    }

    public void overcome(Obstacle obstacle) {
        if (obstacle instanceof Wall) {
            jump((Wall) obstacle);
        } else if (obstacle instanceof RunningTrack) {
            run((RunningTrack) obstacle);
        }
    }

    private void jump(Wall wall){
        action(wall.getName(), wall.getHeight(), limitJump);
    }

    private void run(RunningTrack track){
        action(track.getName(), track.getLength(), limitRun);
    }

    private void action(String nameObstacle, double valueObstacle, double limit) {
        if (limit > valueObstacle) {
            printOKResultCompetition(nameObstacle, valueObstacle);
        } else {
            printFailResultCompetition(nameObstacle, valueObstacle, limit);
            isFailObstacle = true;
        }
    }

    private void printOKResultCompetition(String obstacleName, double obstacleValue) {
        System.out.printf("%s %s passed an obstacle \"%s\" on the distance %.1fm\n", category, name,
                obstacleName, obstacleValue);
    }

    private void printFailResultCompetition(String obstacleName, double obstacleValue, double limit) {
        System.out.printf("%s %s didn't pass an obstacle \"%s\" on the distance %.1fm. Was passed %.1fm\n", category,
                name, obstacleName, obstacleValue, limit);
    }
}
