package com.example.gesplan.Services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.gesplan.Model.LoanCalculation;
import com.example.gesplan.Model.DTO.LoanCalculationDTO;

public class LoanCalculationService {

  public static LoanCalculationDTO getLoanReport(Long startDate,
      Long endDate,
      Long firstPaymentDate,
      Double loanAmount,
      Double taxRate) throws Exception {
    LoanCalculationDTO dto = new LoanCalculationDTO();

    LocalDate startLocalDate = Instant.ofEpochMilli(startDate).atZone(ZoneOffset.UTC).toLocalDate();
    LocalDate endLocalDate = Instant.ofEpochMilli(endDate).atZone(ZoneOffset.UTC).toLocalDate();
    LocalDate firstPaymentLocalDate = Instant.ofEpochMilli(firstPaymentDate).atZone(ZoneOffset.UTC).toLocalDate();

    Integer totalInstallments = getTotalInstallments(firstPaymentLocalDate, endLocalDate);
    Integer dayBase = 360;
    dto.setTotalInstallments(totalInstallments);
    dto.setDayBase(dayBase);
    dto.setEndDate(endDate);
    dto.setStartDate(startDate);
    dto.setFirstPaymentDate(firstPaymentDate);
    dto.setTaxRate(taxRate);
    dto.setLoanAmount(loanAmount);
    dto.setCalculationDataMap(getLoanCalculation(startLocalDate, endLocalDate, firstPaymentLocalDate, loanAmount,
        taxRate, totalInstallments, dayBase));

    return dto;
  }

  public static Integer getTotalInstallments(LocalDate firstPaymentLocalDate, LocalDate endLocalDate) {
    YearMonth firstPaymentMonth = YearMonth.from(firstPaymentLocalDate);
    YearMonth endMonth = YearMonth.from(endLocalDate);
    Long totalInstallments = ChronoUnit.MONTHS.between(firstPaymentMonth, endMonth);
    if (endLocalDate.getDayOfMonth() != firstPaymentLocalDate.getDayOfMonth())
      totalInstallments++;

    return Long.valueOf(totalInstallments).intValue();
  }

  public static Map<Integer, LoanCalculation> getLoanCalculation(LocalDate startDay,
      LocalDate endDay,
      LocalDate paymentDay,
      Double loanAmount,
      Double taxRate,
      Integer totalInstallments,
      Integer dayBase) throws Exception {
    Map<Integer, LoanCalculation> calculationMap = new HashMap<>();
    Integer index = 1;
    Integer mapIndex = 0;
    LocalDate currentDate = startDay;

    if (startDay.isAfter(endDay))
      throw new Exception("Data inicial deve ser inferior a data final");

    if (startDay.isAfter(paymentDay))
      throw new Exception("Data inicial deve ser inferior a data do primeiro pagamento");

    calculationMap.put(mapIndex, emptyData(currentDate, loanAmount));
    currentDate = currentDate.with(TemporalAdjusters.lastDayOfMonth());
    while (index <= totalInstallments) {
      if (paymentDay.isBefore(currentDate)) {
        if (paymentDay.isAfter(endDay))
          paymentDay = startDay.plusMonths(totalInstallments);

        mapIndex++;
        LoanCalculation previousData = calculationMap.get(mapIndex - 1);
        LoanCalculation calculation = calculationData(previousData, paymentDay, index, totalInstallments, loanAmount,
            taxRate, dayBase, true);

        calculationMap.put(mapIndex, calculation);
        paymentDay = paymentDay.plusMonths(1);
        index++;
      }

      if (currentDate.isAfter(endDay))
        break;

      mapIndex++;
      LoanCalculation previousData = calculationMap.get(mapIndex - 1);
      LoanCalculation calculation = calculationData(previousData, currentDate, index, totalInstallments, loanAmount,
          taxRate, dayBase, false);

      calculationMap.put(mapIndex, calculation);
      currentDate = currentDate.plusMonths(1);
      currentDate = currentDate.with(TemporalAdjusters.lastDayOfMonth());
    }

    return calculationMap;
  }

  public static Double getProvision(LocalDate previousDataData, LocalDate currentDateData, Double balance,
      Double taxRate, Double previousAccumulated, Double dayBase) {
    Double expoent = (ChronoUnit.DAYS.between(previousDataData, currentDateData) / dayBase);
    Double provision = (Math.pow((taxRate + 1), expoent) - 1) * (balance + previousAccumulated);

    return provision;
  }

  public static LoanCalculation calculationData(LoanCalculation previousData, LocalDate referenceDate, Integer index,
      Integer totalInstallments, Double loanAmount, Double taxRate, Integer dayBase, Boolean isPayment) {
    LocalDate previousTime = Instant.ofEpochMilli(previousData.getTime()).atZone(ZoneOffset.UTC).toLocalDate();
    LoanCalculation calculation = new LoanCalculation();
    calculation.setInstallment(UUID.randomUUID());
    calculation.setTime(referenceDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
    calculation.setLoanAmount(0.00);
    String installment = isPayment ? index + "/" + totalInstallments : "";
    calculation.setPaymentIndex(installment);

    Double provision = getProvision(previousTime, referenceDate, previousData.getBalance(), taxRate,
        previousData.getAccumulated(), Integer.valueOf(dayBase).doubleValue());
    calculation.setProvision(provision);

    Double paid = isPayment ? provision + previousData.getAccumulated() : 0.00;
    calculation.setPaid(paid);

    Double accumulated = previousData.getAccumulated() + provision - paid;
    calculation.setAccumulated(accumulated);

    Double amortization = isPayment ? loanAmount / totalInstallments : 0.00;
    calculation.setAmortization(amortization);

    Double balance = previousData.getBalance() - amortization;
    calculation.setBalance(balance);
    calculation.setOutstandingBalance(balance + accumulated);
    calculation.setTotal(amortization + paid);

    return calculation;
  }

  public static LoanCalculation emptyData(LocalDate competenceDate, Double loanAmount) {
    LoanCalculation firstData = new LoanCalculation();
    firstData.setInstallment(UUID.randomUUID());
    firstData.setTime(competenceDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
    firstData.setLoanAmount(loanAmount);
    firstData.setOutstandingBalance(loanAmount);
    firstData.setPaymentIndex("");
    firstData.setTotal(0.00);
    firstData.setAmortization(0.00);
    firstData.setBalance(loanAmount);
    firstData.setProvision(0.00);
    firstData.setAccumulated(0.00);
    firstData.setPaid(0.00);

    return firstData;
  }

  public static void main(String[] args) {
    Long endDate = 2019697200000l;
    Long firstPaymentDate = 1707966000000l;
    LocalDate endLocalDate = Instant.ofEpochMilli(endDate).atZone(ZoneOffset.UTC).toLocalDate();
    LocalDate firstPaymentLocalDate = Instant.ofEpochMilli(firstPaymentDate).atZone(ZoneOffset.UTC).toLocalDate();

    YearMonth firstPaymentMonth = YearMonth.from(firstPaymentLocalDate);
    YearMonth endMonth = YearMonth.from(endLocalDate);

    Long monthsBetween = ChronoUnit.MONTHS.between(firstPaymentMonth, endMonth);

    System.out.println(monthsBetween);
  }
}
