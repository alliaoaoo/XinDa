function setstatus(status) {
    sessionStorage.setItem("status", status);
}
function selectname() {
    var word = document.getElementById('word').value;
    sessionStorage.setItem("word", word);
    window.location.href="/e-commerce_select.html";

}