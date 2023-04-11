$(document).ready(function() {
    $('#editAnswerModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var answerId = button.data('answer-id'); // Extract info from data-* attributes
        var modal = $(this);

        // Removed from inside the show.bs.modal function
        $('#submitEditAnswerForm').on('click', function(event) {
            // Send a POST request to the editAnswerModal function with the form data
            $.ajax({
                type: 'POST',
                url: "{{ path('editAnswerModal', {'id': answerId}) }}",
                data: $('#editAnswerModal form').serialize(),
                success: function(data) {
                    // Update the answer content in the page
                    $('#answer-' + answerId + ' .card-text').html(data.message);
                    // Hide the modal
                    modal.modal('hide');
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert('An error occurred while saving the answer.');
                }
            });
        });
    });

    // Added function to remove the previous click event
    $('#editAnswerModal').on('hidden.bs.modal', function(event) {
        $('#submitEditAnswerForm').off('click');
    });
});