package com.aninfo.model;
import javax.persistence.*;
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double value;
    private Long accCbu;
    private TransType type;

    public Transaction(Double value, Long accCbu, TransType type) {
        this.value = value;
        this.accCbu = accCbu;
        this.type = type;
    }

    public Double getValue(){ return value; }

    public Long getAccCbu(){ return accCbu; }

    public TransType getType(){ return type; }

    public Long getId(){ return id; }

}
