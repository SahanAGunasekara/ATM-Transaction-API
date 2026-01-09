package org.example.atm_transaction_api.controller;

import org.example.atm_transaction_api.dto.BalanceDTO;
import org.example.atm_transaction_api.dto.WithdrawDTO;
import org.example.atm_transaction_api.service.CheckBalanceService;
import org.example.atm_transaction_api.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/balance")
@CrossOrigin
public class CheckBalanceController {

    @Autowired
    private CheckBalanceService checkBalanceService;

    @PostMapping("/checkBalance")
    public String checkBalance(@RequestBody BalanceDTO balanceDTO){

        ResponseObject responseObject = checkBalanceService.checkDetails(balanceDTO);

        if (!responseObject.isStatus()){
            return responseObject.getMessage();
        }else{
            int balance = checkBalanceService.checkBalance(balanceDTO.getCardNumber());
            return "Rs. "+String.valueOf(balance);
        }


    }

}
