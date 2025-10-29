function validateForm() {
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    // Если одно из полей пустое
    if (!email || !password) {
        document.getElementById('errorMessage').style.display = 'block';
        return false;
    }

    // Если всё заполнено — отправляем AJAX
    loginUser(email, password);
    return false; // Отменяем обычную отправку формы
}

function loginUser(email, password) {
    var formData = new FormData();
    formData.append("email", email);
    formData.append("password", password);

    fetch('/login', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/test';
            } else {
                response.text().then(text => {
                    document.getElementById('errorMessage').innerText = text;
                    document.getElementById('errorMessage').style.display = 'block';
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('errorMessage').innerText = "Произошла ошибка";
            document.getElementById('errorMessage').style.display = 'block';
        });
}
