import api from "@/utils/api";
async function getMemberList(pageNo = 1, pageSize = 10, params = {}) {
    if (params.joinDate) {
        const date = new Date(params.joinDate);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        params.joinDate = `${year}-${month}-${day}`;
      }
    let resp = await api({
        url: '/members',
        method: 'get',
        params: {
            pageNo,
            pageSize,
            ...params
        }
    })
    return resp;
}
function addMember(data) {
    return api({
        url: '/members',
        method: 'post',
        data
    })
}
function exitMember(data) {
    console.log(data)
    return api({
        url: '/members',
        method: 'put',
        data
    })
}
function delMember(ids) {
    return api({
        url: '/members',
        method: 'delete',
        data: ids
    })
}
export {
    getMemberList,
    addMember,
    exitMember,
    delMember
}