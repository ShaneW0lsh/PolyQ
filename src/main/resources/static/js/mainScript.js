$(document).ready(function(){
    $.get("/api/submission-sessions/all", data => {
        for (let i = 0; i < data.length; ++i) {
            $(`<div class="m-2">
                <button type="button" class="btn btn-primary btn-lg btn-block submission-session-button">${data[i].discipline}</button>
            </div>`).appendTo($("#submission-session-container"))
            //$(`<div>${data[i].discipline} ${data[i].dateAndTime}</div>`).appendTo($("#submission-session-container"));
        }
        console.log(data);
    });
});