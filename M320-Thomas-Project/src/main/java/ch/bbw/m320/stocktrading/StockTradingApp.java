package ch.bbw.m320.stocktrading;

import ch.bbw.m320.stocktrading.ui.ConsoleUI;

/**
 * Main entry point for the Stock Trading Simulator application.
 * M320 Project - Kompetenznachweis
 *
 * This application demonstrates:
 * - Object-Oriented Programming (OOP) concepts
 * - Design Patterns (Singleton, Repository, etc.)
 * - Clean Code principles
 * - Exception Handling
 * - Separation of Concerns (UI, Service, Repository layers)
 *
 * @author Thomas
 * @version 1.0
 */
public class StockTradingApp {

    /**
     * Main method to start the application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create and start the console UI
        ConsoleUI ui = new ConsoleUI();
        ui.start();
    }
}
