package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.DAOs.TransactionDAO;
import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.models.enums.TransactionType;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
        private final TransactionDAO transactionDAO;

        public ReportService(TransactionDAO transactionDAO) {
                this.transactionDAO = transactionDAO;
        }

        public Map<String, Double> getMonthlySummary(int userId, int month, int year) {
                if(userId <= 0){
                        throw new IllegalArgumentException("userId must be greater than 0");
                }
                if(month <= 0 ||  month > 12 || year <= 0){
                        throw new IllegalArgumentException("Invalid date parameter");
                }
                double income = 0;
                double expense = 0;
                LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
                LocalDateTime end = LocalDate.of(year, month, 1)
                        .withDayOfMonth(LocalDate.of(year, month, 1).lengthOfMonth())
                        .atTime(23, 59, 59);

                List<Transaction> transactions = transactionDAO.findByDateRange(userId, start, end);

                for (Transaction tx : transactions) {
                        if (tx.getType() == TransactionType.INCOME)
                                income += tx.getAmount().doubleValue();
                        else
                                expense += tx.getAmount().doubleValue();
                }

                Map<String, Double> monthlySummary = new HashMap<>();
                monthlySummary.put("income", income);
                monthlySummary.put("expense", expense);
                monthlySummary.put("balance", income - expense);

                return monthlySummary;
        }

        public Map<String, Double> getExpenseByCategory(int userId, LocalDate startDate, LocalDate endDate) {
                if(userId <= 0){
                        throw new IllegalArgumentException("userId must be greater than 0");
                }
                if(startDate.isAfter(endDate)){
                        throw new IllegalArgumentException("startDate must be before endDate");
                }
                List<Transaction> transactions = transactionDAO.findByDateRange(userId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
                if (transactions == null || transactions.isEmpty()) {
                        return Collections.emptyMap();
                }
                Map<String, Double> categoryMap = new HashMap<>();

                for (Transaction tx : transactions) {
                        if (tx.getType() == TransactionType.EXPENSE) {
                                String categoryKey = "Category " + tx.getCategoryId();
                                categoryMap.put(categoryKey, categoryMap.getOrDefault(categoryKey, 0.0) + tx.getAmount().doubleValue());
                        }
                }
                return categoryMap;
        }

        public double[] getIncomeVsExpense(int userId, LocalDate startDate, LocalDate endDate) {
                if(userId <= 0){
                        throw new IllegalArgumentException("userId must be greater than 0");
                }
                List<Transaction> transactions = transactionDAO.findByDateRange(userId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
                double income = 0;
                double expense = 0;

                for (Transaction tx : transactions) {
                        if (tx.getType() == TransactionType.INCOME)
                                income += tx.getAmount().doubleValue();
                        else
                                expense += tx.getAmount().doubleValue();
                }

                return new double[]{income, expense};
        }
}