
const form = document.querySelector('form'); // Select the form element
form.addEventListener('submit', (event) => { // Add an event listener to the submit event
    event.preventDefault(); // Prevent the form from submitting by default

    const firstName = document.querySelector('#{{ form.firstName.vars.id }}').value;
    const lastName = document.querySelector('#{{ form.lastName.vars.id }}').value;
    const age = document.querySelector('#{{ form.age.vars.id }}').value;
    const bio = document.querySelector('#{{ form.bio.vars.id }}').value;

    let errors = []; // Create an array to store any validation errors

    // Validate the first name field
    if (firstName.trim() === '') {
        errors.push('Le prénom est obligatoire.'); // Add an error message to the errors array
    }

    // Validate the last name field
    if (lastName.trim() === '') {
        errors.push('Le nom est obligatoire.'); // Add an error message to the errors array
    }

    // Validate the age field
    if (age.trim() === '' || isNaN(age)) {
        errors.push('L\'âge doit être un nombre.'); // Add an error message to the errors array
    }

    // Validate the bio field
    if (bio.trim() === '') {
        errors.push('La bio est obligatoire.'); // Add an error message to the errors array
    }

    if (errors.length > 0) { // If there are any validation errors
        const errorList = document.createElement('ul'); // Create a new unordered list to display the error messages
        errors.forEach((error) => { // Loop through the errors array and add each error to the list
            const li = document.createElement('li');
            li.textContent = error;
            errorList.appendChild(li);
        });

        // Add the error list to the form
        const errorDiv = document.createElement('div');
        errorDiv.classList.add('alert', 'alert-danger');
        errorDiv.appendChild(errorList);
        form.prepend(errorDiv);
    } else { // If there are no validation errors, submit the form
        form.submit();
    }
});

