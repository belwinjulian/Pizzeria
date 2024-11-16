package model;

import model.enums.Crust;
import model.enums.Size;
import model.interfaces.PizzaFactory;

public class ChicagoPizza implements PizzaFactory {

    @Override
    public Pizza createDeluxe(Size size) {
        return new Deluxe(Crust.DEEP_DISH, size);
    }

    @Override
    public Pizza createMeatzza(Size size) {
        return new Meatzza(Crust.STUFFED, size);
    }

    @Override
    public Pizza createBBQChicken(Size size) {
        return new BBQChicken(Crust.PAN, size);
    }

    @Override
    public Pizza createBuildYourOwn(Size size) {
        return new BuildYourOwn(Crust.PAN, size);
    }
}

