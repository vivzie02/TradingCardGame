package at.fhtw.swen1.mcg.dto;

import java.util.*;

public class Stack {
    private List<Card> cardsInStack = new ArrayList<Card>();

    public List<Card> getCardsInStack() {
        return cardsInStack;
    }

    public void setCardsInStack(List<Card> cardsInStack) {
        this.cardsInStack = cardsInStack;
    }
}
