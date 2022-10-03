public class MagicCard extends Card{
    public MagicCard() {
        this.name = "Spell";

        int rand = (int) (Math.random() * 3);
        this.elementType = types.values()[rand].toString();

        rand = (int) ((Math.random() * 100) + 1);
        this.damage = rand;
    }
}
