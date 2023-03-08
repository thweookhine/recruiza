// Calculate the dimension of the web page
var w = window.innerWidth;
var h = window.innerHeight;
if(w>500){
	document.querySelector('nav').classList.remove('navhide')
}else{
	document.querySelector('nav').classList.add('navhide')
}
window.addEventListener("resize", (event)=>{
	w = window.innerWidth;
	console.log(w);
	if(w>500){
		document.querySelector('nav').classList.remove('navhide')
	}else{
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

if(title == 'Profile'){
	activeLink()
	navlist[0].classList.add('active')
}else if(title == 'Team' || title == 'Department' || title == 'Job Position' ||title == 'RecruitementResource'){
	activeLink()
	navlist[3].classList.add('active')
}
// navlist.forEach((item) => item.addEventListener('click', activeLink));

if(title == 'Home'){
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
	setTimeout(()=>{
		document.querySelector('.loader-box').remove()
	},4000)
}
