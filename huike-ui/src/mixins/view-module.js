export default {
  data () {
    /* eslint-disable */
    return {
      // 设置属性
      mixinViewModuleOptions: {
        createdIsNeed: true,       // 此页面是否在创建时，调用查询数据列表接口？
        activatedIsNeed: false,    // 此页面是否在激活（进入）时，调用查询数据列表接口？
        getListFun: null,       // 数据列表接口，查询API
        getDataListIsPage: true, // 数据列表接口，是否需要分页？
        deleteFun: null,            // 删除接口，API地址
        deleteIsBatch: false,     // 删除接口，是否需要批量？
        deleteIsBatchKey: 'id',   // 删除接口，批量状态下由那个key进行标记操作？比如：pid，uid...
        exportFun: null             // 导出接口，API地址
      },
      // 默认属性
      queryParams: {},               // 查询条件
      dataList: [],               // 数据列表
      order: null,                  // 排序，asc／desc
      orderField: null,             // 排序，字段
      pageNum: 1,                    // 当前页码
      pageSize: 10,                  // 每页数
      total: 0,                   // 总条数
      dataListLoading: false,     // 数据列表，loading状态
      dataListSelections: [],     // 数据列表，多选项
      ids: [],                    // 选中数组
      single: true,               // 非单个禁用
      multiple: true,             // 非多个禁用
      showSearch: true,           // 显示搜索条件
      addOrUpdateVisible: false   // 新增／更新，弹窗visible状态
    }
    /* eslint-enable */
  },
  created () {
    if (this.mixinViewModuleOptions.createdIsNeed) {
      this.getList();
    }
  },
  activated () {
    if (this.mixinViewModuleOptions.activatedIsNeed) {
      this.getList();
    }
  },
  methods: {
    // 获取数据列表
    getList () {
      this.dataListLoading = true
      this.mixinViewModuleOptions.getListFun(
        {
          order: this.order,
          orderField: this.orderField,
          pageNum: this.mixinViewModuleOptions.getDataListIsPage ? this.pageNum : null,
          pageSize: this.mixinViewModuleOptions.getDataListIsPage ? this.pageSize : null,
          ...this.queryParams
        }
      ).then((res) => {
        console.log('-=-=-res=-=-=-=',res)
        this.dataListLoading = false
        if (res.code !== 200) {
          this.dataList = []
          this.total = 0
          return this.$message.error(res.msg)
        }
        this.dataList = this.mixinViewModuleOptions.getDataListIsPage ? res.rows : res.data
        this.total = this.mixinViewModuleOptions.getDataListIsPage ? res.total : 0
        console.log('-=-=-res=-=-=-=',this.dataList)
      }).catch(() => {
        this.dataListLoading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.dataListSelections = selection
      this.ids = selection.map(item => item[this.mixinViewModuleOptions.deleteIsBatchKey])
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 排序
    dataListSortChangeHandle (data) {
      if (!data.order || !data.prop) {
        this.order = ''
        this.orderField = ''
        return false
      }
      this.order = data.order.replace(/ending$/, '')
      this.orderField = data.prop.replace(/([A-Z])/g, '_$1').toLowerCase()
      this.getList()
    },
    // 筛选
    childrenFilterChange (filterItem) {
      const key = Object.keys(filterItem)[0]
      const val = Object.values(filterItem)[0][0] ? Object.values(filterItem)[0][0] : ''
      this.queryParams[key] = val
      this.handleQuery()
    },
    // 设置筛选项
    setDefaultFilter (col, val) {
      const column = this.$refs.table.columns[col]
      column.filteredValue = [val]
      this.$refs.table.store.commit('filterChange', {
        column,
        values: column.filteredValue,
        silent: true
      })
    },
    // 新增 / 修改
    addOrUpdateHandle (id) {
      console.log('addOrUpdateHandle  id',id)
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.form.id = id
        this.$refs.addOrUpdate.init()
      })
    },
   // 删除
   handleDelete (id) {
    console.log('-=-handleDelete=-=id',this.ids)
    const ids = id || this.ids;
    if (this.mixinViewModuleOptions.deleteIsBatch && !ids && this.dataListSelections.length <= 0) {
      return this.$message({
        message: '请选择至少一条数据',
        type: 'warning',
        duration: 500
      })
    }
    this.$confirm('确定将选择数据删除?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    .then(() => {
      return this.mixinViewModuleOptions.deleteFun(ids)
    }).then((res) => {
      console.log('handleDelete-----res',res)
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.getList();
      this.msgSuccess("删除成功");
    }).catch(() => {})
  },
   /** 导出按钮操作 */
  handleExport() {
    this.$confirm('是否确认导出数据?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportFun(this.queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
}
