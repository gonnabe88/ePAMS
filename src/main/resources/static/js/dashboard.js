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
            colors: undefined,
            legend: {
                show: true,
                position: 'bottom',
                horizontalAlign: 'center',
                fontSize: '14px',
                fontFamily: 'Nanum Gothic',
                markers: {
                    size: 10, // 마커 크기를 설정
                    shape: 'circle', // 마커 모양을 원형으로 설정
                    fillColors: undefined, // 시리즈 색상을 자동으로 적용
                    strokeWidth: 0, // 마커의 외곽선 두께를 설정 (0으로 설정해 외곽선 없음)
                },
                itemMargin: {
                    horizontal: 5,
                    vertical: 0
                },
                onItemClick: {
                    toggleDataSeries: true
                },
                onItemHover: {
                    highlightDataSeries: true
                }
            },
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
            colors: undefined,
            legend: {
                show: true,
                position: 'bottom',
                horizontalAlign: 'center',
                fontSize: '14px',
                fontFamily: 'Nanum Gothic',
                markers: {
                    size: 10, // 마커 크기를 설정
                    shape: 'circle', // 마커 모양을 원형으로 설정
                    fillColors: undefined, // 시리즈 색상을 자동으로 적용
                    strokeWidth: 0, // 마커의 외곽선 두께를 설정 (0으로 설정해 외곽선 없음)
                },
                itemMargin: {
                    horizontal: 5,
                    vertical: 0
                },
                onItemClick: {
                    toggleDataSeries: true
                },
                onItemHover: {
                    highlightDataSeries: true
                }
            },
            tooltip: {
                enabled: true
            },
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
            legend: {
                show: true,
                position: 'bottom',
                horizontalAlign: 'center',
                fontSize: '14px',
                fontFamily: 'Nanum Gothic',
                markers: {
                    size: 10, // 마커 크기를 설정
                    shape: 'circle', // 마커 모양을 원형으로 설정
                    fillColors: undefined, // 시리즈 색상을 자동으로 적용
                    strokeWidth: 0, // 마커의 외곽선 두께를 설정 (0으로 설정해 외곽선 없음)
                },
                itemMargin: {
                    horizontal: 5,
                    vertical: 0
                },
                onItemClick: {
                    toggleDataSeries: true
                },
                onItemHover: {
                    highlightDataSeries: true
                }
            },
            dataLabels: {
                enabled: true,
            },
            tooltip: {
                enabled: true
            },
            dataLabels: {
                enabled: true
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
                type: 'pie',
            },
            labels: labels,
            colors: undefined, // ApexCharts가 자동으로 색상을 지정
            legend: {
                show: true,
                position: 'bottom',
                horizontalAlign: 'center',
                fontSize: '14px',
                fontFamily: 'Nanum Gothic',
                markers: {
                    size: 10, // 마커 크기를 설정
                    shape: 'circle', // 마커 모양을 원형으로 설정
                    fillColors: undefined, // 시리즈 색상을 자동으로 적용
                    strokeWidth: 0, // 마커의 외곽선 두께를 설정 (0으로 설정해 외곽선 없음)
                },
                itemMargin: {
                    horizontal: 5,
                    vertical: 0
                },
                onItemClick: {
                    toggleDataSeries: true
                },
                onItemHover: {
                    highlightDataSeries: true
                }
            },
            dataLabels: {
                enabled: true,
            },
            tooltip: {
                enabled: true
            }
        };

        let chart = new ApexCharts(document.querySelector("#login-type-chart"), options);
        chart.render();
    });



});
