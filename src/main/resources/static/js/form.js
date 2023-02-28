function deleteform() {
    let form = document.querySelector(".confirmDelete");
    form.style.display = "block";
}

function deleteformclose() {
    let form = document.querySelector(".confirmDelete");
    form.style.display = "none";
}


var today = new Date().toISOString().split('T')[0];
var idate = document.querySelectorAll('.customdate')
console.log(today);
idate[0].setAttribute('min', today);

var dueDateBox = document.querySelector(".dueDateBox")
dueDateBox.style.display = "none"

idate[0].addEventListener("change", (e) => {
    dueDateBox.style.display = "block"
    idate[1].setAttribute('min', idate[0].value);
})
