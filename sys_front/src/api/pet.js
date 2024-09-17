import api from "@/utils/api";
async function getPetList(pageNo = 1, pageSize = 10, params = {}) {
    let resp = await api({
        url: '/pet',
        method: 'get',
        params: {
            pageNo,
            pageSize,
            ...params
        }
    })
    return resp;
}
function addPet(data) {
    return api({
        url: '/pet',
        method: 'post',
        data
    })
}
function exitPet(data) {
    console.log(data)
    return api({
        url: '/pet',
        method: 'put',
        data
    })
}
function delPets(ids) {
    return api({
        url: '/pet',
        method: 'delete',
        data: ids
    })
}
export {
    getPetList,
    addPet,
    exitPet,
    delPets
}