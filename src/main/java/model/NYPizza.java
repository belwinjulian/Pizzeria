package model;

import model.enums.Crust;
import model.enums.Size;
import model.interfaces.PizzaFactory;

public class NYPizza implements PizzaFactory {

    @Override
    public Pizza createDeluxe(Size size) {
        return new Deluxe(Crust.BROOKLYN, size);
    }

    @Override
    public Pizza createMeatzza(Size size) {
        return new Meatzza(Crust.HAND_TOSSED, size);
    }

    @Override
    public Pizza createBBQChicken(Size size) {
        return new BBQChicken(Crust.THIN, size);
    }

    @Override
    public Pizza createBuildYourOwn(Size size) {
        return new BuildYourOwn(Crust.HAND_TOSSED, size);
    }
}


