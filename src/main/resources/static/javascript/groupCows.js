$(document).ready(function() {

    const labelCowCount = vacaModelCountByRasa.map(labelCowCount => labelCowCount[0]);
    const dataCowsCount = vacaModelCountByRasa.map(dataCowsCount => dataCowsCount[1]);

    console.log(labelCowCount);
    console.log(dataCowsCount);

    const polarAreaChart = document.getElementById('polarArea').getContext('2d');
    var dou {
        type: 'polarArea',
        data: {
            labels: labelCowCount,
            datasets: [{
                label: '# of Votes',
                data: dataCowsCount,
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    })
});