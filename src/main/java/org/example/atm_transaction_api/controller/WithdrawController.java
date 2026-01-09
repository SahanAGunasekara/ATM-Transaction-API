package org.example.atm_transaction_api.controller;

import jakarta.servlet.http.HttpSession;
import org.example.atm_transaction_api.dto.WithdrawDTO;
import org.example.atm_transaction_api.entity.Card;
import org.example.atm_transaction_api.service.DeatailConfirmationService;
import org.example.atm_transaction_api.service.WithdrawService;
import org.example.atm_transaction_api.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/withdraw")
@CrossOrigin
public class WithdrawController {

    @Autowired
    private DeatailConfirmationService detailConfirmationService;
    @Autowired
    private WithdrawService withdrawService;

    @PostMapping("/withdrawAmount")
    public String withdraw(@RequestBody WithdrawDTO withdrawDTO, HttpSession session){

        Object[] response =
                detailConfirmationService.confirmation(withdrawDTO, session);
        ResponseObject o = (ResponseObject) response[0];
        Card card = (Card) response[1];
        if(!o.isStatus()){
            return o.getMessage();
        }else{
            //System.out.println("Got It & send to withdraw process");
            ResponseObject resObj = withdrawService.withdrawAmount(card, withdrawDTO.getAmount());
            if(!resObj.isStatus()){
                return resObj.getMessage();
            }else{
                return resObj.getMessage();
            }
        }


    }
}
