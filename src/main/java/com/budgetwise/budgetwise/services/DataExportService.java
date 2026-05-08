package com.budgetwise.budgetwise.services;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.Budget;
import com.budgetwise.budgetwise.models.Goal;
import com.budgetwise.budgetwise.models.Transaction;
import com.budgetwise.budgetwise.utils.ExportUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * DataExportService component.
 */
public class DataExportService {
    private void executeDownload(String format, List<Transaction> transactions, List<Budget> budgets, List<Goal> goals) {
        switch (format.toUpperCase()) {
            case "CSV":
                ExportUtil.exportToCSV(transactions);
                break;
            case "PDF":
                ExportUtil.exportToPDF(transactions, budgets, goals);
                break;
            case "EXCEL":
                ExportUtil.exportToExcel(transactions, budgets, goals);
                break;
            default:
                throw new IllegalArgumentException("Unsupported export format: " + format);
        }
    }

    private void validateExportRequest(String format, LocalDate start, LocalDate end,
                                       boolean trans, boolean budgets, boolean goals) throws Exception {
        if (format == null || format.trim().isEmpty()) {
            throw new Exception("Export format must be selected.");
        }
        if (start == null || end == null) {
            throw new Exception("Date range must be completely specified.");
        }
        if (start.isAfter(end)) {
            throw new Exception("Start date cannot be after the end date.");
        }
        if (!trans && !budgets && !goals) {
            throw new Exception("You must select at least one data type (Transactions, Budgets, or Goals) to export.");
        }
    }

    public void generateAndDownloadReport(int userId, String format, LocalDate startDate, LocalDate endDate,
                                          boolean includeTransactions, boolean includeBudgets, boolean includeGoals) throws Exception {
        validateExportRequest(format, startDate, endDate, includeTransactions, includeBudgets, includeGoals);

        List<Transaction> transactions = includeTransactions ? AppContext.getTransactionService().filterByDateRange(userId, startDate.atStartOfDay(), endDate.atStartOfDay()) : null;
        List<Budget> budgets = includeBudgets ? AppContext.getBudgetService().filterById(userId) : null;
        List<Goal> goals = includeGoals ? AppContext.getGoalService().getGoals(userId) : null;

        if (format.equalsIgnoreCase("EXCEL")) {
            ExportUtil.exportToExcel(transactions, budgets, goals);
        } else if (format.equalsIgnoreCase("PDF")) {
            ExportUtil.exportToPDF(transactions, budgets, goals);
        } else {
            ExportUtil.exportToCSV(transactions);
        }
    }
}
