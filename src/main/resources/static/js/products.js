function sortListings(sortType) {
  const queryParams = new URLSearchParams(window.location.search);
  queryParams.set("sort", sortType);

  const newUrl = window.location.pathname + "?" + queryParams.toString();
  window.location.href = newUrl;
}

function handleCheckboxClick(clickedCheckboxId) {
  const checkboxes = document.querySelectorAll('input[name="type"]');

  checkboxes.forEach((checkbox) => {
    if (checkbox.id !== clickedCheckboxId) {
      checkbox.checked = false;
    }
  });
}
