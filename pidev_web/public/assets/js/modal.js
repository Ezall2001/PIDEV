$(document).ready(function() {
    $('a[data-bs-toggle="modal"]').on('click', function() {
        var url = $(this).attr('href');
        $.ajax({
            url: url,
            type: 'GET',
            success: function(html) {
                $('#editQuestionModal').html(html);
                $('#editQuestionModal').modal('show');
                $('.question-form').on('submit', function(e) {
                    e.preventDefault();
                    var formData = $(this).serialize();
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: formData,
                        success: function(response) {
                            // Code à exécuter en cas de succès
                        },
                        error: function(xhr) {
                            // Affichage des erreurs de contrôle de saisie
                            var errors = xhr.responseJSON.errors;
                            $.each(errors, function(key, value) {
                                $('#' + key).addClass('is-invalid');
                                $('#' + key + '-feedback').html(value[0]);
                            });
                        }
                    });
                });
            }
        });
        return false;
    });
});