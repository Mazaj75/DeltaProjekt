package currency;

import java.util.Locale;

/**
 * Formátuje částku pro výpis do console: "1000.00 CZK (≈ 41.15 EUR)".
 * Přepočet na druhou měnu (displayCurrency) se přidá, pokud se liší od měny částky.
 */
public class MoneyFormatter {

    private final CurrencyConverterService converter;
    private final Currency displayCurrency;

    public MoneyFormatter(CurrencyConverterService converter, Currency displayCurrency) {
        this.converter = converter;
        this.displayCurrency = displayCurrency;
    }

    public String format(double amount, Currency currency) {
        String original = plain(amount, currency);

        if (currency == displayCurrency) {
            return original;
        }

        double converted = converter.convert(amount, currency, displayCurrency);

        return original + " (≈ " + plain(converted, displayCurrency) + ")";
    }

    private String plain(double amount, Currency currency) {
        return String.format(Locale.ROOT, "%.2f %s", amount, currency);
    }
}