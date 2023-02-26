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
