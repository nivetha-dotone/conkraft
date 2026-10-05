package com.wfd.dot1.cwfm.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfd.dot1.cwfm.dto.*;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class EmployeeMapperDirect {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    @Autowired
    private GatePassService gatePassService;
    @Autowired
    private WfdEmployeeService wfdEmployeeService;

    public EmployeeMapperDirect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public String fetchTotalFromUKG(List<String> personNumbers,String startDate,String endDate) {
        try {
            if (personNumbers == null || personNumbers.isEmpty()) {
                return "Records are not inserted deu to personNum is not available in db";
            }
            LocalDate fromDate = LocalDate.parse(startDate);
            LocalDate toDate =LocalDate.parse(endDate);
            if (toDate.isBefore(fromDate)) {
                return "Records are not inserted deu to start date is greater than end date in parameters";
            }
            int batchSize = 200;
            int totalProcessedRecords = 0;
            int totalInsertedRecords = 0;
            for (LocalDate fetchDate = fromDate; !fetchDate.isAfter(toDate);fetchDate = fetchDate.plusDays(1)) {
                String apiDate =fetchDate.toString();
                for (int start = 0; start < personNumbers.size(); start += batchSize) {
                    int end = Math.min(start + batchSize, personNumbers.size());
                    List<String> employeeBatch =  personNumbers.subList(start,  end);
                    try {
                        FetchTotalsRequestDto requestDto =gatePassService.setTotalRequestBodyDirect(employeeBatch,apiDate);
                        String response =wfdEmployeeService.fetchTotals(requestDto);
                        if (response == null) {
                            return "Records are not inserted deu to response is null";
                        }
                        if (!response.startsWith("STATUS:200")) {
                            continue;
                        }
                        int[] result = saveFetchTotals(response,fetchDate);
                        totalProcessedRecords +=result[0];
                        totalInsertedRecords +=result[1];
                    } catch (Exception batchException) {
                        batchException.printStackTrace();
                    }
                }
            }
            if (totalInsertedRecords > 0) {
                return "Records are inserted in DB.";
            }
            if (totalProcessedRecords > 0) {
                return "Records are already in DB.";
            }
            return "Records are not inserted in DB.";
        } catch (Exception e) {
           return ("Records are not inserted deu to "+ e.getMessage());
        }
    }

    public int[] saveFetchTotals(String response, LocalDate historicalDate) {

        try {
            String body = response.substring( response.indexOf("BODY:") + 5);
            ObjectMapper objectMapper = new ObjectMapper();
            List<FetchTotalsResponseDto> responseList = objectMapper.readValue(body,new TypeReference<List<FetchTotalsResponseDto>>() {} );
            LocalDateTime applyDTM =historicalDate.atStartOfDay();
            int processedRecords = 0;
            int insertedRecords = 0;
            for (FetchTotalsResponseDto employeeResponse : responseList) {
                // Employee object validation
                if (employeeResponse == null || employeeResponse.getEmployee() == null) {
                    continue;
                }
                String personNumber = employeeResponse.getEmployee().getQualifier();
                if (personNumber == null || personNumber.trim().isEmpty()) {
                    continue;
                }
                personNumber = personNumber.trim();
                // No totals for employee
                if (employeeResponse.getTotals() == null || employeeResponse.getTotals().isEmpty()) {
                    continue;
                }
                for (FetchTotalsResponseDto.Total total : employeeResponse.getTotals()) {
                    if (total == null  || total.getAggregatedTotals() == null || total.getAggregatedTotals().isEmpty()) {
                        continue;
                    }
                    for (FetchTotalsResponseDto.AggregatedTotal aggregatedTotal :  total.getAggregatedTotals()) {
                        if (aggregatedTotal == null) {
                            continue;
                        }
                        if (aggregatedTotal.getPayCode() == null) {
                            continue;
                        }
                        // Pay Code
                        String payCodeName = aggregatedTotal.getPayCode().getName();
                        String payCodeType = aggregatedTotal.getAmountType();
                        BigDecimal amount = aggregatedTotal.getAmount();
                        // Location
                        String location = null;
                        if (aggregatedTotal.getLocation() != null) {
                            location = aggregatedTotal.getLocation().getName();
                        }

                        // Job
                        String job = null;
                        if (aggregatedTotal.getJob() != null) {
                            job = aggregatedTotal.getJob().getName();
                        }
                        // Labor Category
                        String laborCategory = null;
                        if (aggregatedTotal.getLaborCategory() != null) {
                            Object laborCategoryName = aggregatedTotal.getLaborCategory().get("name");
                            if (laborCategoryName != null) {
                                laborCategory = laborCategoryName.toString();
                            }
                        }
                        // Amount Mapping
                        String pcValTime = null;
                        BigDecimal pcValAmount = null;
                        BigDecimal pcValDays = null;
                        if ("HOUR".equalsIgnoreCase(payCodeType)) {
                            if (amount != null) {
                                pcValTime = amount.stripTrailingZeros().toPlainString();
                            }
                        } else if ("DAY".equalsIgnoreCase(payCodeType)) {
                            pcValDays = amount;
                        } else if ("AMOUNT".equalsIgnoreCase(payCodeType)) {
                            pcValAmount = amount;
                        }

                        processedRecords++;
                        // Save / Update
                        boolean inserted =
                                saveOrUpdateTotal(
                                        personNumber,
                                        payCodeName,
                                        payCodeType,
                                        Timestamp.valueOf(
                                                applyDTM
                                        ),
                                        java.sql.Date.valueOf(
                                                historicalDate
                                        ),
                                        pcValTime,
                                        pcValAmount,
                                        pcValDays,
                                        location,
                                        job,
                                        laborCategory
                                );

                        if (inserted) {
                            insertedRecords++;
                        }
                    }
                }
            }
            return new int[]{
                    processedRecords,
                    insertedRecords
            };

        } catch (Exception e) {

            throw new RuntimeException("Error while storing Fetch Totals response: "+ e.getMessage(), e);
        }
    }
    public String getExistingTotal() {
        return QueryFileWatcher.getQuery("GET_EXISTING_TOTAL");
    }
    public String getInsertWfcTotals() {
        return QueryFileWatcher.getQuery("INSERT_WFC_TOTALS");
    }
    public String getDeleteExistingTotal() {
        return QueryFileWatcher.getQuery("DELETE_EXISTING_TOTAL");
    }

    public boolean  saveOrUpdateTotal(String personNumber,String payCodeName,String payCodeType, Timestamp applyDTM, java.sql.Date historicalDate, String pcValTime, BigDecimal pcValAmount, BigDecimal pcValDays, String location, String job, String laborCategory) {
        try {
            String existingQuery = getExistingTotal();
            List<Map<String, Object>> existingRecords = jdbcTemplate.queryForList(existingQuery, personNumber, applyDTM, payCodeName, location, job, laborCategory);
            //  No existing record
            if (existingRecords.isEmpty()) {
                insertTotal(personNumber, payCodeName, payCodeType, applyDTM, historicalDate, pcValTime, pcValAmount, pcValDays, location, job, laborCategory);
                return true;
            }

            // Existing record found
            Map<String, Object> existing =existingRecords.get(0);

            String existingPcValTime =existing.get("PCVal_Time") != null ? existing.get("PCVal_Time").toString() : null;
            BigDecimal existingPcValAmount =existing.get("PCVal_Amount") != null ? (BigDecimal) existing.get("PCVal_Amount") : null;
            BigDecimal existingPcValDays =existing.get("PCVal_Days") != null ? (BigDecimal) existing.get("PCVal_Days") : null;
            String existingPayCodeType =existing.get("PayCodeType") != null ? existing.get("PayCodeType").toString() : null;

            // CASE 2: Values are same -> DO NOTHING

            boolean sameValue =Objects.equals(existingPcValTime,pcValTime )
                    && sameBigDecimal(existingPcValAmount, pcValAmount)
                    && sameBigDecimal(existingPcValDays, pcValDays)
                    && Objects.equals(existingPayCodeType, payCodeType);

            if (sameValue) {

                return false;
            }
            // CASE 3: Values are different
            // DELETE + INSERT
            Integer totalId = ((Number) existing.get("Total_ID")).intValue();
            String deleteQuery =  getDeleteExistingTotal();

            jdbcTemplate.update( deleteQuery, totalId);

            insertTotal(personNumber, payCodeName, payCodeType,
                    applyDTM, historicalDate, pcValTime,
                    pcValAmount, pcValDays, location, job,
                    laborCategory );
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error while validating/saving total. " + "PersonNumber=" + personNumber  + ", PayCodeName=" + payCodeName  + ", ApplyDTM=" + applyDTM + ", Error=" + e.getMessage(),e
            );
        }
    }
    private boolean sameBigDecimal(BigDecimal value1, BigDecimal value2) {
        if (value1 == null && value2 == null) {
            return true;
        }

        if (value1 == null || value2 == null) {
            return false;
        }

        return value1.compareTo(value2) == 0;
    }
    private void insertTotal(String personNumber, String payCodeName, String payCodeType, Timestamp applyDTM, java.sql.Date historicalDate, String pcValTime, BigDecimal pcValAmount, BigDecimal pcValDays, String location, String job, String laborCategory) {
        jdbcTemplate.update(getInsertWfcTotals(), personNumber, payCodeName,payCodeType,applyDTM,historicalDate,pcValTime, pcValAmount,pcValDays,location,job,laborCategory);
    }

    public String fetchPunchesFromUKG(List<String> personNumber,String startDate, String endDate) {
        try {
            List<String> personNumbers =personNumber;
            if (personNumber == null || personNumber.isEmpty()) {
                return "Records are not inserted in DB.";
            }
            // Remove duplicate employee numbers
            personNumbers = personNumbers.stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .distinct()
                    .collect(Collectors.toList());
            if (personNumbers.isEmpty()) {
                return "Records are not inserted in DB.";
            }

            int batchSize = 200;
            int totalProcessedRecords = 0;
            int totalInsertedRecords = 0;
            for (int start = 0;  start < personNumbers.size(); start += batchSize) {
                int end = Math.min( start + batchSize, personNumbers.size());
                List<String> employeeBatch =  personNumbers.subList(start, end);
                try{
                    FetchPunchesRequestDto requestDto =  gatePassService.setPunchRequestBodyDB(employeeBatch,startDate,endDate);
                    String response = wfdEmployeeService.fetchPunches(requestDto);
                    if (response == null || !response.startsWith("STATUS:200")) {
                        continue;
                    }
                    if (!response.startsWith("STATUS:200")) {
                        continue;
                    }
                    int[] result =saveFetchPunches(response);
                    totalProcessedRecords += result[0];
                    totalInsertedRecords += result[1];
                }catch (Exception batchException) {
                    batchException.printStackTrace();
                }
            }
            if (totalInsertedRecords > 0) {
                return "Records are inserted in DB.";
            }
            if (totalProcessedRecords > 0) {
                return "Records are already in DB.";
            }
            return "Records are not inserted in DB.";
        } catch (Exception e) {
            return ("Error while fetching punches from UKG: "+ e.getMessage());
        }
    }
    public String getInsertWfcPunch() {
        return QueryFileWatcher.getQuery("INSERT_WFC_PUNCH");
    }
    public int[] saveFetchPunches(String response) {
        try {
            String body =response.substring(response.indexOf("BODY:") + 5 );
            List<FetchPunchesResponseDto> responseList =objectMapper.readValue(body, new TypeReference<List<FetchPunchesResponseDto>>() {});
            String insertQuery =getInsertWfcPunch();
            int processedRecords = 0;
            int insertedRecords = 0;
            for (FetchPunchesResponseDto employeeResponse :  responseList) {
                if (employeeResponse == null|| employeeResponse.getPunches() == null || employeeResponse.getPunches().isEmpty()) {
                    continue;
                }
                for (FetchPunchesResponseDto.Punch punch : employeeResponse.getPunches()) {
                    if (punch == null) {
                        continue;
                    }
                    String employeeNumber = null;
                    if (punch.getEmployee() != null) {
                        employeeNumber =punch.getEmployee().getQualifier();
                    }
                    if (employeeNumber == null|| employeeNumber.trim().isEmpty()) {
                        continue;
                    }
                    employeeNumber =employeeNumber.trim();
                    Long punchId =punch.getId();
                    if (punchId == null) {
                        continue;
                    }
                    LocalDateTime punchDtm =parseDateTime(punch.getPunchDtm());
                    LocalDateTime roundedPunchDtm =parseDateTime(punch.getRoundedPunchDtm());
                    LocalDateTime enteredOnDtm =parseDateTime(punch.getEnteredOnDtm());
                    ExistingPunch existingPunch =getExistingPunch(employeeNumber, punchId);
                    if (existingPunch != null) {
                        boolean sameEnteredOnDtm =isSameEnteredOnDtm(existingPunch.getEnteredOnDtm(),enteredOnDtm);
                        if (sameEnteredOnDtm) {
                            processedRecords++;
                            continue;
                        }
                        jdbcTemplate.update( getDeleteWfcPunch(),existingPunch.getPunchDbId());
                    }
                    String punchType = null;
                    if (punch.getTypeOverride() != null) {

                        punchType = punch.getTypeOverride().getQualifier();
                    }
                    String exceptionApplyDate = null;
                    String exceptionName = null;
                    if (punch.getExceptions() != null&& !punch.getExceptions().isEmpty()) {
                        List<String> applyDates = new ArrayList<>();
                        List<String> exceptionNames =new ArrayList<>();
                        for (FetchPunchesResponseDto.ExceptionDto exception : punch.getExceptions()) {
                            if (exception == null) {
                                continue;
                            }
                            if (exception.getApplyDate() != null && !exception.getApplyDate().trim().isEmpty()) {
                                applyDates.add(exception.getApplyDate().trim());
                            }
                            if (exception.getExceptionType() != null && exception.getExceptionType().getName() != null
                                    && !exception.getExceptionType().getName().trim().isEmpty()) {
                                exceptionNames.add(exception.getExceptionType().getName().trim());
                            }
                        }
                        if (!applyDates.isEmpty()) {
                            exceptionApplyDate = String.join(", ", applyDates);
                        }
                        if (!exceptionNames.isEmpty()) {
                            exceptionName = String.join(", ", exceptionNames);
                        }
                    }
                    String comments = extractComments(punch.getCommentsNotes());
                    jdbcTemplate.update(
                            insertQuery,
                            employeeNumber,
                            punchId,
                            punchDtm != null ? Timestamp.valueOf(punchDtm) : null,
                            roundedPunchDtm != null ? Timestamp.valueOf(roundedPunchDtm) : null,
                            enteredOnDtm != null? Timestamp.valueOf(enteredOnDtm) : null,
                            punchType,
                            exceptionApplyDate,
                            exceptionName,
                            comments);
                    processedRecords++;
                    insertedRecords++;
                }
            }
            return new int[]{processedRecords, insertedRecords
            };

        } catch (Exception e) {
            throw new RuntimeException("Error while storing Fetch Punches response: " + e.getMessage(), e);
        }
    }
    public String getDeleteWfcPunch() {
        return QueryFileWatcher.getQuery("DELETE_WFC_PUNCH");
    }
    public String getExistingWfcPunch() {
        return QueryFileWatcher.getQuery("GET_EXISTING_WFC_PUNCH");
    }
    private String extractComments(List<FetchPunchesResponseDto.CommentsNote> commentsNotes) {

        if (commentsNotes == null
                || commentsNotes.isEmpty()) {

            return null;
        }

        List<String> comments =
                new ArrayList<>();

        for (FetchPunchesResponseDto.CommentsNote commentsNote :
                commentsNotes) {

            if (commentsNote == null
                    || commentsNote.getNotes() == null) {
                continue;
            }

            for (FetchPunchesResponseDto.Note note :
                    commentsNote.getNotes()) {

                if (note != null
                        && note.getText() != null
                        && !note.getText().trim().isEmpty()) {

                    comments.add(
                            note.getText().trim()
                    );
                }
            }
        }

        if (comments.isEmpty()) {
            return null;
        }

        return String.join(
                " | ",
                comments
        );
    }
    private boolean isSameEnteredOnDtm(Timestamp dbEnteredOnDtm,LocalDateTime ukgEnteredOnDtm) {
        if (dbEnteredOnDtm == null
                && ukgEnteredOnDtm == null) {
            return true;
        }
        if (dbEnteredOnDtm == null
                || ukgEnteredOnDtm == null) {
            return false;
        }
        Timestamp ukgTimestamp = Timestamp.valueOf(ukgEnteredOnDtm);
        return dbEnteredOnDtm.equals(ukgTimestamp);
    }
    private ExistingPunch getExistingPunch( String employeeNumber,Long punchId) {
        List<ExistingPunch> punches =jdbcTemplate.query(getExistingWfcPunch(),
                (rs, rowNum) -> {
                    ExistingPunch punch = new ExistingPunch();
                    punch.setPunchDbId( rs.getInt("Punch_DB_ID"));
                    punch.setEnteredOnDtm( rs.getTimestamp("EnteredOnDtm") );
                    return punch;
                },
                employeeNumber,
                punchId
        );
        return punches.isEmpty() ? null : punches.get(0);
    }
    private LocalDateTime parseDateTime(String value) {

        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {

            return OffsetDateTime
                    .parse(value)
                    .toLocalDateTime();

        } catch (DateTimeParseException e) {

            try {

                return LocalDateTime.parse(value);

            } catch (DateTimeParseException ex) {



                return null;
            }
        }
    }
    public String  fetchScheduleFromUKG(List<String> personNumber,  String startDate,  String endDate) {
        try {
            List<String> personNumbers = personNumber;
            if (personNumbers == null || personNumbers.isEmpty()) {
                return "personNumber is Null";
            }
            personNumbers = personNumbers.stream().filter(Objects::nonNull).map(String::trim).filter(s -> !s.isEmpty()).distinct().collect(Collectors.toList());
            int batchSize = 200;
            int totalProcessedRecords = 0;
            int totalInsertedRecords = 0;
            for (int start = 0;
                 start < personNumbers.size();
                 start += batchSize) {

                int end =
                        Math.min(
                                start + batchSize,
                                personNumbers.size()
                        );

                List<String> employeeBatch =
                        personNumbers.subList(start, end);

                try {

                    System.out.println(
                            "Fetching UKG Schedule for batch "
                                    + (start + 1)
                                    + "-"
                                    + end
                    );

                    FetchScheduleShiftRequestDto requestDto =
                            gatePassService.setScheduleRequestBodyDB(
                                    employeeBatch,
                                    startDate,
                                    endDate
                            );

                    String response =
                            wfdEmployeeService.fetchSchedule(
                                    requestDto
                            );

                    if (response == null) {
                        continue;
                    }

                    if (!response.startsWith("STATUS:200")) {
                        continue;
                    }

                    int[] result = saveScheduleShifts(response);
                    totalProcessedRecords += result[0];
                    totalInsertedRecords += result[1];
                } catch (Exception batchException) {
                    batchException.printStackTrace();
                }
            }
            if (totalInsertedRecords > 0) {

                return "Records are inserted in DB.";
            }

            if (totalProcessedRecords > 0) {

                return "Records are already in DB.";
            }

            return "Records are not inserted in DB.";

        } catch (Exception e) {
            return ("Error while fetching schedule from UKG: "+ e.getMessage());
        }
    }
    public int[] saveScheduleShifts(String response) {
        try {
            String body =response.substring(response.indexOf("BODY:") + 5 );
            List<FetchScheduleShiftResponseDto> responseList =objectMapper.readValue(body, new TypeReference<List<FetchScheduleShiftResponseDto>>() {}
            );
            int processedRecords = 0;
            int insertedRecords = 0;
            for (FetchScheduleShiftResponseDto employeeResponse : responseList) {
                if (employeeResponse == null || employeeResponse.getScheduleShifts() == null || employeeResponse.getScheduleShifts().isEmpty()) {
                    continue;
                }
                for (FetchScheduleShiftResponseDto.ScheduleShift shift : employeeResponse.getScheduleShifts()) {
                    if (shift == null || shift.getId() == null) {
                        continue;
                    }
                    Long shiftId = shift.getId();
                    String personNumber = null;
                    if (shift.getEmployee() != null) {
                        personNumber =shift.getEmployee().getQualifier();
                    }
                    if (personNumber == null || personNumber.trim().isEmpty()) {
                        continue;
                    }
                    personNumber = personNumber.trim();
                    LocalDateTime startDateTime =parseDateTime( shift.getStartDateTime() );
                    LocalDateTime endDateTime =parseDateTime( shift.getEndDateTime() );
                    ExistingScheduleShift existingShift =getExistingScheduleShift( shiftId );
                    if (existingShift != null) {
                        boolean sameShift = personNumber.equals(existingShift.getPersonNumber() )
                                && isSameDateTime(existingShift.getStartDateTimeShift(),startDateTime)
                                && isSameDateTime(existingShift.getEndDateTimeShift(), endDateTime );
                        if (sameShift) {
                            processedRecords++;
                            continue;
                        }
                        jdbcTemplate.update( getDeleteWfcScheduleShiftSegments(),shiftId);
                        jdbcTemplate.update( getDeleteWfcScheduleShift(), shiftId );
                    }
                    jdbcTemplate.update(getInsertWfcScheduleShift(),shiftId,personNumber, startDateTime != null
                            ? Timestamp.valueOf( startDateTime )
                            : null, endDateTime != null ? Timestamp.valueOf(endDateTime)
                            : null );


                    if (shift.getSegments() != null) {
                        for (FetchScheduleShiftResponseDto.Segment segment : shift.getSegments()) {
                            if (segment == null || segment.getId() == null) {
                                continue;
                            }
                            Long segmentId = segment.getId();
                            String segmentType = null;
                            if (segment.getSegmentTypeRef() != null) {
                                segmentType =segment.getSegmentTypeRef().getQualifier();
                            }
                            LocalDateTime segmentStart =parseDateTime( segment.getStartDateTime() );
                            LocalDateTime segmentEnd =parseDateTime( segment.getEndDateTime() );
                            Long orgJobId = null;
                            if (segment.getOrgJobRef() != null) {
                                orgJobId = segment.getOrgJobRef().getId();
                            }
                            String workrule = null;
                            if (segment.getWorkruleRef() != null) {
                                workrule = segment.getWorkruleRef().getQualifier();
                            }
                            String primaryWorkrule = null;
                            if (segment.getPrimaryWorkruleRef() != null) {
                                primaryWorkrule =segment.getPrimaryWorkruleRef().getQualifier();
                            }

                            jdbcTemplate.update(getInsertWfcScheduleShiftSegment(),
                                    segmentId,
                                    shiftId,
                                    segmentType,
                                    segmentStart != null ? Timestamp.valueOf(segmentStart): null,
                                    segmentEnd != null ? Timestamp.valueOf(segmentEnd ): null,
                                    orgJobId,
                                    workrule,
                                    primaryWorkrule,
                                    segment.getType()
                            );
                        }
                    }
                    processedRecords++;
                    insertedRecords++;
                }
            }
            return new int[] {
                    processedRecords,
                    insertedRecords
            };
        } catch (Exception e) {

            throw new RuntimeException("Error while storing Schedule Shift response: " + e.getMessage(), e );
        }
    }
    public String getInsertWfcScheduleShift() {
        return QueryFileWatcher.getQuery("INSERT_WFC_SCHEDULE_SHIFT");
    }
    public String getDeleteWfcScheduleShiftSegments() {
        return QueryFileWatcher.getQuery("DELETE_WFC_SCHEDULE_SHIFT_SEGMENTS");
    }
    private boolean isSameDateTime(Timestamp dbDateTime, LocalDateTime ukgDateTime) {
        if (dbDateTime == null && ukgDateTime == null) {
            return true;
        }
        if (dbDateTime == null || ukgDateTime == null) {
            return false;
        }
        return dbDateTime.equals( Timestamp.valueOf(ukgDateTime)
        );
    }
    public String getInsertWfcScheduleShiftSegment() {
        return QueryFileWatcher.getQuery(  "INSERT_WFC_SCHEDULE_SHIFT_SEGMENT" );
    }
    public String getDeleteWfcScheduleShift() {
        return QueryFileWatcher.getQuery("DELETE_WFC_SCHEDULE_SHIFT");
    }
    private ExistingScheduleShift getExistingScheduleShift(Long shiftId) {
        List<ExistingScheduleShift> shifts = jdbcTemplate.query(
                getExistingWfcScheduleShift(),
                (rs, rowNum) -> {
                    ExistingScheduleShift shift = new ExistingScheduleShift();
                    shift.setScheduleShiftDbId(rs.getInt("ScheduleShift_DB_ID"));
                    shift.setPersonNumber( rs.getString("PersonNumber") );
                    shift.setStartDateTimeShift( rs.getTimestamp("StartDateTimeShift"));
                    shift.setEndDateTimeShift( rs.getTimestamp( "EndDateTimeShift" ));
                    return shift;
                },shiftId );
        return shifts.isEmpty() ? null : shifts.get(0);
    }
    public String getExistingWfcScheduleShift() {
        return QueryFileWatcher.getQuery("GET_EXISTING_WFC_SCHEDULE_SHIFT");
    }




}
