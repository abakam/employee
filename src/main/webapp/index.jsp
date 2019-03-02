<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee</title>
</head>
<body>

<div>
    <table>
        <tr>
            <td width="60%">
                <table>
                    <tr>
                        <td colspan="2">
                            <h3>Save/Update Employee</h3>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <label>
                                Enter id
                                <input type="number" name="id" id="id" />
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                Enter name
                                <input type="text" name="name" id="name" />
                            </label>

                            <label>
                                Enter email
                                <input type="email" name="email" id="email" />
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                Enter age
                                <input type="number" name="age" id="age" />
                            </label>

                            <label>
                                Enter salary
                                <input type="number" name="salary" id="salary" />
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                Enter Gender
                                <select id="gender">
                                    <option value="MALE">Male</option>
                                    <option value="FEMALE">Female</option>
                                </select>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="button" value="Submit" onclick="addEmployee()">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <br/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h3>Get Employee</h3>
                            <label for="empId">
                                Enter id
                                <input type="number" id="empId">
                            </label>
                            <input type="button" value="Submit" onclick="listEmployee()">
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <br/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <h3>List Employees</h3>
                            <input type="button" value="Submit" onclick="listEmployees()">
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <br/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <h3>Delete Employee</h3>
                            <label for="deleteId">
                                Enter id
                                <input type="number" id="deleteId">
                            </label>
                            <input type="button" value="Submit" onclick="deleteEmployee()">
                        </td>
                    </tr>
                </table>
            </td>
            <td width="40%">
                <p id="result"></p>
            </td>
        </tr>
    </table>
</div>

</body>

<script>
    const url = 'http://localhost:5000/employee';

    function addEmployee() {
        let method = 'POST';
        let id = document.getElementById('id').value;

        const employee = {
            'age': +document.getElementById('age').value,
            'name': document.getElementById('name').value,
            'email': document.getElementById('email').value,
            'salary': +document.getElementById('salary').value,
            'gender': document.getElementById('gender').value
        };

        if (id !== '') {
            method = 'PUT';
            employee['id'] = +id;
        }

        makeXMLHttpRequestImpl(url + '/employees', {}, employee, method, r => {
            document.getElementById('result').innerText = r;
    }, e => {
            document.getElementById('result').innerText = e;
        });
    }

    function listEmployee() {
        let empId = document.getElementById('empId').value;

        if (empId !== '') {
            makeXMLHttpRequestImpl(url + '/employees/' + empId, {}, {}, 'GET', r => {
                document.getElementById('result').innerText = r;
        }, e => {
                document.getElementById('result').innerText = e;
            });
        }
    }

    function listEmployees() {
        makeXMLHttpRequestImpl(url + '/employees', {}, {}, 'GET', r => {
            document.getElementById('result').innerText = r;
    }, e => {
            document.getElementById('result').innerText = e;
        });
    }

    function deleteEmployee() {
        let empId = document.getElementById('deleteId').value;

        if (empId !== '') {
            makeXMLHttpRequestImpl(url + '/employees/' + empId, {}, {}, 'DELETE', r => {
                document.getElementById('result').innerText = r;
        }, e => {
                document.getElementById('result').innerText = e;
            });
        }
    }

    function makeXMLHttpRequestImpl(url, params, body, method, successCallback, errorCallback) {
        let queryParams = Object.keys(params).map(key => {
            return [key, params[key]].map(encodeURIComponent).join("=");
    }).join("&");

        url += queryParams.length > 0 ? '?' + queryParams : '';

        let xhr = new XMLHttpRequest();
        xhr.open(method, url, true);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.send(JSON.stringify(body));

        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    if (successCallback != null) {
                        successCallback(xhr.responseText);
                    }
                }
                else if (xhr.status >= 300) {
                    if (errorCallback != null) {
                        if (xhr.response !== '') {
                            errorCallback(JSON.parse(xhr.response).message);
                        }
                        else {
                            errorCallback(xhr.statusText);
                        }
                    }
                }
                else {
                    alert('Unknown error')
                }
            }
        };
    }
</script>

</html>
