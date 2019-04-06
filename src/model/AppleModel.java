package model;

import Utilities.BoardField;
import java.util.Random;

public class AppleModel {


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
        int locationY = 0;
        BoardField appleLocation = new BoardField(0, 0);

        Random rand = new Random();
        int number = rand.nextInt(37);
        locationX += number;
        appleLocation.setBoardFieldX(locationX);

        number = rand.nextInt(26);
        locationY += number;
        appleLocation.setBoardFieldY(locationY);

        return appleLocation;

    }

}
