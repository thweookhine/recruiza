function deleteform() {
    let form = document.querySelector(".confirmDelete");
    form.style.display = "block";
}

function deleteformclose() {
    let form = document.querySelector(".confirmDelete");
    form.style.display = "none";
}

// post date dur date 
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

// log out form generate
function generateLogoutForm() {
    var element =  document.getElementById('confirmationModal');
    if (typeof(element) != 'undefined' && element != null)
    {
        alert("alreay logging out");
    }
    let parent = document.querySelector('body')
    let modal = document.createElement('div')
    modal.className = 'confirmationModal'
    modal.innerHTML = `
        <div class="modalBox">
            <button onclick="closeLogoutForm()">
                <ion-icon name="close-outline"></ion-icon>
            </button>
            <h2>Logging Out</h2>
            <p>This action will log u out of this website</p>
            <div class="modalButtonBox">
                <a href="#">Yes <ion-icon name="sad-outline"></ion-icon></a>
                <a onclick="closeLogoutForm()">No <ion-icon name="happy-outline"></ion-icon></a>
            </div>
        </div>
        <div class="backgroundExit" onclick="closeLogoutForm()"></div>
    `
    parent.append(modal)
}
function closeLogoutForm() {
    document.querySelector('.confirmationModal').remove();
}

// generateTeamBox()
function generateTeamBox() {
    console.log('data loading');
    let form = document.querySelector('.addForm');
    form.classList.toggle('show')
}