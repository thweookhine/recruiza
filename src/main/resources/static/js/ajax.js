$(document).ready(getTeams);

function getTeams() {

	var departmentId = $("#departmentSelect option:selected").val();

	$.ajax({
		url: '/teamList/' + departmentId,
		type: "get",
		success: function (response) {
			$('#teamSelect').empty();
			if (response == 0) {
				console.log("error");

				let newlink = document.createElement('a');
				newlink.setAttribute("href", "/team");
				newlink.classList.add("newlink")
				newlink.innerHTML = "Create Team";
				document.querySelector('.teamBox').append(newlink);
			} else {
				if(document.querySelector('.newlink') != null){
					document.querySelector('.newlink').remove();
				}
				for (item in response) {
					$('#teamSelect').append($('<option>', {
						value: response[item].teamId,
						text: response[item].teamName
					}));
				}
			}
		},
		error: function (e) {
			// alert("Submit failed" + JSON.stringify(e));
		}
	});
}