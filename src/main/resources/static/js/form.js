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
    <form data-formName="Change ${code}" action="/admin/updateUser?id=${id}" method="post">
        <div>
            <label>Roles</label>
            <select class="changerole" name="role" required="required">
                <option value="HR_Role" ${role == 'HR_Role' ? 'selected': ''}>Human Resource (HR)</option>
                <option value="MARKETER" ${role == 'MARKETER' ? 'selected': ''}>Marketing Team (MT)</option>
                <option value="DH_Role" ${role == 'DH_Role' ? 'selected': ''}>Department Head (DH)</option>
                <option value="GM_Role" ${role == 'GM_Role' ? 'selected': ''}>General Manager (GM)</option>
                <option value="PM_Role" ${role == 'PM_Role' ? 'selected': ''}>Project Manager (PM)</option>
                <option value="TM_Role" ${role == 'TM_Role' ? 'selected': ''}>Team Manager (TM)</option>
            </select>
        </div>
        <input type="submit" value="Update Role">
    </form>

    <div class="deleteBox">
        <button onclick="deleteUpdateform()">Back</button>
    </div>
    `
    parent.append(modal)
}

function editclose(){
    console.log('closed');
    document.querySelector('.jobPostEdit').remove()
}

function generateUpdateRoleForm(id,name,count){
    let parent = document.querySelector('body')
    let modal = document.createElement('div')
    modal.className = 'jobPostEdit'
    modal.innerHTML = `
    <div class="closer" onclick="editclose()"></div>
		<form class="jobPostEditForm" action="/postJP" method="post">
			<input type="hidden" name="jobPostId" value="${id}">
			<h2 data-label="Name" >
				<img src="https://api.dicebear.com/5.x/shapes/svg?seed=${name}">
				<p>${name}</p>
			</h2>
			<input data-label="Post Date" type="date" name="postDate" placeholder="Enter Post Date" class="customdate" required="" min="2023-03-16">
			<input data-label="Due Date"  type="date" name="dueDate" placeholder="Enter Due Date" class="customdate" required="">
			<textarea data-label="Sheet ID" name="sheetId" cols="30" rows="10" placeholder="Enter Sheet ID" required=""></textarea>
			<div>
				<span>Count : <span>${count}</span></span>
				<input type="submit" value="Post">
			</div>
		</form>
    `
    parent.append(modal)
}