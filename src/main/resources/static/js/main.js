function showPassword(id) {
	var x = document.getElementById(id);
	if (x.type === "password") {
		x.type = "text";
	} else {
		x.type = "password";
	}
}

console.log('JS working');

const navlist = document.querySelectorAll('.list');

function activeLink() {
	navlist.forEach((item) =>
		item.classList.remove('active'));
	this.classList.add('active');
}
navlist.forEach((item) => item.addEventListener('click', activeLink));


var today = new Date().toISOString().split('T')[0];
var idate = document.querySelectorAll('.customdate')
console.log(today+" | "+idate.innerHTML);
idate[0].setAttribute('min', today);

function getPostDateData(){
	console.log(idate[0].value);
	if(idate[0].value == ""){
		idate[1].setAttribute('min', today);
	}else{
	idate[1].setAttribute('min', idate[0].value);}
}