package com.wfd.dot1.cwfm.service;




import com.wfd.dot1.cwfm.dto.FetchPunchesRequestDto;
import com.wfd.dot1.cwfm.dto.FetchScheduleShiftRequestDto;
import com.wfd.dot1.cwfm.dto.FetchTotalsRequestDto;
import com.wfd.dot1.cwfm.dto.GatePassToOnBoard;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GatePassService {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    public String getGTByTrnsId() {
        return QueryFileWatcher.getQuery("GET_DETAILS_BY_GATEPASSID_QUERY");
    }
    public String getBribsQuery() {
        return QueryFileWatcher.getQuery("GET_BRIBS_BY_GATEPASSID_QUERY");
    }
    private static final Logger log = LoggerFactory.getLogger(GatePassService.class.getName());





    public String getPERSONUMBER() {
        return QueryFileWatcher.getQuery("GETPERSONUMBER");
    }
    public String getFetchTotalHyperFind() {
        return QueryFileWatcher.getQuery("FETCH_TOTAL_HYPERFIND");
    }

    public String getFetchTotalSelect() {
        return QueryFileWatcher.getQuery("FETCH_TOTAL_SELECT");
    }
    public String getFetchPunchSelect() {
        return QueryFileWatcher.getQuery("FETCH_PUNCH_SELECT");
    }
    public String getFetchScheduleSelect() {
        return QueryFileWatcher.getQuery("FETCH_SCHEDULE_SELECT");
    }




    public FetchTotalsRequestDto setTotalRequestBody(List<String> personNumbers) {

        try {

            FetchTotalsRequestDto requestDto =
                    new FetchTotalsRequestDto();

            // Today's date
            String today = LocalDate.now()
                    .format(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    );

            // ---------------------------------------------
            // Date Range
            // ---------------------------------------------

            FetchTotalsRequestDto.DateRange dateRange =
                    new FetchTotalsRequestDto.DateRange();

            dateRange.setStartDate(today);
            dateRange.setEndDate(today);

            // ---------------------------------------------
            // Employees
            // ---------------------------------------------

            FetchTotalsRequestDto.Employees employees =
                    new FetchTotalsRequestDto.Employees();

            employees.setQualifiers(personNumbers);

            // ---------------------------------------------
            // HyperFind
            // ---------------------------------------------

            FetchTotalsRequestDto.HyperFind hyperFind =
                    new FetchTotalsRequestDto.HyperFind();

            String hyperFindValue =
                    getFetchTotalHyperFind();

            if (hyperFindValue == null
                    || hyperFindValue.trim().isEmpty()) {

                hyperFindValue = "All Home";
            }

            hyperFind.setQualifier(hyperFindValue);

            // ---------------------------------------------
            // Where
            // ---------------------------------------------

            FetchTotalsRequestDto.Where where =
                    new FetchTotalsRequestDto.Where();

            where.setDateRange(dateRange);
            where.setEmployees(employees);
            where.setHyperFind(hyperFind);

            requestDto.setWhere(where);

            // ---------------------------------------------
            // Select
            // ---------------------------------------------

            String selectValue =
                    getFetchTotalSelect();

            if (selectValue == null
                    || selectValue.trim().isEmpty()) {

                selectValue = "TOTALS";
            }

            List<String> selectList =
                    Arrays.stream(selectValue.split(","))
                            .map(String::trim)
                            .filter(value -> !value.isEmpty())
                            .collect(Collectors.toList());

            if (selectList.size() > 10) {

                throw new IllegalArgumentException(
                        "Fetch Totals select list cannot contain "
                                + "more than 10 entities."
                );
            }

            requestDto.setSelect(selectList);

            return requestDto;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error while creating Fetch Totals request: "
                            + e.getMessage(),
                    e
            );
        }
    }

    public FetchTotalsRequestDto setTotalRequestBodyDirect(List<String> personNumbers, String fetchDate) {
        try {
            FetchTotalsRequestDto requestDto = new FetchTotalsRequestDto();
            FetchTotalsRequestDto.DateRange dateRange =new FetchTotalsRequestDto.DateRange();
            dateRange.setStartDate(fetchDate);
            dateRange.setEndDate(fetchDate);
            FetchTotalsRequestDto.Employees employees =  new FetchTotalsRequestDto.Employees();
            employees.setQualifiers(personNumbers);
            FetchTotalsRequestDto.HyperFind hyperFind =new FetchTotalsRequestDto.HyperFind();
            String hyperFindValue =getFetchTotalHyperFind();
            if (hyperFindValue == null|| hyperFindValue.trim().isEmpty()) {
                hyperFindValue = "All Home";
            }
            hyperFind.setQualifier(hyperFindValue);
            FetchTotalsRequestDto.Where where =new FetchTotalsRequestDto.Where();
            where.setDateRange( dateRange);
            where.setEmployees(employees);
            where.setHyperFind(hyperFind);
            requestDto.setWhere( where);
            String selectValue =   getFetchTotalSelect();

            if (selectValue == null || selectValue.trim().isEmpty()) {
                selectValue = "TOTALS";
            }
            List<String> selectList = Arrays.stream(selectValue.split(",")).map(String::trim).filter(value ->!value.isEmpty()).collect(Collectors.toList());
            if (selectList.size() > 10) {
                throw new IllegalArgumentException( "Fetch Totals select list cannot "+ "contain more than 10 entities.");
            }
            requestDto.setSelect(selectList);
            return requestDto;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating Fetch Totals request: "+ e.getMessage(),e);
        }
    }
    public FetchPunchesRequestDto setPunchRequestBody(
            List<String> personNumbers) {

        FetchPunchesRequestDto requestDto =
                new FetchPunchesRequestDto();

        String today =
                LocalDate.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd"
                                )
                        );

        FetchPunchesRequestDto.DateRange dateRange =
                new FetchPunchesRequestDto.DateRange();

        dateRange.setStartDate(today);
        dateRange.setEndDate(today);

        FetchPunchesRequestDto.Employees employees =
                new FetchPunchesRequestDto.Employees();

        employees.setQualifiers(personNumbers);

        FetchPunchesRequestDto.HyperFind hyperFind =
                new FetchPunchesRequestDto.HyperFind();

        hyperFind.setQualifier(getFetchTotalHyperFind());

        FetchPunchesRequestDto.Where where =
                new FetchPunchesRequestDto.Where();

        where.setDateRange(dateRange);
        where.setEmployees(employees);
        where.setHyperFind(hyperFind);

        requestDto.setWhere(where);

        requestDto.setSelect(
                Collections.singletonList(getFetchPunchSelect())
        );

        return requestDto;
    }


    public FetchPunchesRequestDto setPunchRequestBodyDB(
            List<String> personNumbers,String startDate, String endDate) {

        FetchPunchesRequestDto requestDto =
                new FetchPunchesRequestDto();

        String today =
                LocalDate.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd"
                                )
                        );

        FetchPunchesRequestDto.DateRange dateRange =
                new FetchPunchesRequestDto.DateRange();

        dateRange.setStartDate(startDate);
        dateRange.setEndDate(endDate);

        FetchPunchesRequestDto.Employees employees =
                new FetchPunchesRequestDto.Employees();

        employees.setQualifiers(personNumbers);

        FetchPunchesRequestDto.HyperFind hyperFind =
                new FetchPunchesRequestDto.HyperFind();

        hyperFind.setQualifier(getFetchTotalHyperFind());

        FetchPunchesRequestDto.Where where =
                new FetchPunchesRequestDto.Where();

        where.setDateRange(dateRange);
        where.setEmployees(employees);
        where.setHyperFind(hyperFind);

        requestDto.setWhere(where);

        requestDto.setSelect(
                Collections.singletonList(getFetchPunchSelect())
        );

        return requestDto;
    }

    public FetchScheduleShiftRequestDto setScheduleRequestBody(List<String> personNumbers) {
        FetchScheduleShiftRequestDto requestDto = new FetchScheduleShiftRequestDto();

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd" ));

        FetchScheduleShiftRequestDto.DateRange dateRange = new FetchScheduleShiftRequestDto.DateRange();
        dateRange.setStartDate(today);
        dateRange.setEndDate(today);
        FetchScheduleShiftRequestDto.Employees employees =new FetchScheduleShiftRequestDto.Employees();
        employees.setQualifiers(personNumbers);
        FetchScheduleShiftRequestDto.HyperFind hyperFind =new FetchScheduleShiftRequestDto.HyperFind();
        hyperFind.setQualifier(getFetchTotalHyperFind());
        FetchScheduleShiftRequestDto.Where where =new FetchScheduleShiftRequestDto.Where();
        where.setDateRange(dateRange);
        where.setEmployees(employees);
        where.setHyperFind(hyperFind);
        requestDto.setWhere(where);
        requestDto.setSelect( Collections.singletonList(getFetchScheduleSelect()));
        return requestDto;
    }

   public FetchScheduleShiftRequestDto setScheduleRequestBodyDB( List<String> personNumbers,String startDate,String endDate) {
        FetchScheduleShiftRequestDto requestDto = new FetchScheduleShiftRequestDto();

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd" ));

        FetchScheduleShiftRequestDto.DateRange dateRange = new FetchScheduleShiftRequestDto.DateRange();
        dateRange.setStartDate(startDate);
        dateRange.setEndDate(endDate);
        FetchScheduleShiftRequestDto.Employees employees =new FetchScheduleShiftRequestDto.Employees();
        employees.setQualifiers(personNumbers);
        FetchScheduleShiftRequestDto.HyperFind hyperFind =new FetchScheduleShiftRequestDto.HyperFind();
        hyperFind.setQualifier(getFetchTotalHyperFind());
        FetchScheduleShiftRequestDto.Where where =new FetchScheduleShiftRequestDto.Where();
        where.setDateRange(dateRange);
        where.setEmployees(employees);
        where.setHyperFind(hyperFind);
        requestDto.setWhere(where);
        requestDto.setSelect( Collections.singletonList(getFetchScheduleSelect()));
        return requestDto;
    }


}
