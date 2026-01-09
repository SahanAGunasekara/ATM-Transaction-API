package org.example.atm_transaction_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositDTO {
    private String accountNumber;
    private int amount;
}
