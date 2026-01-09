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
public class Account {

    @Id
    @Column(name = "accountNumber",length = 20,nullable = false)
    private String accountNumber;

    @Column(name = "holderName",length = 25,nullable = false)
    private String holderName;

    @ManyToOne
    @JoinColumn(name = "cardId",nullable = false)
    private Card card;

    @Column(name = "createdAt",nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "accountType",nullable = false)
    private AccountType accountType;

}
