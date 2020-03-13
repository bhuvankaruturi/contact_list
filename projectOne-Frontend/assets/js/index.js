$(function() {
    let baseUri = "http://localhost:8080/app";
    let $list = $("#list");
    let elements = ["address", "phone", "date"];
    let currentDetailsId = null;
    function template(strings, ...keys) {
        return (function(...values) {
          let dict = values[values.length - 1] || {};
          let result = [strings[0]];
          keys.forEach(function(key, i) {
            let value = Number.isInteger(key) ? values[key] : dict[key];
            result.push(value, strings[i + 1]);
          });
          return result.join('');
        });
    }
    let addressLiTemplate = template`<li class="list-group-item" id="${0}">${1}, ${2}, ${3}, ${4}, ${5}</li>`;
    let phoneLiTemplate = template`<li class="list-group-item" id="${0}">${1}: +${2} ${3}</li>`;
    let dateLiTemplate = template`<li class="list-group-item" id="${0}">${1}: ${2}</li>`; 
    elements.forEach(function(element) {
        $(`#${element}-form`).hide();
        $(`#${element}-add`).on('click', function() {
            $(`#${element}-form`).trigger('reset');
            $(`#${element}-form`).show();
            $(this).hide();
        });
        $(`#${element}-cancel`).on('click', function(event){
            event.preventDefault();
            $(`#${element}-form`).trigger('reset');
            $(`#${element}-form`).hide();
            $(`#${element}-add`).show();
        });
        $(`#${element}-submit`).on('click', function(event){
            event.preventDefault();
            switch(element) {
                case "address":
                    handleAddressSubmit();
                case "phone":
                    handlePhoneSubmit();
                case "date":
                    handleDateSubmit();
            }
        });
    });

    var handleAddressSubmit = function() {
        let requestBody = {};
        if ($("#addressType").val() == "" || $("#address").val() == "") return;
        requestBody.addressType = $("#addressType").val();
        requestBody.address = $("#addressVal").val();
        requestBody.city = $("#city").val();
        requestBody.state = $("#state").val();
        requestBody.zip = $("#zip").val();
        console.log(requestBody);
        $.ajax({
            type: "post",
            url: baseUri + "/contact/" + currentDetailsId + "/address",
            data: JSON.stringify(requestBody),
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            crossDomain: true
        })
        .done(function(e) {
            $("#address").append(addressLiTemplate(e.addressId, e.addressType, e.address, e.city, e.state, e.zip));
        })
        .fail(function() {
            console.log("Something went wrong while trying to add address");
        });
    }

    var handlePhoneSubmit = function() {
        let requestBody = {};
        if ($("#phoneType").val() == "" || $("#number").val().length != 10) return;
        requestBody.phoneType = $("#phoneType").val();
        requestBody.areaCode = $("#areaCode").val();
        requestBody.number = $("#number").val();
        console.log(requestBody);
        $.ajax({
            type: "post",
            url: baseUri + "/contact/" + currentDetailsId + "/phone",
            data: JSON.stringify(requestBody),
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            crossDomain: true
        })
        .done(function(e) {
            $("#phone").append(phoneLiTemplate(e.phoneId, e.phoneType, e.areaCode, e.number));
        })
        .fail(function() {
            console.log("Something went wrong while trying to add phone");
        });
    }

    var handleDateSubmit = function() {
        let requestBody = {};
        if ($("#dateType").val().length == 0) return;
        requestBody.dateType = $("#dateType").val();
        console.log($("#dateVal").val());
        requestBody.date = formatDate($("#dateVal").val());
        console.log(requestBody);
        $.ajax({
            type: "post",
            url: baseUri + "/contact/" + currentDetailsId + "/date",
            data: JSON.stringify(requestBody),
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            crossDomain: true
        })
        .done(function(e) {
            $("#date").append(dateLiTemplate(e.dateId, e.dateType, e.date));
        })
        .fail(function() {
            console.log("Something went wrong while trying to add date");
        });
    }

    function formatDate (input) {
        var datePart = input.match(/\d+/g),
        year = datePart[0].substring(2), // get only two digits
        month = datePart[1], day = datePart[2];
      
        return month+'/'+day+'/'+year;
    }

    $.ajax({
        type: "get",
        url: baseUri + "/contacts",
        crossDomain: true
    })
    .done(function(data) {
        // console.log(data);
        data.forEach(contact => {
            $list.append(`<li class="list-group-item" id="${contact.contactId}">${contact.fName}</li>`);
        });
        $list.children().each(function(index, li) {
            // console.log($(li).text());
            $(li).on('click', contactClickHandler);
        })
        if (data.length > 0) showDetails(data[0].contactId);
    })
    .fail(function() {
        console.log("Something went wrong when fetching contact list");
    })

    var contactClickHandler = function() {
        let contactId = $(this).attr("id");
        showDetails(contactId);
    }

    var showDetails = function(contactId) {
        $.ajax({
            type: "get",
            url: baseUri + "/contact/" + contactId,
            data: "data",
            dataType: "json",
            crossDomain: true
        })
        .done(function(data) {
            $('#name').text(data.fName + " " + data.mName + "," + data.lName);
            currentDetailsId = data.contactId;
            setSection(data.addresses, "address", "Address");
            setSection(data.phones, "phone", "Phone Numbers");
            setSection(data.dates, "date", "Dates");
        })
        .fail(function() {
            console.log("Something went wrong while trying to get contact details");
        });
    }

    var setSection = function(dataList, dataType, heading) {
        $("#" + dataType).empty();
        // $(`#${dataType}-section > h4`).text(heading);
        dataList.forEach(e => {
            switch(dataType) {
                case "address":
                    $("#" + dataType).append(addressLiTemplate(e.addressId, e.addressType, e.address, e.city, e.state, e.zip));
                    break;
                case "phone":
                    $("#" + dataType).append(phoneLiTemplate(e.phoneId, e.phoneType, e.areaCode, e.number));
                    break;
                case "date":
                    $("#" + dataType).append(dateLiTemplate(e.dateId, e.dateType, e.date));
                    break;
                default:
                    console.log("Invalid dataType");
            }
        })
    }
    
});