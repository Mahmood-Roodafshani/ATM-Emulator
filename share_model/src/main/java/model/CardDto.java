package model;

import enums.AuthMethod;
import enums.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    @Pattern(regexp = "[\\d]{16}")
    @NotNull
    private String cardNumber;
    @NotNull
    private CardStatus cardStatus;
    @Pattern(regexp = "[1-3]{1}")
    private byte numberOfLoginTries;
    private AuthMethod authMethod;
    private String authenticationValue;
}
