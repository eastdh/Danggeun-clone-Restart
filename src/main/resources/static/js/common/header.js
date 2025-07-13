
document.addEventListener('DOMContentLoaded', function () {


    const btn = document.getElementById('userProfileBtn');
    const dropdown = document.getElementById('profileDropdown');

    btn.addEventListener('click', function (e) {
        e.stopPropagation();
        dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
    });

    document.addEventListener('click', function (e) {
        if (!dropdown.contains(e.target) && e.target !== btn) {
            dropdown.style.display = 'none';
        }
    });
});
