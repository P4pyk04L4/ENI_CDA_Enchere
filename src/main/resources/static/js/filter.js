/*
console.log(" filter.js has been loaded!");

document.getElementById("reset-btn").addEventListener("click", function () {
    console.log("btn fonction youpi");
});
*/

/* filter sans "DOMContentLoaded"

console.log("debug filter.js has been loaded!");

function filterProducts() {
    const searchBar = document.getElementById("search-bar");
    const categoryFilter = document.getElementById("category-select");
    const products = document.querySelectorAll(".card");

    if (!searchBar || !categoryFilter) {
        console.error(" search-bar/category-select pas trouvés");
        return;
    }

    const searchText = searchBar.value.toLowerCase();
    const selectedCategory = categoryFilter.value.toLowerCase();

    products.forEach(product => {
        const name = product.querySelector(".card-title").textContent.toLowerCase();
        const category = product.getAttribute("data-category") ? product.getAttribute("data-category").toLowerCase() : "";

        const matchesName = name.includes(searchText);
        const matchesCategory = selectedCategory === "" || category === selectedCategory;

        if (matchesName && matchesCategory) {
            product.parentElement.style.display = "flex";
        } else {
            product.parentElement.style.display = "none";
        }
    });
}

// eventListner
const searchBar = document.getElementById("search-bar");
const categoryFilter = document.getElementById("category-select");
const resetBtn = document.getElementById("reset-btn");

if (searchBar) searchBar.addEventListener("input", filterProducts);
if (categoryFilter) categoryFilter.addEventListener("change", filterProducts);
if (resetBtn) {
    resetBtn.addEventListener("click", function () {
        console.log("debug reset cliqué");
        searchBar.value = "";
        categoryFilter.value = "";
        filterProducts();
    });
} else {
    console.error(" reset-btn pas trouvé！");
}

console.log("filter.js loaded");
*/



console.log("filter.js has been loaded!");

document.addEventListener("DOMContentLoaded", function () {
    const searchBar = document.getElementById("search-bar");
    const categoryFilter = document.getElementById("category-select");

    function filterProducts() {
        const searchText = searchBar.value.toLowerCase();
        const selectedCategory = categoryFilter.value.toLowerCase();
        const products = document.querySelectorAll(".card");

        products.forEach(product => {
            const name = product.querySelector(".card-title").textContent.toLowerCase();
            const category = product.getAttribute("data-category") ? product.getAttribute("data-category").toLowerCase() : "";

            const matchesName = name.includes(searchText);
            const matchesCategory = selectedCategory === "" || category === selectedCategory;

            if (matchesName && matchesCategory) {
                product.parentElement.style.display = "flex";
            } else {
                product.parentElement.style.display = "none";
            }
        });
    }

    searchBar.addEventListener("input", filterProducts);
    categoryFilter.addEventListener("change", filterProducts);

    // btn annuler
    document.getElementById("reset-btn").addEventListener("click", function () {
        console.log("debug reset cliqué"); // debug
        searchBar.value = "";
        categoryFilter.value = "";
        filterProducts();
    });
});
console.log("filter.js loaded");
