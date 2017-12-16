// Reference: https://stackoverflow.com/questions/18754020/bootstrap-3-with-jquery-validation-plugin

jQuery.validator.setDefaults({
    errorElement: "span",
    errorClass: "help-block",
    
    // Style the HTML element in case of validation errors
    highlight: function (element, errorClass, validClass) {
        if (!$(element).hasClass('novalidation')) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        }
    },

    // Style the HTML element in case of validation success
    unhighlight: function (element, errorClass, validClass) {
        if (!$(element).hasClass('novalidation')) {
            $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
        }
    },
    
    // Place the error text for different input element types
    errorPlacement: function (error, element) {
        if (element.parent('.input-group').length) {
            error.insertAfter(element.parent());
        }
        else if (element.prop('type') === 'radio' && element.parent('.radio-inline').length) {
            error.insertAfter(element.parent().parent());
        }
        else if (element.prop('type') === 'checkbox' || element.prop('type') === 'radio') {
            error.appendTo(element.parent().parent());
        }
        else {
            error.insertAfter(element);
        }
    }
});