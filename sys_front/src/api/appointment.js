import api from "@/utils/api";
async function getDoctor() {
    return await api({
        url: '/staff/getDoctor',
        method: 'get'
    });
}
function getAppointmentByTime(sid,date) {
    return api({
        url: '/appointments/getAppoints',
        method: 'get',
        params: {
            sid,
            date
        }
    });
}
export { getDoctor, getAppointmentByTime }