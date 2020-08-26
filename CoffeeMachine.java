package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {

        final Scanner scan = new Scanner(System.in);

        CoffeeMachineOb coffeeMachine = new CoffeeMachineOb();
        boolean coffeeMachineWorks = true;

        while (coffeeMachineWorks) {

            System.out.println("\nWrite action (buy, fill, take, remaining, exit): ");

            switch (scan.next()) {
                case "remaining":
                    coffeeMachine.printStatus();
                    continue;

                case "fill":
                    System.out.println("\nWrite how many ml of water do you want to add: ");
                    coffeeMachine.fillWater(scan.nextLong());

                    System.out.println("Write how many ml of milk do you want to add: ");
                    coffeeMachine.fillMilk(scan.nextLong());

                    System.out.println("Write how many grams of coffee beans do you want to add: ");
                    coffeeMachine.fillBeans(scan.nextLong());

                    System.out.println("Write how many disposable cups of coffee do you want to add: ");
                    coffeeMachine.fillCups(scan.nextLong());

                    continue;

                case "take":
                    coffeeMachine.takeMoney();
                    continue;

                case "buy":
                    System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back" +
                            " - to main menu: ");
                    coffeeMachine.buy(scan.next());
                    continue;

                case "exit":
                    coffeeMachineWorks = false;
            }

        }

    }
}

class CoffeeMachineOb {

    private final int espressoWaterGrams = 250;
    private final int espressoBeansGrams = 16;
    private final int espressoCost = 4;

    private final int latteWaterGrams = 350;
    private final int latteMilkGrams = 75;
    private final int latteBeansGrams = 20;
    private final int latteCost = 7;

    private final int cappuccinoWaterGrams = 200;
    private final int cappuccinoMilkGrams = 100;
    private final int cappuccinoBeansGrams = 12;
    private final int cappuccinoCost = 6;

    private long cappuccinosCanMake;
    private long lattesCanMake;
    private long espressosCanMake;

    private long waterContains = 400;
    private long milkContains = 540;
    private long beansContains = 120;
    private long cupsContains = 9;
    private long moneyContains = 550;

    public CoffeeMachineOb() {

        cappuccinosCanMake = cappuccinosCanMake();
        lattesCanMake = lattesCanMake();
        espressosCanMake = espressoCanMake();

    }

    private long cappuccinosCanMake() {

        long fromWater = waterContains / cappuccinoWaterGrams;
        long fromBeans = beansContains / cappuccinoBeansGrams;
        long fromMilk = milkContains / cappuccinoMilkGrams;

        return Math.min(Math.min(fromBeans, fromWater), fromMilk);
    }

    private long lattesCanMake() {

        long fromWater = waterContains / latteWaterGrams;
        long fromBeans = beansContains / latteBeansGrams;
        long fromMilk = milkContains / latteMilkGrams;

        return Math.min(Math.min(fromBeans, fromWater), fromMilk);
    }

    private long espressoCanMake() {

        long fromWater = waterContains / espressoWaterGrams;
        long fromBeans = beansContains / espressoBeansGrams;

        return Math.min(fromBeans, fromWater);
    }

    private void makeEspresso() {

        if (espressosCanMake > 0 && cupsContains > 0) {
            System.out.println("I have enough resources, making you a coffee!");
            waterContains -= espressoWaterGrams;
            beansContains -= espressoBeansGrams;

            espressosCanMake = espressoCanMake();
            cupsContains--;

        } else {
            lackOf(true);
        }
    }

    private void lackOf(boolean forEspresso) {

        String lackOf;

        if (forEspresso) {
            lackOf = waterContains < beansContains ? "water!" : "beans!";
        } else {
            lackOf = waterContains < milkContains ? "water!" : milkContains < beansContains ? "milk!" : "beans!";
        }
        System.out.println("Sorry, not enough " + lackOf);

    }

    private void makeLatte() {
        if (lattesCanMake > 0 && cupsContains > 0) {

            System.out.println("I have enough resources, making you a coffee!");

            waterContains -= latteWaterGrams;
            beansContains -= latteBeansGrams;
            milkContains -= latteMilkGrams;

            lattesCanMake = lattesCanMake();

            cupsContains--;

        } else {
            lackOf(false);
        }
    }

    private void makeCappuccino() {
        if (cappuccinosCanMake > 0 && cupsContains > 0) {
            System.out.println("I have enough resources, making you a coffee!");
            waterContains -= cappuccinoWaterGrams;
            beansContains -= cappuccinoBeansGrams;
            milkContains -= cappuccinoMilkGrams;

            cappuccinosCanMake = cappuccinosCanMake();
            cupsContains--;

        } else {
            lackOf(false);
        }
    }

    public void buy(String typeOfCoffee) {

        final String CAPPUCCINO = "3";
        final String ESPRESSO = "1";
        final String LATTE = "2";
        final String EXIT = "exit";

        switch (typeOfCoffee) {
            case ESPRESSO:
                makeEspresso();
                moneyContains += espressoCost;
                break;

            case LATTE:
                makeLatte();
                moneyContains += latteCost;
                break;

            case CAPPUCCINO:
                makeCappuccino();
                moneyContains += cappuccinoCost;
                break;

            case EXIT:
                break;
        }
    }

    public void fillWater(long ml) {
        waterContains += ml;

        lattesCanMake = lattesCanMake();
        cappuccinosCanMake = cappuccinosCanMake();
        espressosCanMake = espressoCanMake();
    }

    public void fillMilk(long ml) {
        milkContains += ml;

        lattesCanMake = lattesCanMake();
        cappuccinosCanMake = cappuccinosCanMake();
        espressosCanMake = espressoCanMake();
    }

    public void fillBeans(long grams) {
        beansContains += grams;

        lattesCanMake = lattesCanMake();
        cappuccinosCanMake = cappuccinosCanMake();
        espressosCanMake = espressoCanMake();
    }

    public void fillCups(long cups) {
        cupsContains += cups;
    }

    public void takeMoney() {
        System.out.println("\nI gave you $" + moneyContains);
        moneyContains = 0;
    }

    public void printStatus() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(waterContains + " of water");
        System.out.println(milkContains + " of milk");
        System.out.println(beansContains + " of coffee beans");
        System.out.println(cupsContains + " of disposable cups");
        System.out.println(moneyContains + " of money");
    }
}
