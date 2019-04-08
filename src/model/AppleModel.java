package model;

import Utilities.BoardField;
import java.util.Random;

public final class AppleModel {


    private BoardField appleLocation;
    
    public AppleModel(){
        this.appleLocation = randomizeAppleLocation();
    }


    public BoardField getAppleLocation() {
        return appleLocation;
    }

    public void setAppleLocation(BoardField appleLocation) {
        this.appleLocation = appleLocation;
    }

    public BoardField randomizeAppleLocation() {
        int locationX = 0;
        int locationY = 1;
        BoardField randomAppleLocation;

        Random rand = new Random();
        int number = rand.nextInt(37);
        locationX += number;
        

        number = rand.nextInt(26);
        locationY += number;
        
        randomAppleLocation = new BoardField(locationX, 1);

        return randomAppleLocation;

    }

}
