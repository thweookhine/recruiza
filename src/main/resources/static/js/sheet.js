let sheetTemp = [];
let dataInTable = [];
let dataToGenerate = [];
let count = 0;
let jobpostid = 1;
let duplicateFound = false;

function retrieveSheetData(sheetid, postid) {
	document.querySelector('.titleForApplicants').innerHTML = document.querySelector('.J' + postid).value;
	document.querySelector('.titleForApplicants').innerHTML = document.querySelector('.J' + postid).value;
	sheetTemp = [];
	let SHEET_ID = sheetid
	let SHEET_TITLE = 'formresponse';
	let SHEET_RANGE = 'A2:K100'

	let FULL_URL = ('https://docs.google.com/spreadsheets/d/' + SHEET_ID + '/gviz/tq?sheet=' + SHEET_TITLE + '&range=' + SHEET_RANGE);

	getApplicants(postid);
	console.log(dataInTable);

	fetch(FULL_URL)
		.then(res => res.text())
		.then(rep => {
			let data = JSON.parse(rep.substr(47).slice(0, -2));
			let length = data.table.rows.length;

			// remove existing cards
			let cards = document.querySelectorAll(".applicard");
			cards.forEach(element => {
				element.remove()
			})

			document.querySelector('.applicantsLoading').style.display = "flex";

			setTimeout(() => {
				document.querySelector('.applicantsLoading').style.display = "none";
				// add new cards
				for (let i = 0; i < length; i++) {
					console.log(data.table.rows[i]);
					sheetTemp.push(data.table.rows[i]);

					dataInTable.forEach(element => {
						if (element.split(',')[0] == data.table.rows[i].c[2].v && element.split(',')[1] == data.table.rows[i].c[1].v) {
							count++;
						}
					})

					if (count > 0) {
						console.log(count + " duplicate " + data.table.rows[i].c[2].v);
					} else {
						let parent = document.querySelector('.applicantContainer');
						let newcard = document.createElement('div')
						newcard.className = "applicard";
						newcard.style = "--i:0." + (i + 5) + "s;";
						// newcard.innerHTML = `
						// 	<h4 class="applicantsource">${data.table.rows[i].c[5].v}</h4>
						// 	<img src="https://api.dicebear.com/5.x/initials/svg?seed=${data.table.rows[i].c[2].v}" alt="">
						// 	<div class="applicantInfo">
						// 		<h3>${data.table.rows[i].c[2].v}</h3>
						// 		<p><ion-icon name="mail-outline"></ion-icon> ${data.table.rows[i].c[1].v}</p>
						// 		<p><ion-icon name="phone-portrait-outline"></ion-icon> ${data.table.rows[i].c[3].v}</p>
						// 		<p><ion-icon name="earth-outline"></ion-icon> ${data.table.rows[i].c[4].v}</p>
						// 		<div class="applicantButtonContainer">
						// 			<!-- insert drive link in here -->
						// 			<a href="${data.table.rows[i].c[6].v}" target="_blank">
						// 				<ion-icon name="eye-outline"></ion-icon>
						// 			</a>
						// 			<!-- to add the applicant to the table -->
						// 			<a onclick='generateAppModel(${i},"${postid}")'>
						// 				<ion-icon name="person-add-outline"></ion-icon>
						// 			</a>
						// 		</div>
						// 	</div>
						// `

						newcard.innerHTML = `
						<img class="applicantImage" style="transform: scale(0.7); border-radius: 50%;" src="https://api.dicebear.com/5.x/initials/svg?seed=${data.table.rows[i].c[2].v}" alt="">
						<img class="backImage" src="https://api.dicebear.com/5.x/shapes/svg?seed=${data.table.rows[i].c[2].v}" alt="">
					
										<div class="applicantInformation">
											<p>
												<span>
													<ion-icon name="person-outline" role="img" class="md hydrated" aria-label="person outline"></ion-icon>
													Name
												</span>
												<span>${data.table.rows[i].c[2].v}</span>
											</p>
											<p>
												<span>
													<ion-icon name="mail-outline" role="img" class="md hydrated" aria-label="mail outline"></ion-icon>
													Email
												</span>
												<span>${data.table.rows[i].c[1].v}</span>
											</p>
											<p>
												<span>
													<ion-icon name="phone-portrait-outline" role="img" class="md hydrated" aria-label="phone portrait outline"></ion-icon>
													Mobile
												</span>
												<span>${data.table.rows[i].c[3].v}</span>
											</p>
											<p>
												<span>
													<ion-icon name="storefront-outline" role="img" class="md hydrated" aria-label="storefront outline"></ion-icon>
													Address
												</span>
												<span>${data.table.rows[i].c[4].v}</span>
											</p>
										</div>

										<div class="applicardButtonContainer">
											<div>
												<a class="applicantProcessButton" href="${data.table.rows[i].c[5].v}" target="_blank">
													<ion-icon name="cloud-outline" role="img" class="md hydrated" aria-label="cloud outline"></ion-icon>Check CV
												</a>
											</div>
											<div>
												<a class="applicantProcessButton" onclick='generateAppModel(${i},"${postid}")'>
												<ion-icon name="person-add-outline" role="img" class="md hydrated" aria-label="chevron forward outline"></ion-icon>Add to Database
												</a>
											</div>
										</div>
						`
						parent.append(newcard);
					}

					count = 0;
				}
			}, 2000);


		})
}

