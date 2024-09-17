import api from "@/utils/api";
async function getRecordList(pageNo = 1, pageSize = 10, pars = {}) {
    let resp = await api({
        url: '/record',
        method: 'get',
        params: {
            pageNo,
            pageSize,
            ...pars
        }
    })
    return resp;
}

function delRecords(ids) {
    return api({
        url: '/record',
        method: 'delete',
        data: ids
    })
}

export {
    getRecordList,
    delRecords
}