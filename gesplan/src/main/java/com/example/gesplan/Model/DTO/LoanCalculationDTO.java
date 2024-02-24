package com.example.gesplan.Model.DTO;

import java.util.Map;
import com.example.gesplan.Model.LoanCalculation;

public class LoanCalculationDTO {
  private Long startDate;
  private Long endDate;
  private Long firstPaymentDate;
  private Double loanAmount;
  private Double taxRate;
  private Integer dayBase;
  private Integer totalInstallments;
  private Map<Integer, LoanCalculation> calculationDataMap;

  public Map<Integer, LoanCalculation> getCalculationDataMap() {
    return calculationDataMap;
  }

  public void setCalculationDataMap(Map<Integer, LoanCalculation> calculationDataMap) {
    this.calculationDataMap = calculationDataMap;
  }

  public Long getStartDate() {
    return startDate;
  }

  public void setStartDate(Long startDate) {
    this.startDate = startDate;
  }

  public Long getEndDate() {
    return endDate;
  }

  public void setEndDate(Long endDate) {
    this.endDate = endDate;
  }

  public Long getFirstPaymentDate() {
    return firstPaymentDate;
  }

  public void setFirstPaymentDate(Long firstPaymentDate) {
    this.firstPaymentDate = firstPaymentDate;
  }

  public Double getLoanAmount() {
    return loanAmount;
  }

  public void setLoanAmount(Double loanAmount) {
    this.loanAmount = loanAmount;
  }

  public Double getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(Double taxRate) {
    this.taxRate = taxRate;
  }

  public Integer getDayBase() {
    return dayBase;
  }

  public void setDayBase(Integer dayBase) {
    this.dayBase = dayBase;
  }

  public Integer getTotalInstallments() {
    return totalInstallments;
  }

  public void setTotalInstallments(Integer totalInstallments) {
    this.totalInstallments = totalInstallments;
  }
}
