package org.example.atm_transaction_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementDTO {
    private String cardNumber;
    private String pin;
    private String expireDate;
    private String cvcNumber;
    private String accountType;
}
