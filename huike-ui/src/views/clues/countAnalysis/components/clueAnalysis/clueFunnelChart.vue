<template>
  <div class="clue-funnel">
    <div id="clueFunnelChart" style="height: 100%;" />
  </div>
</template>

<script>
import echarts from 'echarts'
import { reportGetVulnerabilityMapChart } from '@/api/clues/countAnalysis.js'
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
      funnelChartData: [
        { value: 0, name: '线索总数' },
        { value: 0, name: '有效线索' },
        { value: 0, name: '转化商机' },
        { value: 0, name: '转化客户' }
      ]
    }
  },
  computed: {
    funnelOption () {
      return {
        color: ['rgba(89,105,139,1)', 'rgba(5,204,149,1)', 'rgba(255,200,72,1)', 'rgba(253,112,101,1)'],
        title: {
          left: 'center'
        },
        tooltip: {
          show: false
          // trigger: 'item'
          // formatter: '{b} : {c}%'
        },
        series: [
          {
            name: '漏斗图',
            type: 'funnel',
            width: '34%',
            height: '78%',
            left: 'center',
            top: '8%',
            minSize: '30%',
            funnelAlign: 'center',
            label: {
              position: 'outside',
              formatter: (params) => {
                const arr = ['{b|' + params.data.name + '}' + '{c|' + params.data.value + '}']
                return arr.join('\n')
              },
              rich: {
                b: {
                  color: '#ccc',
                  fontSize: 12,
                  width: 52
                },
                c: {
                  color: 'rgba(0,0,0,0.85)',
                  fontSize: 12,
                  padding: 2
                }
              }
            },
            labelLine: {
              show: true,
              position: 'bottom',
              length: 100,
              lineStyle: {
                // 此处的color颜色要加深一些，不然hover会看不到
                // color: '#e9e9eb',
                color: '#D1D1D1',
                fontSize: '12px',
                fontWeight: 400
              }
            },
            itemStyle: {
              borderWidth: 0
              // barBorderRadius: [50, 50, 50, 50]
            },
            data: this.funnelChartData
          },
          {
            name: '漏斗图',
            type: 'funnel',
            width: '34%',
            height: '78%',
            left: 'center',
            top: '8%',
            minSize: '30%',
            funnelAlign: 'center',
            label: {
              position: 'left',
              formatter: (params) => {
                const data = this.funnelChartData
                let total = 0
                let target
                for (let i = 0; i < data.length; i++) {
                  total += data[i].value
                  if (data[i].name === params.data.name) {
                    target = data[i].value
                  }
                }
                const rate = (target === 0 || total === 0) ? '0.00' : ((target / total) * 100).toFixed(2)
                const arr = ['{a|' + params.data.name + '转化率' + '}{c|' + rate + '%}']
                return arr.join('\n')
              },
              rich: {
                a: {
                  color: '#ccc',
                  fontSize: 12
                },
                c: {
                  color: 'rgba(0,0,0,0.85)',
                  fontSize: 12,
                  padding: 2,
                  width: 46
                }
              }
            },
            labelLine: {
              show: true,
              position: 'bottom',
              length: 100,
              lineStyle: {
                // 此处的color颜色要加深一些，不然hover会看不到
                // color: '#e9e9eb',
                color: '#D1D1D1',
                fontSize: '12px',
                fontWeight: 400
              }
            },
            itemStyle: {
              borderWidth: 0
            },
            data: this.funnelChartData
          },
          // 添加一个为了遮盖
          {
            name: '漏斗图',
            type: 'funnel',
            width: '34%',
            height: '78%',
            left: 'center',
            top: '8%',
            minSize: '30%',
            funnelAlign: 'center',
            itemStyle: {
              borderWidth: 0
            },
            label: {
              show: false,
              position: 'inside',
              formatter: '',
              rich: {
                b: {
                  color: '#ccc',
                  fontSize: 12
                },
                c: {
                  color: 'rgba(0,0,0,0.85)',
                  fontSize: 12,
                  padding: 2
                }
              }
            },
            labelLine: {
              show: false,
              position: 'bottom'
            },
            data: this.funnelChartData
          }
        ]
      }
    }
  },
  watch: {
    search () {
      this.$nextTick(() => {
        this.getFunnelChart(this.search.dateDayArr)
      })
    },
    deep: true
  },
  mounted () {
    // TODO
    this.getFunnelChart('null')
  },
  methods: {
    getFunnelChart (arr) {
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
      reportGetVulnerabilityMapChart(params).then(res => {
        if (res) {
          // console.log(res.data)
          if (res && res.data) {
            const list = []
            for (const type in res.data) {
              const obj = {}
              // obj[type] = res.data[type]
              obj.value = res.data[type]
              switch (type) {
                case 'cluesNums':
                  obj.name = '线索总数'
                  break
                case 'effectiveCluesNums':
                  obj.name = '有效线索'
                  break
                case 'businessNums':
                  obj.name = '转化商机'
                  break
                case 'contractNums':
                  obj.name = '转化客户'
                  break
                default:
                  return ''
              }
              list.push(obj)
            }
            this.funnelChartData = list
          }
          const dom = document.getElementById('clueFunnelChart')
          const myFunnelChart = echarts.init(dom)
          myFunnelChart.setOption(this.funnelOption, true)
          window.addEventListener('resize', () => {
            myFunnelChart.resize()
          })

          this.$once('hook:beforeDestory', () => {
            window.removeEventListener('resize', () => {
              myFunnelChart.resize()
            })
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .clue-funnel {
    width: 100%;
    height: 415px;
    #clueFunnelChart {
      width: 100%;
      height: 415px;
    }
  }
</style>
