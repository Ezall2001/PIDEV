$(document).ready(function() {
    // Lorsqu'un utilisateur clique sur le bouton d'upvote
    $('.vote-up').click(function(event) {
        event.preventDefault();
        var answerId = $(this).data('answer-id');
        var userId = $(this).data('user-id');
        var voteType = 1;

        // Envoyer une requête AJAX pour enregistrer le vote
        $.ajax({
            type: 'POST',
            url: '/vote',
            data: {
                answerId: answerId,
                userId: userId,
                voteType: voteType
            },
            success: function(data) {
                // Mettre à jour l'apparence des boutons
                if (data == 'upvoted') {
                    $('.vote-up[data-answer-id=' + answerId + ']').addClass('active');
                    $('.vote-down[data-answer-id=' + answerId + ']').removeClass('active');
                } else {
                    $('.vote-up[data-answer-id=' + answerId + ']').removeClass('active');
                    $('.vote-down[data-answer-id=' + answerId + ']').removeClass('active');
                }
            }
        });
    });

    // Lorsqu'un utilisateur clique sur le bouton de downvote
    $('.vote-down').click(function(event) {
        event.preventDefault();
        var answerId = $(this).data('answer-id');
        var userId = $(this).data('user-id');
        var voteType = -1;

        // Envoyer une requête AJAX pour enregistrer le vote
        $.ajax({
            type: 'POST',
            url: '/vote',
            data: {
                answerId: answerId,
                userId: userId,
                voteType: voteType
            },
            success: function(data) {
                // Mettre à jour l'apparence des boutons
                if (data == 'downvoted') {
                    $('.vote-down[data-answer-id=' + answerId + ']').addClass('active');
                    $('.vote-up[data-answer-id=' + answerId + ']').removeClass('active');
                } else {
                    $('.vote-up[data-answer-id=' + answerId + ']').removeClass('active');
                    $('.vote-down[data-answer-id=' + answerId + ']').removeClass('active');
                }
            }
        });
    });
});