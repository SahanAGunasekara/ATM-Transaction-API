package org.example.atm_transaction_api.repo;

import org.example.atm_transaction_api.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepo extends JpaRepository<Card,Integer> {
    List<Card> findByCardNumberAndPassword(String cardNumber,String password);
    List<Card> findByCardNumber(String card);
}
