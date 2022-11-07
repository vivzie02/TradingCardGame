package at.fhtw.swen1.mcg.dto;

abstract public class Card {

    public enum types{
        WATER, FIRE, REGULAR
    }
    protected String name;
    protected int damage;
    protected String elementType;

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementType() {
        return elementType;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }
}
