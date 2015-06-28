
var events = function() {

    this.urlP = "/zapas";

    this.list = document.getElementById("listNav");

    this.eHeader = document.getElementById("eHeader");
    this.categoryID = this.getParam('Category', window.location.href );
    this.categoryName = this.getParam('Cname', window.location.href );
    //alert(this.categoryName)
    this.eHeader.content = "Events for category " + this.categoryName;




    this.init();

}

events.prototype.getParam = function( name, url ) {
    if (!url) url = location.href
    name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec( url );
    return results == null ? null : results[1];
}

events.prototype.init = function() {
    this.getAll();

}

events.prototype.getAll = function() {
    var asyncRequest = new XMLHttpRequest();
    asyncRequest.open("GET",  this.urlP + "?Category=" + this.categoryID, true);    //   /Test is url to Servlet!
    res = asyncRequest.addEventListener("load", this.responseEvents.bind(this));
    asyncRequest.send();
   // alert("was sent")

}

events.prototype.responseEvents = function(e) {
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

events.prototype.addCategories = function(json){

        for (var i = 0; i < json.length; i++) {
           // this.lines.push(new Line(this, json[i]["id"],json[i]["name"]));
            //alert(json[i]["id"]);
            //alert(json[i]["name"]);
            var recordLi = document.createElement('li');
            var recordA = document.createElement('a');
            recordA.href = "Messages.jsp?Event=" +  json[i]["id"] + "&Ename" + json[i]["name"]; // Insted of calling setAttribute
            recordA.innerHTML = json[i]["name"] + " " + json[i]["date"]; // <a>INNER_TEXT</a>
            recordLi.appendChild(recordA);
            this.list.appendChild(recordLi);
        }


}




var Event = new events();