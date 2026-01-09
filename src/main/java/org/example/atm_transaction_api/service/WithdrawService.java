package org.example.atm_transaction_api.service;

import org.example.atm_transaction_api.dto.WithdrawDTO;
import org.example.atm_transaction_api.entity.*;
import org.example.atm_transaction_api.repo.AccountRepo;
import org.example.atm_transaction_api.repo.AmountRepo;
import org.example.atm_transaction_api.repo.AtmFunctionRepo;
import org.example.atm_transaction_api.repo.TransactionRepo;
import org.example.atm_transaction_api.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AmountRepo amountRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AtmFunctionRepo atmFunctionRepo;

    public ResponseObject withdrawAmount(Card card,int amount){

        ResponseObject responseObject = new ResponseObject();

        List<Account> accountList = accountRepo.findByCard(card);
        //System.out.println(accountList);
        List<Amount> byAccount = amountRepo.findByAccount(accountList.get(0));

        int iniAmount = byAccount.get(0).getAmount();

        if (amount>iniAmount){
            responseObject.setMessage("Request Cannot be complete. Amount In your account less than amount you Requested");
            responseObject.setStatus(false);
        }else{
            byAccount.get(0).setAmount(iniAmount-amount);
            byAccount.get(0).setAccount(accountList.get(0));

            amountRepo.save(byAccount.get(0));


            Date date = new Date();
            AtmFunction atmFunction = atmFunctionRepo.findById(2).orElseThrow(() -> new RuntimeException("Function not found"));

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDate(date);
            transaction.setAccount(accountList.get(0));
            transaction.setAtmFunction(atmFunction);

            transactionRepo.save(transaction);

            responseObject.setMessage("Your Request Completed. Please wait for a while and take your Money !");

            responseObject.setStatus(true);
        }
        return responseObject;
    }
}
