// Calculate the dimension of the web page
var w = window.innerWidth;
var h = window.innerHeight;
console.log(w + " \n " + h);
if(w>600){
    document.querySelector('nav').classList.toggle('navhide')
}

function deleteform() {
    let form = document.querySelector(".deleteConfirmationModal");
    form.style.display = "flex";
}
function deleteformclose() {
    let form = document.querySelector(".deleteConfirmationModal");
    form.style.display = "none";
}

// Show password
function showPassword(id) {
	var x = document.getElementById(id);
	if (x.type === "password") {
		x.type = "text";
	} else {
		x.type = "password";
	}
}

// Get page data
const title = document.getElementsByTagName("title")[0].innerHTML;
alert(title);
const navlist = document.querySelectorAll('.list');

function activeLink() {
	navlist.forEach((item) =>
		item.classList.remove('active'));
}

if(title == 'Profile'){
	activeLink()
	navlist[0].classList.add('active')
}else if(title == 'Team' || title == 'Department' || title == 'Job Position' ||title == 'RecruitementResource'){
	activeLink()
	navlist[3].classList.add('active')
}
// navlist.forEach((item) => item.addEventListener('click', activeLink));


