package org.example.atm_transaction_api.repo;

import org.example.atm_transaction_api.entity.Account;
import org.example.atm_transaction_api.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account,String> {

    List<Account> findByCard(Card card);
}
