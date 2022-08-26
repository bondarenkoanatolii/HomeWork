package com.hillel.anatoliibondarenko.homework5.part2;

public abstract class Competitor {
    final private String category;
    final private String name;
    final private double limitRun;
    final private double limitJump;

    public Competitor(String category, String name, double limitRun, double limitJump) {
        this.category = category;
        this.name = name;
        this.limitRun = limitRun;
        this.limitJump = limitJump;
    }

    public boolean overcome(Obstacle obstacle) {
        if (obstacle instanceof Wall) {
            return jump((Wall) obstacle);
        } else if (obstacle instanceof RunningTrack) {
            return run((RunningTrack) obstacle);
        } else return false; // unknown obstacle
    }

    private boolean jump(Wall wall){
        return action(wall.getName(), wall.getHeight(), limitJump);
    }

    private boolean run(RunningTrack track){
        return action(track.getName(), track.getLength(), limitRun);
    }

    private boolean action(String nameObstacle, double valueObstacle, double limit) {
        if (limit > valueObstacle) {
            printOKResultCompetition(nameObstacle, valueObstacle);
            return true;
        } else {
            printFailResultCompetition(nameObstacle, valueObstacle, limit);
            return false;
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
