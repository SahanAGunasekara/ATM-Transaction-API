package org.example.atm_transaction_api.service;

import org.example.atm_transaction_api.dto.BalanceDTO;
import org.example.atm_transaction_api.dto.ChangePinDTO;
import org.example.atm_transaction_api.entity.Card;
import org.example.atm_transaction_api.repo.AccountRepo;
import org.example.atm_transaction_api.repo.CardRepo;
import org.example.atm_transaction_api.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ChangePinService {

    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private AccountRepo accountRepo;

    public ResponseObject checkDetails(ChangePinDTO changePinDTO){


        ResponseObject responseObject = new ResponseObject();

        if (changePinDTO.getCardNumber().isEmpty()){
            responseObject.setMessage("Card Number is Missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(changePinDTO.getPin().isEmpty()){
            responseObject.setMessage("PIN is Missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(changePinDTO.getCvcNumber().isEmpty()){
            responseObject.setMessage("CVV is Missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(changePinDTO.getCvcNumber().length()>3){
            responseObject.setMessage("CVV can only contain 3 numbers");
            responseObject.setStatus(false);
            return responseObject;
        }else if(changePinDTO.getExpireDate().isEmpty()) {
            responseObject.setMessage("Expirer date is missing");
            responseObject.setStatus(false);
            return responseObject;
        }else if(changePinDTO.getAccountType().isEmpty()) {
            responseObject.setMessage("Account type is Empty");
            responseObject.setStatus(false);
            return responseObject;
        }else{
            List<Card> cardList = cardRepo.findByCardNumberAndPassword(changePinDTO.getCardNumber(), changePinDTO.getPin());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (cardList.isEmpty()){
                responseObject.setMessage("PIN number is Incorrect");
                responseObject.setStatus(false);
                return responseObject;
            }else if(cardList.get(0).getStatus().getStatus().equals("Innactive")){
                responseObject.setMessage("Card Is in Innactive state");
                responseObject.setStatus(false);
                return responseObject;
            }else if(!changePinDTO.getExpireDate().equals(sdf.format(cardList.get(0).getExpireDate()))) {
                responseObject.setMessage("Card Expired");
                responseObject.setStatus(false);
                return responseObject;
            }else if(Integer.parseInt(changePinDTO.getCvcNumber())!=cardList.get(0).getCvcNumber()){
                //System.out.println("I hold this");
                responseObject.setMessage("Check card again");
                responseObject.setStatus(false);
                return responseObject;
            }else if(!changePinDTO.getAccountType().equals(accountRepo.findByCard(cardList.get(0)).get(0).getAccountType().getType())){
                responseObject.setMessage("Account is not a "+changePinDTO.getAccountType()+" Account");
                responseObject.setStatus(false);
                return responseObject;
            }else {
                responseObject.setStatus(true);

            }
        }
        return responseObject;
    }

    public String changePin(ChangePinDTO changePinDTO){

        if (changePinDTO.getNewPin().isEmpty()){
            return "New Pin is Empty";
        }else if(changePinDTO.getNewPin().length()>4){
            return "Pin should only contain 4 numbers";
        }else{
            List<Card> cardList = cardRepo.findByCardNumber(changePinDTO.getCardNumber());
            cardList.get(0).setPassword(changePinDTO.getNewPin());
            cardRepo.save(cardList.get(0));

            return "Pin Updated Succesfully";
        }



    }

}
