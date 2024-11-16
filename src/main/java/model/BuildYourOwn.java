package model;

import model.enums.Crust;
import model.enums.Size;

public class BuildYourOwn extends Pizza {

    public BuildYourOwn(Crust crust, Size size) {
        super(crust, size);
    }

    @Override
    public double price() {
        double basePrice;
        switch (getSize()) {
            case SMALL:
                basePrice = 8.99;
                break;
            case MEDIUM:
                basePrice = 10.99;
                break;
            case LARGE:
                basePrice = 12.99;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + getSize());
        }
        // Add cost for each topping
        return basePrice + (getToppings().size() * 1.69);
    }
}

