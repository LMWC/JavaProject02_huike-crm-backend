<template>
  <div class="clue-funnel-chart">
    <h2>线索转化漏斗</h2>
    <div class="chart" style="height: 360px;">
      <div id="clueFunnelChart" style="height: 100%;" />
      <!-- 做两条线遮盖漏斗图缺陷 -->
      <!-- <div class="line-div">
        <p id="line-left" class="line-left" />
        <p id="line-right" class="line-right" />
      </div> -->
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import dayjs from 'dayjs'
import { reportGetVulnerabilityMapChart } from '@/api/index/indexHome.js'
// import { treeselect } from '@/api/system/dept'

export default {
  props: {
    funnelData: {
      type: Array,
      require: true,
      default: () => []
    },
    searchData: {
      type: Object,
      require: true,
      default: () => {}
    }
  },
  data () {
    return {
      funnelChartData: [...this.funnelData]
    }
  },
  computed: {
    funnelOption () {
      // const _this = this
      return {
        color: ['rgba(89,105,139,1)', 'rgba(5,204,149,1)', 'rgba(255,200,72,1)', 'rgba(253,112,101,1)'],
        title: {
          left: 'center'
        },
        tooltip: {
          show: true,
          formatter: (params) => {
            let str = `${params.data.name}：${params.data.value}`
            if (params.data.name !== '线索总数') {
              str += `<br/>转化率：${params.data.rate}`
            }
            return str
          }
        },
        series: [
          {
            name: '漏斗图',
            type: 'funnel',
            width: '35%',
            height: '70%',
            left: '38%',
            top: '15%',
            minSize: '30%',
            funnelAlign: 'center',
            avoidLabelOverlap: false,
            label: {
              position: 'outside',
              show: true,
              // position: function () {
              //   // 'outside'
              //   for (let i = 0; i < _this.funnelChartData.length; i++) {
              //     if (_this.funnelChartData[i].name === '转化客户') {
              //       return 'inside'
              //     } else {
              //       return 'outside'
              //     }
              //   }
              // }(),
              // 有用
              formatter: (params) => {
                const arr = ['{b|' + params.data.name + '}' + '{c|' + params.data.value + '}']
                // return arr.join('\n')
                if (params.data.name === '线索总数') {
                  return ''
                } else {
                  const arr = ['{b|' + params.data.name + '}' + '{c|' + params.data.value + '}']
                  return arr.join('\n')
                }
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
                  padding: 10
                }
              }
            },
            labelLine: {
              show: true,
              // show: function () {
              //   for (let i = 0; i < _this.funnelChartData.length; i++) {
              //     console.log(_this.funnelChartData[i].name)
              //     if (_this.funnelChartData[i].name !== '转化客户') {
              //       console.log(1)
              //       return true
              //     } else {
              //       console.log(2)
              //       return false
              //     }
              //   }
              //   // const test = _this.funnelChartData.map((obj) => {
              //   //   console.log(obj)
              //   //   if (obj.name !== '转化客户') {
              //   //     console.log(1111)
              //   //     return true
              //   //   } else {
              //   //     console.log(2222)
              //   //     return false
              //   //   }
              //   // })
              //   // console.log(test)
              // }(),
              position: 'bottom',
              length: 10,
              lineStyle: {
                // 此处的color颜色要加深一些，不然hover会看不到
                color: '#ffffff',
                // color: 'red',
                fontSize: '12px',
                fontWeight: 400
              }
            },
            itemStyle: {
              borderWidth: 0
            },
            data: this.funnelChartData
          },
          {
            name: '漏斗图',
            type: 'funnel',
            width: '35%',
            height: '70%',
            left: '38%',
            top: '15%',
            minSize: '30%',
            funnelAlign: 'center',
            label: {
              position: 'left',
              // 有用
              formatter: (params) => {
                if (params.data.name === '线索总数') {
                  return ''
                } else {
                  // const data = this.funnelChartData
                  // let total = 0
                  // let target
                  // for (let i = 1; i < data.length; i++) {
                  //   if (data[i].name === params.data.name) {
                  //     target = data[i].value
                  //   }
                  // }
                  // console.log('params', params)
                  // const arr = ['{a|' + params.data.name + '转化率' + '}{c|' + ((target / total) * 100).toFixed(2) + '%}']
                  const arr = ['{a|' + params.data.rateName + '}{c|' + params.data.rate + '}']
                  return arr.join('\n')
                }
              },
              rich: {
                a: {
                  color: '#ccc',
                  fontSize: 12
                },
                c: {
                  color: 'rgba(0,0,0,0.85)',
                  fontSize: 12,
                  padding: 5,
                  width: 46
                }
              }
            },
            labelLine: {
              show: true,
              position: 'bottom',
              length: 10,
              opacity: this.funnelChartData[0].name === '线索总数' ? 0 : 1,
              lineStyle: {
                // 此处的color颜色要加深一些，不然hover会看不到
                // color: '#D1D1D1',
                color: '#ffffff',
                fontSize: '12px',
                fontWeight: 400
              }
            },
            itemStyle: {
              borderWidth: 0
            },
            data: this.funnelChartData
          },
          // 添加一个为了遮盖 转化客户
          {
            name: '漏斗图',
            type: 'funnel',
            width: '35%',
            height: '70%',
            left: '38%',
            top: '15%',
            minSize: '30%',
            funnelAlign: 'center',
            itemStyle: {
              borderWidth: 0
            },
            label: {
              show: true,
              position: 'inside',
              // 有用
              formatter: (params) => {
                if (params.data.name === '线索总数') {
                  return params.data.name + params.data.value
                } else {
                  return ''
                }
              },
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
            data: this.funnelChartData
          }

        ]
      }
    }
  },
  mounted () {
    this.getFunnelChart('null')
  },
  methods: {
    getFunnelChart (arr) {
      let params = {}
      if (arr === 'null') {
        params = {
          beginCreateTime: dayjs().subtract(30, 'days').format('YYYY-MM-DD'),
          endCreateTime: dayjs().format('YYYY-MM-DD')
        }
      } else {
        params = {
          beginCreateTime: arr.dateDayArr[0],
          endCreateTime: arr.dateDayArr[1]
        }
      }
      reportGetVulnerabilityMapChart(params).then(res => {
        if (res && res.data) {
          this.cluesNums = res.data.cluesNums
          const list = []
          // for (const type in res.data) {
          //   const obj = {}
          //   // obj[type] = res.data[type]
          //   obj.value = res.data[type]
          //   switch (type) {
          //     case 'cluesNums':
          //       obj.name = '线索总数'
          //       obj.rate = '100%'
          //       break
          //     case 'effectiveCluesNums':
          //       obj.name = '有效线索'
          //       obj.rate = res.data.effectiveCluesNums === 0 ? '0.00%' : ((res.data.effectiveCluesNums / res.data.cluesNums) * 100).toFixed(2) + '%'
          //       console.log('res.data.effectiveCluesNums', res.data.effectiveCluesNums)
          //       console.log('res.data.cluesNums', res.data.cluesNums)
          //       break
          //     case 'businessNums':
          //       obj.name = '转化商机'
          //       obj.rate = res.data.businessNums === 0 ? '0.00%' : ((res.data.businessNums / res.data.effectiveCluesNums) * 100).toFixed(2) + '%'
          //       break
          //     case 'contractNums':
          //       obj.name = '转化客户'
          //       obj.rate = res.data.contractNums === 0 ? '0.00%' : ((res.data.contractNums / res.data.businessNums) * 100).toFixed(2) + '%'
          //       break
          //     default:
          //       return ''
          //   }
          //   list.push(obj)
          // }
          list.push({
            name: '线索总数',
            value: res.data.cluesNums,
            rate: '100.00%',
            rateName: '线索总数'
          })
          list.push({
            name: '有效线索数',
            value: res.data.effectiveCluesNums,
            rate: (res.data.effectiveCluesNums === 0 || res.data.cluesNums === 0) ? '0.00%' : ((res.data.effectiveCluesNums / res.data.cluesNums) * 100).toFixed(2) + '%',
            rateName: '有效线索转化率'
          })
          list.push({
            name: '商机数',
            value: res.data.businessNums,
            rate: (res.data.businessNums === 0 || res.data.effectiveCluesNums === 0) ? '0.00%' : ((res.data.businessNums / res.data.effectiveCluesNums) * 100).toFixed(2) + '%',
            rateName: '商机转化率'
          })
          list.push({
            name: '客户数',
            value: res.data.contractNums,
            rate: (res.data.contractNums === 0 || res.data.businessNums === 0) ? '0.00%' : ((res.data.contractNums / res.data.businessNums) * 100).toFixed(2) + '%',
            rateName: '客户转化率'
          })
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
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .clue-funnel-chart {
    // height: 362px;
    flex: 1;
    // height: 100%;
    margin-left: 20px;
    box-shadow: 0px 3px 7px 0px rgba(234,234,234,0.33);
    h2 {
      margin: 0;
      margin: 41px 0 20px 0;
      font-size: 14px;
      font-family: PingFangSC, PingFangSC-Medium;
      font-weight: 550;
      text-align: left;
      color: #333333;
      padding-left: 34px;
      box-sizing: border-box;
    }
    .chart {
      border-radius: 6px;
      background-color: #fff;
      // height: 364px;
      position: relative;
    }

    // 做两条线遮盖漏斗图缺陷
    .line-div {
      .line-left {
        width: 60px;
        height: 1px;
        background-color: #fff;
        position: absolute;
        top: 19.1%;
        left: 32%;
      }
      .line-right {
        width: 60px;
        height: 1px;
        background-color: #fff;
        position: absolute;
        top: 19.1%;
        right: 25.8%;
      }
    }
    @media  (max-width: 1850px) {
      .line-div {
        .line-left {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          left: 31.8%;
        }
        .line-right {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          right: 25.5%;
        }
      }
    }
    @media  (max-width: 1800px) {
      .line-div {
        .line-left {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          left: 31.5%;
        }
        .line-right {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          right: 25.2%;
        }
      }
    }
    @media  (max-width: 1750px) {
      .line-div {
        .line-left {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          left: 31.2%;
        }
        .line-right {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          right: 24.9%;
        }
      }
    }
    @media  (max-width: 1700px) {
      .line-div {
        .line-left {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          left: 30.9%;
        }
        .line-right {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          right: 24.6%;
        }
      }
    }
    @media  (max-width: 1650px) {
      .line-div {
        .line-left {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          left: 30.6%;
        }
        .line-right {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          right: 24.3%;
        }
      }
    }
    @media  (max-width: 1600px) {
      .line-div {
        .line-left {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          left: 30.3%;
        }
        .line-right {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          right: 24.0%;
        }
      }
    }
    @media  (max-width: 1550px) {
      .line-div {
        .line-left {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          left: 30.0%;
        }
        .line-right {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          right: 23.7%;
        }
      }
    }
    @media  (max-width: 1500px) {
      .line-div {
        .line-left {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          left: 29.7%;
        }
        .line-right {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          right: 23.4%;
        }
      }
    }
    @media  (max-width: 1450px) {
      .line-div {
        .line-left {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          left: 29.4%;
        }
        .line-right {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          right: 23.1%;
        }
      }
    }
    @media  (max-width: 1400px) {
      .line-div {
        .line-left {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          left: 29.1%;
        }
        .line-right {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          right: 22.8%;
        }
      }
    }
    @media  (max-width: 1366px) {
      .line-div {
        .line-left {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          left: 28.8%;
        }
        .line-right {
          width: 60px;
          height: 1px;
          background-color: #fff;
          position: absolute;
          top: 19.1%;
          right: 22.5%;
        }
      }
    }
  }
</style>
