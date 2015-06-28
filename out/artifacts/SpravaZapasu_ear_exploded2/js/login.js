
var Logins = function() {

    this.urlP = "/user";

    /******** FORMS *******/
    this.form = document.getElementById("loginF");


    /******** INPUTS *******/
    this.login = document.getElementById("username");
    this.password = document.getElementById("password");


    this.init();

}

Logins.prototype.init = function() {
    this.form.addEventListener("submit",this.submit.bind(this));
}

Logins.prototype.submit = function(e) {
    alert("loguju");
    e.preventDefault();
    var error = false;

    if (this.login == null || this.login.value == "") {
        this.login.style.border = "2px solid red";
        error = true;
    } else {
        var login = this.login.value;
        this.login.style.border = "";
    }

    if (this.password == null || this.password.value == "") {
        error = true;
        this.password.style.border = "2px solid red";
    } else {

        this.password.style.border = "";
        var password = this.password.value;

    }


    if(error == true) {
        alert("error: username and password is required");
        return;
    }

    /*var xhr = new XMLHttpRequest();
    xhr.open("POST", this.apiURL);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.addEventListener("load", this.showFlightsIdASC.bind(this));
    xhr.send(JSON.stringify({"name":name,"from":{"id":from},"to":{"id":to},"seats":seats,"dateOfDeparture":date}));*/
    this.form.submit(e);


}

Logins.prototype.responseLogin = function(e) {
    var x = e.target;
    if (x.readyState == 4) {
        if (x.status == 200) {
            console.log(x);
            if (x.responseText != "") {
                res =  x.responseText;
                //alert("res: " + res);
                if(res == null){
                    alert("ERROR");
                }else if(res == "error 1"){
                    alert("user already exists - try another login");
                }else if (res == "done"){
                    alert("user was created");
                    window.location.href = "/Login/login.html";

                }
            }
        }
    }
}
var Login = new Logins();