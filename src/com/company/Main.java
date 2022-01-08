package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 800;
    public static int bossDamage = 50;
    public static String bossDefence = "";

    public static int[] heroesHealth = {270, 260, 250, 220, 277, 100, 200, 300};
    public static int[] heroesDamage = {15, 20, 25, 10, 7, 9, 12, 10};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int round_number = 0;
    public static boolean skip = false;


    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void changeBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefence);
    }

    public static void round() {
        round_number++;
        changeBossDefence();
        if (!skip) {
            bossHits();
        }
        heroesHit();
        luckyAvoid();
        berserkSpike();
        golemBlock();
        skipBoss();
        printStatistics();
        System.out.println("\n");
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(4) + 2;
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage to Boss: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void bossHealthIsZero() {
        if (bossHealth < 0) {
            bossHealth = 0;
        }
    }

    public static void berserkSpike() {
        int spike = bossDamage / 10;
        if (heroesHealth[6] > 0) {
            heroesDamage[6] += spike;
            bossHealth -= spike;
        }
        bossHealthIsZero();
    }

    public static void skipBoss() {
        Random random = new Random();
        int chance = random.nextInt(7);
        if (chance == 1) {
            System.out.println("Thor stunned Boss! Boss skips his next turn");
            skip = true;
        }
        else {
            skip = false;
        }
    }

    public static void luckyAvoid() {
        Random random = new Random();
        boolean avoid = random.nextBoolean();
        if (heroesHealth[5] > 0 && heroesHealth[4] > 0) {
            if (avoid) {
                heroesHealth[5] = heroesHealth[5] + bossDamage - (bossDamage/5) ;
                System.out.println("Lucky avoided from Boss");
            }
        }
        else if (heroesHealth[5] > 0 && heroesHealth[4] <= 0) {
            if (avoid) {
                heroesHealth[5] += bossDamage;
                System.out.println("Lucky avoided from Boss");
            }
        }
    }

    public static void golemBlock() {
        int block = bossDamage / 5;
        int checkAlive = 0;
        if (heroesHealth[4] > 0 && !skip) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i == 4) {
                    continue;
                } else if (heroesHealth[i] > 0) {
                    checkAlive += 1;
                    heroesHealth[i] += block;
                }

            }
            heroesHealth[4] -= block * checkAlive;
            System.out.println(heroesAttackType[4]+" took " +block * checkAlive + " damage");
        }
        if (heroesHealth[4] < 0) {
            heroesHealth[4] = 0;
        }
    }


    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + round_number + ":");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
        System.out.println("_______________________");
    }
}
