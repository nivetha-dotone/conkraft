package com.wfd.dot1.cwfm.util;

import com.wfd.dot1.cwfm.enums.IntentType;

public class IntentMatcher {

    public static IntentType detectIntent(String question) {

        if(question == null)
            return IntentType.UNKNOWN;

        question = question.toLowerCase().trim();

        // Pending Approvals

        if(question.contains("pending approval")
                || question.contains("approvals")
                || question.contains("approval pending")
                || question.contains("approval count")) {

            return IntentType.PENDING_APPROVALS;

        }

        // Contractor

        if(question.contains("active contractor")
                || question.contains("contractor count")
                || question.contains("contractors")) {

            return IntentType.ACTIVE_CONTRACTORS;

        }

        // Gatepass

        if(question.contains("gatepass")
                || question.contains("gate pass")) {

            return IntentType.GATEPASS_COUNT;

        }

        // Workorder

        if(question.contains("work order")
                || question.contains("workorder")) {

            return IntentType.WORKORDER_COUNT;

        }

        // Principal Employer

        if(question.contains("principal employer")
                || question.contains("pe list")
                || question.contains("principal")) {

            return IntentType.PRINCIPAL_EMPLOYER;

        }

        // License

        if(question.contains("license")
                || question.contains("licence")
                || question.contains("expiry")) {

            return IntentType.LICENSE_EXPIRY;

        }

        // Search Contractor

        if(question.startsWith("search contractor")) {

            return IntentType.CONTRACTOR_SEARCH;

        }

        if(question.contains("help")) {

            return IntentType.HELP;

        }

        return IntentType.UNKNOWN;

    }

}
