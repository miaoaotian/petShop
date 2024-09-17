import { ElMessage,ElMessageBox as ElmsgBox } from "element-plus";
function msg(msg,type) {
    if(type==='success'){
        ElMessage.success(msg)
    } else if(type==='error') {
        ElMessage.error(msg)
    } else if(type==='warning') {
        ElMessage.warning(msg)
    } else if(type==='info') {
        ElMessage.info(msg)
    }
}
function msgBox(msg, title) {
    return ElmsgBox.confirm(title,msg, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    });
}
export {
    msg,
    msgBox
};