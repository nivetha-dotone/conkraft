package com.wfd.dot1.cwfm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.BillConfigDao;
import com.wfd.dot1.cwfm.dao.GatepassEmailDao;
import com.wfd.dot1.cwfm.dto.GatepassEmailDTO;
import com.wfd.dot1.cwfm.dto.WorkOrderDTOMail;

@Service
public class GatepassEmailServiceImpl implements GatepassEmailService{
	 private static final Logger log = LoggerFactory.getLogger(GatePassToOnBoardService.class.getName());
	 @Autowired
	    private GatepassEmailDao gatepassEmaildao;

//	 @Override
//	 @Scheduled(cron = "0 0 10 * * ?")
	// cron = "*/10 * * * * ?"
//	 public void setupCreateApprovalPendingMail() {
//
//	     try {
//	         log.info("Gatepass Email Service Start");
//	         List<GatepassEmailDTO> createApprovalPending = gatepassEmaildao.getCreateApprovalPendingRecords();
//	         String regardsEmail = gatepassEmaildao.getRegardsEmail();
//	         for (GatepassEmailDTO gatepass : createApprovalPending) {
//	             List<GatepassEmailDTO> singleRecord = new ArrayList<>();
//	             singleRecord.add(gatepass);
//	             String bodyMail = buildHtmlTable(singleRecord, regardsEmail);
//	             String subject = "Gatepass Pending Approval - " + gatepass.getTransactionid();
//
//	              // Contractor Mail
//	             if (gatepass.getContractorMail() != null&& !gatepass.getContractorMail().trim().isEmpty()) {
//	                 Set<String> contractorMail = new HashSet<>();
//	                 contractorMail.add(gatepass.getContractorMail());
//	                 sendHtmlMail(contractorMail, subject, bodyMail);
//	                 log.info("Contractor Mail Sent : {}", gatepass.getTransactionid());
//	             }
//
//	              // Approver Mails
//	             Set<String> approverMails =gatepassEmaildao.getCreateApproverMails(gatepass.getUnitId(),gatepass.getUnitCode());
//	             if (approverMails != null && !approverMails.isEmpty()) {
//	                 sendHtmlMail(approverMails, subject, bodyMail);
//	                 log.info("Approver Mail Sent : {} -> {}",gatepass.getTransactionid(),approverMails);
//	             }
//	         }
//
//	     } catch (Exception e) {
//
//	         log.error("Error while sending Gatepass mails", e);
//
//	     }
//	 }
	 @Override
	 @Scheduled(cron = "0 0 10 * * ?")
	 public void setupCreateApprovalPendingMail() {

		    try {
		        log.info("Gatepass Email Service Start");
		        List<GatepassEmailDTO> createApprovalPending = gatepassEmaildao.getCreateApprovalPendingRecords();
		        String regardsEmail = gatepassEmaildao.getRegardsEmail();
		        for (GatepassEmailDTO gatepass : createApprovalPending) {

		            // 1. Create email body for this ONE gatepass
		            List<GatepassEmailDTO> singleRecord = new ArrayList<>();
		            singleRecord.add(gatepass);
		            String bodyMail = buildHtmlTable(singleRecord, regardsEmail);

		            String subject = "Gatepass Pending Approval - " + gatepass.getTransactionid();

		            // 2. Send mail to CONTRACTOR
		            if (gatepass.getContractorMail() != null && !gatepass.getContractorMail().trim().isEmpty()) {
		                Set<String> contractorMail = new HashSet<>();
		                contractorMail.add(gatepass.getContractorMail().trim());
		                sendHtmlMail(contractorMail,subject,bodyMail);
		                log.info("Contractor mail sent. TransactionId={}, To={}",gatepass.getTransactionid(),gatepass.getContractorMail());
		            }
		            // 3. Get APPROVER email list

		            Set<String> approverMails = gatepassEmaildao.getCreateApproverMails(gatepass.getUnitId(),gatepass.getUnitCode());
		           
		            // 4. Send ONE MAIL PER APPROVER
		            if (approverMails != null && !approverMails.isEmpty()) {
		                for (String approverMail : approverMails) {
		                    // Ignore null / blank email
		                    if (approverMail == null || approverMail.trim().isEmpty()) {
		                        continue;
		                    }
		                    Set<String> singleApproverMail = new HashSet<>();
		                    singleApproverMail.add(approverMail.trim());
		                    sendHtmlMail(singleApproverMail,subject,bodyMail);
		                    log.info("Approver mail sent. TransactionId={}, To={}",gatepass.getTransactionid(),approverMail);
		                }
		            }
		        }
		    } catch (Exception e) {
		        log.error("Error while sending Gatepass pending approval mails",e);
		    }
		}
//	 @Scheduled(cron = "*/10 * * * * ?")
//	 public void setupBlockApprovalPendingMail() {
//
//		    try {
//		        log.info("Gatepass Email Service Start");
//		        List<GatepassEmailDTO> createApprovalPending = gatepassEmaildao.getBlockApprovalPendingRecords();
//		        String regardsEmail = gatepassEmaildao.getRegardsEmail();
//		        for (GatepassEmailDTO gatepass : createApprovalPending) {
//
//		            // 1. Create email body for this ONE gatepass
//		            List<GatepassEmailDTO> singleRecord = new ArrayList<>();
//		            singleRecord.add(gatepass);
//		            String bodyMail = buildHtmlTable(singleRecord, regardsEmail);
//
//		            String subject = "Gatepass Block Approval Pending - " + gatepass.getGatepassid();
//
//		            // 2. Send mail to CONTRACTOR
//		            if (gatepass.getContractorMail() != null && !gatepass.getContractorMail().trim().isEmpty()) {
//		                Set<String> contractorMail = new HashSet<>();
//		                contractorMail.add(gatepass.getContractorMail().trim());
//		                sendHtmlMail(contractorMail,subject,bodyMail);
//		                log.info("Contractor mail sent. TransactionId={}, To={}",gatepass.getGatepassid(),gatepass.getContractorMail());
//		            }
//		            // 3. Get APPROVER email list
//
//		            Set<String> approverMails = gatepassEmaildao.getBlockApproverMails(gatepass.getUnitId(),gatepass.getUnitCode());
//		           
//		            // 4. Send ONE MAIL PER APPROVER
//		            if (approverMails != null && !approverMails.isEmpty()) {
//		                for (String approverMail : approverMails) {
//		                    // Ignore null / blank email
//		                    if (approverMail == null || approverMail.trim().isEmpty()) {
//		                        continue;
//		                    }
//		                    Set<String> singleApproverMail = new HashSet<>();
//		                    singleApproverMail.add(approverMail.trim());
//		                    sendHtmlMail(singleApproverMail,subject,bodyMail);
//		                    log.info("Block Approver mail sent. TransactionId={}, To={}",gatepass.getTransactionid(),approverMail);
//		                }
//		            }
//		        }
//		    } catch (Exception e) {
//		        log.error("Error while sending Gatepass pending approval mails",e);
//		    }
//		}
	  public String buildHtmlTable(List<GatepassEmailDTO> list, String regards) {
	        StringBuilder html = new StringBuilder();
	        html.append("<html><body>");
	        html.append("<h3>GatePass Approval Pending</h3>");
	        html.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse:collapse;'>");
	        html.append("<tr style='background-color:#f2f2f2;'>").append("<th>Workmen Name</th>").append("<th>Aadhar Number</th>").append("<th>Principal Employer</th>").append("<th>Contractor</th>").append("<th>Work Order</th>").append("<th>Dapartment</th>").append("<th>Area</th>").append("<th>Trade</th>").append("<th>Skill</th>").append("<th>LL</th>").append("<th>WCESIC</th>").append("<th>ESIC</th>").append("</tr>");

	        for(GatepassEmailDTO dto : list) {
	            html.append("<tr>").append("<td>").append(dto.getFullName()).append("</td>").append("<td>").append(dto.getAadhar()).append("</td>").append("<td>").append(dto.getPrincipalEmployer()).append("</td>").append("<td>").append(dto.getContractor()).append("</td>").append("<td>").append(dto.getWorkorder()).append("</td>").append("<td>").append(dto.getDepartment()).append("</td>").append("<td>").append(dto.getArea()).append("</td>").append("<td>").append(dto.getTrade()).append("</td>").append("<td>").append(dto.getSkill()).append("</td>").append("<td>").append(dto.getLlNumber()).append("</td>").append("<td>").append(dto.getWcesic()).append("</td>").append("<td>").append(dto.getEsic()).append("</td>").append("</tr>");
	        }

	        html.append("</table>");
	        html.append("<br><br>Regards,<br>");
	        html.append(regards);
	        return html.toString();
	    }
	  @Autowired
	    private  JavaMailSender mailSender;
	    public void sendHtmlMail(Set<String> to, String subject, String htmlContent) {

	        try {

	            MimeMessage message = mailSender.createMimeMessage();

	            MimeMessageHelper helper =
	                    new MimeMessageHelper(message, true);

	            helper.setTo(to.toArray(new String[0]));
	            helper.setSubject(subject);
	            helper.setText(htmlContent, true);
	            helper.setFrom("hemalatha.karanam@dot1.in");
	            helper.setCc("nivetha.mohansingh@dot1.in");
	            mailSender.send(message);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
