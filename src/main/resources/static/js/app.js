// ── Auto-refresh status indicators ────────────────────────────────────────
document.addEventListener('DOMContentLoaded', function () {

    // Ping /api/health every 30 seconds and update the status dot
    function checkHealth() {
        fetch('/api/health')
            .then(res => res.json())
            .then(data => {
                const dot = document.querySelector('.status-dot');
                if (data.status === 'UP') {
                    dot.className = 'status-dot green';
                } else {
                    dot.className = 'status-dot red';
                }
            })
            .catch(() => {
                const dot = document.querySelector('.status-dot');
                if (dot) dot.className = 'status-dot red';
            });
    }

    checkHealth();
    setInterval(checkHealth, 30000);

    // Highlight newly added rows briefly
    const rows = document.querySelectorAll('.table tbody tr');
    if (rows.length > 0) {
        const lastRow = rows[rows.length - 1];
        lastRow.style.transition = 'background 1s';
        lastRow.style.background = '#e8f5e9';
        setTimeout(() => { lastRow.style.background = ''; }, 2000);
    }

    console.log('Capstone DevOps App - UI loaded');
    console.log('REST API available at /api/students');
    console.log('Metrics available at /actuator/prometheus');
});
