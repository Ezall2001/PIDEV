$(function() {
    $('.emoji-picker').emojiPicker({
        width: '300px',
        height: '200px'
    });

    $('.emoji-picker-toggle').click(function() {
        $('.emoji-picker').toggle();
    });

    $('.emoji').click(function() {
        var emoji = $(this).attr('alt');
        var input = $('.js-emoji-picker');
        input.val(input.val() + emoji);
    });
});