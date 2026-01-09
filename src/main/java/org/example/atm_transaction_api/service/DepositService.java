package org.example.atm_transaction_api.service;

import org.example.atm_transaction_api.dto.DepositDTO;
import org.example.atm_transaction_api.entity.Account;
import org.example.atm_transaction_api.entity.Amount;
import org.example.atm_transaction_api.entity.Transaction;
import org.example.atm_transaction_api.repo.AccountRepo;
import org.example.atm_transaction_api.repo.AmountRepo;
import org.example.atm_transaction_api.repo.AtmFunctionRepo;
import org.example.atm_transaction_api.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DepositService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AmountRepo amountRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AtmFunctionRepo atmFunctionRepo;

    public Account depositDetail(String accountNumber){
        Account account = accountRepo.findById(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        return account;
    }

    public String depositFunction(DepositDTO depositDTO){
        Account account = accountRepo.findById(depositDTO.getAccountNumber()).orElseThrow(() -> new RuntimeException("Account not found"));
        List<Amount> accAmount = amountRepo.findByAccount(account);
        accAmount.get(0).setAmount(accAmount.get(0).getAmount()+depositDTO.getAmount());
        accAmount.get(0).setAccount(account);

        amountRepo.save(accAmount.get(0));

        Transaction transaction = new Transaction();
        transaction.setAmount(depositDTO.getAmount());
        transaction.setDate(new Date());
        transaction.setAccount(account);
        transaction.setAtmFunction(atmFunctionRepo.findById(1).orElseThrow(() -> new RuntimeException("ATM Function not found")));

        transactionRepo.save(transaction);

        return "Transaction Successfull.";
    }
}
