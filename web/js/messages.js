/**
 * Created by Petr on 15.6.2015.
 */
var Messages = function() {

    this.urlP = "/udalost";

    this.input = document.getElementById("inputDiv");
    this.list = document.getElementById("listNav");
    /******** FORMS *******/
    this.form = document.getElementById("inputForm");
    this.messagetext = document.getElementById("message");
    this.eHeader = document.getElementById("eHeader");
    this.lastId = -1;

    this.eventID = this.getParam('Event', window.location.href );
    this.eventName = this.getParam('Ename', window.location.href );

    this.eHeader.content = "Messages for event: " + this.categoryName;

    this.ws = null;


    this.init();

}

Messages.prototype.websocket = function(){
    var eId = this.eventID;
    function openSocket(){

        // Ensures only one connection is open at a time
        if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
            writeResponse("WebSocket is already opened.");
            return;
        }
        // Create a new instance of the websocket
        //webSocket = new WebSocket("ws://localhost:8080/online?eventId=" + eId);
        webSocket = new WebSocket("ws://localhost:"+location.port+"/online?eventId="+eId);


        webSocket.onopen = function(event){

            //alert(eId);
            if(event.data === undefined) {
                console.log("undefined")
                return;
            }
        };

        webSocket.onmessage = function(event){
            //alert("onmessage: " + event.data);
            // Message.lastId = event.data;
            Message.getAll();
        };

        webSocket.onclose = function(event){
            console.log("Connection closed");
        };
    }

    function closeSocket(){
        webSocket.close();
    }

    function writeResponse(text){
        alert( text);
    }

    openSocket();
}
var webSocket;

Messages.prototype.getParam = function( name, url ) {
    if (!url) url = location.href
    name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec( url );
    return results == null ? null : results[1];
}

Messages.prototype.init = function() {
    this.getAll();
    this.form.addEventListener("submit",this.submit.bind(this));
    this.websocket();
}

Messages.prototype.getAll = function() {
    var asyncRequest = new XMLHttpRequest();
    asyncRequest.open("GET",  this.urlP + "?Event=" + this.eventID  + "&LastId=" + this.lastId, true);    //   /Test is url to Servlet!
    res = asyncRequest.addEventListener("load", this.responseMessag.bind(this));
    asyncRequest.send();
    // alert("was sent")

}

Messages.prototype.responseMessag = function(e) {
    var x = e.target;
    if (x.readyState == 4) {
        if (x.status == 200) {
            console.log(x);
            var userIsAdmin = x.getResponseHeader ("showInput");
            // alert(userIsAdmin);
            if(userIsAdmin == "yes"){
                this.input.style.display = 'block';
            }else{
                this.input.style.display = 'none';
            }

            if (x.responseText != "") {
                res =  x.responseText;
                var p = JSON.parse(res);
                //alert("res: " + res + " " + p);

                this.addCategories(p);
                //this.getAll();
                //  alert("call getAll");
            }
        }
    }
}

Messages.prototype.addCategories = function(json){



    for (var i = 0; i < json.length; i++) {

        var recordA = document.createElement('div');
        recordA.innerHTML = "<b>"+json[i]["id"]
            +" <br/> "
            + json[i]["date"] + " </b> "
            +" <br/> " + json[i]["text"];

        if(json[i]["id"] > this.lastId){
            this.lastId = json[i]["id"];
        }


        this.list.insertBefore(recordA, this.list.firstChild);
    }


}

Messages.prototype.submit = function(e) {
    e.preventDefault();
    var error = false;

    if (this.messagetext == null || this.messagetext.value == "") {
        error = true;
    } else {
        var messagetext = this.messagetext.value;
        this.messagetext.style.border = "";
    }



    if(error == true) {
        alert("error: Text is required");
        return;
    }
    var asyncRequest = new XMLHttpRequest();

    var params = "Text=" + this.messagetext.value + "&Event=" + this.eventID ;
    asyncRequest.open("POST",  this.urlP + "?" + params, true);    //   /Test is url to Servlet!
    res = asyncRequest.addEventListener("load", this.responseAdd.bind(this));
    asyncRequest.send(params);





}

Messages.prototype.responseAdd = function(e) {
    var x = e.target;
    if (x.readyState == 4) {
        if (x.status == 200) {
            console.log(x);
            if (x.responseText != "") {
                res =  x.responseText;
                if(res != "done") {
                    alert("res: " + res);
                }
            }
        }
    }
}


var Message = new Messages();