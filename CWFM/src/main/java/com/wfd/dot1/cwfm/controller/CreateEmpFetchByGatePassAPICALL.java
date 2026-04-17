

package com.wfd.dot1.cwfm.controller;

import com.wfd.dot1.cwfm.dto.EmployeeRequestDTO;
import com.wfd.dot1.cwfm.dto.GatePassToOnBoard;
import com.wfd.dot1.cwfm.enums.EmployeeStatusType;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.service.EmployeeMapper;
import com.wfd.dot1.cwfm.service.GatePassToOnBoardService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping({"/schedularUpdate"})
    public void addOnBoardingSchedular() {
        try {
            this.employeeMapper.gatePassEmpDtoSchedular();
        } catch (Exception var2) {
        }

    }

    @PostMapping({"/addByTrnsIdToUKG/{trnId}"})
    public ResponseEntity<?> addOnBoardingDetailsActual(@PathVariable String trnId) {
        Long gpTransactionId = null;

        try {
            gpTransactionId = Long.parseLong(trnId);
            String result = this.employeeMapper.gatePassEmpDtoDynamic(trnId);
            if (result == null) {
                this.passToOnBoardService.saveErrorTraceTrNOT(gpTransactionId, 200, "Transaction Id Not Found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction Id Not Found");
            } else if (result.matches("\\d+")) {
                Long personKey = Long.parseLong(result);
                this.passToOnBoardService.saveSuccessTrace(gpTransactionId, personKey, 200);
                return ResponseEntity.ok(result);
            } else if (result.startsWith("STATUS:")) {
                String[] parts = result.split("\n", 2);
                int statusCode = Integer.parseInt(parts[0].replace("STATUS:", "").trim());
                String body = parts.length > 1 ? parts[1] : "";
                if (body.contains("WCO-101520") && body.contains("ID already exists")) {
                    this.passToOnBoardService.saveErrorTraceTrNOT(gpTransactionId, 200, body);
                } else if (body.contains("Transaction Id Not Found")) {
                    this.passToOnBoardService.saveErrorTraceTrNOT(gpTransactionId, 200, body);
                } else {
                    this.passToOnBoardService.saveErrorTrace(gpTransactionId, statusCode, body);
                }

                return ResponseEntity.status(statusCode).body(body);
            } else {
                this.passToOnBoardService.saveErrorTrace(gpTransactionId, 500, result);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
        } catch (Exception var7) {
            if (gpTransactionId != null) {
                this.passToOnBoardService.saveErrorTrace(gpTransactionId, 500, var7.getMessage());
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + var7.getMessage());
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

    @GetMapping({"/getAccessTokenOnly"})

    public ResponseEntity<?> getAccessTokenOnly(@RequestParam String username,
                                                @RequestParam String password) {
        try {
            String gatePassEmpDtoDynamic = this.employeeMapper.getTokenCheck( username,  password);

            return gatePassEmpDtoDynamic != null ? new ResponseEntity(gatePassEmpDtoDynamic, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @GetMapping({"/getVerifyLaborCat"})

    public ResponseEntity<?> verifyLaborCate(@RequestParam String Workorder
                                               ) {
        try {
            String gatePassEmpDtoDynamic = this.employeeMapper.getVerifyLabor( Workorder);

            return gatePassEmpDtoDynamic != null ? new ResponseEntity(gatePassEmpDtoDynamic, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @GetMapping("/check-authentication")
    public ResponseEntity<?> getAccessAuthentication(
            @RequestParam String username,
            @RequestParam String password) {
        try {
            Object authCheckup = employeeMapper.getAuthCheckup(username, password);

            if (authCheckup instanceof MasterUser) {
                return ResponseEntity.ok(authCheckup);
            }

            // ✅ now this will truly be null
            return ResponseEntity.ok(null);

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

    @PostMapping({"/postJobInWFD/{gmId}"})
    public ResponseEntity<?> postJobs(@PathVariable Integer gmId) {
        try {
            String individualOnBoardDetailsByTrnId = this.employeeMapper.postJobTowfd(gmId);
            if (individualOnBoardDetailsByTrnId != null && individualOnBoardDetailsByTrnId.equals("already in the WFD")) {
                return new ResponseEntity("already in the WFD", HttpStatus.BAD_REQUEST);
            } else {
                return individualOnBoardDetailsByTrnId != null ? new ResponseEntity(individualOnBoardDetailsByTrnId, HttpStatus.OK) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping({"/postLaborCategoriesEntry/{WORKORDERID}"})
    public ResponseEntity<?> postLaborCategoriesEntry(@PathVariable String WORKORDERID) {
        try {
            String individualOnBoardDetailsByTrnId = this.employeeMapper.postToLaborCate(WORKORDERID);
            if (individualOnBoardDetailsByTrnId != null && individualOnBoardDetailsByTrnId.equals("Labor category already in the WFD")) {
                return new ResponseEntity("Labor Cate already in the WFD ", HttpStatus.BAD_REQUEST);
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

    @PostMapping({"/updatedEmpStatus/{gatepassId}/{empStatus}"})
    public ResponseEntity<String> updateEmpStatusTerOrAct(@PathVariable String gatepassId, @PathVariable EmployeeStatusType empStatus) {
        try {
            String response = this.employeeMapper.updateEmpstatusTrorAc(gatepassId, empStatus);
            if ("already in the WFD".equalsIgnoreCase(response)) {
                return ResponseEntity.badRequest().body("Not updated Employment status in WFD");
            } else {
                return response != null ? ResponseEntity.ok(response) : ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping({"/updateEmpStatusTerSchedule"})
    public ResponseEntity<Object> updateEmpStatusTerSchedule() {
        try {
            Map<String, List<String>> response = this.employeeMapper.updateEmpstatusTrSchedule();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping({"/checkWorkOrderExMAil"})
    public ResponseEntity<String> workorderMail() {
        try {
            this.employeeMapper.setupWorkorderMail();
            return ResponseEntity.ok("Workorder expiry emails triggered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while sending workorder expiry emails: " + e.getMessage());
        }
    }

    @GetMapping({"/checkLLExMAil"})
    public ResponseEntity<String> lLMail() {
        try {
            this.employeeMapper.setupLaborLMail();
            return ResponseEntity.ok("LL expiry emails triggered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while sending LL expiry emails: " + e.getMessage());
        }
    }
}
