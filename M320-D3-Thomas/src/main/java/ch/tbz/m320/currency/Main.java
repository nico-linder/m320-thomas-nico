package ch.tbz.m320.currency;

import ch.tbz.m320.currency.controller.CurrencyController;
import ch.tbz.m320.currency.ui.CurrencyUI;

public class Main {
    public static void main(String[] args) {
        CurrencyController controller = new CurrencyController();

        CurrencyUI ui = new CurrencyUI(controller);

        ui.start();
    }
}
