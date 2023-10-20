package com.aninfo.model;
import javax.persistence.*;
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long txid;
    private Double value;
    private Long accCbu;

    public Transaction(Double value, Long cbu) {
        this.value = value;
        this.accCbu = cbu;
    }

    public Double getValue(){ return this.value; }

    public Long getAccCbu(){ return this.accCbu; }
}
