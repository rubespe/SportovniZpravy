var Categories = function() {

    this.urlP = "/sport";

    this.input = document.getElementById("inputDiv");
    this.list = document.getElementById("listNav");
    /******** FORMS *******/
    this.form = document.getElementById("inputForm");
    this.categorytext = document.getElementById("category");

    this.init();
}

Categories.prototype.init = function() {
    this.getAll();
    this.form.addEventListener("submit",this.submit.bind(this));
}



Categories.prototype.getAll = function() {
    var asyncRequest = new XMLHttpRequest();
    asyncRequest.open("GET",  this.urlP  , true);
    res = asyncRequest.addEventListener("load", this.responseCateg.bind(this));
    asyncRequest.send();
}

Categories.prototype.responseCateg = function(e) {
    var x = e.target;
    if (x.readyState == 4) {
        if (x.status == 200) {
            console.log(x);
            var userIsAdmin = x.getResponseHeader ("showInput");

            if(userIsAdmin == "yes"){
                this.input.style.display = 'block';
            }else{
                this.input.style.display = 'none';
            }

            if (x.responseText != "") {
                res =  x.responseText;
                var p = JSON.parse(res);

                this.addCategories(p);
            }
        }
    }
}

Categories.prototype.addCategories = function(json){

        for (var i = 0; i < json.length; i++) {
            var recordLi = document.createElement('li');
            var recordA = document.createElement('a');
            recordA.href = "Events.jsp?Category=" +  json[i]["id"] + "&Cname=" + json[i]["name"]; // Insted of calling setAttribute
            recordA.innerHTML = json[i]["name"]; // <a>INNER_TEXT</a>
            recordLi.appendChild(recordA);
            this.list.appendChild(recordLi);
        }
}

Categories.prototype.submit = function(e) {
    e.preventDefault();
    var error = false;

    if (this.categorytext == null || this.categorytext.categorytext == "") {
        this.categorytext.style.border = "2px solid red";
        error = true;
    } else {
        var categorytext = this.categorytext.value;
        this.categorytext.style.border = "";
    }

    if(error == true) {
        alert("error: Category name is required");
        return;
    }
    var asyncRequest = new XMLHttpRequest();

        var params = "nameC=" + this.categorytext.value;
        asyncRequest.open("POST",  this.urlP + "?" + params, true);    //   /Test is url to Servlet!
        res = asyncRequest.addEventListener("load", this.responseAdd.bind(this));
        asyncRequest.send(params);
}

Categories.prototype.responseAdd = function(e) {
    var x = e.target;
    if (x.readyState == 4) {
        if (x.status == 200) {
            console.log(x);
            if (x.responseText != "") {
                res =  x.responseText;
                //alert("res: " + res);
                if(res == null ||res == "error"){
                    alert("ERROR");
                }else if(res == "done"){
                    alert("Category added");
                    this.list.innerHTML = "";
                    this.getAll();
                }
            }
        }
    }
}
var Category = new Categories();