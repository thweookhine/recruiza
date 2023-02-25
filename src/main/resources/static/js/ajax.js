$(document).ready(getTeams);

function getTeams()
	{
		
		var departmentId = $("#departmentSelect option:selected").val();
		
		console.log(departmentId);
		
		$.ajax({
			url : '/teamList/' + departmentId,
			type : "get",
			success : function(response) {
			 	 $('#teamSelect').empty();
				for (item in response) {
					$('#teamSelect').append($('<option>', {
					    value: response[item].teamId,
					    text:  response[item].teamName
					}));
				}
			},
			error : function(e) {
				 alert("Submit failed" + JSON.stringify(e));
			}
		});
	}