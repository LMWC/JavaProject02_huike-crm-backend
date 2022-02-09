<template>
  <!-- 此页面为系统首页
  备注：首页、统计分析模块代码注释，原因：原型变动，注释掉的代码为暂不做的需求，请悉知 -->
  <div class="index-home">
    <!-- 顶部搜索项 -->
    <searchTitle :department-options="departmentOptions" @sendQueryParams="getQueryParams" />
    <!-- 基础数据、今日简报、待办事项 -->
    <div class="show-data">
      <!-- 基础数据 -->
      <div v-hasPermi="['indexHome:baseQuery']" class="base-data">
        <h2 class="word-title">基础数据</h2>
        <div class="card">
          <div class="demo">
            <div class="content-left">
              <p>线索数量</p>
              <h4>{{ formData.cluesNum }}</h4>
            </div>
            <div class="content-right">
              <img src="@/assets/index_home/clue.png" alt="">
            </div>
          </div>
          <div class="demo">
            <div class="content-left">
              <p>商机数量</p>
              <h4>{{ formData.businessNum }}</h4>
            </div>
            <div class="content-right">
              <img src="@/assets/index_home/opport.png" alt="">
            </div>
          </div>
          <div class="demo">
            <div class="content-left">
              <p>客户数量</p>
              <h4>{{ formData.contractNum }}</h4>
            </div>
            <div class="content-right">
              <img src="@/assets/index_home/customer.png" alt="">
            </div>
          </div>
          <div class="demo">
            <div class="content-left">
              <p>销售金额</p>
              <h4>{{ formData.salesAmount }}</h4>
            </div>
            <div class="content-right">
              <img src="@/assets/index_home/sale.png" alt="">
            </div>
          </div>
        </div>
      </div>
      <!-- 今日简报 -->
      <div v-hasPermi="['indexHome:todayQuery']" class="today-report">
        <h2 class="word-title">今日简报</h2>
        <div class="card">
          <div class="demo">
            <div class="content-left">
              <p>今日新增线索</p>
              <h4>{{ formData.todayCluesNum }}</h4>
            </div>
            <div class="content-right">
              <img src="@/assets/index_home/today_clue.png" alt="">
            </div>
          </div>
          <div class="demo">
            <div class="content-left">
              <p>今日新增商机</p>
              <h4>{{ formData.todayBusinessNum }}</h4>
            </div>
            <div class="content-right">
              <img src="@/assets/index_home/today_opport.png" alt="">
            </div>
          </div>
          <div class="demo">
            <div class="content-left">
              <p>今日新增客户</p>
              <h4>{{ formData.todayContractNum }}</h4>
            </div>
            <div class="content-right">
              <img src="@/assets/index_home/today_customer.png" alt="">
            </div>
          </div>
          <div class="demo">
            <div class="content-left">
              <p>今日销售额</p>
              <h4>{{ formData.todaySalesAmount }}</h4>
            </div>
            <div class="content-right">
              <img src="@/assets/index_home/today_sale.png" alt="">
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 漏斗图 -->
    <div class="funnel-sale">
      <!-- 代办事项迁移至此 -->
      <!-- 待办事项 -->
      <div v-hasPermi="['indexHome:todoQuery']" class="todo-matter">
        <h2 class="word-title" style="padding: 0; padding: 37px 34px 20px 34px;">待办事项</h2>
        <div class="card">
          <!-- 上部 -->
          <div class="top">
            <!-- <div class="demo" @click="() => $router.push('/transferManage')">
              <div class="content-left">
                <p>转派</p>
                <h4>{{ formData.transferNum }}</h4>
              </div>
              <div class="content-right">
                <img src="@/assets/index_home/transfer.png" alt="">
              </div>
            </div> -->
            <div class="demo" style="cursor: pointer;" @click="() => $router.push('/clue')">
              <div class="content-left">
                <p>待跟进线索</p>
                <h4>{{ formData.tofollowedCluesNum }}</h4>
              </div>
              <div class="content-right">
                <img src="@/assets/index_home/follow-up_clue.png" alt="">
              </div>
            </div>
          </div>
          <!-- 中间 -->
          <div class="center">
            <div class="demo" style="cursor: pointer;" @click="() => $router.push('/business')">
              <div class="content-left">
                <p>待跟进商机</p>
                <h4>{{ formData.tofollowedBusinessNum }}</h4>
              </div>
              <div class="content-right">
                <img src="@/assets/index_home/follow-up_opport.png" alt="">
              </div>
            </div>
            <div class="demo left-demo" style="cursor: pointer;" @click="() => $router.push('/clue')">
              <div class="content-left">
                <p>待分配线索</p>
                <h4>{{ formData.toallocatedCluesNum }}</h4>
              </div>
              <div class="content-right">
                <img src="@/assets/index_home/follow-up_assign_clue.png" alt="">
              </div>
            </div>
          </div>
          <!-- 底部 -->
          <div class="bottom">
            <div class="demo" style="cursor: pointer;" @click="$router.push('/business')">
              <div class="content-left">
                <p>待分配商机</p>
                <h4>{{ formData.toallocatedBusinessNum }}</h4>
              </div>
              <div class="content-right">
                <img src="@/assets/index_home/follow-up_money.png" alt="">
              </div>
            </div>
          </div>

        </div>
      </div>
      <div v-hasPermi="['indexHome:funnelQuery']" class="chart">
        <clueFunnelChart ref="funnelChart" :funnel-data="funnelData" />
      </div>
    </div>
    <!-- ui新设计变动2021-04-28 -->
    <!-- 销售排行榜和商机转化排行榜并排展示 -->
    <div class="sale-business-RankingList">
      <div v-hasPermi="['indexHome:saleQuery']" class="left">
        <!-- 销售排行榜 -->
        <saleRankingList :sale-data="saleData" />
      </div>
      <!-- 商机转化排行榜 -->
      <div v-hasPermi="['indexHome:businessQuery']" class="right">
        <businessRankingList :chennl-data="chennlData" />
      </div>
    </div>
  </div>
