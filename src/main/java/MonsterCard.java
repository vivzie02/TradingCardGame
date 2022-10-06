import java.lang.Math;

public class MonsterCard extends Card{

    private enum names{
        GOBLIN, DRAGON, WIZARD, ORK, KNIGHT, KRAKEN, ELF
    }

    public MonsterCard() {
        int rand = (int) (Math.random() * 7);
        this.name = names.values()[rand].toString();

        rand = (int) (Math.random() * 3);
        this.elementType = types.values()[rand].toString();

        rand = (int) ((Math.random() * 100) + 1);
        this.damage = rand;

    }
}
