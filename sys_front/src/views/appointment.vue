<template>
    <div class="appointment-container">
        <div style="display:flex;align-items:center;gap:15px">
            <label>请选择医生：</label>
            <el-select v-model="selectedDoctor" placeholder="选择医生">
                <el-option v-for="doctor in doctors" :key="doctor.id" :label="doctor.name"
                    :value="doctor.id"></el-option>
            </el-select>
        </div>
        <el-calendar v-model="selectedDate" :range="dateRange" @input="fetchAppointments"></el-calendar>
        <div style="margin-top:-20px">
            <h3><span>{{ getDoctorName() }}</span>在{{ formatDate(selectedDate) }}预约数据</h3>
            <div class="labels">
                <div>
                    <span>未预约：</span>
                    <div class="green"></div>
                </div>
                <div>
                    <span>已预约：</span>
                    <div class="red"></div>
                </div>
            </div>
            <div class="timeList">
                <h4>上午</h4>
                <div class="" v-for="time in morningTimes" :key="time.time" :class="{ 'active': time.status, 'inactive': !time.status }">
                    <div v-if="time.status" class="fr">
                        <span>{{ displaytime(time.time) }}</span>
                        <span>预约人：{{ time.name }}</span>
                    </div>
                    <div v-else class="fr">
                        <span>{{ displaytime(time.time) }}</span>
                        <span>预约人：无</span>
                    </div>
                </div>
            </div>
            <div class="timeList">
                <h4>下午</h4>
                <div class="" v-for="time in afternoonTimes" :key="time.time" :class="{ 'active': time.status, 'inactive': !time.status }">
                    <div v-if="time.status" class="fr">
                        <span>{{ displaytime(time.time) }}</span>
                        <span>预约人：{{ time.name }}</span>
                    </div>
                    <div v-else class="fr">
                        <span>{{ displaytime(time.time) }}</span>
                        <span>预约人：无</span>
                    </div>
                </div>
            </div>
            <div class="timeList">
                <h4>晚上</h4>
                <div class="" v-for="time in eveningTimes" :key="time.time" :class="{ 'active': time.status, 'inactive': !time.status }">
                    <div v-if="time.status" class="fr">
                        <span>{{ displaytime(time.time) }}</span>
                        <span>预约人：{{ time.name }}</span>
                    </div>
                    <div v-else class="fr">
                        <span>{{ displaytime(time.time) }}</span>
                        <span>预约人：无</span>
                    </div>
                </div>
            </div>
            
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted,watch } from 'vue';
import { getDoctor, getAppointmentByTime } from '@/api/appointment';
import { msg } from '@/utils/msgUtil';
const doctors = ref([]);
onMounted(async () => {
    doctors.value = await getDoctor();
});
const selectedDate = ref(new Date());
const selectedDoctor = ref(null);
const timeList = ref([
    { time: '8:00', status: false, name: '' },
    { time: '9:00', status: false, name: '' },
    { time: '10:00', status: false, name: '' },
    { time: '11:00', status: false, name: '' },
    { time: '13:00', status: false, name: '' },
    { time: '14:00', status: false, name: '' },
    { time: '15:00', status: false, name: '' },
    { time: '16:00', status: false, name: '' },
    { time: '18:00', status: false, name: '' },
    { time: '19:00', status: false, name: '' },
    { time: '20:00', status: false, name: '' },
    { time: '21:00', status: false, name: '' }
]);
const morningTimes = computed(() => {
  return timeList.value.slice(0, 4); // 上午时间，假设是列表的前4个
});

const afternoonTimes = computed(() => {
  return timeList.value.slice(4, 8); // 下午时间，假设是列表的第5到第8个
});

const eveningTimes = computed(() => {
  return timeList.value.slice(8); // 晚上时间，假设是列表的最后3个
});
const dateRange = computed(() => {
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);
    const dayAfterTomorrow = new Date(today);
    dayAfterTomorrow.setDate(dayAfterTomorrow.getDate() + 2);
    return [today, dayAfterTomorrow];
});

async function fetchAppointments(date) {
    date = formatDate(date);
    const sid = selectedDoctor.value;
    if (sid == null) {
        msg('请选择医生', 'warning')
        return;
    }
    let res = getAppointmentByTime(sid, date);
    const result = await res;
    for(let i=0;i<result.length;i++){
        let hour = result[i].date.split(' ')[1].split(':')[0];
        hour = parseInt(hour);
        for(let j=0;j<timeList.value.length;j++){
            let h = timeList.value[j].time.split(':')[0];
            console.log(h);
            if(hour == h){
                timeList.value[j].status = true;
                timeList.value[j].name = result[i].name;
            }
        }
    }
}
//格式化
function formatDate(date) {
    date = date.toLocaleDateString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    });;
    date = date.replace(/\//g, '-');
    return date;
}
//根据id获取医生名字
function getDoctorName() {
    let id = selectedDoctor.value;
    if (id == null) {
        return '';
    }
    const doctor = doctors.value.find((item) => item.id === id);
    return doctor ? doctor.name : '未找到';
}
//显示时间
function displaytime(time) {
    let hour = time.split(':')[0];
    return `${hour}:00:00-${hour}:59:59`;
}
//初始化：
function reset() {
    timeList.value = [
        { time: '8:00', status: false, name: '' },
        { time: '9:00', status: false, name: '' },
        { time: '10:00', status: false, name: '' },
        { time: '11:00', status: false, name: '' },
        { time: '13:00', status: false, name: '' },
        { time: '14:00', status: false, name: '' },
        { time: '15:00', status: false, name: '' },
        { time: '16:00', status: false, name: '' },
        { time: '18:00', status: false, name: '' },
        { time: '19:00', status: false, name: '' },
        { time: '20:00', status: false, name: '' },
        { time: '21:00', status: false, name: '' }
    ]
    
}
//监听器，当日期或医生变化时，重新获取预约时间
watch([selectedDate, selectedDoctor], () => {
    reset();
    fetchAppointments(selectedDate.value);

});
</script>

<style scoped>
.appointment-container {
    max-width: 1000px;
    margin: auto;
    padding: 20px;
    padding-top: 0;
}

.el-select {
    width: 200px !important;
}

.labels {
    display: flex;
    max-width: 400px;
    margin: auto;
    justify-content: space-between;
    margin-top: -10px;
}

.labels div {
    display: flex;
    align-items: center;
}

.green {
    width: 30px;
    height: 10px;
    background-color: green;
    border-radius: 30%;
}

.red {
    width: 30px;
    height: 10px;
    background-color: red;
    border-radius: 30%;
}
.active {
    background-color: rgba(255, 0, 0, 0.4);
    width: 200px;
    height: 50px;
    border-radius: 25px;
}
.inactive {
    background-color: rgba(0, 128, 0, 0.4);
    width: 200px;
    height: 50px;
    border-radius: 25px;
}
.timeList {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    gap: 30px;
    font-size: 13px;
    margin-top: 20px;
}
.fr {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
</style>