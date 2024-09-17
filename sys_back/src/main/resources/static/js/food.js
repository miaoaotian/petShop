function buyFood(id,price) {
    const nums = $('#numsInput').val();
    console.log(nums);
    $.post('/user/buy-food',{id,nums,price},function (data) {
        alert('购买成功');
    }).fail(function() {
        alert('购买失败！');
    });
}