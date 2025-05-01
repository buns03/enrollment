document.addEventListener("DOMContentLoaded", function() {
        const fetchButton = document.querySelector('.fetch');
        const evaluateButton = document.getElementById('evaluateBtn');
        const failedSubjectsList = document.getElementById('failed-subjects-list');
        
        // Add click event listener to the fetch button
        fetchButton.addEventListener('click', function(event) {
            event.preventDefault(); // Prevent form submission for now

            // Gather the input data
            const schoolId = document.getElementById('schoolId').value;
            const lastName = document.getElementById('LName').value;
            const firstName = document.getElementById('FName').value;
            const course = document.getElementById('Course').value;

            // Simulate a fetch operation (replace this with actual logic)
            simulateFetchData(schoolId, lastName, firstName, course)
                .then((data) => {
                    // If fetch is successful, enable the 'Evaluate Grades' button
                    evaluateButton.disabled = false;
                    console.log('Data fetched successfully:', data);
                })
                .catch((error) => {
                    // If there's an error, the button remains disabled
                    evaluateButton.disabled = true;
                    console.log('Error fetching data:', error);
                });
        });

        // Simulate a fetch data function (replace this with your actual fetch logic)
        function simulateFetchData(schoolId, lastName, firstName, course) {
            return new Promise((resolve, reject) => {
                // Simulate a delay like an API call (e.g., setTimeout)
                setTimeout(() => {
                    // Simulate success or failure (replace with actual check)
                    const success = true; // Assume the data is fetched successfully
                    if (success) {
                        // Simulated data: if fetching is successful, return failed subjects data
                        const failedSubjects = ["Math 101", "History 202"]; // Example failed subjects
                        resolve(failedSubjects);
                    } else {
                        reject('No matching data found');
                    }
                }, 2000); // Simulate 2 seconds delay
            });
        }

        // Add click event listener to the evaluate button
        evaluateButton.addEventListener('click', function() {
            // Fetch the failed subjects list
            const failedSubjects = ["Math 101", "History 202"]; // Replace with fetched data

            // Display failed subjects in the output section
            if (failedSubjects.length > 0) {
                failedSubjectsList.innerHTML = failedSubjects.map(subject => `<p>${subject}</p>`).join('');
            } else {
                failedSubjectsList.innerHTML = '<p>No failed subjects.</p>';
            }
        });
    });