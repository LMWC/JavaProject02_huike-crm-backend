<template>
  <div class="channel-charts">
    <!-- <div id="chartLeft" class="chart-left" style="height:100%;width:100%;" /> -->
    <!-- <div id="chartRight" class="chart-right" style="height:100%;" /> -->
    <div v-show="(leftChartData && leftChartData.length>0) || (rightChartData && rightChartData.length>0)" class="test">
      <div v-show="leftChartData && leftChartData.length>0" id="chartLeft" class="chart-left" style="height:100%;" />
      <div v-show="rightChartData && rightChartData.length>0" id="chartRight" class="chart-right" style="height:100%;" />
    </div>
    <empty v-show="(!leftChartData || leftChartData.length<=0) && (!rightChartData || rightChartData.length<=0)" style="height: 100%" class="table-empty-box" />
  </div>
</template>

<script>
import echarts from 'echarts'
// 渠道统计 活动统计
import { reportChanelStatistics, reportActivityStatistics } from '@/api/clues/countAnalysis.js'
import dayjs from 'dayjs'

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
      test: 12345,
      pieLeftChartData: [],
      pieRightChartData: [],
      leftData: [],
      rightData: [],
      leftChartData: [],
      rightChartData: [],
      pieLeftChart: null,
      pieRightChart: null
    }
  },
  computed: {
    pieLeftOption () {
      return {
        color: ['rgba(255,200,72,1)', 'rgba(5,204,149,1)', 'rgba(89,105,139,1)'],
        legend: {
          orient: 'horizontal',
          icon: 'circle',
          itemWidth: 8,
          itemHeight: 8,
          bottom: '40',
          // left: '10%',
          left: 'center',
          textStyle: {
            rich: {
              a: {
                verticalAlign: 'right',
                fontSize: 12,
                align: 'left',
                width: 100
              },
              b: {
                fontSize: 12,
                align: 'left',
                width: 50
              },
              c: {
                fontSize: 12,
                align: 'left',
                width: 50
              }
            }
          }
        },
        tooltip: {
          show: true,
          trigger: 'item',
          padding: [10, 30]
        },
        series: [
          {
            type: 'pie',
            labelLine: {
              normal: {
                show: false
              }
            },
            radius: [0, 140],
            center: ['50%', '45%'],
            // 半径模式
            // roseType: 'radius',
            stillShowZeroSum: false,
            avoidLabelOverlap: false,
            zlevel: 1,
            label: {
              normal: {
                backgroundColor: '#fff',
                padding: [20, 50],
                show: false,
                position: 'center',
                formatter: [
                  '{name|{b}}',
                  '{value|{c}}'
                ].join('\n'),
                rich: {
                  value: {
                    color: '#303133',
                    fontSize: 28,
                    fontWeight: 'bold',
                    lineHeight: 40
                  },
                  name: {
                    color: '#909399',
                    lineHeight: 20
                  },
                  test: {
                    color: 'red'
                  }
                }
              },
              emphasis: {
                // 关闭label展示
                show: false,
                textStyle: {
                  fontSize: '16',
                  fontWeight: 'bold'
                }
              }
            },
            data: this.pieLeftChartData ? this.pieLeftChartData : [0, 0, 0],
            itemStyle: {
              normal: {
                // 控制块间距
                borderWidth: 0,
                borderColor: '#ffffff'
              }
            }
          }
        ]
      }
    },
    pieRightOption () {
      return {
        color: ['rgba(5,204,149,1)', 'rgba(152,234,212,1)', 'rgba(89,105,139,1)', 'rgba(168,185,220,1)', 'rgba(253,112,101,1)', 'rgba(255,175,169,1)', 'rgba(69,154,252,1)', 'rgba(153,200,255,1)', 'rgba(255,197,62,1)'],
        title: {
          zlevel: 0,
          // 测试用法
          text: [
            '{name|成交客户' + '}',
            `{value|${this.test}}` + '个'
          ].join('\n'),
          rich: {
            value: {
              color: '#303133',
              fontSize: 12,
              fontWeight: 'bold',
              lineHeight: 40
            },
            name: {
              color: '#909399',
              lineHeight: 20
            }
          },
          top: '42%',
          // left: '29.8%',
          // right: '23%',
          left: '50%',
          textAlign: 'center',
          textStyle: {
            rich: {
              value: {
                color: '#303133',
                fontSize: 28,
                fontWeight: 'bold',
                lineHeight: 40
              },
              // 成交客户样式设置
              name: {
                color: '#909399',
                lineHeight: 20
              }
            }
          }
        },
        legend: {
          orient: 'horizontal',
          icon: 'circle',
          itemWidth: 8,
          itemHeight: 8,
          bottom: '40',
          // right: '0%',
          left: 'center',
          textStyle: {
            rich: {
              a: {
                verticalAlign: 'right',
                fontSize: 12,
                align: 'left',
                width: 100
              },
              b: {
                fontSize: 12,
                align: 'left',
                width: 50
              },
              c: {
                fontSize: 12,
                align: 'left',
                width: 50
              }
            }
          }
        },
        tooltip: {
          show: true,
          trigger: 'item',
          padding: [10, 30]
        },
        series: [
          {
            type: 'pie',
            labelLine: {
              normal: {
                show: false
              }
            },
            radius: [80, 120],
            center: ['50%', '50%'],
            // 半径模式
            // roseType: 'radius',
            stillShowZeroSum: false,
            avoidLabelOverlap: false,
            zlevel: 1,
            label: {
              normal: {
                backgroundColor: '#fff',
                padding: [20, 50],
                show: false,
                position: 'center',
                formatter: [
                  '{name|{b}}',
                  '{value|{c}}'
                ].join('\n'),
                rich: {
                  value: {
                    color: '#303133',
                    fontSize: 28,
                    fontWeight: 'bold',
                    lineHeight: 40
                  },
                  name: {
                    color: '#909399',
                    lineHeight: 20
                  },
                  test: {
                    color: 'red'
                  }
                }
              },
              emphasis: {
                // 关闭label展示
                show: false,
                textStyle: {
                  fontSize: '16',
                  fontWeight: 'bold'
                }
              }
            },
            data: this.pieRightChartData ? this.pieRightChartData : [0, 0, 0],
            itemStyle: {
              normal: {
                // 控制块间距
                borderWidth: 0,
                borderColor: '#ffffff'
              }
            }
          }
        ]
      }
    }
  },
  watch: {
    search () {
      this.$nextTick(() => {
        this.getPieLeftChart(this.search.dateDayArr)
        this.getPieRightChart(this.search.dateDayArr)
      })
    }
  },
  mounted () {
    this.getPieLeftChart('null')
    this.getPieRightChart('null')
  },
  methods: {
    getPieLeftChart (arr) {
      const params = {
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
      this.leftChartData = []
      reportChanelStatistics({ ...params }).then(res => {
        this.leftChartData = res
        console.log('-=-=-this.leftChartData-==--', this.leftChartData)
        if (res) {
          this.pieLeftChartData = res.length > 0 ? [...res] : this.leftData
          if (res.length > 0) {
            this.pieLeftChartData = this.pieLeftChartData.map((obj) => ({ ...obj, value: obj.num, name: obj.channel }))
          }
          this.$nextTick(() => {
            const domLeft = document.getElementById('chartLeft')
            this.pieLeftChart = echarts.init(domLeft)
            this.pieLeftChart.setOption(this.pieLeftOption, true)
            this.pieLeftChart.resize()
          })
          window.addEventListener('resize', () => {
            this.pieLeftChart.resize()
          })

          this.$once('hook:beforeDestroy', () => {
            window.removeEventListener('resize', () => {
              this.pieLeftChart.resize()
            })
          })
        }
      })
    },
    getPieRightChart (arr) {
      const params = {
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
      this.rightChartData = []
      reportActivityStatistics(params).then(res => {
        this.rightChartData = res
        console.log('-=-=-this.rightChartData-==--', this.rightChartData)
        if (res) {
          this.pieRightChartData = res.length > 0 ? [...res] : this.rightData
          if (res.length > 0) {
            this.pieRightChartData = this.pieRightChartData.map(obj => ({ ...obj, value: obj.num, name: obj.activity }))
          }
          this.test = this.pieRightChartData.length > 0 && this.pieRightChartData.map(num => (num.value)).reduce((prev, curr) => (prev + curr))
          this.$nextTick(() => {
            const dom = document.getElementById('chartRight')
            this.pieRightChart = echarts.init(dom)
            this.pieRightChart.setOption(this.pieRightOption, true)
            this.pieRightChart.resize()
          })

          window.addEventListener('resize', () => {
            this.pieRightChart.resize()
          })

          this.$once('hook:beforeDestory', () => {
            window.removeEventListener('resize', () => {
              this.pieRightChart.resize()
            })
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .channel-charts {
    width: 100%;
    height: 415px;
    .test {
      width:1000px;
      height:100%;
      margin:0 auto;
      display: flex;
      .chart-left, .chart-right {
        width: 500px;
      }
      .chart-right {
        // height: 200px;
      }
    }
  }
</style>
