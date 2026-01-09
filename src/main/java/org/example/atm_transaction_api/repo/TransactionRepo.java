package org.example.atm_transaction_api.repo;

import org.example.atm_transaction_api.entity.Account;
import org.example.atm_transaction_api.entity.Amount;
import org.example.atm_transaction_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Integer> {
    List<Transaction> findByAccount(Account account);
}
