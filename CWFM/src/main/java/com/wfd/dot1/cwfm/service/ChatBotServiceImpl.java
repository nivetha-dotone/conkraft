package com.wfd.dot1.cwfm.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.ChatBotDAO;
import com.wfd.dot1.cwfm.dto.ActiveContractorDTO;
import com.wfd.dot1.cwfm.dto.ChatBotVideoDTO;
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
import com.wfd.dot1.cwfm.enums.UserRole;
import com.wfd.dot1.cwfm.pojo.GatePassMain;
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

//        case PENDING_APPROVALS:

//            return handlePendingApprovals(request);
            
        case CREATE_PENDING_APPROVALS:

            return handleCreatePendingApprovals(request);
            
        case PROJECT_PENDING_APPROVALS:

            return handleProjectPendingApprovals(request);
            
        case BLOCK_PENDING_APPROVALS:

            return handleBlockPendingApprovals(request);
            
        case UNBLOCK_PENDING_APPROVALS:

            return handleUnblockPendingApprovals(request);
            
        case BLACKLIST_PENDING_APPROVALS:

            return handleBlacklistPendingApprovals(request);
            
        case DEBLACKLIST_PENDING_APPROVALS:

            return handleDeblacklistPendingApprovals(request);

        case CANCEL_PENDING_APPROVALS:

            return handleCancelPendingApprovals(request);

        case RENEW_PENDING_APPROVALS:

            return handleRenewPendingApprovals(request);
            
        case QUICK_PENDING_APPROVALS:

            return handleQuickPendingApprovals(request);
            
        case ACTIVE_CONTRACTORS:

            return handleActiveContractors(request);

        case TODAY_GATEPASSES:

            return handleGatepass(request);

        case WORKORDER_COUNT:

            return handleWorkOrders(request);

        case PRINCIPAL_EMPLOYER:

            return handlePrincipalEmployers(request);

        case CONTRACTOR_SEARCH:

            return handleContractorSearch(request);

        case LICENSE_EXPIRY:

            return handleLicenseExpiry(request);
            
        case REGULAR_GATEPASS_VIDEO:

            return handleCreateGatepassVideo();

        case HELP:

            return buildHelpResponse(request);

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
    private ChatResponse handleCreatePendingApprovals(ChatRequest request) {

        List<PersonOrgLevel> orgLevel =
                commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef =
                orgLevel.stream()
                        .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList =
                groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList =
                groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());

        List<GatePassMain> records = chatBotDAO.getCreatePendingApprovals(peList,contList);

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.PENDING_APPROVAL_TABLE);
        response.setResponse("Regular Pending Records");
        response.setData(records);
        response.setSuggestions(getSuggestions());

        return response;
    }
    private ChatResponse handleQuickPendingApprovals(ChatRequest request) {

        List<PersonOrgLevel> orgLevel =
                commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef =
                orgLevel.stream()
                        .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList =
                groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList =
                groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());

        List<GatePassMain> records = chatBotDAO.getQuickPendingApprovals(peList,contList);

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.PENDING_APPROVAL_TABLE);
        response.setResponse("Quick Pending Records");
        response.setData(records);
        response.setSuggestions(getSuggestions());

        return response;
    }
    private ChatResponse handleProjectPendingApprovals(ChatRequest request) {

        List<PersonOrgLevel> orgLevel =
                commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef =
                orgLevel.stream()
                        .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList =
                groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList =
                groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());

        List<GatePassMain> projrecords =
                chatBotDAO.getProjectPendingApprovals(peList,contList);

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.PENDING_APPROVAL_TABLE);
        response.setResponse("Project Pending Records");
        response.setData(projrecords);
        response.setSuggestions(getSuggestions());

        return response;
    }
    private ChatResponse handleBlockPendingApprovals(ChatRequest request) {

        List<PersonOrgLevel> orgLevel =
                commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef =
                orgLevel.stream()
                        .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList =
                groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList =
                groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        List<GatePassMain> blockRecords = chatBotDAO.getBlockPendingApprovals(peList,contList);

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.PENDING_APPROVAL_TABLE);
        response.setResponse("Block Pending Records");
        response.setData(blockRecords);
        response.setSuggestions(getSuggestions());

        return response;
    }
    private ChatResponse handleUnblockPendingApprovals(ChatRequest request) {

        List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream().collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList = groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        List<GatePassMain> unblockRecords = chatBotDAO.getUnblockPendingApprovals(peList,contList);

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.PENDING_APPROVAL_TABLE);
        response.setResponse("Unblock Pending Records");
        response.setData(unblockRecords);
        response.setSuggestions(getSuggestions());

        return response;
    }
    private ChatResponse handleBlacklistPendingApprovals(ChatRequest request) {

        List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream().collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList = groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        List<GatePassMain> blackRecords = chatBotDAO.getBlacklistPendingApprovals(peList,contList);

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.PENDING_APPROVAL_TABLE);
        response.setResponse("Blacklist Pending Records");
        response.setData(blackRecords);
        response.setSuggestions(getSuggestions());

        return response;
    }
    private ChatResponse handleDeblacklistPendingApprovals(ChatRequest request) {

        List<PersonOrgLevel> orgLevel =
                commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef =
                orgLevel.stream()
                        .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList =
                groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList =
                groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        List<GatePassMain> deblackRecords = chatBotDAO.getDeblacklistPendingApprovals(peList,contList);

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.PENDING_APPROVAL_TABLE);
        response.setResponse("Deblacklist Pending Records");
        response.setData(deblackRecords);
        response.setSuggestions(getSuggestions());

        return response;
    }
    private ChatResponse handleCancelPendingApprovals(ChatRequest request) {

        List<PersonOrgLevel> orgLevel =
                commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef =
                orgLevel.stream()
                        .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList =
                groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList =
                groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        List<GatePassMain> cancelRecords =
                chatBotDAO.getCancelPendingApprovals(peList,contList);

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.PENDING_APPROVAL_TABLE);
        response.setResponse("Cancel Pending Records");
        response.setData(cancelRecords);
        response.setSuggestions(getSuggestions());

        return response;
    }
    private ChatResponse handleRenewPendingApprovals(ChatRequest request) {

        List<PersonOrgLevel> orgLevel =
                commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef =
                orgLevel.stream()
                        .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList =
                groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList =
                groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        List<GatePassMain> renewRecords =
                chatBotDAO.getRenewPendingApprovals(peList,contList);

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.PENDING_APPROVAL_TABLE);
        response.setResponse("Renew Pending Records");
        response.setData(renewRecords);
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
    
    private ChatResponse handleGatepass(ChatRequest request) {

        List<PersonOrgLevel> orgLevel =
                commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef =
                orgLevel.stream()
                        .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> peList =
                groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList =
                groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        List<GatePassMain> todayGatepasses =
                chatBotDAO.getTodayGatePass(peList,contList);

        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.TODAY_GATEPASS_TABLE);
        response.setResponse("Today's Gatepasses");
        response.setData(todayGatepasses);
        response.setSuggestions(getSuggestions());

        return response;
    }
    /**
     * Work Order Count
     */
    private ChatResponse handleWorkOrders(ChatRequest request){

        List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());
        Map<String, List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream().collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
        List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList = groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        List<WorkOrderDTO> workorderlist = chatBotDAO.getWorkOrderList(peList,contList);

        ChatResponse response = new ChatResponse();
        response.setSuccess(true);
        response.setResponseType(ResponseType.WORKORDERS_LIST);
        response.setResponse("Work Orders");
        response.setData(workorderlist);
        response.setSuggestions(getSuggestions());
        return response;
    }
    
    /**
     * Principal Employer List
     */
    private ChatResponse handlePrincipalEmployers(ChatRequest request) {

    	
    	List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());
        Map<String, List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream().collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
        List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PrincipalEmployerDTO> principalemployerList = chatBotDAO.getPrincipalEmployers(peList);
        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.PE_LIST);
        response.setResponse("Principal Employers List");
        response.setData(principalemployerList);
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
    
  //      License Expiry
   private ChatResponse handleLicenseExpiry(ChatRequest request) {

    	
    	List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(request.getUser().getUserAccount());
        Map<String, List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream().collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));
        List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());
        List<PersonOrgLevel> contList = groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        List<LicenseExpiryDTO> licenseList = chatBotDAO.getLicenseExpiryList(peList,contList);
        ChatResponse response = new ChatResponse();

        response.setSuccess(true);
        response.setResponseType(ResponseType.LICENSE_LIST);
        response.setResponse("License Expiry");
        response.setData(licenseList);
        response.setSuggestions(getSuggestions());

        return response;

    }
   
   private ChatResponse handleCreateGatepassVideo() {

	    ChatBotVideoDTO video = chatBotDAO.getTrainingVideo("Create Gatepass");

	    ChatResponse response =
	            new ChatResponse();

	    if (video == null) {

	        response.setSuccess(false);
	        response.setResponseType(ResponseType.TEXT);
	        response.setResponse("Video not found.");

	        return response;
	    }

	    String videoUrl = video.getVideoUrl();

	    if (videoUrl != null) {

	        videoUrl = videoUrl.replace("/view?usp=drive_link", "/preview");
	        videoUrl = videoUrl.replace("/view?usp=sharing", "/preview");
	        videoUrl = videoUrl.replace("/view", "/preview");

	        video.setVideoUrl(videoUrl);
	    }

	    response.setSuccess(true);
	    response.setResponseType(ResponseType.VIDEO);
	    response.setData(video);

	    return response;

	}

    /**
     * Help Response
     * @param request 
     */
    private ChatResponse buildHelpResponse(ChatRequest request) {

        ChatResponse response =
                new ChatResponse();

        response.setSuccess(true);

        response.setResponseType(ResponseType.TEXT);
        StringBuilder builder = new StringBuilder();
        if(request.getRoleName().toUpperCase().equals(UserRole.CONTRACTORSUPERVISOR.getName())){

        	builder.append("Hello 👋\n\n");

            builder.append("I can help you with the following.\n\n");

            builder.append("1. Pending Approvals\n");

            //builder.append("2. Active Contractors\n");

            builder.append("2. Today's Gatepasses\n");

            builder.append("3. Work Orders\n");

            builder.append("4. Principal Employers\n");

            builder.append("5. License Expiry\n");

            //builder.append("7. Search Contractor\n\n");

            builder.append("Examples\n\n");

            builder.append("Pending Approvals\n");

            builder.append("Today's Gatepasses\n");
        }else {
        	 builder.append("Hello 👋\n\n");

             builder.append("I can help you with the following.\n\n");

             builder.append("1. Pending Approvals\n");

             builder.append("2. Active Contractors\n");

             builder.append("3. Today's Gatepasses\n");

             builder.append("4. Work Orders\n");

             builder.append("5. Principal Employers\n");

             builder.append("6. License Expiry\n");

             //builder.append("7. Search Contractor\n\n");

             builder.append("Examples\n\n");

             builder.append("Pending Approvals\n");

             builder.append("Today's Gatepasses\n");		
        }
        //builder.append("Search Contractor ABC Pvt Ltd");

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

//        suggestions.add(
//                new SuggestedQuestion(
//                        "Search Contractor ABC Pvt Ltd"));

        return suggestions;

    }


}