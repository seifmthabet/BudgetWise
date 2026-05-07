package com.budgetwise.budgetwise.utils;

import com.budgetwise.budgetwise.core.AppContext;
import com.budgetwise.budgetwise.models.Budget;
import com.budgetwise.budgetwise.models.Goal;
import com.budgetwise.budgetwise.models.Transaction;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class ExportUtil {
    private static File showSavingDialog(String title, String fileName, String extensionDescription, String extension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialFileName(fileName);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extensionDescription, extension));
        return fileChooser.showSaveDialog(new Stage());
    }
    //========================= CSV =================================
    public static void exportToCSV(List<Transaction> transactions) {
        File file = showSavingDialog("Export Transactions", "BudgetWise_report.csv", "CSV Files", "*.csv");
        if (file == null) return;
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("Date,Description,Category,Amount,Payment Method");
            for (Transaction tx : transactions) {
                writer.printf("%s,%s,%s,%.2f,%s",
                        tx.getDate().toLocalDate(),
                        tx.getDescription(),
                        AppContext.getCategoryService().findById(tx.getCategoryId()).getName(),
                        tx.getAmount(),
                        tx.getPaymentMethod()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to export transactions to CSV", e);
        }
    }

    //========================= PDF =================================
    public static void exportToPDF(List<Transaction> transactions, List<Budget> budgets, List<Goal> goals) {
        File file = showSavingDialog("Export Transactions", "BudgetWise_report.pdf", "PDF Files", "*.pdf");
        if (file == null) return;

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(new Paragraph("BudgetWise Financial Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20)));
            document.add(new Paragraph("Generated Range: " + LocalDate.now()));

            if (transactions != null && !transactions.isEmpty()) {
                document.add(new Paragraph("\n ======= TRANSACTIONS ======= \n"));
                PdfPTable table = new PdfPTable(5);
                table.addCell("Date");
                table.addCell("Description");
                table.addCell("Category");
                table.addCell("Amount");
                table.addCell("Payment Method");

                for (Transaction tx : transactions) {
                    table.addCell(tx.getDate().toLocalDate().toString());
                    table.addCell(tx.getDescription());
                    table.addCell(AppContext.getCategoryService().findById(tx.getCategoryId()).getName());
                    table.addCell(tx.getAmount().toString());
                    table.addCell(tx.getPaymentMethod().toString());
                }
                document.add(table);
            }

            if (budgets != null && !budgets.isEmpty()) {
                document.add(new Paragraph("\n ======= BUDGETS ======= \n"));
                PdfPTable table = new PdfPTable(4);
                table.addCell("Category");
                table.addCell("Amount");
                table.addCell("Spent");
                table.addCell("Remaining");

                for (Budget budget : budgets) {
                    table.addCell(budget.getCategoryName());
                    table.addCell(String.format("%.2f", budget.getAmount()));
                    table.addCell(String.format("%.2f", budget.getSpentAmount()));
                    table.addCell(String.format("%.2f", budget.getRemainingAmount()));
                }
                document.add(table);
            }

            if (goals != null && !goals.isEmpty()) {
                document.add(new Paragraph("\n ======= GOALS ======= \n"));
                PdfPTable table = new PdfPTable(3);
                table.addCell("Goal");
                table.addCell("Target Amount");
                table.addCell("Progress");
                table.addCell("Status");

                for (Goal goal : goals) {
                    table.addCell(goal.getName());
                    table.addCell(String.format("%.2f", goal.getTargetAmount()));
                    table.addCell(String.format("%.2f", goal.getProgressPercent()));
                    table.addCell(goal.getStatus().toString());
                }
                document.add(table);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to export report to PDF", e);
        } finally {
            document.close();
        }
    }

    //========================= EXCEL =================================

    private static void fillSheetData(Workbook wb, Sheet sheet, String[] headers, List<?> data) {
        Row headerRoe = sheet.createRow(0);
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRoe.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
        int rowIndex = 1;
        for (Object item : data) {
            Row row = sheet.createRow(rowIndex++);
            if (item instanceof Transaction tx) {
                row.createCell(0).setCellValue(tx.getDate().toLocalDate().toString());
                row.createCell(1).setCellValue(tx.getDescription());
                row.createCell(2).setCellValue(AppContext.getCategoryService().findById(tx.getCategoryId()).getName());
                row.createCell(3).setCellValue(tx.getAmount().toString());
                row.createCell(4).setCellValue(tx.getPaymentMethod().toString());
            } else if (item instanceof Budget budget) {
                row.createCell(0).setCellValue(budget.getCategoryName());
                row.createCell(1).setCellValue(budget.getAmount());
                row.createCell(2).setCellValue(budget.getSpentAmount());
                row.createCell(3).setCellValue(budget.getRemainingAmount());
                row.createCell(4).setCellValue(budget.getEndDate().toLocalDate().toString());
            } else if (item instanceof Goal goal) {
                row.createCell(0).setCellValue(goal.getName());
                row.createCell(1).setCellValue(goal.getTargetAmount());
                row.createCell(2).setCellValue(goal.getCurrentAmount());
                row.createCell(3).setCellValue(goal.getProgressPercent());
                row.createCell(4).setCellValue(goal.getStatus().toString());
            }
        }
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void createTransactionSheet(Workbook wb, List<Transaction> list){
        Sheet sheet = wb.createSheet("Transactions");
        String[] headers = {"Date", "Description", "Category", "Amount", "Payment Method"};
        fillSheetData(wb, sheet, headers, list);
    }

    private static void createGoalSheet(Workbook wb, List<Goal> list){
        Sheet sheet = wb.createSheet("Goals");
        String[] headers = {"Goal", "Target Amount", "Current Amount", "Progress", "Status"};
    }

    private static void createBudgetSheet(Workbook wb, List<Budget> list){
        Sheet sheet = wb.createSheet("Budgets");
        String[] headers = {"Category", "Amount", "Spent", "Remaining", "End Date"};
        fillSheetData(wb, sheet, headers, list);
    }

    public static void exportToExcel(List<Transaction> transactions, List<Budget> budgets, List<Goal> goals) {
        File file = showSavingDialog("Save Excel File", "BudgetWise_report.xlsx", "Excel Files", "*.xlsx");
        if (file == null) return;

        try (Workbook wb = new XSSFWorkbook()) {
            if (transactions != null && !transactions.isEmpty()) createTransactionSheet(wb, transactions);
            if (budgets != null && !budgets.isEmpty()) createBudgetSheet(wb, budgets);
            if (goals != null && !goals.isEmpty()) createGoalSheet(wb, goals);

            try (FileOutputStream out = new FileOutputStream(file)) {
                wb.write(out);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create Excel workbook", e);
        }
    }

}
