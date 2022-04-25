$(document).ready(function(){

    $('#password').on('focusin', function(){
        $('.err-msg .error, .password-msg').text('');
    });
    $('#rePassword').on('focusin', function(){
        $('.err-msg .error, .re-password-msg').text('');
    });
    $('#firstName').on('focusin', function(){
        $('.err-msg .error, .firstName-msg').text('');
    });
    $('#lastName').on('focusin', function(){
        $('.err-msg .error, .lastName-msg').text('');
    });
    $('#phoneNumber').on('focusin', function(){
        $('.err-msg .error, .phoneNumber-msg').text('');
    });
    $('#address').on('focusin', function(){
        $('.err-msg .error, .address-msg').text('');
    });

    $('#update-form').validate({
        onfocus: false,
        onkeyup: false,
        onclick: false,
        rules: {
            // email: {
            //     required: true,
            //     emailFormat: true
            // },
            password : {
                required: true,
                // strongPassword: true
            },
            rePassword : {
                required: true,
                equalTo: '#password'
            },
            firstName: {
                required: false,
                length: 30
            },
            lastName: {
                required: false,
                length: 30
            },
            phoneNumber: {
                required: false,
                minlength: 9,
                length: 12
            },
            address: {
                required: false,
                minlength: 2
            }
        }
    });
});

