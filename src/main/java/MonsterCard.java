import java.lang.Math;

public class MonsterCard extends Card{

    private enum names{
        GOBLIN, DRAGON, WIZARD, ORK, KNIGHT, KRAKEN, ELF
    }

    public MonsterCard(String name, String elementType, int damage) {
        this.name = name;
        this.elementType = elementType;
        this.damage = damage;
    }

    public Card randomizeCard(){
        int rand = (int) (Math.random() * 7);
        this.name = names.values()[rand].toString();

        rand = (int) (Math.random() * 3);
        this.elementType = types.values()[rand].toString();

        rand = (int) ((Math.random() * 100) + 1);
        this.damage = rand;

        return this;
    }
}
