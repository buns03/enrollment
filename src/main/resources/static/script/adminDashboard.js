document.addEventListener('DOMContentLoaded', function () {
    // Menu item click functionality
    const menuItems = document.querySelectorAll('.menu-item');



    menuItems.forEach(item => {
        item.addEventListener('click', function () {
            if (item.classList.contains('logout')) {
                alert('Logging out...');
                return;
            }

            menuItems.forEach(mi => mi.classList.remove('active'));
            item.classList.add('active');

            const sectionId = item.getAttribute('data-section');
            if (sectionId) {
                const sections = document.querySelectorAll('.content-section');
                sections.forEach(section => section.classList.remove('active'));
                document.getElementById(sectionId).classList.add('active');
            }
        });
    });

    // Simulated data for enrollment statistics
    const barHeights = [
        { year: 'FIRST YEAR', value: 500 },
        { year: 'SECOND YEAR', value: 1000 },
        { year: 'THIRD YEAR', value: 2000 },
        { year: 'FOURTH YEAR', value: 3000 }
    ];

    const maxValue = 4000;
    const bars = document.querySelectorAll('.bar');
    const yLabels = document.querySelectorAll('.y-label');

    // Tooltip element
    const tooltip = document.getElementById('tooltip');

    // Set Y-axis labels
    const yValues = [4000, 3000, 2000, 1000, 500];
    yLabels.forEach((label, index) => {
        label.textContent = yValues[index];
    });

    // Initialize bar height and tooltips
    bars.forEach(bar => bar.style.height = '0px');


    setTimeout(() => {
        bars.forEach((bar, index) => {
            if (index < barHeights.length) {
                const value = barHeights[index].value;
                const normalizedHeight = (value / maxValue) * 200;
                bar.style.height = normalizedHeight + 'px';

                bar.addEventListener('mousemove', (e) => {
                    const rect = bar.getBoundingClientRect();
                    tooltip.style.left = (rect.left + window.scrollX + 10) + 'px';
                    tooltip.style.top = (rect.top + window.scrollY - 30) + 'px';
                    tooltip.style.opacity = 10 ;
                    tooltip.textContent = `${value} students`;
                    console.log('hovering', value);
                });

                bar.addEventListener('mouseleave', () => {
                    tooltip.style.opacity = 0;
                });
            }
        });
    }, 300);

    // Current date for pending applications
    const currentDate = new Date();
    const formattedDate = `${currentDate.getMonth() + 1}/${currentDate.getDate()}/${currentDate.getFullYear()}`;
    document.querySelector('.pending .period').textContent = formattedDate;
});
let rows = 0; // Start with no rows initially.
let rowData = []; // Array to store row data

function createGrid() {
    const container = document.getElementById('gridInputs');
    container.innerHTML = ''; // Clear previous rows

    // Loop through the rows and create them
    for (let i = 0; i < rows; i++) {
        const rowDiv = document.createElement('div');
        rowDiv.className = 'input-row';
        rowDiv.style.display = 'flex';
        rowDiv.style.justifyContent = 'space-between'; // Keep fields and delete button aligned

        // Create Subject input
        const subjectInput = document.createElement('input');
        subjectInput.type = 'text';
        subjectInput.placeholder = 'Enter Subject';
        subjectInput.style.width = '60%'; // Set width to 60%
        subjectInput.value = rowData[i]?.subject || ''; // Populate with saved data
        rowDiv.appendChild(subjectInput);

        // Create Day (Monday, Tuesday, etc.) dropdown
        const daySelect = document.createElement('select');
        const daysOfWeek = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
        daySelect.style.width = '60%'; // Set width to 60%
        daysOfWeek.forEach(day => {
            const option = document.createElement('option');
            option.value = day;
            option.textContent = day;
            if (rowData[i]?.day === day) {
                option.selected = true; // Set the saved day as selected
            }
            daySelect.appendChild(option);
        });
        rowDiv.appendChild(daySelect);

        // Create Time input
        const timeInput = document.createElement('input');
        timeInput.type = 'time';
        timeInput.style.width = '60%'; // Set width to 60%
        timeInput.value = rowData[i]?.time || ''; // Populate with saved data
        rowDiv.appendChild(timeInput);

        // Create Delete button (always on the right)
        const deleteButton = document.createElement('button');
        deleteButton.className = 'delete-button';
        deleteButton.textContent = 'Delete';
        deleteButton.onclick = function () {
            deleteRow(rowDiv, i); // Pass the index to delete specific row data
        };
        rowDiv.appendChild(deleteButton);

        container.appendChild(rowDiv); // Append to container
    }
}

function addRow() {
    // Save the current row data before adding a new row
    const rowsData = [];
    const inputRows = document.querySelectorAll('.input-row');

    inputRows.forEach((row, index) => {
        const subjectInput = row.querySelector('input[type="text"]');
        const daySelect = row.querySelector('select');
        const timeInput = row.querySelector('input[type="time"]');

        rowsData.push({
            subject: subjectInput.value,
            day: daySelect.value,
            time: timeInput.value
        });
    });

    rowData = rowsData; // Store data before adding a row
    rows++; // Increase row count
    createGrid(); // Re-create grid with new row
}

function deleteRow(rowDiv, index) {
    rowDiv.remove(); // Removes the entire row
    rowData.splice(index, 1); // Remove data from the rowData array
    rows--; // Decrease row count
}

// Initialize grid when window loads
window.onload = createGrid;



