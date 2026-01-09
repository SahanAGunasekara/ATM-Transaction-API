package org.example.atm_transaction_api.service;

import org.example.atm_transaction_api.dto.BalanceDTO;
import org.example.atm_transaction_api.dto.WithdrawDTO;
import org.example.atm_transaction_api.entity.Account;
import org.example.atm_transaction_api.entity.Amount;
import org.example.atm_transaction_api.entity.Card;
import org.example.atm_transaction_api.repo.AccountRepo;
import org.example.atm_transaction_api.repo.AmountRepo;
import org.example.atm_transaction_api.repo.CardRepo;
import org.example.atm_transaction_api.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class CheckBalanceService {

    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AmountRepo amountRepo;

    public ResponseObject checkDetails(BalanceDTO balanceDTO){


        ResponseObject responseObject = new ResponseObject();

        if (balanceDTO.getCardNumber().isEmpty()){
            responseObject.setMessage("Card Number is Missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(balanceDTO.getPin().isEmpty()){
            responseObject.setMessage("PIN is Missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(balanceDTO.getCvcNumber().isEmpty()){
            responseObject.setMessage("CVV is Missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(balanceDTO.getCvcNumber().length()>3){
            responseObject.setMessage("CVV can only contain 3 numbers");
            responseObject.setStatus(false);
            return responseObject;
        }else if(balanceDTO.getExpireDate().isEmpty()) {
            responseObject.setMessage("Expirer date is missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(balanceDTO.getAccountType().isEmpty()) {
            responseObject.setMessage("Account type is Empty");
            responseObject.setStatus(false);
            return responseObject;
        }else{
            List<Card> cardList = cardRepo.findByCardNumberAndPassword(balanceDTO.getCardNumber(), balanceDTO.getPin());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (cardList.isEmpty()){
                responseObject.setMessage("PIN number is Incorrect");
                responseObject.setStatus(false);
                return responseObject;
            }else if(cardList.get(0).getStatus().getStatus().equals("Innactive")){
                responseObject.setMessage("Card Is in Innactive state");
                responseObject.setStatus(false);
                return responseObject;
            }else if(!balanceDTO.getExpireDate().equals(sdf.format(cardList.get(0).getExpireDate()))){
                responseObject.setMessage("Card Expired");
                responseObject.setStatus(false);
                return responseObject;
            }else if(Integer.parseInt(balanceDTO.getCvcNumber())!=cardList.get(0).getCvcNumber()){
                //System.out.println("I hold this");
                responseObject.setMessage("Check card again");
                responseObject.setStatus(false);
                return responseObject;
            }else if(!balanceDTO.getAccountType().equals(accountRepo.findByCard(cardList.get(0)).get(0).getAccountType().getType())){
                responseObject.setMessage("Account is not a "+balanceDTO.getAccountType()+" Account");
                responseObject.setStatus(false);
                return responseObject;
            }else {
                responseObject.setStatus(true);

            }
        }
        return responseObject;
    }

    public int checkBalance(String cardNumber){

        List<Card> cardList = cardRepo.findByCardNumber(cardNumber);
        List<Account> accountList = accountRepo.findByCard(cardList.get(0));

        List<Amount> accAmount = amountRepo.findByAccount(accountList.get(0));

        return accAmount.get(0).getAmount();
    }
}
