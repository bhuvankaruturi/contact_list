package com.databaseDesign.projectOne.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.databaseDesign.projectOne.Services.PhoneService;
import com.databaseDesign.projectOne.Entities.Phone;

@Controller
@RequestMapping("/app")
public class PhoneController {
    @Autowired
    private PhoneService addressService;

    @GetMapping(path="/contact/{id}/phones")
    public @ResponseBody Iterable<Phone> getAllAddresses(@PathVariable("id") Integer id) {
        return addressService.getAllPhones(id);
    }

    @PostMapping(path="/contact/{id}/phone")
    public @ResponseBody Phone addAddress(@PathVariable("id") Integer id
                    ,@RequestParam String phoneType
                    ,@RequestParam String areaCode
                    ,@RequestParam String number) {
        Phone entity = new Phone(phoneType, areaCode, number);
        return addressService.addPhone(id, entity);
    }

}