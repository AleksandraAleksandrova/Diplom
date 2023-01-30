function createChart(elementId, expenses, name) {
    var ctx = document.getElementById(elementId).getContext('2d');
    ctx.canvas.width = 400;
    ctx.canvas.height = 400;
    var labels = Object.keys(expenses);
    var values = Object.values(expenses);
    var chart = new Chart(ctx, {
        type: 'pie',
        data: {
        labels: labels,
        datasets: [{
        data: values,
        backgroundColor: [
                 'rgba(255, 99, 132, 0.2)',
                 'rgba(54, 162, 235, 0.2)',
                 'rgba(255, 206, 86, 0.2)',
                 'rgba(75, 192, 192, 0.2)',
                 'rgba(153, 102, 255, 0.2)',
                 'rgba(255, 159, 64, 0.2)'
             ],
             borderColor: [
                 'rgba(255, 99, 132, 1)',
                 'rgba(54, 162, 235, 1)',
                 'rgba(255, 206, 86, 1)',
                 'rgba(75, 192, 192, 1)',
                 'rgba(153, 102, 255, 1)',
                 'rgba(255, 159, 64, 1)'
             ],
             borderWidth: 1
             }]
        },
        options: {
             title: {
                 display: true,
                 text: name
                 },
             responsive: true,
             maintainAspectRatio: false,
             legend: {
                 position: 'bottom'
             }
        }
    });
}
