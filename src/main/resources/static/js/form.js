function updateForm(){
    var type = document.getElementById("expense-type").value;
    if(type == "one-time"){
        document.getElementById("start-date-label").innerHTML = "Date";
        document.getElementById("end-date-label").style.display = "none";
        var startDate = document.getElementById("start-date")
        var endDate = document.getElementById("end-date")
        endDate.style.visibility = "hidden";
        document.getElementById("error-end-date").style.display = "none";
        startDate.addEventListener("change", function(){
            endDate.value = startDate.value;
        });
    }
    else{
        document.getElementById("end-date-label").style.display = "block";
        var endDate = document.getElementById("end-date")
        endDate.style.visibility = "visible";
        startDate.removeEventListener("change", function(){
            endDate.value = startDate.value;
        });
    }
}
