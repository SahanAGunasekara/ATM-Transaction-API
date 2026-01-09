package org.example.atm_transaction_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePinDTO {
    private String cardNumber;
    private String pin;
    private String newPin;
    private String expireDate;
    private String cvcNumber;
    private String accountType;
}
