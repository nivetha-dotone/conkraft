

package com.wfd.dot1.cwfm.controller;

import com.wfd.dot1.cwfm.dto.EmployeeRequestDTO;
import com.wfd.dot1.cwfm.dto.GatePassToOnBoard;
import com.wfd.dot1.cwfm.enums.EmployeeStatusType;
import com.wfd.dot1.cwfm.service.EmployeeMapper;
import com.wfd.dot1.cwfm.service.GatePassToOnBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/WFDjson"})
public class CreateEmpFetchByGatePassAPICALL {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private GatePassToOnBoardService passToOnBoardService;

    public CreateEmpFetchByGatePassAPICALL() {
    }


    @PostMapping({"/CreateEmpByGatePIdStatisCall/{gatePassId}"})
    public ResponseEntity<?> createEmpGateStatic(@PathVariable String gatePassId) {
        try {
            String responseAPI = this.employeeMapper.gatePassEmpDtoStatic(gatePassId);
            return responseAPI != null ? new ResponseEntity(responseAPI, HttpStatus.OK) : new ResponseEntity("it's null ", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping({"/fetchONDTOByTrns/{gatePassId}"})
    public ResponseEntity<?> fetchOnBoardingDetailsTest(@PathVariable String gatePassId) {
        try {
            GatePassToOnBoard individualOnBoardDetailsByTrnId = this.passToOnBoardService.getIndividualOnBoardDetailsByTrnId(gatePassId);
            return individualOnBoardDetailsByTrnId != null ? new ResponseEntity(individualOnBoardDetailsByTrnId, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping({"/fetchONByTrnsConvertJsonBeforeStore/{gatePassId}"})
    public ResponseEntity<?> fetchOnBoardingDetailsTest2(@PathVariable String gatePassId) {
        try {
            EmployeeRequestDTO individualOnBoardDetailsByTrnId = this.employeeMapper.gatePassEmpDto(gatePassId);
            return individualOnBoardDetailsByTrnId != null ? new ResponseEntity(individualOnBoardDetailsByTrnId, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @Scheduled(cron = "0 */15 * * * *")
    @GetMapping("/schedularUpdate")
    public void  addOnBoardingSchedular(){
        try{

            employeeMapper.gatePassEmpDtoSchedular();

        } catch (Exception e) {

        }
    }


    @PostMapping("/addByTrnsIdToUKG/{trnId}")
    public ResponseEntity<?> addOnBoardingDetailsActual(@PathVariable String trnId) {

        Long gpTransactionId = null;

        try {

            gpTransactionId = Long.parseLong(trnId);

            String result = employeeMapper.gatePassEmpDtoDynamic(trnId);

            if (result == null) {

                passToOnBoardService.saveErrorTrace(
                        gpTransactionId,
                        404,
                        "Transaction Id Not Found"
                );

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Transaction Id Not Found");
            }

            if (result.matches("\\d+")) {

                Long personKey = Long.parseLong(result);

                passToOnBoardService.saveSuccessTrace(
                        gpTransactionId,
                        personKey,
                        200,
                        true
                );

                return ResponseEntity.ok(result);
            }

            if (result.startsWith("STATUS:")) {

                String[] parts = result.split("\n", 2);

                int statusCode = Integer.parseInt(
                        parts[0].replace("STATUS:", "").trim()
                );

                String body = parts.length > 1 ? parts[1] : "";

                passToOnBoardService.saveErrorTrace(
                        gpTransactionId,
                        statusCode,
                        body
                );

                return ResponseEntity.status(statusCode).body(body);
            }

            passToOnBoardService.saveErrorTrace(
                    gpTransactionId,
                    500,
                    result
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(result);

        } catch (Exception e) {

            if (gpTransactionId != null) {
                passToOnBoardService.saveErrorTrace(
                        gpTransactionId,
                        500,
                        e.getMessage()
                );
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: " + e.getMessage());
        }
    }

    @PutMapping({"/updateByTrnsIdToUKG/{trendId}"})
    public ResponseEntity<?> updateOnBoardingDetails(@PathVariable String trendId) {
        try {
            String gatePassEmpDtoDynamic = this.employeeMapper.updatePassEmpDtoDynamic(trendId);
            return gatePassEmpDtoDynamic != null ? new ResponseEntity(gatePassEmpDtoDynamic, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping({"/postSkillInWFD/{gmId}"})
    public ResponseEntity<?> postSkills(@PathVariable Integer gmId) {
        try {
            String individualOnBoardDetailsByTrnId = this.employeeMapper.postSkillTowfd(gmId);
            if (individualOnBoardDetailsByTrnId != null && individualOnBoardDetailsByTrnId.equals("already in the WFD")) {
                return new ResponseEntity("already in the WFD", HttpStatus.BAD_REQUEST);
            } else {
                return individualOnBoardDetailsByTrnId != null ? new ResponseEntity(individualOnBoardDetailsByTrnId, HttpStatus.OK) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping({"/postProfLevels/{gmId}"})
    public ResponseEntity<?> postProfLevels(@PathVariable Integer gmId) {
        try {
            String individualOnBoardDetailsByTrnId = this.employeeMapper.postProfTowfd(gmId);
            if (individualOnBoardDetailsByTrnId != null && individualOnBoardDetailsByTrnId.equals("already in the WFD")) {
                return new ResponseEntity("already in the WFD", HttpStatus.BAD_REQUEST);
            } else {
                return individualOnBoardDetailsByTrnId != null ? new ResponseEntity(individualOnBoardDetailsByTrnId, HttpStatus.OK) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping({"/postCertific/{gmId}"})
    public ResponseEntity<?> postCertific(@PathVariable Integer gmId) {
        try {
            String individualOnBoardDetailsByTrnId = this.employeeMapper.postCertificTowfd(gmId);
            if (individualOnBoardDetailsByTrnId != null && individualOnBoardDetailsByTrnId.equals("already in the WFD")) {
                return new ResponseEntity("already in the WFD", HttpStatus.BAD_REQUEST);
            } else {
                return individualOnBoardDetailsByTrnId != null ? new ResponseEntity(individualOnBoardDetailsByTrnId, HttpStatus.OK) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping({"/assignMentCertificate/{gatepassId}"})
    public ResponseEntity<?> assignmentGatepassId(@PathVariable String gatepassId) {
        try {
            String individualOnBoardDetailsByTrnId = this.employeeMapper.assignmentTowfd(gatepassId);
            if (individualOnBoardDetailsByTrnId != null && individualOnBoardDetailsByTrnId.equals("already in the WFD")) {
                return new ResponseEntity("already in the WFD", HttpStatus.BAD_REQUEST);
            } else {
                return individualOnBoardDetailsByTrnId != null ? new ResponseEntity(individualOnBoardDetailsByTrnId, HttpStatus.OK) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping({"/assignMentSKILLSPRO/{gatepassId}"})
    public ResponseEntity<?> assignmentSkillsPro(@PathVariable String gatepassId) {
        try {
            String individualOnBoardDetailsByTrnId = this.employeeMapper.assignmentSkillsProTowfd(gatepassId);
            if (individualOnBoardDetailsByTrnId != null && individualOnBoardDetailsByTrnId.equals("already in the WFD")) {
                return new ResponseEntity("already in the WFD", HttpStatus.BAD_REQUEST);
            } else {
                return individualOnBoardDetailsByTrnId != null ? new ResponseEntity(individualOnBoardDetailsByTrnId, HttpStatus.OK) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/updatedEmpStatus/{gatepassId}/{empStatus}")
    public ResponseEntity<String> updateEmpStatusTerOrAct(
            @PathVariable String gatepassId,
            @PathVariable EmployeeStatusType empStatus) {

        try {
            String response = employeeMapper.updateEmpstatusTrorAc(gatepassId, empStatus);

            if ("already in the WFD".equalsIgnoreCase(response)) {
                return ResponseEntity.badRequest()
                        .body("Not updated Employment status in WFD");
            }

            return response != null
                    ? ResponseEntity.ok(response)
                    : ResponseEntity.internalServerError().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/checkWorkOrderExMAil")
    public ResponseEntity<String> workorderMail() {

        try {
            employeeMapper.setupWorkorderMail();
            return ResponseEntity.ok("Workorder expiry emails triggered successfully.");

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while sending workorder expiry emails: " + e.getMessage());
        }
    }

    @GetMapping("/checkLLExMAil")
    public ResponseEntity<String> lLMail() {

        try {
            employeeMapper.setupLaborLMail();
            return ResponseEntity.ok("LL expiry emails triggered successfully.");

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while sending LL expiry emails: " + e.getMessage());
        }
    }







}
