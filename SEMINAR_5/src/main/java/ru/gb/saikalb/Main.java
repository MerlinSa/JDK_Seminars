package ru.gb.saikalb;

/** TODO 5.
 * 1.Пять безмолвных философов сидят вокруг круглого стола, перед каждым философом стоит тарелка спагетти.
 * 2.Вилки лежат на столе между каждой парой ближайших философов.
 * 3.Каждый философ может либо есть, либо размышлять.
 * 4.Философ может есть только тогда, когда держит две вилки — взятую справа и слева.
 * 5.Философ не может есть два раза подряд, не прервавшись на размышления (можно не учитывать)
 * 6.Философ может брать две вилки одновременно.
 *
 * Описать в виде кода такую ситуацию. Каждый философ должен поесть 3 раза.
 */
public class Main {
    public static void main(String[] args) {
        Table table = new Table();
        for (int i = 1; i <= 5; i++) {
            table.add(new Fork());
        }

        System.out.println(Thread.currentThread());
        Thread philosopher1 = new Thread(new Thinker("Философ_1", table.get(0), table.get(1)));
        Thread philosopher2 = new Thread(new Thinker("Философ_2", table.get(1), table.get(2)));
        Thread philosopher3 = new Thread(new Thinker("Философ_3", table.get(2), table.get(3)));
        Thread philosopher4 = new Thread(new Thinker("Философ_4", table.get(3), table.get(4)));
        Thread philosopher5 = new Thread(new Thinker("Философ_5", table.get(4), table.get(0)));
        philosopher1.start();
        philosopher2.start();
        philosopher3.start();
        philosopher4.start();
        philosopher5.start();

    }
}
