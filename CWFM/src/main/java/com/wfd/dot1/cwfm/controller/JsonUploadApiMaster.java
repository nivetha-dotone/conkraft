package com.wfd.dot1.cwfm.controller;


import com.wfd.dot1.cwfm.dto.KTCWorkorderDTO;
import com.wfd.dot1.cwfm.service.JsonUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiUpload")
public class JsonUploadApiMaster {


@Autowired
private JsonUploadService jsonUploadService;


    @PostMapping("/workorder")
    public Map<String, Object> uploadWorkorders(
            @RequestBody List<KTCWorkorderDTO> workorders) {

        return jsonUploadService.processWorkordersFromJson(workorders);
    }







}



