public class MagicCard extends Card{
    public MagicCard(String elementType, int damage) {
        this.name = "Spell";

        this.elementType = elementType;

        this.damage = damage;
    }

    public Card randomizeCard(){
        int rand = (int) (Math.random() * 3);
        this.elementType = types.values()[rand].toString();

        rand = (int) ((Math.random() * 100) + 1);
        this.damage = rand;
        return this;
    }
}
