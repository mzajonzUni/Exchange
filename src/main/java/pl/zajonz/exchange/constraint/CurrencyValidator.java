package pl.zajonz.exchange.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import pl.zajonz.exchange.model.AvailableCurrencies;

@RequiredArgsConstructor
public class CurrencyValidator implements ConstraintValidator<CurrencyConstraint, String> {
    private final AvailableCurrencies availableCurrencies;

    @Override
    public void initialize(CurrencyConstraint currencyConstraint) {
    }

    @Override
    public boolean isValid(String currency, ConstraintValidatorContext constraintValidatorContext) {
        if (currency == null) {
            return true;
        }

        return availableCurrencies.getSymbols().containsKey(currency);
    }
}
