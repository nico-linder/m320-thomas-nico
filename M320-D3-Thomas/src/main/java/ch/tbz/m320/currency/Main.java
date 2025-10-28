package ch.tbz.m320.currency;

import ch.tbz.m320.currency.controller.CurrencyController;
import ch.tbz.m320.currency.ui.CurrencyUI;

/**
 * Main entry point for the Currency Converter application.
 * This application demonstrates MVC architecture and OOP principles
 * by providing currency conversion functionality with a console interface.
 *
 * @author Thomas & Nico
 * @version 1.0
 */
public class Main {
    /**
     * Starts the Currency Converter application.
     * Creates the controller and UI, then launches the user interface.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create the controller to manage business logic
        CurrencyController controller = new CurrencyController();

        // Create the UI with the controller
        CurrencyUI ui = new CurrencyUI(controller);

        // Start the application
        ui.start();
    }
}
