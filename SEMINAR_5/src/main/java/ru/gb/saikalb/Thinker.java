package ru.gb.saikalb;

import java.util.Random;

/**
 * Класс Thinker имплементирует интерфейс Runnable, что означает он может выполняться как отдельный поток.
 * Метод run класса Thinker выполняется продолжительно, проверяя, голоден ли философ, и пытается ли он поесть.
 * Если философ не голоден он думает. Если голоден, то ест.
 *
 */

public class Thinker implements Runnable{
    private String name;
    Fork left, right;
    Action action;
    boolean hungry;

    @Override
    public void run() {
        while (true) {
            if (hungry) {
                try {
                    hungry = eat();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else ponders();
            try {
                timer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * перечисление действий
     */

    public enum Action{
        eats, ponders
    }

    /**
     * Метод getAction типа Action
     * @return одно из действий их класса перечислений Action/
     */
    public Action getAction(){
        return action;
    }

    public Thinker(String name, Fork left, Fork right){
        this.left = left;
        this.right = right;
        this.name = name; //имя философа
        this.action =Action.ponders; //начальное действие философов: размышляет.
        this.hungry= false; // не голодны.
    }

    /**
     * Метод eat() выполняет действие покушать при условии, что философ голоден, взял левую и правую вилки одновременно.
     * @return возвращает false если заданные условия не выполнены.
     * @throws InterruptedException
     */
    public boolean eat() throws InterruptedException{
        boolean statusLeftFork =left.getAction() == Fork.Action.notUsed;
        boolean statusRightFork =right.getAction() == Fork.Action.notUsed;

        if (statusLeftFork && statusRightFork && hungry){
            left.action = Fork.Action.used; // левая вилка использована
            right.action = Fork.Action.used; // и правая вилка использована
            action = Action.eats; //происходит действие поедания.
            System.out.println(name + ": " + action);
        }
        Thread.sleep(5000); //иначе философ ждет 5сек.чтобы покушать.
        return false;
    }

    /**
     * Метод ponders() выполняет дейтствие "мыслить" когда вилки не использованы и философ не голоден, ничего не возвращает.
     */
    public void ponders()  {
        if (!hungry){ //если не голоден
            left.action = Fork.Action.notUsed; //левая вилка не используется
            right.action = Fork.Action.notUsed; //правая вилка не используется
            action = Action.ponders; //философ размышляет
            System.out.println(name + ": " + action);
        }
    }

    /**
     * Метод timer вызывается в конце каждой итерации метода run. Он проверяет, не ест ли философ,
     * и устанавливает флаг hungry в значение true после случайной задержки.
     * @throws InterruptedException
     */

    private void timer() throws InterruptedException {
        if (Action.eats != getAction()){ //если текущее действие "Не кушает", то включается случайная задержка
            Thread.sleep(getMills());//задержка
            hungry = true; // флаг голоден поднимается
            System.out.println(name + " hungry: " + hungry);
        }
    }

    /**
     * метод getMills возвращает случайную задержку между указанными значениями
     * @return mills.
     */

    private int getMills(){
        Random r = new Random();
        int mills =  r.nextInt(2500, 10000);
//        System.out.println("mils= " + mills);
        return mills;
    }

    @Override
    public String toString() {
        return String.format("Philosopher: %s", name);
    }
}
