$(document).ready(function () {
    // 로그인 성공/실패 이력 데이터를 가져와서 그래프를 그리기
    $.getJSON('/api/admin/loginUserCount', function (data) {
        let categories = data.map(function (item) {
            return item.createdDate;
        });
        let successData = data.map(function (item) {
            return item.successCount;
        });
        let failureData = data.map(function (item) {
            return item.failureCount;
        });

        let loginData = { categories: categories, successData: successData, failureData: failureData };

        let options = {
            series: [
                {
                    name: "성공",
                    data: loginData.successData
                },
                {
                    name: "실패",
                    data: loginData.failureData
                }
            ],
            chart: {
                id: 'loginUser-chart',
                type: 'line',
                height: 350,
                dropShadow: {
                    enabled: true,
                    color: '#000',
                    top: 18,
                    left: 7,
                    blur: 10,
                    opacity: 0.2
                },
                toolbar: {
                    show: false
                }
            },
            colors: ['#2d8cda', '#c96363'],
            dataLabels: {
                enabled: true,
            },
            stroke: {
                curve: 'smooth'
            },
            markers: {
                size: 1
            },
            xaxis: {
                categories: loginData.categories,
                title: {
                    text: '날짜'
                }
            },
            yaxis: {
                title: {
                    text: '건수'
                }
            }
        };

        let chart = new ApexCharts(document.querySelector('#loginUser-chart'), options);
        chart.render();
    });

    // 로그인 성공/실패 이력 데이터를 가져와서 그래프를 그리기
    $.getJSON('/api/admin/logincount', function (data) {
        let categories = data.map(function (item) {
            return item.createdDate;
        });
        let successData = data.map(function (item) {
            return item.successCount;
        });
        let failureData = data.map(function (item) {
            return item.failureCount;
        });

        let loginData = { categories: categories, successData: successData, failureData: failureData };

        let options = {
            series: [
                {
                    name: "성공",
                    data: loginData.successData
                },
                {
                    name: "실패",
                    data: loginData.failureData
                }
            ],
            chart: {
                id: 'login-chart',
                type: 'line',
                height: 350,
                dropShadow: {
                    enabled: true,
                    color: '#000',
                    top: 18,
                    left: 7,
                    blur: 10,
                    opacity: 0.2
                },
                toolbar: {
                    show: false
                }
            },
            colors: ['#2d8cda', '#c96363'],
            dataLabels: {
                enabled: true,
            },
            stroke: {
                curve: 'smooth'
            },
            markers: {
                size: 1
            },
            xaxis: {
                categories: loginData.categories,
                title: {
                    text: '날짜'
                }
            },
            yaxis: {
                title: {
                    text: '건수'
                }
            }
        };

        let chart = new ApexCharts(document.querySelector('#login-chart'), options);
        chart.render();
    });

    // View 페이지 호출 건수 데이터를 가져와서 그래프를 그리기
    $.getJSON('/api/admin/viewcount', function (data) {
        let categories = data.map(function (item) {
            return item.createdDate;
        });
        let totalData = data.map(function (item) {
            return item.totalCount;
        });

        let viewLogData = { categories: categories, totalData: totalData };

        let options = {
            series: [
                {
                    name: "페이지뷰",
                    data: viewLogData.totalData
                }
            ],
            chart: {
                id: 'viewlog-chart',
                type: 'line',
                height: 350,
                dropShadow: {
                    enabled: true,
                    color: '#000',
                    top: 18,
                    left: 7,
                    blur: 10,
                    opacity: 0.2
                },
                toolbar: {
                    show: false
                }
            },
            colors: ['#2d8cda'],
            dataLabels: {
                enabled: true,
            },
            stroke: {
                curve: 'smooth'
            },
            markers: {
                size: 1
            },
            xaxis: {
                categories: viewLogData.categories,
                title: {
                    text: '날짜'
                }
            },
            yaxis: {
                title: {
                    text: '건수'
                }
            }
        };

        let chart = new ApexCharts(document.querySelector('#viewlog-chart'), options);
        chart.render();
    });

    // 로그인 종류별 성공 건수 데이터를 가져와서 파이 차트를 그리기
    $.getJSON('/api/admin/logintype', function (data) {
        let labels = data.map(function (item) {
            return item.category;
        });
        let series = data.map(function (item) {
            return item.totalCount;
        });

        let options = {
            series: series,
            chart: {
                id: 'login-type-chart',
                width: 380,
                type: 'pie',
            },
            labels: labels,
            responsive: [{
                breakpoint: 480,
                options: {
                    chart: {
                        width: 200
                    },
                    legend: {
                        position: 'bottom'
                    }
                }
            }]
        };

        let chart = new ApexCharts(document.querySelector("#login-type-chart"), options);
        chart.render();
    });
});
