// Calculate the dimension of the web page
var w = window.innerWidth;
var h = window.innerHeight;
if (w > 500) {
	document.querySelector('nav').classList.remove('navhide')
} else {
	document.querySelector('nav').classList.add('navhide')
}
window.addEventListener("resize", (event) => {
	w = window.innerWidth;
	console.log(w);
	if (w > 500) {
		document.querySelector('nav').classList.remove('navhide')
	} else {
		document.querySelector('nav').classList.add('navhide')
	}
});

function deleteform() {
	let form = document.querySelector(".deleteConfirmationModal");
	form.style.display = "flex";
}
function deleteformclose() {
	let form = document.querySelector(".deleteConfirmationModal");
	form.style.display = "none";
}
function deleteUpdateform(){
	document.querySelector('.updateUserform').remove();
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
const navlist = document.querySelectorAll('.list');

function activeLink() {
	navlist.forEach((item) =>
		item.classList.remove('active'));
}

if (title == 'Profile') {
	activeLink()
	navlist[0].classList.add('active')
} else if (title == 'Team' || title == 'Department' || title == 'Job Position' || title == 'RecruitementResource') {
	activeLink()
	navlist[3].classList.add('active')
}
// navlist.forEach((item) => item.addEventListener('click', activeLink));

if (title == 'Home') {
	let parent = document.querySelector('body');
	let child = document.createElement('div');
	child.className = 'loader-box';
	child.innerHTML = `
		 <h2>Recruiza</h2>
        <div class="loader">
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
            <span class="ring"></span>
        </div>
        <h2>Loading ...</h2>
	`
	parent.append(child);
	setTimeout(() => {
		document.querySelector('.loader-box').remove()
	}, 2000)
}

if(localStorage.getItem('theme')==null){
	console.log('no theme')
	localStorage.setItem('theme', 'default');
}else{
	console.log(localStorage.getItem('theme'));
	changeTheme();
}

var checkBoxes = document.querySelectorAll('.chooseTheme');
checkBoxes.forEach(element => {
	element.addEventListener("change", () => {
		localStorage.setItem('theme', element.value);
		changeTheme()
	})
});

function changeTheme(){
	let settingbox = document.querySelector('.settingBox')
	if(localStorage.getItem('theme')=='default'){
		if(settingbox){
			document.getElementById('default').checked = true;
		}
		document.documentElement.style.setProperty('--boxColor', '#ffffff');
		document.documentElement.style.setProperty('--background', '#ffffff');
		document.documentElement.style.setProperty('--fontColor', 'black');
		document.documentElement.style.setProperty('--secondaryColor', '#378eea');
		document.documentElement.style.setProperty('--shadowColor', 'rgba(0, 0, 0, 0.2)');
		document.documentElement.style.setProperty('--errorColor', 'tomato');
	}else if(localStorage.getItem('theme')=='orangy'){
		if(settingbox){
			document.getElementById('orangy').checked = true;
		}
		document.documentElement.style.setProperty('--boxColor', '#fff2de');
		document.documentElement.style.setProperty('--background', '#fff2de');
		document.documentElement.style.setProperty('--fontColor', 'black');
		document.documentElement.style.setProperty('--secondaryColor', '#f6922e');
		document.documentElement.style.setProperty('--shadowColor', 'rgba(0, 0, 0, 0.2)');
		document.documentElement.style.setProperty('--errorColor', 'tomato');
	}else if(localStorage.getItem('theme')=='techno'){
		if(settingbox){
			document.getElementById('techno').checked = true;
		}
		document.documentElement.style.setProperty('--boxColor', '#222222');
		document.documentElement.style.setProperty('--background', '#222222');
		document.documentElement.style.setProperty('--fontColor', '#0AE4EE');
		document.documentElement.style.setProperty('--secondaryColor', '#0B32B0');
		document.documentElement.style.setProperty('--shadowColor', 'rgba(76, 174, 255, 0.5)');
		document.documentElement.style.setProperty('--errorColor', '#EF1B23');
	}else if(localStorage.getItem('theme')=='hacker'){
		if(settingbox){
			document.getElementById('hacker').checked = true;
		}
		document.documentElement.style.setProperty('--boxColor', '#111212');
		document.documentElement.style.setProperty('--background', '#111212');
		document.documentElement.style.setProperty('--fontColor', 'limegreen');
		document.documentElement.style.setProperty('--secondaryColor', '#11851e');
		document.documentElement.style.setProperty('--shadowColor', 'rgba(0, 209, 24, 0.5)');
		document.documentElement.style.setProperty('--errorColor', '#004f09');
	}
}

function toggleUpdateProfile(){
	document.querySelector(".profileBox:nth-child(2)").classList.toggle('movetop');
}

function toggleUpdatePasswordProfile(){
	toggleUpdateProfile();
	document.querySelector(".profileBox:nth-child(3)").classList.toggle('moveright');
}
