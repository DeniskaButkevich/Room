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
        stompClient.subscribe('/topic/greetings', function (result) {
            turning(result);
        });
    });
}

function switching(id) {
    stompClient.send("/app/hello", {}, id);
}

function turning(result) {

    var a = result.body.split('-');
    var str = a[1];

    var th_id = 'th-' + a[0];
    var th_elem = document.getElementById(th_id);
    if(th_elem != null){
        if(str == 'false'){
            th_elem.classList.remove("lamp-active")
        }else {
            th_elem.classList.add("lamp-active");
        }
    }

    var td_id = 'td-' + a[0];
    var td_elem = document.getElementById(td_id);
    if(td_elem != null) {
        if (str == 'false') {
            td_elem.innerText = "Off";
        } else {
            td_elem.innerText = "On";
        }
    }

    var td_id = 'body_room';
    var td_elem = document.getElementById(td_id)
    if(td_elem != null) {
        if (str == 'false') {
            td_elem.classList.remove('body-active-true')
            td_elem.classList.add('body-active-false')
        } else {
            td_elem.classList.remove('body-active-false')
            td_elem.classList.add('body-active-true')
        }
    }
}