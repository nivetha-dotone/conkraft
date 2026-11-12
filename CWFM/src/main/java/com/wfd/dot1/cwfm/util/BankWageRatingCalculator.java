
package com.wfd.dot1.cwfm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class BankWageRatingCalculator {

    private static final BigDecimal HUNDRED =
            new BigDecimal("100");

    private BankWageRatingCalculator() {
    }

    public static BigDecimal calculateVariance(
            BigDecimal expected, BigDecimal paid) {

        validate(expected, paid);

        return expected.subtract(paid)
                .multiply(HUNDRED)
                .divide(expected, 4, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculatePaymentPercentage(
            BigDecimal expected, BigDecimal paid) {

        validate(expected, paid);

        return paid.multiply(HUNDRED)
                .divide(expected, 4, RoundingMode.HALF_UP);
    }

    public static int calculateRating(
            BigDecimal expected, BigDecimal paid) {

        BigDecimal percentage =
                calculatePaymentPercentage(expected, paid);

        if (percentage.compareTo(new BigDecimal("75")) < 0)
            return 1;

        if (percentage.compareTo(new BigDecimal("85")) < 0)
            return 2;

        if (percentage.compareTo(new BigDecimal("95")) < 0)
            return 3;

        if (percentage.compareTo(new BigDecimal("100")) < 0)
            return 4;

        return 5;
    }

    public static String getRatingDescription(int rating) {

        switch (rating) {
            case 1: return "Poor";
            case 2: return "Average";
            case 3: return "Good";
            case 4: return "Very Good";
            case 5: return "Excellent";
            default:
                throw new IllegalArgumentException(
                        "Invalid rating: " + rating);
        }
    }

    private static void validate(
            BigDecimal expected, BigDecimal paid) {

        if (expected == null
                || expected.compareTo(BigDecimal.ZERO) <= 0
                || paid == null
                || paid.compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Invalid expected or paid amount.");
        }
    }
}
