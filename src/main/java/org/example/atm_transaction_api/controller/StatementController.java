package org.example.atm_transaction_api.controller;

import org.example.atm_transaction_api.dto.StatementDTO;
import org.example.atm_transaction_api.service.StatementService;
import org.example.atm_transaction_api.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

//import static jdk.internal.org.jline.utils.Colors.s;

@RestController
@RequestMapping("api/v1/statement")
@CrossOrigin
public class StatementController {

    @Autowired
    private StatementService statementService;

    @PostMapping(value ="/generateStatement",produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateStatement(@RequestBody StatementDTO statementDTO){

        ResponseObject responseObject = statementService.checkDetails(statementDTO);

        if(!responseObject.isStatus()){
            //return responseObject.getMessage();
            System.out.println(responseObject.getMessage());
        }
            byte[] bytes = statementService.generateStatement(statementDTO);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=account-statement-report.pdf").body(bytes);



    }
}
