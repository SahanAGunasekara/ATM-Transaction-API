package org.example.atm_transaction_api.controller;

import org.example.atm_transaction_api.dto.ChangePinDTO;
import org.example.atm_transaction_api.service.ChangePinService;
import org.example.atm_transaction_api.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/pin")
@CrossOrigin
public class ChangePinController {

    @Autowired
    private ChangePinService changePinService;

    @PutMapping ("/changePin")
    public String changePin(@RequestBody ChangePinDTO changePinDTO){

        ResponseObject responseObject = changePinService.checkDetails(changePinDTO);

        if (!responseObject.isStatus()){
            return responseObject.getMessage();
        }else{
            String s = changePinService.changePin(changePinDTO);
            return s;
        }


    }
}
