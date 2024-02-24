package com.example.gesplan.Model;

import java.util.UUID;

public class LoanCalculation {
  private UUID installment;
  private Long time;
  private Double loanAmount;
  private Double outstandingBalance;
  private String paymentIndex;
  private Double total;
  private Double amortization;
  private Double balance;
  private Double provision;
  private Double accumulated;
  private Double paid;

  public UUID getInstallment() {
    return installment;
  }

  public void setInstallment(UUID installment) {
    this.installment = installment;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public Double getLoanAmount() {
    return loanAmount;
  }

  public void setLoanAmount(Double loanAmount) {
    this.loanAmount = loanAmount;
  }

  public Double getOutstandingBalance() {
    return outstandingBalance;
  }

  public void setOutstandingBalance(Double outstandingBalance) {
    this.outstandingBalance = outstandingBalance;
  }

  public String getPaymentIndex() {
    return paymentIndex;
  }

  public void setPaymentIndex(String paymentIndex) {
    this.paymentIndex = paymentIndex;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public Double getAmortization() {
    return amortization;
  }

  public void setAmortization(Double amortization) {
    this.amortization = amortization;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public Double getProvision() {
    return provision;
  }

  public void setProvision(Double provision) {
    this.provision = provision;
  }

  public Double getAccumulated() {
    return accumulated;
  }

  public void setAccumulated(Double accumulated) {
    this.accumulated = accumulated;
  }

  public Double getPaid() {
    return paid;
  }

  public void setPaid(Double paid) {
    this.paid = paid;
  }
}
