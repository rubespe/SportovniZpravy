
var Registrs = function() {

    this.urlP = "/user";

    /******** FORMS *******/
    this.form = document.getElementById("registrF");


    /******** INPUTS *******/
    this.login = document.getElementById("username");
    this.password = document.getElementById("password");


    this.init();

}

Registrs.prototype.init = function() {
    this.form.addEventListener("submit",this.submit.bind(this));
}

Registrs.prototype.submit = function(e) {
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
    var asyncRequest = new XMLHttpRequest();
    var res = null;
    try
    {

        var params = "username=" + this.login.value + "&password=" + this.password.value;
        asyncRequest.open("POST",  this.urlP + "?" + params, true);    //   /Test is url to Servlet!
        res = asyncRequest.addEventListener("load", this.responseT.bind(this));
        asyncRequest.send(params);

    }
    catch(exception)
    {
        alert("error \n" + exception.toString());
    }


    /*alert(res);
    if(res == null){
        alert("ERROR");
    }else if(res == "error 1"){
        alert("user already exists - try another login");
    }else if (res == "done"){
        alert("user was created");
        window.location.href = "/Login/login.html";

    }*/
    /*var xhr = new XMLHttpRequest();
     xhr.open("POST", this.apiURL);
     xhr.setRequestHeader("Content-Type", "application/json");
     xhr.addEventListener("load", this.showFlightsIdASC.bind(this));
     xhr.send(JSON.stringify({"name":name,"from":{"id":from},"to":{"id":to},"seats":seats,"dateOfDeparture":date}));*/


}

Registrs.prototype.responseT = function(e) {
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
var Registr = new Registrs();