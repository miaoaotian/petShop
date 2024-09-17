function fetchAppointments() {
    const doctorId = document.getElementById('doctorSelect').value;
    if (doctorId === "0") {
        document.getElementById('timeSlots').innerHTML = '';
        return;
    }
    $.ajax({
        url: `/user/getAppointmentBySid`,
        method: 'GET',
        data: {
            sid: doctorId,
        },
        success: function(data) {
            console.log(data);
            displayAppointments(data);
        },
        error: function() {
            console.error('获取失败');
        }
    });

}
document.getElementById('bookAppointment').addEventListener('click', function() {
    const selectedAppointment = document.querySelector('.time-slot.selected');
    if (selectedAppointment) {
        const startTime = selectedAppointment.querySelector('h4').textContent.split(' - ')[0];
        const doctorId = document.getElementById('doctorSelect').value;
        console.log(startTime, doctorId);
        bookAppointment(startTime, doctorId);
    } else {
        alert('请选择一个预约');
    }
});
function displayAppointments(appointments) {
    const container = document.getElementById('timeSlots');
    container.innerHTML = '';

    if (!appointments.length) {
        container.innerHTML = '<p>No appointments available.</p>';
        return;
    }
    let selectedAppointment = null;

    const timeGroups = [appointments.slice(0, 4), appointments.slice(4, 8), appointments.slice(8, 12)];
    timeGroups.forEach((group, index) => {
        const groupContainer = document.createElement('div');
        groupContainer.className = 'time-group';
        groupContainer.style.display = 'flex';
        groupContainer.style.justifyContent = 'space-around';
        groupContainer.style.marginBottom = '20px';
        console.log(group);
        group.forEach(appointment => {
            const div = document.createElement('div');
            div.style.cursor = 'pointer';
            div.className = 'time-slot ' + (appointment.status ? 'booked' : 'available');
            div.style.fontSize = '15px';
            div.innerHTML = `<h4>${appointment.startTime} - ${appointment.endTime}</h4><p>${appointment.name || 'No appointment'}</p>`;

            div.addEventListener('click', function() {
                // 先移除所有选中状态
                document.querySelectorAll('.time-slot').forEach(slot => {
                    slot.classList.remove('selected');
                    slot.style.borderColor = 'transparent';
                    slot.style.borderWidth = '0px';
                });
                // 设置当前选中元素的样式
                div.classList.add('selected');
                div.style.borderColor = 'blue';
                div.style.borderWidth = '2px';
                div.style.borderStyle = 'solid';
            });

            groupContainer.appendChild(div);
        });
        container.appendChild(groupContainer);
    });


}

function bookAppointment(startTime, sid) {
    console.log('Booking for:', startTime, 'with SID:', sid);
    $.ajax({
        url: '/user/bookAppointment',
        method: 'POST',
        data: {
            date: startTime,
            sid: sid
        },
        success: function(response) {
            fetchAppointments();
        },
        error: function() {
            alert('预约失败');
        }
    });
}
