package ch.tbz.m320.currency.controller;

import ch.tbz.m320.currency.exception.ApiConnectionException;
import ch.tbz.m320.currency.exception.InvalidAmountException;
import ch.tbz.m320.currency.exception.InvalidCurrencyException;
import ch.tbz.m320.currency.model.Currency;
import ch.tbz.m320.currency.model.CurrencyPair;
import ch.tbz.m320.currency.model.ExchangeRateData;
import ch.tbz.m320.currency.service.CurrencyApiService;
import ch.tbz.m320.currency.service.CurrencyConversionService;
import ch.tbz.m320.currency.service.DataValidationService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CurrencyController {
    private final CurrencyApiService currencyApiService;
    private final DataValidationService validationService;
    private final CurrencyConversionService conversionService;
    private final List<CurrencyPair> favoritePairs;
    private final LinkedList<ExchangeRateData> conversionHistory;
    private Currency preferredBaseCurrency;
    private static final int MAX_HISTORY_SIZE = 10;

    public CurrencyController() {
        this.currencyApiService = new CurrencyApiService();
        this.validationService = new DataValidationService();
        this.conversionService = new CurrencyConversionService();
        this.favoritePairs = new ArrayList<>();
        this.conversionHistory = new LinkedList<>();
        this.preferredBaseCurrency = Currency.USD;
    }

    public ExchangeRateData getExchangeRate(String fromCurrencyCode, String toCurrencyCode)
            throws InvalidCurrencyException, ApiConnectionException {
        Currency fromCurrency = validationService.validateCurrency(fromCurrencyCode);
        Currency toCurrency = validationService.validateCurrency(toCurrencyCode);

        ExchangeRateData rateData = currencyApiService.fetchExchangeRate(fromCurrency, toCurrency);

        addToHistory(rateData);

        return rateData;
    }

    public String convertCurrency(String fromCurrencyCode, String toCurrencyCode, double amount)
            throws InvalidCurrencyException, ApiConnectionException, InvalidAmountException {
        validationService.validateAmount(amount);

        ExchangeRateData rateData = getExchangeRate(fromCurrencyCode, toCurrencyCode);
        double convertedAmount = conversionService.convertAmount(amount, rateData.getExchangeRate());

        return conversionService.formatConversion(
                amount, rateData.getCurrencyPair().getFromCurrency(),
                convertedAmount, rateData.getCurrencyPair().getToCurrency()
        );
    }

    public void addFavoritePair(String fromCurrencyCode, String toCurrencyCode) throws InvalidCurrencyException {
        Currency fromCurrency = validationService.validateCurrency(fromCurrencyCode);
        Currency toCurrency = validationService.validateCurrency(toCurrencyCode);

        CurrencyPair pair = new CurrencyPair(fromCurrency, toCurrency);
        if (!favoritePairs.contains(pair)) {
            favoritePairs.add(pair);
        }
    }

    public void removeFavoritePair(CurrencyPair pair) {
        favoritePairs.remove(pair);
    }

    public List<CurrencyPair> getFavoritePairs() {
        return new ArrayList<>(favoritePairs);
    }

    public List<ExchangeRateData> getConversionHistory() {
        return new ArrayList<>(conversionHistory);
    }

    public void setPreferredBaseCurrency(String currencyCode) throws InvalidCurrencyException {
        this.preferredBaseCurrency = validationService.validateCurrency(currencyCode);
    }

    public Currency getPreferredBaseCurrency() {
        return preferredBaseCurrency;
    }

    private void addToHistory(ExchangeRateData data) {
        conversionHistory.addFirst(data);

        if (conversionHistory.size() > MAX_HISTORY_SIZE) {
            conversionHistory.removeLast();
        }
    }

    public ExchangeRateData getMostRecentConversion() {
        return conversionHistory.isEmpty() ? null : conversionHistory.getFirst();
    }

    public void clearHistory() {
        conversionHistory.clear();
    }

    public boolean isServiceAvailable() {
        return currencyApiService.isServiceAvailable();
    }

    public String[] getAllCurrencyCodes() {
        Currency[] currencies = Currency.values();
        String[] codes = new String[currencies.length];
        for (int i = 0; i < currencies.length; i++) {
            codes[i] = currencies[i].name();
        }
        return codes;
    }
}
