package org.example.atm_transaction_api.service;

import jakarta.servlet.http.HttpSession;
import org.example.atm_transaction_api.dto.WithdrawDTO;
import org.example.atm_transaction_api.entity.Card;
import org.example.atm_transaction_api.repo.AccountRepo;
import org.example.atm_transaction_api.repo.CardRepo;
import org.example.atm_transaction_api.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DeatailConfirmationService {

    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private AccountRepo accountRepo;

    public Object[] confirmation(WithdrawDTO withdrawDTO, HttpSession session){

        ResponseObject responseObject = new ResponseObject();

        Object [] x = new Object[2];

        if (withdrawDTO.getCardNumber().isEmpty()){
            responseObject.setMessage("Card Number is missing");
            responseObject.setStatus(false);
        }else if(withdrawDTO.getPin().isEmpty()){
            responseObject.setMessage("Please enter PIN");
            responseObject.setStatus(false);
        }else if(withdrawDTO.getCvcNumber().isEmpty()){
            responseObject.setMessage("CVC Number is Empty");
            responseObject.setStatus(false);
        }else if(withdrawDTO.getCvcNumber().length()>3){
            responseObject.setMessage("CVV can only contain 3 numbers");
            responseObject.setStatus(false);
        }else if(withdrawDTO.getExpireDate().isEmpty()) {
            responseObject.setMessage("Expirer date is missing");
            responseObject.setStatus(false);
        }else if(withdrawDTO.getAccountType().isEmpty()){
            responseObject.setMessage("Account type is Empty");
            responseObject.setStatus(false);
        }else{
            List<Card> iCard = cardRepo.findByCardNumberAndPassword(withdrawDTO.getCardNumber(), withdrawDTO.getPin());
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if (iCard.isEmpty()){
                //System.out.println("I got it here 1");
                responseObject.setMessage("Incorrect PIN Number");
                responseObject.setStatus(false);
                x[0]=responseObject;
                return x;
            }else if(iCard.get(0).getStatus().getStatus().equals("Innactive")) {
                //System.out.println("I got it here 2");
                responseObject.setMessage("Card is at Innactive Status");
                responseObject.setStatus(false);
                x[0]=responseObject;
                return x;
            }else if(!withdrawDTO.getExpireDate().equals(sdf.format(iCard.get(0).getExpireDate()))) {
                //System.out.println("I got it here 3");
                System.out.println(iCard.get(0).getExpireDate());
                responseObject.setMessage("Card is Expired");
                responseObject.setStatus(false);
                x[0] = responseObject;
                return x;
            }else if(Integer.parseInt(withdrawDTO.getCvcNumber())!=iCard.get(0).getCvcNumber()){
                //System.out.println("I hold this");
                responseObject.setMessage("Check card again");
                responseObject.setStatus(false);
                x[0] = responseObject;
                return x;
            } else if (!withdrawDTO.getAccountType().equals(accountRepo.findByCard(iCard.get(0)).get(0).getAccountType().getType())) {
                //System.out.println(accountRepo.findByCard(iCard.get(0)).get(0).getAccountType());
                responseObject.setMessage("Account is not a "+withdrawDTO.getAccountType()+" Account");
                responseObject.setStatus(false);
                x[0] = responseObject;
                return x;

        }else{
                System.out.println(iCard);
                responseObject.setStatus(true);
                session.setAttribute("Ucard",iCard.get(0));
                x[0]=responseObject;
                x[1]=iCard.get(0);

            }
        }

        return x;
    }
}
