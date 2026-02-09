package com.wfd.dot1.cwfm.controller;


import com.wfd.dot1.cwfm.dto.EmployeeRequestDTO;
import com.wfd.dot1.cwfm.dto.GatePassToOnBoard;
import com.wfd.dot1.cwfm.service.EmployeeMapper;
import com.wfd.dot1.cwfm.service.GatePassToOnBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/WFDjson")
public class CreateEmpFetchByGatePassAPICALL {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private GatePassToOnBoardService passToOnBoardService;



    @PostMapping("/CreateEmpByGatePIdStatisCall/{gatePassId}")
    public ResponseEntity<?> createEmpGateStatic(@PathVariable String gatePassId){
        try{

            String responseAPI = employeeMapper.gatePassEmpDtoStatic(gatePassId);
            if(responseAPI!=null ){
                return new ResponseEntity<>(responseAPI, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("it's null ",HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/fetchONDTOByTrns/{gatePassId}")
    public ResponseEntity<?> fetchOnBoardingDetailsTest(@PathVariable String gatePassId){
        try{

            GatePassToOnBoard individualOnBoardDetailsByTrnId = passToOnBoardService.getIndividualOnBoardDetailsByTrnId(gatePassId);
            if(individualOnBoardDetailsByTrnId!=null){
                return  new ResponseEntity<>(individualOnBoardDetailsByTrnId,HttpStatus.OK);
            }else{
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/fetchONByTrnsConvertJsonBeforeStore/{gatePassId}")
    public ResponseEntity<?> fetchOnBoardingDetailsTest2(@PathVariable String gatePassId){
        try{

            EmployeeRequestDTO individualOnBoardDetailsByTrnId = employeeMapper.gatePassEmpDto(gatePassId);
            if(individualOnBoardDetailsByTrnId!=null){
                return  new ResponseEntity<>(individualOnBoardDetailsByTrnId,HttpStatus.OK);
            }else{
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    @PostMapping("/addByTrnsIdToUKG/{trnId}")
    public ResponseEntity<?> addOnBoardingDetailsActual(@PathVariable String trnId){
        try{

            String gatePassEmpDtoDynamic = employeeMapper.gatePassEmpDtoDynamic(trnId);
            if(gatePassEmpDtoDynamic!=null){
                return  new ResponseEntity<>(gatePassEmpDtoDynamic,HttpStatus.OK);
            }else{
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping("/updateByTrnsIdToUKG/{trendId}")
    public ResponseEntity<?> updateOnBoardingDetails(@PathVariable String trendId){
        try{

            String gatePassEmpDtoDynamic = employeeMapper.updatePassEmpDtoDynamic(trendId);



            if(gatePassEmpDtoDynamic!=null){
                return  new ResponseEntity<>(gatePassEmpDtoDynamic,HttpStatus.OK);
            }else{
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/postSkillInWFD/{gmId}")
    public ResponseEntity<?> postSkills(@PathVariable Integer gmId){
        try{
            String individualOnBoardDetailsByTrnId = employeeMapper.postSkillTowfd(gmId);
            if(individualOnBoardDetailsByTrnId!=null && individualOnBoardDetailsByTrnId.equals("already in the database")){
                return  new ResponseEntity<>("already in the database",HttpStatus.BAD_REQUEST);
            }else if(individualOnBoardDetailsByTrnId!=null ){
                return new ResponseEntity<>(individualOnBoardDetailsByTrnId,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
