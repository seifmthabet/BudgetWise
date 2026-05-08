package com.budgetwise.budgetwise.core;

/**
 * CurrencyConverter component.
 */
public class CurrencyConverter {

    public static double convert(double amount,
                                 String from,
                                 String to) {

        if (from.equalsIgnoreCase(to)) {
            return amount;
        }

        // ===== USD BASE =====
        if (from.equals("USD") && to.equals("EGP")) return amount * 50;
        if (from.equals("USD") && to.equals("EUR")) return amount * 0.92;
        if (from.equals("USD") && to.equals("SAR")) return amount * 3.75;
        if (from.equals("USD") && to.equals("AED")) return amount * 3.67;
        if (from.equals("USD") && to.equals("GBP")) return amount * 0.79;

        // ===== EGP =====
        if (from.equals("EGP") && to.equals("USD")) return amount / 50;
        if (from.equals("EGP") && to.equals("EUR")) return amount / 54.5;
        if (from.equals("EGP") && to.equals("SAR")) return amount / 13.3;
        if (from.equals("EGP") && to.equals("AED")) return amount / 13.6;
        if (from.equals("EGP") && to.equals("GBP")) return amount / 63.0;

        // ===== EUR =====
        if (from.equals("EUR") && to.equals("USD")) return amount / 0.92;
        if (from.equals("EUR") && to.equals("EGP")) return amount * 54.5;
        if (from.equals("EUR") && to.equals("SAR")) return amount * 4.1;
        if (from.equals("EUR") && to.equals("AED")) return amount * 4.0;
        if (from.equals("EUR") && to.equals("GBP")) return amount * 0.86;

        // ===== SAR =====
        if (from.equals("SAR") && to.equals("USD")) return amount / 3.75;
        if (from.equals("SAR") && to.equals("EGP")) return amount * 13.3;
        if (from.equals("SAR") && to.equals("EUR")) return amount / 4.1;

        // ===== GBP =====
        if (from.equals("GBP") && to.equals("USD")) return amount / 0.79;
        if (from.equals("GBP") && to.equals("EGP")) return amount * 63.0;

        // fallback
        return amount;
    }
}
