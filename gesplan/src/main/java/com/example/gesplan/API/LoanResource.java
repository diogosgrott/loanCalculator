package com.example.gesplan.API;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.example.gesplan.Model.LoanCalculation;
import com.example.gesplan.Model.ResponseHelper;
import com.example.gesplan.Model.DTO.LoanCalculationDTO;
import com.example.gesplan.Services.LoanCalculationService;

import java.time.Instant;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class LoanResource {

  @CrossOrigin(origins = "*")
  @GetMapping("loan/postLoanData")
  public ResponseEntity<?> getMethodName(@RequestParam Long startDate, @RequestParam Long endDate,
      @RequestParam Long firstPaymentDate, @RequestParam Double loanAmount, @RequestParam Double taxRate) {

    try {
      LoanCalculationDTO dto = LoanCalculationService.getLoanReport(startDate, endDate, firstPaymentDate, loanAmount,
          taxRate);

      return ResponseEntity.ok(dto);
    } catch (Exception ers) {
      ResponseHelper errorResponse = new ResponseHelper(ers.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
  }
}
