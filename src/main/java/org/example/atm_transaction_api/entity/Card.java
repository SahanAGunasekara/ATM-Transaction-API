package org.example.atm_transaction_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cardNumber",length = 25,nullable = false)
    private String cardNumber;

    @Column(name = "password",length = 4,nullable = false)
    private String password;

    @Column(name = "holderName",length = 45,nullable = false)
    private String holderName;

    @Column(name = "expireDate",nullable = false)
    private Date expireDate;

    @Column(name = "cvcNumber",nullable = false)
    private int cvcNumber;

    @ManyToOne
    @JoinColumn(name = "statusId",nullable = false)
    private Status status;
}
