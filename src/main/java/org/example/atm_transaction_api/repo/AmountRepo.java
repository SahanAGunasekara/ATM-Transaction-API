package org.example.atm_transaction_api.repo;


import org.example.atm_transaction_api.entity.Account;
import org.example.atm_transaction_api.entity.Amount;
import org.example.atm_transaction_api.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmountRepo extends JpaRepository<Amount,Integer> {
    List<Amount> findByAccount(Account account);
}
