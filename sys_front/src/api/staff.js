import api from "@/utils/api";
async function getStaffList(pageNo = 1, pageSize = 10, params = {}) {
    let resp = await api({
        url: '/staff',
        method: 'get',
        params: {
            pageNo,
            pageSize,
            ...params
        }
    })
    return resp;
}
function addStaff(data) {
    return api({
        url: '/staff',
        method: 'post',
        data
    })
}
function exitStaff(data) {
    console.log(data)
    return api({
        url: '/staff',
        method: 'put',
        data
    })
}
function delStaffs(ids) {
    return api({
        url: '/staff',
        method: 'delete',
        data: ids
    })
}
export {
    getStaffList,
    addStaff,
    exitStaff,
    delStaffs
}