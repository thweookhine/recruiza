// post date dur date 
var today = new Date().toISOString().split('T')[0];
var idate = document.querySelectorAll('.customdate')
if (idate[0]) {
    idate[0].setAttribute('min', today);

    idate[0].addEventListener("change", (e) => {
        dueDateBox.style.display = "block"
        idate[1].setAttribute('min', idate[0].value);
    })
}

var dueDateBox = document.querySelector(".dueDateBox")
if (dueDateBox) {
    dueDateBox.style.display = "none"
}

// log out form generate
function generateLogoutForm() {
    var element = document.getElementById('confirmationModal');
    if (typeof (element) != 'undefined' && element != null) {
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
                <a href="/logout">Yes <ion-icon name="sad-outline"></ion-icon></a>
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
    document.querySelector('.addForm').classList.toggle('show')
}

// toggle form
function toggleNav() {
    document.querySelector('nav').classList.toggle('navhide')
    document.querySelector('.hamburger').classList.toggle('hamburgermove')
}

// password and confirm password validate
function checkPasswordMatch() {
    var pass = document.querySelector("#passcode");
    if (pass.value != $("#password").val()) {
        pass.setCustomValidity("Passwords did not match");
    } else {
        pass.setCustomValidity("");
    }
}

//profile form 
function showProfileForm() {
    document.querySelector('.user-update-new-data-form').classList.toggle('form-hider')
}

function generateUpdateRoleForm(code,id,role){
    let parent = document.querySelector('body')
    let modal = document.createElement('div')
    modal.className = 'updateUserform'
    modal.innerHTML = `
    <div class="background" onclick="deleteUpdateform()"></div>
    <form data-formName="Change ${code}">
        <div>
            <label>Roles</label>
            <select class="changerole">
                ${role=="ADMIN" ? "<option th:value=\"'ADMIN'\" selected>ADMIN</option>" : "<option th:value=\"'ADMIN'\">ADMIN</option>"}
                ${role=="HR_Role" ? "<option th:value=\"'HR_Role'\" selected>Human Resource (HR)</option>" : "<option th:value=\"'HR_Role'\">Human Resource (HR)</option>"}
                ${role=="MARKETER" ? "<option th:value=\"'MARKETER'\" selected>Marketing Team (MT)</option>" : "<option th:value=\"'MARKETER'\">Marketing Team (MT)</option>"}
                ${role=="DH_Role" ? "<option th:value=\"'DH_Role'\" selected>Department Head (DH)</option>" : "<option th:value=\"'DH_Role'\">Department Head (DH)</option>"}
                ${role=="GM_Role" ? "<option th:value=\"'GM_Role'\" selected>General Manager (GM)</option>" : "<option th:value=\"'GM_Role'\">General Manager (GM)</option>"}
                ${role=="PM_Role" ? "<option th:value=\"'PM_Role'\" selected>Project Manager (PM)</option>" : "<option th:value=\"'PM_Role'\">Project Manager (PM)</option>"}
                ${role=="TM_Role" ? "<option th:value=\"'TM_Role'\" selected>Team Manager (TM)</option>" : "<option th:value=\"'TM_Role'\">Team Manager (TM)</option>"}
            </select>
        </div>
        <a href="@{/applicantStatusChange(id=${id})}">Update Role</a>
    </form>

    <div class="deleteBox">
        <button onclick="deleteUpdateform()">Back</button>
    </div>
    `
    parent.append(modal)
}