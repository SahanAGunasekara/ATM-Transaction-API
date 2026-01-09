package org.example.atm_transaction_api.controller;

import org.example.atm_transaction_api.dto.DepositDTO;
import org.example.atm_transaction_api.entity.Account;
import org.example.atm_transaction_api.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/deposit")
@CrossOrigin
public class DepositController {

    @Autowired
    private DepositService depositService;

    @PostMapping("/depositDetails")
    public String depositDetails(@RequestBody DepositDTO depositDTO){
        Account account = depositService.depositDetail(depositDTO.getAccountNumber());
        return "Account Number : "+account.getAccountNumber()+" \n"
                +"Account Holder : "+account.getHolderName();

    }

    @PostMapping("/depositMoney")
    public String depositMoney(@RequestBody DepositDTO depositDTO){
        String s = depositService.depositFunction(depositDTO);
        return s;
    }

}
