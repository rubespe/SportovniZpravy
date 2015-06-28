
var EventsA = function() {

    this.urlP = "/zapas";

    //this.input = document.getElementById("inputDiv");
    this.categDiv = document.getElementById("categDiv");
    /******** FORMS *******/
    this.form = document.getElementById("inputForm");
    this.eventName = document.getElementById("eventName");
    this.eventDate = document.getElementById("eventDate");
    this.eventCat = document.getElementById("eventCat");



    this.init();

}

EventsA.prototype.init = function() {
    this.getAll();
    this.form.addEventListener("submit",this.submit.bind(this));
}



EventsA.prototype.getAll = function() {
    var asyncRequest = new XMLHttpRequest();
    asyncRequest.open("GET",  "/CategoriesS"  , true);    //   /Test is url to Servlet!
    res = asyncRequest.addEventListener("load", this.responseCateg.bind(this));
    asyncRequest.send();
   // alert("was sent")

}

EventsA.prototype.responseCateg = function(e) {
    var x = e.target;
    if (x.readyState == 4) {
        if (x.status == 200) {
            console.log(x);


            if (x.responseText != "") {
                res =  x.responseText;
                var p = JSON.parse(res);
                //alert("res: " + res + " " + p);

                this.addCategories(p);
            }
        }
    }
}

EventsA.prototype.addCategories = function(json){



        for (var i = 0; i < json.length; i++) {
           // this.lines.push(new Line(this, json[i]["id"],json[i]["name"]));
            //alert(json[i]["id"]);
            //alert(json[i]["name"]);
            var recordDiv = document.createElement('p');
            recordDiv.textContent = "id: " + json[i]["id"] + "  name: " + json[i]["name"];
           // var recordA = document.createElement('a');
          //  recordA.href = "Event?Category=" +  json[i]["id"]; // Insted of calling setAttribute
           // recordA.innerHTML = json[i]["name"]; // <a>INNER_TEXT</a>
           // recordLi.appendChild(recordA);
            //alert("append");

            this.categDiv.appendChild(recordDiv);
        }


}

EventsA.prototype.submit = function(e) {
    e.preventDefault();

    var asyncRequest = new XMLHttpRequest();

        var params = "eventName=" + this.eventName.value + "&eventDate=" + this.eventDate.value + "&eventCat=" + this.eventCat.value;
        asyncRequest.open("POST",  this.urlP + "?" + params, true);    //   /Test is url to Servlet!
        res = asyncRequest.addEventListener("load", this.responseAdd.bind(this));
        asyncRequest.send(params);





}

EventsA.prototype.responseAdd = function(e) {
    var x = e.target;
    if (x.readyState == 4) {
        if (x.status == 200) {
            console.log(x);
            if (x.responseText != "") {
                res =  x.responseText;
                alert("res: " + res);
                this.categDiv.innerHTML = "";
                this.getAll();

            }
        }
    }
}


var EventA = new EventsA();