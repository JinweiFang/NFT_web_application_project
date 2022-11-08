let editAccountByAdminForm = document.getElementById('editAccountFormContainer');

if(editAccountByAdminForm != null) {
    editAccountByAdminForm.addEventListener('show.bs.modal', function (event) {
        // Button that triggered the modal
        let button = event.relatedTarget;
        // Extract info from data-bs-* attributes
        let uid = button.getAttribute('data-bs-uid');

        let data = document.getElementById(`user-${uid}`);
        let form = document.getElementById("editAccountForm");

        form.querySelector("#inputId2").value = uid;
        form.querySelector("#inputfName2").value = data.querySelector("#user-fname").textContent;
        form.querySelector("#inputlName2").value = data.querySelector("#user-lname").textContent;
        form.querySelector("#inputEmail2").value = data.querySelector("#user-email").textContent;
        form.querySelector("#inputAccountType2").value = data.querySelector("#user-isadmin").textContent == "Yes" ? 1 : 0;
        form.querySelector("#inputUsername2").value = data.querySelector("#user-username").textContent;
        form.querySelector("#inputSecurityAnswer21").value = data.querySelector("#user-secAns1").textContent;
        form.querySelector("#inputSecurityAnswer22").value = data.querySelector("#user-secAns2").textContent;
        form.querySelector("#inputSecurityAnswer23").value = data.querySelector("#user-secAns3").textContent;
    });
}

