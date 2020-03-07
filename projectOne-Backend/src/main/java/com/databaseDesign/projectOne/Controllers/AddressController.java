package com.databaseDesign.projectOne.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.databaseDesign.projectOne.Services.AddressService;
import com.databaseDesign.projectOne.Entities.Address;

@Controller
@RequestMapping("/app")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping(path="/contact/{id}/addresses")
    public @ResponseBody Iterable<Address> getAllAddresses(@PathVariable("id") Integer id) {
        return addressService.getAllAddresses(id);
    }

    @PostMapping(path="/contact/{id}/address")
    public @ResponseBody Address addAddress(@PathVariable("id") Integer id
                    ,@RequestParam String addressType
                    ,@RequestParam String address
                    ,@RequestParam String city
                    ,@RequestParam String state
                    ,@RequestParam String zip) {
        Address entity = new Address(addressType, address, city, state, zip);
        return addressService.addAddress(id, entity);
    }

}