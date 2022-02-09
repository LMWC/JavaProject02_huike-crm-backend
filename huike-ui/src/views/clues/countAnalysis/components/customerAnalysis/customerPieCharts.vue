<template>
  <div v-loading="dataListLoading" class="customer-pie">
    <!-- :style="{ height: showChartsIndex === '新增客户数' ? '350px' : '415px' }" -->
    <div v-if="dataList && dataList.length>0" id="customerPieChart" style="height: 100%" />
    <empty v-if="(!dataList || dataList.length<=0) && !dataListLoading" style="height: 100%" class="table-empty-box" />
  </div>
</template>

<script>
import echarts from 'echarts'
import { reportSubjectStatistics } from '@/api/clues/countAnalysis.js'
import dayjs from 'dayjs'

export default {
  // props: {
  //   showChartsIndex: {
  //     type: String,
  //     required: true,
  //     defalut: '学科客户分布'
  //   }
  // },
  props: {
    search: {
      type: Object,
      require: true,
      default: () => {}
    }
  },
  data () {
    return {
      showChartsIndex: '学科客户分布',
      test: 0,
      pieChartData: [],
      demoData: [],
      dataList: [],
      myPieChart: null,
      dataListLoading: false
    }
  },
  computed: {
    pieOption () {
      return {
        color: ['rgba(5,204,149,1)', 'rgba(152,234,212,1)', 'rgba(89,105,139,1)', 'rgba(168,185,220,1)', 'rgba(253,112,101,1)', 'rgba(255,175,169,1)', 'rgba(69,154,252,1)', 'rgba(153,200,255,1)', 'rgba(255,197,62,1)'],
        title: {
          zlevel: 0,
          // text: [
          //   '{name|成交客户' + '}',
          //   '{value|总金额}'
          // ].join('\n'),
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
          top: '37%',
          left: '39.8%',
          // center: ['40%', '45%'],
          textAlign: 'center',
          textStyle: {
            rich: {
              value: {
                color: '#303133',
                // color: 'red',
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
          orient: 'vertical',
          icon: 'circle',
          itemWidth: 8,
          itemHeight: 8,
          top: 'center',
          left: '55%',
          formatter: (name) => {
            const data = this.pieChartData
            let total = 0
            let target
            for (let i = 0; i < data.length; i++) {
              total += data[i].value
              if (data[i].name === name) {
                target = data[i].value
              }
            }
            // const arr = ['{a|' + name + '}{b|' + target + '}{c|' + ((target / total) * 100).toFixed(2) + '%}']
            const arr = ['{a|' + name + '}{c|' + ((target / total) * 100).toFixed(2) + '%}']
            return arr.join('\n')
          },
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
          // formatter: '{b} <br/> {d}%'
        },
        series: [
          {
            type: 'pie',
            labelLine: {
              normal: {
                show: false
              }
            },
            radius: [90, 160],
            center: ['40%', '45%'],
            // 半径模式
            // roseType: 'radius',
            // avoidLabelOverlap: false,
            // silent: true,
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
            data: this.pieChartData ? this.pieChartData : [0, 0, 0],
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
    search (value) {
      this.$nextTick(() => {
        this.getPieChart(this.search.dateDayArr)
      })
    }
  },
  mounted () {
    this.getPieChart('null')
  },
  beforeDestroy () {
    window.removeEventListener('resize', () => {
      this.myPieChart.resize()
    })
  },
  methods: {
    getPieChart (arr) {
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
      this.dataListLoading = true
      reportSubjectStatistics(params).then(res => {
        this.dataList = res
        this.dataListLoading = false
        if (res && res.length > 0) {
          this.pieChartData = res.length > 0 ? [...res] : this.demoData
          if (res.length > 0) {
            this.pieChartData = this.pieChartData.map(obj => ({ ...obj, value: obj.num, name: obj.subject }))
          }
          this.test = this.pieChartData.map(num => (num.value)).reduce((prev, curr) => (prev + curr))
          this.$nextTick(() => {
            const dom = document.getElementById('customerPieChart')
            this.myPieChart = echarts.init(dom)
            this.myPieChart.setOption(this.pieOption, true)
            this.myPieChart.resize()
          })

          window.addEventListener('resize', () => {
            this.myPieChart.resize()
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .customer-pie {
    width: 100%;
    height: 415px;
    #customerPieChart {
      width: 100%;
      height: 370px;
    }
  }
</style>
