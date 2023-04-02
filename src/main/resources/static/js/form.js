var startDate = document.getElementById("start-date")
var endDate = document.getElementById("end-date")


function updateForm(){
    var type = document.getElementById("expense-type").value;
    if(type == "one-time"){
        document.getElementById("start-date-label").innerHTML = "Date";
        document.getElementById("end-date-label").style.display = "none";
        endDate.style.visibility = "hidden";
        if(document.getElementById("error-end-date") != null){
            document.getElementById("error-end-date").style.display = "none";
        }
        startDate.addEventListener("change", function(){
            endDate.value = startDate.value;
        });

        var today = new Date().toISOString().split('T')[0];
        document.getElementById("start-date").setAttribute('max', today);
    }
    else{
        document.getElementById("end-date-label").style.display = "block";
        endDate.style.visibility = "visible";
        startDate.removeEventListener("change", function(){
            endDate.value = startDate.value;
        });
    }
}
