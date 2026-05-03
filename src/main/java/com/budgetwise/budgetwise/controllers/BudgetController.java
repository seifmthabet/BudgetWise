package com.budgetwise.budgetwise.controllers;

import com.budgetwise.budgetwise.services.BudgetService;
import com.budgetwise.budgetwise.services.CategoryService;

public class BudgetController {
    private BudgetService budgetService = new BudgetService();
    private CategoryService categoryService = new CategoryService();
}
