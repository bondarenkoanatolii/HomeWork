package com.hillel.anatoliibondarenko.homework5.part2;

public class Example {
    public static void main(String[] args) {
        Competitor[] competitors = createCompetitors();
        Obstacle[] obstacles = createObstacles();

        for (Competitor competitor : competitors) {
            for (Obstacle obstacle : obstacles) {
                if (!competitor.overcome(obstacle)) break;
            }
            System.out.println();
        }
    }

    private static Obstacle[] createObstacles() {
        Obstacle[] obstacles = new Obstacle[6];

        obstacles[0] = new Wall("Low wall", 1.2);
        obstacles[1] = new RunningTrack("Short track", 400);
        obstacles[2] = new Wall("Medium wall", 1.5);
        obstacles[3] = new RunningTrack("Medium track", 1500);
        obstacles[4] = new Wall("High wall", 2.0);
        obstacles[5] = new RunningTrack("Long track", 10000);

        return obstacles;
    }

    private static Competitor[] createCompetitors() {
        Competitor[] competitors = new Competitor[9];

        competitors[0] = new Human("Taras", 2000, 1.6);
        competitors[1] = new Human("Vasil", 1000, 1.3);
        competitors[2] = new Human("Oleg", 15000, 2.2);

        competitors[3] = new Cat("Pushok", 2000, 2.5);
        competitors[4] = new Cat("Murzik", 1300, 1.8);
        competitors[5] = new Cat("Matroskin", 500, 1.4);

        competitors[6] = new Robot("R2D2", 500, 1.3);
        competitors[7] = new Robot("Karel", 15000, 0);
        competitors[8] = new Robot("Verter", 15000, 2.5);

        return competitors;
    }
}
