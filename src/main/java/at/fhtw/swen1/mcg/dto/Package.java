package at.fhtw.swen1.mcg.dto;

import java.util.ArrayList;
import java.util.List;

public class Package {
    private List<Card> cardsInPackage = new ArrayList<>();

    public List<Card> getCardsInPackage(){
        return this.cardsInPackage;
    }

    public void setPackage(List<Card> test){
        this.cardsInPackage = test;
    }
}
