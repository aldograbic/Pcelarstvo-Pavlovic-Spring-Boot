function toggleTheme(themeToggleDarkIcon, themeToggleLightIcon) {
  if (
    localStorage.getItem("color-theme") === "dark" ||
    (!localStorage.getItem("color-theme") &&
      window.matchMedia("(prefers-color-scheme: dark)").matches)
  ) {
    themeToggleLightIcon.classList.add("hidden");
    themeToggleDarkIcon.classList.remove("hidden");
  } else {
    themeToggleLightIcon.classList.remove("hidden");
    themeToggleDarkIcon.classList.add("hidden");
  }
}

function setupToggle(buttonId, darkIconId, lightIconId) {
  var themeToggleDarkIcon = document.getElementById(darkIconId);
  var themeToggleLightIcon = document.getElementById(lightIconId);

  toggleTheme(themeToggleDarkIcon, themeToggleLightIcon);

  var themeToggleBtn = document.getElementById(buttonId);

  themeToggleBtn.addEventListener("click", function () {
    themeToggleDarkIcon.classList.toggle("hidden");
    themeToggleLightIcon.classList.toggle("hidden");

    if (localStorage.getItem("color-theme")) {
      if (localStorage.getItem("color-theme") === "light") {
        document.documentElement.classList.add("dark");
        localStorage.setItem("color-theme", "dark");
      } else {
        document.documentElement.classList.remove("dark");
        localStorage.setItem("color-theme", "light");
      }
    } else {
      if (document.documentElement.classList.contains("dark")) {
        document.documentElement.classList.remove("dark");
        localStorage.setItem("color-theme", "light");
      } else {
        document.documentElement.classList.add("dark");
        localStorage.setItem("color-theme", "dark");
      }
    }
  });
}

setupToggle(
  "theme-toggle1",
  "theme-toggle-dark-icon1",
  "theme-toggle-light-icon1"
);

setupToggle(
  "theme-toggle2",
  "theme-toggle-dark-icon2",
  "theme-toggle-light-icon2"
);
