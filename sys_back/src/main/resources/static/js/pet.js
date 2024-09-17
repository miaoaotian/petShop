function buyPet(petId,price) {
    $.post('/user/buy-pet', { petId: petId,price: price }, function(data) {
        alert('购买成功! ');
    }).fail(function() {
        alert('购买失败！');
    });
}
$(document).ready(function() {
    $('.pet').each(function() {
        var gender = $(this).find('.gender').text();
        switch(gender) {
            case '0':
                $(this).find('.gender').text('公');
                break;
            case '1':
                $(this).find('.gender').text('母');
                break;
            case '2':
                $(this).find('.gender').text('已绝育');
                break;
            default:
                $(this).find('.gender').text('未知');
        }
    });
});

