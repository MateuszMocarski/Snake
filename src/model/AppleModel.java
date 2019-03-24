
package model;

public class AppleModel {
    
    private static int totalAmountOfEatenApples = 0;
    
    public static int getTotalAmountOfEatenApples() {
        return totalAmountOfEatenApples;
    }
    public static void setTotalAmountOfEatenApples(int totalAmountOfEatenApples) {
        AppleModel.totalAmountOfEatenApples = totalAmountOfEatenApples;
    }
}
