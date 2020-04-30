var stompClient = null;

window.onload = function () {
    connect();
};
window.onbeforeunload = function () {
    disconnect();
};

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (id) {
            turning(id);
        });
    });
}

function switching(id) {
    stompClient.send("/app/hello", {}, id);
}

function turning(id) {
    var th_id = 'th-' + id.body;
    var td_id = 'td-' + id.body;
    var th_elem = document.getElementById(th_id);
    var td_elem = document.getElementById(td_id);

    if(th_elem != null){
        if(th_elem.classList.contains("lamp-active")){
            th_elem.classList.remove("lamp-active")
        }else {
            th_elem.classList.add("lamp-active");
        }
    }
    if(td_elem != null) {
        if (td_elem.innerText == "On") {
            td_elem.innerText = "Off";
        } else {
            td_elem.innerText = "On";
        }
    }
}