function generateAppModel(i, id) {
	console.log(sheetTemp[i] + " " + id)
	let parent = document.querySelector('body');
	let newcard = document.createElement('div')
	newcard.className = "addApplicantBox";
	newcard.innerHTML = `
	<form class="addApplicant" action="/saveapplicant" method="post" enctype="multipart/form-data">
		<h2>Add Applicant</h2>
		<div>
			<div>
				<input type="hidden" name="jobPostId" value="${id}">
				<input name="name" data-name="Name" type="text" value="${sheetTemp[i].c[2].v}" placeholder="${sheetTemp[i].c[2].v}" required="required">
				<input name="email" data-name="Email" type="email" value="${sheetTemp[i].c[1].v}" placeholder="${sheetTemp[i].c[1].v}" required="required">
				<input name="mobile" data-name="Mobile" type="number" value="${sheetTemp[i].c[3].v}" placeholder="${sheetTemp[i].c[3].v}" required="required">
				<input name="address" data-name="Address" type="text" value="${sheetTemp[i].c[4].v}" placeholder="${sheetTemp[i].c[4].v}" required="required">
				<input name="Source" data-name="Source" type="text" value="${sheetTemp[i].c[5].v}" placeholder="${sheetTemp[i].c[5].v}" readonly="raedonly" required="required">
			</div>
			<div>
				<textarea data-name="link" name="link" cols="30" rows="2" placeholder="${sheetTemp[i].c[6].v}" readonly="readonly">${sheetTemp[i].c[6].v}</textarea>
				<input type="file" name="file" multiple required/>
				<textarea name="comment" data-name="comment" cols="30" rows="4" placeholder="Comment"></textarea>
			</div>
		</div>
		<input type="submit" value="Add To Database"/>
	</form>
	<div class="backgroundExit" onclick="closeAppModel()"></div>
	`
	parent.append(newcard);
}
function closeAppModel() {
	document.querySelector(".addApplicantBox").remove();
}

// ajax call for applicant data

function getApplicants(postId) {


	$.ajax({
		url: '/applicantList/' + postId,
		type: "get",
		success: function (response) {

			if (response == 0) {
				console.log("error");
			} else {
				for (item in response) {
					console.log(response[item])
					dataInTable.push(response[item].applicantName + "," + response[item].applicantEmail + "," + response[item].applicantEmail);
				}
			}
		},
		error: function (e) {
			alert("Submit failed" + JSON.stringify(e));
		}
	});
}