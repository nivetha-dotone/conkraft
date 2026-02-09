var base_url = document.getElementById('base_url').value;
function backtolisting() {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_loginhistory';
}