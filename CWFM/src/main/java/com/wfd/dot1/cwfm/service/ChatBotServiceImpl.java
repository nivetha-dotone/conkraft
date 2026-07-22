package com.wfd.dot1.cwfm.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.ChatBotDAO;
import com.wfd.dot1.cwfm.dto.ActiveContractorDTO;
import com.wfd.dot1.cwfm.dto.ChatRequest;
import com.wfd.dot1.cwfm.dto.ChatResponse;
import com.wfd.dot1.cwfm.dto.ContractorDTO;
import com.wfd.dot1.cwfm.dto.DashboardDTO;
import com.wfd.dot1.cwfm.dto.GatepassDTO;
import com.wfd.dot1.cwfm.dto.LicenseExpiryDTO;
import com.wfd.dot1.cwfm.dto.PendingApprovalDTO;
import com.wfd.dot1.cwfm.dto.PrincipalEmployerDTO;
import com.wfd.dot1.cwfm.dto.SuggestedQuestion;
import com.wfd.dot1.cwfm.dto.WorkOrderDTO;
import com.wfd.dot1.cwfm.enums.IntentType;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;
import com.wfd.dot1.cwfm.util.IntentMatcher;
import com.wfd.dot1.cwfm.util.ResponseType;

@Service
public class ChatBotServiceImpl implements ChatBotService {

    @Autowired
    private ChatBotDAO chatBotDAO;
    @Autowired
    private CommonService commonService;

    @Override
    public ChatResponse processMessage(ChatRequest request) {

        ChatResponse response = new ChatResponse();

        IntentType intent =
                IntentMatcher.detectIntent(
                        request.getQuestion());

        switch (intent) {

        case PENDING_APPROVALS:

            return handlePendingApprovals(request);

        case ACTIVE_CONTRACTORS:

            return handleActiveContractors(request);

        case GATEPASS_COUNT:

            return handleGatepass(request);

        case WORKORDER_COUNT:

            return handleWorkOrders(request);

        case PRINCIPAL_EMPLOYER:

            return handlePrincipalEmployers();

        case CONTRACTOR_SEARCH:

            return handleContractorSearch(request);

        case LICENSE_EXPIRY:

            return handleLicenseExpiry(request);

        case HELP:

            return buildHelpResponse();

        default:

            return buildUnknownResponse();

        }

    }
    private ChatResponse handlePendingApprovals(ChatRequest request){
    	
    	List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
                .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> contList = groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());

        String userId = String.valueOf(request.getUserId());
        String roleName = request.getUser().getRoleName();

      


        Integer pendingCount =
                chatBotDAO.getPendingApprovalCount(peList,contList);

        PendingApprovalDTO dto =
                new PendingApprovalDTO();

        dto.setPendingCount(pendingCount);

        ChatResponse response =
                new ChatResponse();

        response.setSuccess(true);

        response.setResponseType(ResponseType.CARD);

        response.setResponse("Pending Approvals");

        response.setData(dto);

        response.setSuggestions(getSuggestions());

