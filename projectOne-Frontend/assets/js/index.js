$(function() {
    let baseUri = "http://localhost:8080/app";
    let $contact = $("#contact");
    let elements = ["address", "phone", "date", "contact"];
    let currentDetailsId = null;
    let currentFormMode = {address: ['add'], phone: ['add'], date: ['add'], contact: ['add']};
    // function to format input date from yyyy-MM-dd to MM/dd/yyyy 
    function formatDate (input, m) {
        var datePart = input.match(/\d+/g),
        year = datePart[0+2*m], // get only two digits
        month = datePart[1-m], day = datePart[2-m];
        return m==0?month+'/'+day+'/'+year:year+'-'+month+'-'+day;
    }
    // populate a form for modification
    var populateForm = function(dataType, element) {
        let l = element.children().length-2;
        element.children().each((i, e) => {
            if (i < l) {
                if (dataType == 'date' && componentIds[dataType][i] == "dateVal") {
                    $('#' + componentIds[dataType][i]).val(formatDate(e.textContent, 1));
                } 
                else $('#' + componentIds[dataType][i]).val(e.textContent);
            }
        });
    }
    // ajax delete element request 
    var deleteAjax = function(dataType, id, event, element) {
        $.ajax({
            type: "delete",
            url: baseUri + `/${dataType}/` + id,
            crossDomain: true
        })
        .done(function(res) {
            element.fadeOut(500, function() {
                console.log(res);
                $(this).remove();
            });
            if (dataType="contact") {
                $("#name").text("Contact deleted");
                elements.forEach((v, i) => {
                    if (i <= 2) $(`#${v}-section`).hide();
                })
            }
        })
        .fail(function() {
            console.log("Something went wrong while deleting " + dataType + " with id " + id);
        });
    }
    // hide form
    var hideForm = function(dataType) {
        $(`#${dataType}-form`).hide(200);
        $(`#${dataType}-add`).show();
    }
    // submit handler definition for contact
    var handleContactSubmit = function() {
        let requestBody = {};
        if ($("#fName").val() == "") return;
        requestBody.fName = $("#fName").val();
        requestBody.lName = $("#lName").val();
        requestBody.mName = $("#mName").val();
        console.log(requestBody);
        let reqParams = {
            type: currentFormMode["contact"][0]=="mod"?"put":"post",
            url: currentFormMode["contact"][0]=="mod" 
                ? baseUri + "/contact/" + currentFormMode["contact"][1]
                : baseUri + "/contact/add",
            data: JSON.stringify(requestBody),
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            crossDomain: true
        };
        $.ajax(reqParams)
        .done(function(c) {
            if (currentFormMode["contact"][0] == "mod")
                currentFormMode["contact"][2].replaceWith(contactLiTemplate(c.contactId, c.fName, c.mName, c.lName, {buttons}));
            else     
                $("#contact").prepend(contactLiTemplate(c.contactId, c.fName, c.mName, c.lName, {buttons}));
            $(`#address-form`).trigger('reset');
            currentFormMode["contact"] = ['add'];
            hideForm("contact");
        })
        .fail(function() {
            if (currentFormMode["contact"]=="mod") console.log("Something went wrong while trying to update contact")
            else console.log("Something went wrong while trying to add contact");
        });
    }
    // submit handler definition for address
    var handleAddressSubmit = function() {
        let requestBody = {};
        if ($("#addressType").val() == "" || $("#addressVal").val() == "") return;
        requestBody.addressType = $("#addressType").val();
        requestBody.address = $("#addressVal").val();
        requestBody.city = $("#city").val();
        requestBody.state = $("#state").val();
        requestBody.zip = $("#zip").val();
        console.log(requestBody);
        let reqParams = {
            type: currentFormMode["address"][0]=="mod"?"put":"post",
            url: currentFormMode["address"][0]=="mod" 
                ? baseUri + "/address/" + currentFormMode["address"][1]
                : baseUri + "/contact/" + currentDetailsId + "/address",
            data: JSON.stringify(requestBody),
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            crossDomain: true
        };
        $.ajax(reqParams)
        .done(function(e) {
            if (currentFormMode["address"][0] == "mod")
                currentFormMode["address"][2].replaceWith(addressLiTemplate(e.addressId, e.addressType, e.address, e.city, e.state, e.zip, {buttons: buttons}));
            else     
                $("#address").append(addressLiTemplate(e.addressId, e.addressType, e.address, e.city, e.state, e.zip, {buttons: buttons}));
            $(`#address-form`).trigger('reset');
            currentFormMode["address"] = ['add'];
            hideForm("address");
        })
        .fail(function() {
            if (currentFormMode["address"]=="mod") console.log("Something went wrong while trying to update address")
            else console.log("Something went wrong while trying to add address");
        });
    }
    // submit handler definition for phone
    var handlePhoneSubmit = function() {
        let requestBody = {};
        if ($("#phoneType").val() == "" || $("#number").val().length != 10) return;
        requestBody.phoneType = $("#phoneType").val();
        requestBody.areaCode = $("#areaCode").val();
        requestBody.number = $("#number").val();
        console.log(requestBody);
        let reqParams = {
            type: currentFormMode["phone"][0]=="mod"?"put":"post",
            url: currentFormMode["phone"][0]=="mod"
                        ?baseUri + "/phone/" + currentFormMode["phone"][1]
                        :baseUri + "/contact/" + currentDetailsId + "/phone",
            data: JSON.stringify(requestBody),
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            crossDomain: true
        }
        $.ajax(reqParams)
        .done(function(e) {
            if (currentFormMode["phone"][0] == "mod")
                currentFormMode["phone"][2].replaceWith(phoneLiTemplate(e.phoneId, e.phoneType, e.areaCode, e.number, {buttons: buttons}));
            else 
                $("#phone").append(phoneLiTemplate(e.phoneId, e.phoneType, e.areaCode, e.number, {buttons: buttons}));
            $(`#phone-form`).trigger('reset');
            currentFormMode["phone"] = ["add"];
            hideForm("phone");
        })
        .fail(function() {
            if (currentFormMode["phone"][0] == "mod") console.log("Something went wrong while trying to update phone");
            else console.log("Something went wrong while trying to add phone");
        });
    }
    // submit handler definition for date
    var handleDateSubmit = function() {
        let requestBody = {};
        if ($("#dateType").val().length == 0) return;
        requestBody.dateType = $("#dateType").val();
        console.log($("#dateVal").val());
        requestBody.date = formatDate($("#dateVal").val(), 0);
        console.log(requestBody);
        let reqParams = {
            type: currentFormMode["date"][0]=="mod"?"put":"post",
            url: currentFormMode["date"][0]=="mod"
            ?baseUri + "/date/" + currentFormMode["date"][1]
            :baseUri + "/contact/" + currentDetailsId + "/date",
            data: JSON.stringify(requestBody),
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            crossDomain: true
        }
        $.ajax(reqParams)
        .done(function(e) {
            if (currentFormMode["phone"][0] == "mod")
                currentFormMode["phone"][2].replaceWith(dateLiTemplate(e.dateId, e.dateType, e.date, {buttons: buttons}));
            else
                $("#date").append(dateLiTemplate(e.dateId, e.dateType, e.date, {buttons: buttons}));
            $(`#date-form`).trigger('reset');
            currentFormMode["date"] = ["add"];
            hideForm("date");
        })
        .fail(function() {
            if (currentFormMode["date"][0] == "mod") console.log("Something went wrong while trying to update date");
            else console.log("Something went wrong while trying to add date");
        });
    }
    // handle mod, del button clicks
    var handleLiButtonClick = function(event) {
        event.stopPropagation();
        let actionType = $(this).attr("type");
        let $li = $(this).parent();
        let dataType = $li.attr("type");
        let id = $li.attr("id");
        if (actionType == "mod") {
            if (currentFormMode[dataType][0] == "mod") currentFormMode[dataType][2].removeClass("mod");
            $li.addClass("mod");
            console.log(dataType + ' ' + actionType);
            $(`#${dataType}-add`).hide();
            $(`#${dataType}-form`).show(300);
            populateForm(dataType, $li);
            currentFormMode[dataType] = ["mod", id, $li];
        }
        if (actionType == "del") {
            deleteAjax(dataType, id, event, $li);
        }
    }
    // click handlers for add, submit and cancel for each components
    elements.forEach(function(element) {
        if (element != "contact") $(`#${element}-section`).append(forms[element]);
        else $(`#${element}-section`).prepend(forms[element]);
        $(`#${element}-form`).hide();
        $(`#${element}-add`).on('click', function() {
            currentFormMode[element] = ['add'];
            $(`#${element}-form`).trigger('reset');
            $(`#${element}-form`).show(300);
            $(this).hide();
        });
        $(`#${element}-section`).on('click', `form #${element}-cancel`, function(event){
            event.preventDefault();
            if (currentFormMode[element][0] == "mod") currentFormMode[element][2].removeClass("mod");
            currentFormMode[element] = ['add'];
            $(`#${element}-form`).trigger('reset');
            $(`#${element}-form`).hide(300);
            $(`#${element}-add`).show();
        });
        $(`#${element}-section`).on('click', `form #${element}-submit`,function(event){
            event.preventDefault();
            switch(element) {
                case "contact":
                    handleContactSubmit();
                case "address":
                    handleAddressSubmit();
                    break;
                case "phone":
                    handlePhoneSubmit();
                    break;
                case "date":
                    handleDateSubmit();
                    break;
                default:
                    console.log("default case reached");
                    break;
            }
        });
        $(`#${element}`).on('click', 'li button', handleLiButtonClick);
    });
    // fetch all the contacts from the database
    $.ajax({
        type: "get",
        url: baseUri + "/contacts",
        crossDomain: true
    })
    .done(function(data) {
        // console.log(data);
        data.forEach(c => {
            $contact.append(contactLiTemplate(c.contactId, c.fName, c.mName, c.lName, {buttons}));
        });
        if (data.length > 0) showDetails(data[0].contactId);
    })
    .fail(function() {
        console.log("Something went wrong when fetching contact list");
    })
    
    var contactClickHandler = function() {
        let contactId = $(this).attr("id");
        showDetails(contactId);
    }

    $("#contact").on('click', 'li', contactClickHandler);

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
        $(`#${dataType}-section`).show();
        $("#" + dataType).empty();
        // $(`#${dataType}-section > h4`).text(heading);
        dataList.forEach(e => {
            switch(dataType) {
                case "address":
                    $("#" + dataType).append(addressLiTemplate(e.addressId, e.addressType, e.address, e.city, e.state, e.zip, {buttons: buttons}));
                    // attach delete handlers
                    break;
                case "phone":
                    $("#" + dataType).append(phoneLiTemplate(e.phoneId, e.phoneType, e.areaCode, e.number, {buttons: buttons}));
                    break;
                case "date":
                    $("#" + dataType).append(dateLiTemplate(e.dateId, e.dateType, e.date, {buttons: buttons}));
                    break;
                default:
                    console.log("Invalid dataType");
            }
        })
    }    
});