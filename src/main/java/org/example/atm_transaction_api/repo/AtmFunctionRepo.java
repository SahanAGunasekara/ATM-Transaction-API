package org.example.atm_transaction_api.repo;

import org.example.atm_transaction_api.entity.AtmFunction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtmFunctionRepo extends JpaRepository<AtmFunction,Integer> {
}
