const formData = {
    "team" : `
        <form th:action="@{/saveTeam}" method="post" th:object="\${team}">
			<div>
				<label for="name">Name</label> <input type="text" th:field="*{teamName}"
					placeholder="Enter Team Name" /> <label th:if="\${#fields.hasErrors('teamName')}"
					th:errors="*{teamName}" style="color: red;">Error</label>
			</div>

			<div>
				<label for="depts">Select Department</label> <select th:field="*{departmentName}">
					<option th:value="none">Select Department</option>
					<option th:each="d : \${deptNameList}" th:value="\${d}" th:text="\${d}"></option>
				</select>
				<p th:text="\${selectDept}"></p>
			</div>
			<div>
				<input type="submit" value="Add" />
			</div>
        </form>
    `
}