document.getElementById("logoutBtn").addEventListener("click", async () => {
    try {
        const response = await fetch("/logout", {
            method: "POST",
            credentials: "include" // чтобы отправить куку
        });

        if (response.ok) {
            alert("Вы вышли из системы");
            window.location.href = "/showLogin"; // или на страницу логина
        } else {
            alert("Ошибка при выходе");
        }
    } catch (error) {
        console.error("Logout failed:", error);
    }
});
