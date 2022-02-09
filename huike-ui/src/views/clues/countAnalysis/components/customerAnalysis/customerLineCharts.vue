<template>
  <div class="customer">
    <!-- :style="{ height: showChartsIndex === '新增客户数' ? '350px' : '350px' }" -->
    <div id="customerLineChart" style="height: 100%" />
  </div>
</template>

<script>
import echarts from 'echarts'
// 客户统计
import { reportContractStatistics } from '@/api/clues/countAnalysis.js'
import dayjs from 'dayjs'
// import { getBigNum } from '@/utils/tool.js'

export default {
  props: {
    search: {
      type: Object,
      require: true,
      default: () => {}
    }
  },
  data () {
    return {
      showChartsIndex: '新增客户数',
      lineChartData: {
        dateData: [],
        addCustomerCount: [],
        customerAllCont: []
      }
    }
  },
  computed: {
    lineOption () {
      return {
        color: ['#05cc95', '#fd7065'],
        legend: {
          data: ['新增客户数量(个)', '客户总数量(个)'],
          icon: 'circle',
          bottom: '22',
          itemWidth: 6,
          itemHeight: 6,
          color: 'red'
        },
        tooltip: {
          trigger: 'axis',
          transitionDuration: 0
        },
        grid: {
          left: '48px',
          right: '50px',
          width: '94%',
          top: '40px',
          containLabel: true
        },
        xAxis: {
          // 配置数据从哪里开始
          boundaryGap: false,
          type: 'category',
          axisTick: {
            show: true
          },
          axisLabel: {
            color: '#666666',
            borderColor: '#dfdff0'
          },
          axisLine: {
            lineStyle: {
              color: '#cdcdcd'
            }
          },
          data: this.lineChartData.dateData
        },
        yAxis: [
          {
            // name: '新增客户数量(个)',
            type: 'value',
            position: 'left',
            min: 0,
            minInterval: 1,
            // max: getBigNum(this.lineChartData.addCustomerCount),
            axisTick: {
              show: false
            },
            // 网格线
            splitLine: {
              lineStyle: {
                type: 'dashed'
              },
              show: true
            },
            axisLabel: {
              color: '#666666'
            },
            axisLine: {
              lineStyle: {
                color: '#cdcdcd'
              }
            }
          },
          {
            // name: '客户总数量(个)',
            type: 'value',
            position: 'right',
            min: 0,
            minInterval: 1,
            // max: getBigNum(this.lineChartData.customerAllCont),
            axisTick: {
              show: false
            },
            // 网格线
            splitLine: {
              lineStyle: {
                type: 'dashed'
              },
              show: true
            },
            axisLabel: {
              color: '#666666'
            },
            axisLine: {
              lineStyle: {
                color: '#cdcdcd'
              }
            }
          }
        ],
        series: [
          {
            name: '新增客户数量(个)',
            data: this.lineChartData.addCustomerCount,
            type: 'line',
            smooth: true,
            symbol: 'circle',
            symbolSize: 6,
            itemStyle: {
              normal: {
                color: '#05cc95',
                borderColor: '#05cc95',
                lineStyle: {
                  color: '#05cc95'
                }
              },
              emphasis: {
                color: '#05cc95',
                borderColor: '#fff',
                borderWidth: 2
              }
            }
          },
          {
            name: '客户总数量(个)',
            data: this.lineChartData.customerAllCont,
            type: 'line',
            yAxisIndex: 1,
            smooth: true,
            symbol: 'circle',
            symbolSize: 6,
            itemStyle: {
              normal: {
                color: '#fd7065',
                borderColor: '#fd7065',
                lineStyle: {
                  color: '#fd7065'
                }
              },
              emphasis: {
                color: '#fd7065',
                borderColor: '#fff',
                borderWidth: 2
              }
            }
          }
        ]
      }
    }

  },
  watch: {
    search (value) {
      this.$nextTick(() => {
        this.getLineChart(this.search.dateDayArr)
      })
    },
    deep: true
  },
  mounted () {
    this.getLineChart('null')
  },
  methods: {
    getLineChart (arr) {
      let params = {
        beginCreateTime: '',
        endCreateTime: ''
      }
      if (arr === 'null') {
        params.beginCreateTime = dayjs().subtract(7, 'days').format('YYYY-MM-DD')
        params.endCreateTime = dayjs().format('YYYY-MM-DD')
      } else {
        params.beginCreateTime = arr[0]
        params.endCreateTime = arr[1]
      }
      reportContractStatistics({ ...params }).then(res => {
        if (res) {
          this.lineChartData.dateData = res.xAxis ? res.xAxis : []
          this.lineChartData.addCustomerCount = res.series ? res.series[0].data : []
          this.lineChartData.customerAllCont = res.series ? res.series[1].data : []
        }
        const dom = document.getElementById('customerLineChart')
        const myChart = echarts.init(dom)
        myChart.setOption(this.lineOption, true)
        window.addEventListener('resize', () => {
          myChart.resize()
        })
        this.$once('hook:beforeDestroy', () => {
          window.removeEventListener('resize', () => {
            myChart.resize()
          })
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .customer {
    width: 100%;
    height: 350px;
    #customerLineChart {
      width: 100%;
      height: 350px;
    }
    #customerPieChart {
      width: 100%;
      height: 370px;
    }
  }
</style>
