package pl.zajonz.exchange.constraint;

import lombok.RequiredArgsConstructor;
import pl.zajonz.exchange.model.AvailableCurrencies;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
