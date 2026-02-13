

package com.wfd.dot1.cwfm.controller;

import com.wfd.dot1.cwfm.dto.EmployeeRequestDTO;
import com.wfd.dot1.cwfm.dto.GatePassToOnBoard;
import com.wfd.dot1.cwfm.service.EmployeeMapper;
import com.wfd.dot1.cwfm.service.GatePassToOnBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping({"/addByTrnsIdToUKG/{trnId}"})
    public ResponseEntity<?> addOnBoardingDetailsActual(@PathVariable String trnId) {
        try {
            String gatePassEmpDtoDynamic = this.employeeMapper.gatePassEmpDtoDynamic(trnId);
            return gatePassEmpDtoDynamic != null ? new ResponseEntity(gatePassEmpDtoDynamic, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
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

//    @PostMapping({"/updatedEmpStatus/{gatepassId}"})
//    public ResponseEntity<?> updateEmpStatusTerOrAct(@PathVariable String gatepassId) {
//        try {
//            String individualOnBoardDetailsByTrnId = this.employeeMapper.updateEmpstatusTrorAc(gatepassId);
//            if (individualOnBoardDetailsByTrnId != null && individualOnBoardDetailsByTrnId.equals("already in the WFD")) {
//                return new ResponseEntity("already in the WFD", HttpStatus.BAD_REQUEST);
//            } else {
//                return individualOnBoardDetailsByTrnId != null ? new ResponseEntity(individualOnBoardDetailsByTrnId, HttpStatus.OK) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }








}
