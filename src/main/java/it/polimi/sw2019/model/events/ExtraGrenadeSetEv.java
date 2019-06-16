package it.polimi.sw2019.model.events;

public class ExtraGrenadeSetEv {

    private String square;
    private String moveto;

    public ExtraGrenadeSetEv(String square) {
        this.square = square;
        moveto = null;
    }

    public ExtraGrenadeSetEv(String square, String moveto) {
        this.square = square;
        this.moveto = moveto;
    }

    public String getSquare() {
        return square;
    }

    public String getMoveto() {
        return moveto;
    }
}
