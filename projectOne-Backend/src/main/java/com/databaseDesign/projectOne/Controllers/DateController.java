package com.databaseDesign.projectOne.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;

import com.databaseDesign.projectOne.Services.DateService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.databaseDesign.projectOne.Entities.Date;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/app")
public class DateController {
    @Autowired
    private DateService dateService;

    @GetMapping(path = "/contact/{id}/dates")
    public @ResponseBody Iterable<Date> getAllDates(@PathVariable("id") Integer id) {
        return dateService.getAllDates(id);
    }

    @PostMapping(path = "/contact/{id}/date")
    public @ResponseBody Date addDate(@PathVariable("id") Integer id, @RequestParam String dateType,
            @RequestParam String date) {
        DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        java.util.Date formatedDate = new java.util.Date();
        try {
            formatedDate = originalFormat.parse(date);
        } 
        catch(ParseException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal Date Format");
        } 
        Date entity = new Date(dateType, formatedDate);
        return dateService.addDate(id, entity);
    }

    @PutMapping(path="/date/{id}")
    public @ResponseBody Date modifyDate(@PathVariable("id") Integer id
            ,@RequestParam String dateType
            ,@RequestParam String date) {
        DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        java.util.Date formatedDate = new java.util.Date();
        try {
            formatedDate = originalFormat.parse(date);
        } 
        catch(ParseException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal Date Format");
        }
        Date modifiedDate = new Date(dateType, formatedDate);
        modifiedDate = dateService.modifyDate(id, modifiedDate);
        if (modifiedDate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Date not found");
        }
        return modifiedDate;
    }

}