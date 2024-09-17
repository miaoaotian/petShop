import api from "@/utils/api";
async function getFoodList(pageNo = 1, pageSize = 10, params = {}) {
    if (params.expiryDate) {
        const date = new Date(params.expiryDate);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        params.expiryDate = `${year}-${month}-${day}`;
      }
    let resp = await api({
        url: '/food',
        method: 'get',
        params: {
            pageNo,
            pageSize,
            ...params
        }
    })
    return resp;
}
function addFood(data) {
    return api({
        url: '/food',
        method: 'post',
        data
    })
}
function exitFood(data) {
    console.log(data)
    return api({
        url: '/food',
        method: 'put',
        data
    })
}
function delFoods(ids) {
    return api({
        url: '/food',
        method: 'delete',
        data: ids
    })
}
export {
    getFoodList,
    addFood,
    exitFood,
    delFoods
}