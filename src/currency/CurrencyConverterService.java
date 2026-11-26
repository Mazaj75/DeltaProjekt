package currency;

import java.util.EnumMap;
import java.util.Map;

/**
 * Převádí částky mezi měnami. Kurzy jsou pevně dané (ilustrační),
 * všechny jsou vyjádřené vůči CZK.
 */
public class CurrencyConverterService {

    private final Map<Currency, Double> rateToCzk = new EnumMap<>(Currency.class);

    public CurrencyConverterService() {
        rateToCzk.put(Currency.CZK, 1.0);
        rateToCzk.put(Currency.EUR, 24.30);
        rateToCzk.put(Currency.USD, 21.00);
    }

    public double convert(double amount, Currency from, Currency to) {
        if (from == to) {
            return amount;
        }

        return amount * rateToCzk.get(from) / rateToCzk.get(to);
    }
}