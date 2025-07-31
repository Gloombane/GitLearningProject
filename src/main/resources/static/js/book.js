function addBook(event) {
    event.preventDefault();

    const book = {
        bookName: document.getElementById("bookName").value,
        bookImage: document.getElementById("bookImage").value,
        authorName: document.getElementById("authorName").value,
        releaseDate: document.getElementById("releaseDate").value
    };

    fetch('/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(book)
    })
        .then(response => {
            if (response.ok) {
                alert('Книга успешно добавлена!');
                // window.location.href = '/books';
            } else {
                alert('Ошибка при добавлении книги.');
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
            alert('Произошла ошибка при добавлении.');
        });
}
