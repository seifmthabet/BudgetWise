package com.budgetwise.budgetwise.core;

import com.budgetwise.budgetwise.DAOs.*;
import com.budgetwise.budgetwise.services.*;
import com.budgetwise.budgetwise.utils.AlertUtil;
import com.budgetwise.budgetwise.utils.Validation;

/**
 * AppContext component.
 */
public class AppContext {
    private static final SessionManager session = new SessionManager();
    private static final Validation validator = new Validation();
    private static final AlertUtil alertUtil = new AlertUtil();
    private static final CurrencyConverter currencyConverter = new CurrencyConverter();

    private static final UserDAO userDAO = new UserDAO();
    private static final TransactionDAO transactionDAO = new TransactionDAO();
    private static final NotificationDAO notificationDAO = new NotificationDAO();
    private static final GoalDAO goalDAO = new GoalDAO();
    private static final BudgetDAO budgetDAO = new BudgetDAO();
    private static final CategoryDAO categoryDAO = new CategoryDAO();

    private static final UserService userService = new UserService(userDAO);
    private static final TransactionService transactionService = new TransactionService(transactionDAO, validator);
    private static final NotificationService notificationService = new NotificationService(notificationDAO);
    private static final GoalService goalService = new GoalService(goalDAO, validator);
    private static final BudgetService budgetService = new BudgetService(budgetDAO);
    private static final CategoryService categoryService = new CategoryService(categoryDAO);
    private static final DataExportService dataExportService = new DataExportService();

    /**
     * getSession operation.
     * @return result value
     */
    public static SessionManager getSession() {
        return session;
    }

    /**
     * getUserService operation.
     * @return result value
     */
    public static UserService getUserService() {
        return userService;
    }

    /**
     * getTransactionService operation.
     * @return result value
     */
    public static TransactionService getTransactionService() {
        return transactionService;
    }

    /**
     * getNotificationService operation.
     * @return result value
     */
    public static NotificationService getNotificationService() {
        return notificationService;
    }
    /**
     * getGoalService operation.
     * @return result value
     */
    public static GoalService getGoalService() {
        return goalService;
    }
    /**
     * getBudgetService operation.
     * @return result value
     */
    public static BudgetService getBudgetService() {
        return budgetService;
    }
    /**
     * getCategoryService operation.
     * @return result value
     */
    public static CategoryService getCategoryService() {
        return categoryService;
    }
    /**
     * getDataExportService operation.
     * @return result value
     */
    public static DataExportService getDataExportService() {
        return dataExportService;
    }
    /**
     * getValidator operation.
     * @return result value
     */
    public static Validation getValidator() {
        return validator;
    }
    /**
     * getAlertUtil operation.
     * @return result value
     */
    public static AlertUtil getAlertUtil() {
        return alertUtil;
    }
    public static CategoryDAO getCategoryDAO() {return categoryDAO;}
    public static CurrencyConverter getCurrencyConverter() {return currencyConverter;}
}
