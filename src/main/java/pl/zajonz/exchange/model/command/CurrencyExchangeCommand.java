package pl.zajonz.exchange.model.command;

import lombok.Data;
import pl.zajonz.exchange.constraint.CurrencyConstraint;

import javax.validation.constraints.NotBlank;

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
