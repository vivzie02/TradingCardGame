package at.fhtw.swen1.mcg.dto;

public class MagicCard extends Card{
    public MagicCard(String elementType, float damage, String cardID) {
        this.name = "Spell";

        this.elementType = elementType;

        this.damage = damage;

        this.cardID = cardID;
    }

    public Card randomizeCard(){
        int rand = (int) (Math.random() * 3);
        this.elementType = types.values()[rand].toString();

        rand = (int) ((Math.random() * 100) + 1);
        this.damage = rand;
        return this;
    }
}
