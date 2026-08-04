package com.wfd.dot1.cwfm.util;

import com.wfd.dot1.cwfm.enums.IntentType;

public class IntentMatcher {

    public static IntentType detectIntent(String question) {

        if(question == null)
            return IntentType.UNKNOWN;

        question = question.toLowerCase().trim();

        // Pending Approvals

//        if(question.contains("pending approval")
//                || question.contains("approvals")
//                || question.contains("approval pending")
//                || question.contains("approval count")) {
//
//            return IntentType.PENDING_APPROVALS;
//
//        }
     // create PENDING APPROVALS
        
     // ======================================
     // Gatepass Training Video
     // ======================================

     if (question.contains("how to create regular gatepass")
             || question.contains("how to create regular gatepasses")
             || question.contains("how do i create regular gatepass")
             || question.contains("how do i create a regular gatepass")
             || question.contains("create regular gatepass")
             || question.contains("regular gatepass process")
             || question.contains("regular gatepass process")
             || question.contains("regular gatepass tutorial")
             || question.contains("regular gatepass training")
             || question.contains("show regular gatepass video")
             || question.contains("regular gatepass video")
             || question.contains("explain how to create regular gatepass")) {

         return IntentType.REGULAR_GATEPASS_VIDEO;
     }
     
        if (question.contains("pending approvals - regular")||
        		question.contains("pending approval - regular")||
        		question.contains("regular pending approvals")||
        		question.contains("regular approvals")||
        		question.contains("regular pending")||
        		question.contains("regular pendings")||
        		question.contains("regular")||
        		question.contains ("regular pending approval")){

            return IntentType.CREATE_PENDING_APPROVALS;
        }

     // PROJECT PENDING APPROVALS
     if (question.contains("pending approvals - project") ||
         question.contains("pending approval - project") ||
         question.contains("project pending approvals") ||
         question.contains("project approvals") ||
         question.contains("project pending") ||
         question.contains("project pendings") ||
         question.contains("project") ||
         question.contains("project pending approval")) {

         return IntentType.PROJECT_PENDING_APPROVALS;
     }
     
  // BLOCK PENDING APPROVALS
  if (question.contains("pending approvals - block") ||
      question.contains("pending approval - block") ||
      question.contains("block pending approvals") ||
      question.contains("block approvals") ||
      question.contains("block pending") ||
      question.contains("block pendings") ||
      question.contains("block") ||
      question.contains("block pending approval")) {

      return IntentType.BLOCK_PENDING_APPROVALS;
  }

//UNBLOCK PENDING APPROVALS
if (question.contains("pending approvals - unblock") ||
   question.contains("pending approval - unblock") ||
   question.contains("unblock pending approvals") ||
   question.contains("unblock approvals") ||
   question.contains("unblock pending") ||
   question.contains("unblock pendings") ||
   question.contains("unblock") ||
   question.contains("unblock pending approval")) {

   return IntentType.UNBLOCK_PENDING_APPROVALS;
}

//BLACKLIST PENDING APPROVALS
if (question.contains("pending approvals - blacklist") ||
 question.contains("pending approval - blacklist") ||
 question.contains("blacklist pending approvals") ||
 question.contains("blacklist approvals") ||
 question.contains("blacklist pending") ||
 question.contains("blacklist pendings") ||
 question.contains("blacklist") ||
 question.contains("blacklist pending approval")) {

 return IntentType.BLACKLIST_PENDING_APPROVALS;
}

//DEBLACKLIST PENDING APPROVALS
if (question.contains("pending approvals - deblacklist") ||
 question.contains("pending approval - deblacklist") ||
 question.contains("deblacklist pending approvals") ||
 question.contains("deblacklist approvals") ||
 question.contains("deblacklist pending") ||
 question.contains("deblacklist pendings") ||
 question.contains("deblacklist") ||
 question.contains("deblacklist pending approval")) {

 return IntentType.DEBLACKLIST_PENDING_APPROVALS;
}

//CANCEL PENDING APPROVALS
if (question.contains("pending approvals - cancel") ||
 question.contains("pending approval - cancel") ||
 question.contains("cancel pending approvals") ||
 question.contains("cancel approvals") ||
 question.contains("cancel pending") ||
 question.contains("cancel pendings") ||
 question.contains("cancel") ||
 question.contains("cancel pending approval")) {

 return IntentType.CANCEL_PENDING_APPROVALS;
}

//RENEW PENDING APPROVALS
if (question.contains("pending approvals - renew") ||
 question.contains("pending approval - renew") ||
 question.contains("renew pending approvals") ||
 question.contains("renew approvals") ||
 question.contains("renew pending") ||
 question.contains("renew pendings") ||
 question.contains("renew") ||
 question.contains("renew pending approval")) {

 return IntentType.RENEW_PENDING_APPROVALS;
}

//QUICK PENDING APPROVALS
if (question.contains("pending approvals - quick") ||
 question.contains("pending approval - quick") ||
 question.contains("quick pending approvals") ||
 question.contains("quick pending") ||
 question.contains("quick pendings") ||
 question.contains("quick") ||
 question.contains("quick approvals") ||
 question.contains("quick pending approval")) {

 return IntentType.QUICK_PENDING_APPROVALS;
}

        // Contractor

        if(question.contains("active contractor")
                || question.contains("contractor count")
                || question.contains("list of contractor count")
                || question.contains("contractor count list")
                || question.contains("contractors")) {

            return IntentType.ACTIVE_CONTRACTORS;

        }

        // Gatepass

        if(
        		question.contains("today gate passes")
        		|| question.contains("today's gate passes")
        		|| question.contains("today gate pass")
                || question.contains("today's gatepass")
                || question.contains("today gatepasses")
                || question.contains("today's gatepasses")
                || question.contains("gatepasses created today")
                || question.contains("today created gate pass")
                || question.contains("today created gatepasses")
                || question.contains("today gatepasses")) {

            return IntentType.TODAY_GATEPASSES;

        }

        // Workorder

        if(question.contains("work order")
                || question.contains("workorder")
                || question.contains("work orders")
                || question.contains("workorder list")
                || question.contains("workorders list")
                || question.contains("list of workorders")
                || question.contains("workorders")) {

            return IntentType.WORKORDER_COUNT;

        }

        // Principal Employer

        if(question.contains("principal employer")
        		|| question.contains("principal employers")
        		|| question.contains("principalemployers")
        		|| question.contains("principalemployer")
        		|| question.contains("list of principalemployers")
        		|| question.contains("list of principal employers")
        		|| question.contains("list of principal employer")
                || question.contains("pe list")
                || question.contains("principal")) {

            return IntentType.PRINCIPAL_EMPLOYER;

        }

        // License

        if(question.contains("license")
                || question.contains("licence")
                || question.contains("licence expiry list")
                || question.contains("licence expiry")
                || question.contains("list of licences expiry")
                || question.contains("list of licences expiry in 30 days")
                || question.contains("licences expiry 30 days")
                || question.contains("list of licencesexpiry")
                || question.contains("licences expiry list")
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
