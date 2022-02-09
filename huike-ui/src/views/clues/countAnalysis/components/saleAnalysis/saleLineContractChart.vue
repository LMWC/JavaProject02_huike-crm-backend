<template>
  <div class="sale">
    <div id="saleLineContractChart" style="height: 100%" />
  </div>
</template>

<script>
import echarts from 'echarts'

export default {
  // props: {
  //   showChartsIndex: {
  //     type: String,
  //     required: true,
  //     default: '合同数量'
  //   }
  // },
  data () {
    return {
      lineContractData: {
        dateData: [],
        addCustomerCount: [],
        customerAllCont: []
      }
    }
  },
  computed: {
    getLineOption () {
      return {
        color: ['#05cc95', '#fd7065'],
        legend: {
          data: ['新增客户数量(个)', '客户总数量(万个)'],
          icon: 'circle',
          bottom: '22',
          itemWidth: 6,
          itemHeight: 6,
          color: 'red'
        },
        tooltip: {
          trigger: 'axis'
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
          data: this.lineContractData.dateData
        },
        yAxis: [
          {
            // name: '新增客户数量(个)',
            type: 'value',
            position: 'left',
            min: 0,
            max: 10000,
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
            data: this.lineContractData.addCustomerCount,
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
            name: '客户总数量(万个)',
            data: this.lineContractData.customerAllCont,
            type: 'line',
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
  mounted () {
    this.getContractChart()
  },
  methods: {
    getContractChart () {
      const dom = document.getElementById('saleLineContractChart')
      const myChart = echarts.init(dom)
      myChart.setOption(this.getLineOption, true)
      window.addEventListener('resize', () => {
        myChart.resize()
      })
      this.$once('hook:beforeDestroy', () => {
        window.removeEventListener('resize', () => {
          myChart.resize()
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .sale {
    width: 100%;
    height: 350px;
    #saleLineContractChart {
      width: 100%;
      height: 350px;
    }
  }
</style>
