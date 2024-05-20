package ru.gb.saikalb;

/**
 * TODO 2.
 * Реализуйте простой обобщённый класс, такой как пара или кортеж. Затем создайте обобщенный метод, который работает
 * с этим классом. Например, вы можете создать метод, который принимает пару и возвращает их сумму или конкатенацию.
 */
public class Main {
    public static void main(String[] args) {
        Pair<Integer> digits = new Pair<>(10, 20);
        Pair<String> strings = new Pair<>("10", "20");

        System.out.println(digits + " = " + digits.sum());
        System.out.println(strings + " = " + strings.sum());
    }

    }
