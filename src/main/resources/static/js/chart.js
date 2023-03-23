let ctx = document.getElementById('myChart').getContext('2d');
let labels = [document.querySelector('.chart-name1').innerHTML, 
document.querySelector('.chart-name2').innerHTML, 
document.querySelector('.chart-name3').innerHTML,
document.querySelector('.chart-name4').innerHTML,
document.querySelector('.chart-name5').innerHTML];
let colorHex = ['#EDD2F3', '#FFFCDC', '#84DFFF','#516BEB','#FFACAC'];
ctx.width = 200;
ctx.height = 200;

let myChart = new Chart(ctx, {
  type: 'pie',
  data: {
    datasets: [{
      data: [document.querySelector('.agencyCount').innerHTML, 
      document.querySelector('.universityCount').innerHTML, 
      document.querySelector('.directRecruitCount').innerHTML, 
      document.querySelector('.trainingCenterCount').innerHTML, 
      document.querySelector('.othersCount').innerHTML],
      backgroundColor: colorHex
    }],
    labels: labels
  },
  options: {
    responsive: true,
    legend: {
      position: 'left'
    },
    plugins: {
      datalabels: {
        color: '#fff',
        anchor: 'end',
        align: 'start',
        offset: -10,
        borderWidth: 2,
        borderColor: '#fff',
        borderRadius: 25,
        backgroundColor: (context) => {
          return context.dataset.backgroundColor;
        },
        font: {
          weight: 'bold',
          size: '20'
        },
        formatter: (value) => {
          return value + ' %';
        }
      }
    }
  }
})
