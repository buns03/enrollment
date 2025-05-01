const tbody = document.querySelector(".grades-table tbody");
const sectionDropdown = document.getElementById("sectionDropdown");
const subjectDropdown = document.getElementById("subjectDropdown");
const fetchBtn = document.querySelector(".fetch-btn");

// Fetch and render students
async function loadStudents(section = "", subject = "") {
  tbody.innerHTML = ""; // Clear previous rows

  const params = new URLSearchParams();
  if (section) params.append("section", section);
  if (subject) params.append("subjectCode", subject);

  const response = await fetch(`/api/grades/students?${params}`);
  const students = await response.json();

  students.forEach((student, index) => {
    const row = document.createElement("tr");

    row.innerHTML = `
      <td>${index + 1}</td>
      <td>${student.fullName}</td>
      <td>${student.section}</td>
      <td>${student.subject}</td>
      <td contenteditable="true"
          data-student="${student.studentNo}"
          data-subject="${student.subject}">
          ${student.grade}
      </td>
    `;

    tbody.appendChild(row);
  });

  // Enable saving grades
  document.querySelectorAll("td[contenteditable=true]").forEach(cell => {
    cell.addEventListener("blur", saveGrade);
    cell.addEventListener("keypress", function (e) {
      if (e.key === "Enter") {
        e.preventDefault();
        cell.blur();
      }
    });
  });
}

// Save updated grade
async function saveGrade(event) {
  const cell = event.target;
  const studentNo = cell.dataset.student;
  const subjectCode = cell.dataset.subject;
  const grade = cell.textContent.trim();

  if (!grade || grade === "--") return;

  await fetch("/api/grades/update-grade", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ studentNo, subjectCode, grade })
  });
}

// Triggered on FILTER button click
fetchBtn.addEventListener("click", () => {
  const section = sectionDropdown.value;
  const subject = subjectDropdown.value;
  loadStudents(section, subject);
});

// Load all students on initial page load
loadStudents();
