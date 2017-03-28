function showErrorStack(linkElt, detailsDivId, msgShow, msgHide){
    var stackTraceContainer = document.getElementById(detailsDivId);
    if(stackTraceContainer.style.display == "none"){
        stackTraceContainer.style.display = "block";
        linkElt.innerHTML = msgHide;
    }else{
        stackTraceContainer.style.display = "none";
        linkElt.innerHTML = msgShow;
    }
}