</template>

<script>
import searchTitle from './indexHome/searchTitle.vue'
import clueFunnelChart from './indexHome/clueFunnelChart.vue'
import saleRankingList from './indexHome/saleRankingList.vue'
import businessRankingList from './indexHome/businessRankingList.vue'
// 基础数据 商机转换龙虎榜 销售龙虎榜
import { indexBase, getBusinessChangeStatistics, getSalesStatistic } from '@/api/index/indexHome.js'
import dayjs from 'dayjs'
import { treeselect } from '@/api/system/dept'

export default {
  components: {
    searchTitle,
    clueFunnelChart,
    saleRankingList,
    businessRankingList
  },
  filters: {},
  data () {
    return {
      // 部门
      departmentOptions: [],
      formData: {},
      // 分发搜索值
      searchData: {
        beginCreateTime: dayjs().subtract(30, 'days').format('YYYY-MM-DD'),
        endCreateTime: dayjs().format('YYYY-MM-DD'),
        deptId: null
      },
      funnelData: [],
      // 商机转换龙虎榜数据
      chennlData: [],
      // 销售龙虎榜数据
      saleData: []
    }
  },
  computed: {

  },
  watch: {

  },
  created () {
    /** 查询部门下拉树结构 */
    treeselect().then(response => {
      this.departmentOptions = response.data
      console.log('this.departmentOptions', this.departmentOptions)
      // 获取基础数据
      this.getBaseData('null')
      // 商机转换龙虎榜
      this.getBusinessChangeStatistics('null')
      // 销售龙虎榜
      this.getSalesStatistic('null')
      // 漏斗图
      // this.getFunnelData('null')
    })
  },
  mounted () {
  },
  methods: {
    // 获取基础数据接口
    getBaseData (data) {
      const params = {
        ...this.searchData,
        deptId: data === 'null' ? this.departmentOptions[0]?.id : data.deptId
      }
      indexBase(params).then(res => {
        if (res && res.code === 200) {
          this.formData = res.data
        }
      })
    },
    // 商机转换龙虎榜
    getBusinessChangeStatistics (data) {
      const params = {
        ...this.searchData,
        deptId: data === 'null' ? this.departmentOptions[0]?.id : data.deptId
      }
      getBusinessChangeStatistics(params).then(res => {
        if (res) {
          this.chennlData = res.data.map((obj, index) => ({ ...obj, chennlId: index + 1 }))
        }
      })
    },
    // 销售龙虎榜
    getSalesStatistic (data) {
      const params = {
        ...this.searchData,
        deptId: data === 'null' ? this.departmentOptions[0]?.id : data.deptId
      }
      getSalesStatistic(params).then(res => {
        if (res) {
          this.saleData = res.data.map((obj, index) => ({ ...obj, saleId: index + 1 }))
        }
      })
    },
    // 搜索点击
    getQueryParams (data) {
      this.searchData.beginCreateTime = data.dateDayArr[0]
      this.searchData.endCreateTime = data.dateDayArr[1]
      this.searchData.deptId = data.deptId
      this.getBaseData(data)
      this.getBusinessChangeStatistics(data)
      this.getSalesStatistic(data)
      this.$refs.funnelChart.getFunnelChart(data)
      console.log(data)
    }
  }
}
</script>
<style lang="scss" scoped>
  .index-home {
    width: 100%;
    height: 100%;
    background-color: #f9f9f9;
    margin-bottom: 40px;
    .show-data {
      width: 100%;
      .base-data, .today-report {
        width: 100%;
        // background-color: #f9f9f9;
        .word-title {
          padding: 20px 34px;
          box-sizing: border-box;
          margin: 0;
          width: 100%;
          font-size: 14px;
          font-family: PingFangSC, PingFangSC-Medium;
          font-weight: 550;
          text-align: left;
          color: #333333;
          line-height: 20px;
        }
        .card {
          width: 100%;
          display: flex;
          text-align: center;
          .demo {
            cursor: pointer;
            flex: 1;
            display: flex;
            margin: 0 10px;
            height: 104px;
            background-color: #fff;
            border-radius: 6px;
            box-shadow: 0px 3px 7px 0px rgba(234,234,234,0.33);
            .content-left, .content-right {
              flex: 1;
              margin-top: 25px;
              p {
                margin: 0;
                font-size: 14px;
                font-family: PingFangSC, PingFangSC-Regular;
                font-weight: 400;
                text-align: left;
                // color: #aeb5c4;
                color: #666666;
              }
              h4 {
                margin: 0;
                margin-top: 5px;
                font-size: 24px;
                font-family: PingFangSC, PingFangSC-Medium;
                font-weight: 500;
                text-align: left;
                color: #333333;
              }
            }
            .content-left {
              margin-left: 34px;
            }
            .content-right {
              text-align: right;
              padding-right: 23px;
              box-sizing: border-box;
              img {
                width: 68px;
                height: 60px;
                vertical-align: middle;
              }
            }
          }
          .demo:first-child {
            margin-left: 20px;
          }
          .demo:last-child {
            margin-right: 0px;
          }
        }
      }
      .todo-matter {
        .card {
          .demo {
            cursor: pointer;
            .content-left {
              p {
                font-size: 14px;
                font-family: PingFangSC, PingFangSC-Regular;
                font-weight: 400;
                text-align: left;
                color: #aeb5c4;
              }
              h4 {
                font-size: 16px;
                font-family: PingFangSC, PingFangSC-Medium;
                font-weight: 500;
                text-align: left;
                color: #333333;
              }
            }
            .content-right {
              img {
                width: 34px;
                height: 34px;
              }
            }
          }
        }
      }
    }

    // 漏斗图和销售排行榜
    .funnel-sale {
      width: 100%;
      display: flex;
      .todo-matter, .chart {
        flex: 1;
      }

      // 代办事项样式迁入
      .todo-matter {
        .word-title {
          padding: 20px 34px;
          box-sizing: border-box;
          margin: 0;
          width: 100%;
          font-size: 14px;
          font-family: PingFangSC, PingFangSC-Medium;
          font-weight: 550;
          text-align: left;
          color: #333333;
          line-height: 20px;
        }
        .card {
          .top, .center, .bottom {
            width: 100%;
            height: 104px;
            display: flex;
            padding-left: 20px;
            box-sizing: border-box;
            .demo {
              flex: 1;
              display: flex;
              background-color: #fff;
              border: 1px solid #fff;
              border-radius: 6px;
              box-shadow: 0px 3px 7px 0px rgba(234,234,234,0.33);
              .content-left, .content-right {
                flex: 1;
                margin: auto 10px;
                p {
                  margin: 0;
                  font-size: 14px;
                  font-family: PingFangSC, PingFangSC-Regular;
                  font-weight: 400;
                  text-align: left;
                  // color: #aeb5c4;
                  // color: #666666;
                }
                h4 {
                  margin: 0;
                  margin-top: 5px;
                  font-size: 24px;
                  font-family: PingFangSC, PingFangSC-Medium;
                  font-weight: 500;
                  text-align: left;
                  color: #333333;
                }
              }
              .content-left {
                margin-left: 34px;
              p {
                font-size: 14px;
                font-family: PingFangSC, PingFangSC-Regular;
                font-weight: 400;
                text-align: left;
                // color: #aeb5c4;
                color: #666666;
              }
              h4 {
                font-size: 16px;
                font-family: PingFangSC, PingFangSC-Medium;
                font-weight: 500;
                text-align: left;
                color: #333333;
              }
            }
            .content-right {
              text-align: right;
              margin-right: 36px;
              img {
                margin-top: 8px;
                width: 34px;
                height: 34px;
              }
            }
            }
            .demo:hover {
              background-color: rgba(245,253,249,1);
              border: 1px solid rgba(234,235,235,1);
            }
            .left-demo {
              margin-left: 20px;
            }
          }
          .center, .bottom {
            margin-top: 25px;
          }
        }
      }
    }

    // 销售排行榜和商机转化排行榜并排展示
    .sale-business-RankingList {
      width: 100%;
      display: flex;
      .left, .right {
        flex: 1;
      }
    }
  }
</style>
