package com.databaseDesign.projectOne.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.databaseDesign.projectOne.Services.AddressService;
import com.databaseDesign.projectOne.Entities.AddressEntity;

@Controller
@RequestMapping("/app")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping(path="/contact/{id}/addresses")
    public @ResponseBody Iterable<AddressEntity> getAllAddresses(@PathVariable("id") Integer id) {
        return addressService.getAllAddresses(id);
    }

    @PostMapping(path="/contact/{id}/address")
    public @ResponseBody AddressEntity addAddress(@PathVariable("id") Integer id
            ,@RequestBody AddressEntity newAddress) {
        return addressService.addAddress(id, newAddress);
    }

    @PutMapping(path="/address/{id}")
    public @ResponseBody AddressEntity modifyAddress(@PathVariable("id") Integer id
            ,@RequestBody AddressEntity modifiedAddress) {
        return addressService.modifyAddress(id, modifiedAddress);
    }

}