        return response;

    }
    private ChatResponse handleActiveContractors(ChatRequest request) {

        List<PersonOrgLevel> orgLevel =
                commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef =
                orgLevel.stream()
                        .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList =
                groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());

        List<ActiveContractorDTO> contractors =
                chatBotDAO.getActiveContractors(peList);

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.CARD);
        response.setResponse("Active Contractors (" + contractors.size() + ")");
        response.setData(contractors);
        response.setSuggestions(getSuggestions());

        return response;
    }
    
    /**
     * Gatepass Count
     */
    private ChatResponse handleGatepass(ChatRequest request){

        Integer gatepassCount =
                chatBotDAO.getTodayGatePassCount(
                        request.getPrincipalEmployerId());

        GatepassDTO dto =
                new GatepassDTO();

        dto.setTodayGatepasses(gatepassCount);

        ChatResponse response =
                new ChatResponse();

        response.setSuccess(true);

        response.setResponseType(ResponseType.CARD);

        response.setResponse("Today's Gatepasses");

        response.setData(dto);

        response.setSuggestions(getSuggestions());

        return response;

    }


    /**
     * Work Order Count
     */
    private ChatResponse handleWorkOrders(ChatRequest request){

        Integer workOrderCount =
                chatBotDAO.getWorkOrderCount(
                        request.getPrincipalEmployerId());

        WorkOrderDTO dto =
                new WorkOrderDTO();

        dto.setWorkOrderCount(workOrderCount);

        ChatResponse response =
                new ChatResponse();

        response.setSuccess(true);

        response.setResponseType(ResponseType.CARD);

        response.setResponse("Work Orders");

        response.setData(dto);

        response.setSuggestions(getSuggestions());

        return response;

    }
    
    /**
     * Principal Employer List
     */
    private ChatResponse handlePrincipalEmployers() {

        List<PrincipalEmployerDTO> peList =
                chatBotDAO.getPrincipalEmployers();

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);

        response.setResponseType(ResponseType.TABLE);

        response.setResponse("Principal Employer List");

        response.setData(peList);

        response.setSuggestions(getSuggestions());

        return response;

    }


    /**
     * Search Contractor
     *
     * Supported Questions:
     * Search Contractor ABC
     * Search Contractor XYZ Pvt Ltd
     */
    private ChatResponse handleContractorSearch(ChatRequest request) {

        ChatResponse response = new ChatResponse();

        String question = request.getQuestion();

        String contractorName = extractContractorName(question);

        if(contractorName == null || contractorName.trim().isEmpty()) {

            response.setSuccess(false);

            response.setResponseType(ResponseType.TEXT);

            response.setResponse(
                    "Please provide contractor name.\n\n"
                    + "Example:\n"
                    + "Search Contractor ABC Pvt Ltd");

            response.setSuggestions(getSuggestions());

            return response;

        }

        ContractorDTO contractor =
                chatBotDAO.searchContractor(contractorName);

        if(contractor == null) {

            response.setSuccess(false);

            response.setResponseType(ResponseType.TEXT);

            response.setResponse(
                    "No contractor found with name : "
                    + contractorName);

            response.setSuggestions(getSuggestions());

            return response;

        }

        response.setSuccess(true);

        response.setResponseType(ResponseType.CARD);

        response.setResponse("Contractor Details");

        response.setData(contractor);

        response.setSuggestions(getSuggestions());

        return response;

    }


    /**
     * Extract contractor name from question
     *
     * Example:
     * Search Contractor ABC Pvt Ltd
     *
     * Returns:
     * ABC Pvt Ltd
     */
    private String extractContractorName(String question){

        if(question == null){

            return "";

        }

        question = question.trim();

        question = question.replaceFirst(
                "(?i)search contractor", "");

        return question.trim();

    }
    
    /**
     * License Expiry
     */
    private ChatResponse handleLicenseExpiry(ChatRequest request) {

        Integer expiryCount =
                chatBotDAO.getLicenseExpiryCount(
                        request.getPrincipalEmployerId());

        LicenseExpiryDTO dto =
                new LicenseExpiryDTO();

        dto.setExpiryCount(expiryCount);

        ChatResponse response =
                new ChatResponse();

        response.setSuccess(true);

        response.setResponseType(ResponseType.CARD);

        response.setResponse("License Expiry");

        response.setData(dto);

        response.setSuggestions(getSuggestions());

        return response;

    }


    /**
     * Help Response
     */
    private ChatResponse buildHelpResponse() {

        ChatResponse response =
                new ChatResponse();

        response.setSuccess(true);

        response.setResponseType(ResponseType.TEXT);

        StringBuilder builder =
                new StringBuilder();

        builder.append("Hello 👋\n\n");

        builder.append("I can help you with the following.\n\n");

        builder.append("1. Pending Approvals\n");

        builder.append("2. Active Contractors\n");

        builder.append("3. Today's Gatepasses\n");

        builder.append("4. Work Orders\n");

        builder.append("5. Principal Employers\n");

        builder.append("6. License Expiry\n");

        builder.append("7. Search Contractor\n\n");

        builder.append("Examples\n\n");

        builder.append("Pending Approvals\n");

        builder.append("Today's Gatepasses\n");

        builder.append("Search Contractor ABC Pvt Ltd");

        response.setResponse(builder.toString());

        response.setSuggestions(getSuggestions());

        return response;

    }


    /**
     * Unknown Response
     */
    private ChatResponse buildUnknownResponse() {

        ChatResponse response =
                new ChatResponse();

        response.setSuccess(false);

        response.setResponseType(ResponseType.TEXT);

        response.setResponse(

                "Sorry, I couldn't understand your request.\n\n"

                + "Type 'Help' to know what I can do."

        );

        response.setSuggestions(getSuggestions());

        return response;

    }


    /**
     * Suggestions
     */
    private List<SuggestedQuestion> getSuggestions(){

        List<SuggestedQuestion> suggestions =
                new ArrayList<>();

        suggestions.add(
                new SuggestedQuestion(
                        "Pending Approvals"));

        suggestions.add(
                new SuggestedQuestion(
                        "Active Contractors"));

        suggestions.add(
                new SuggestedQuestion(
                        "Today's Gatepasses"));

        suggestions.add(
                new SuggestedQuestion(
                        "Work Orders"));

        suggestions.add(
                new SuggestedQuestion(
                        "Principal Employers"));

        suggestions.add(
                new SuggestedQuestion(
                        "License Expiry"));

        suggestions.add(
                new SuggestedQuestion(
                        "Search Contractor ABC Pvt Ltd"));

        return suggestions;

    }


}