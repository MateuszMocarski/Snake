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
        int locationX = 45;
        int locationY = 101;
        BoardField appleLocation = new BoardField(95, 176);

        Random rand = new Random();
        int number = rand.nextInt(36);
        locationX += 25 * number;
        appleLocation.setBoardFieldX(locationX);

        number = rand.nextInt(26);
        locationY += 25 * number;
        appleLocation.setBoardFieldY(locationY);

        return appleLocation;

    }

}
