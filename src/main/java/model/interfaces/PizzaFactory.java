package model.interfaces;

import model.Pizza;
import model.enums.Size;

public interface PizzaFactory {
    Pizza createDeluxe(Size size);
    Pizza createMeatzza(Size size);
    Pizza createBBQChicken(Size size);
    Pizza createBuildYourOwn(Size size);
}


