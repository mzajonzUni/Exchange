package pl.zajonz.exchange.model.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import pl.zajonz.exchange.constraint.CurrencyConstraint;

@Data
public class CurrencyExchangeCommand {
    @NotBlank
    @CurrencyConstraint
    private String from;
    @NotBlank
    @CurrencyConstraint
    private String to;
    @NotBlank
    private String amount;
}
