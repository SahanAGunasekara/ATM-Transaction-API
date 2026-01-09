package org.example.atm_transaction_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "accountId",nullable = false)
    private Account account;

    @Column(name = "amount",nullable = false)
    private int amount;

    @Column(name = "transactionDate",nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "atmfunctionId",nullable = false)
    private AtmFunction atmFunction;
}
