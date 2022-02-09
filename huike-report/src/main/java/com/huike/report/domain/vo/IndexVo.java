package com.huike.report.domain.vo;

public class IndexVo {

    private Integer cluesNum=100;  //线索数目
    private Integer businessNum=100;  //商机数目
    private Integer contractNum=100;  //合同数目
    private Double salesAmount=100.00;  //销售金额


    private Integer todayCluesNum=0;  //今日线索数目
    private Integer todayBusinessNum=0;  //今日商机数目
    private Integer todayContractNum=0;  //今日合同数目
    private Double todaySalesAmount=1.0;  //今日销售金额

    //待办
    private Integer transferNum=0;  //转派
    private Integer tofollowedCluesNum=0;  //待跟进线索数目
    private Integer tofollowedBusinessNum=0;  //待跟进商机数目
    private Integer toallocatedCluesNum=0;  //待分配线索数目
    private Integer toallocatedBusinessNum=0;  //待分配商机数目

    public Integer getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(Integer transferNum) {
        this.transferNum = transferNum;
    }

    public Integer getTofollowedCluesNum() {
        return tofollowedCluesNum;
    }

    public void setTofollowedCluesNum(Integer tofollowedCluesNum) {
        this.tofollowedCluesNum = tofollowedCluesNum;
    }

    public Integer getTofollowedBusinessNum() {
        return tofollowedBusinessNum;
    }

    public void setTofollowedBusinessNum(Integer tofollowedBusinessNum) {
        this.tofollowedBusinessNum = tofollowedBusinessNum;
    }

    public Integer getToallocatedCluesNum() {
        return toallocatedCluesNum;
    }

    public void setToallocatedCluesNum(Integer toallocatedCluesNum) {
        this.toallocatedCluesNum = toallocatedCluesNum;
    }

    public Integer getToallocatedBusinessNum() {
        return toallocatedBusinessNum;
    }

    public void setToallocatedBusinessNum(Integer toallocatedBusinessNum) {
        this.toallocatedBusinessNum = toallocatedBusinessNum;
    }

    public Integer getCluesNum() {
        return cluesNum;
    }

    public void setCluesNum(Integer cluesNum) {
        this.cluesNum = cluesNum;
    }

    public Integer getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(Integer businessNum) {
        this.businessNum = businessNum;
    }

    public Integer getContractNum() {
        return contractNum;
    }

    public void setContractNum(Integer contractNum) {
        this.contractNum = contractNum;
    }

    public Double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Integer getTodayCluesNum() {
        return todayCluesNum;
    }

    public void setTodayCluesNum(Integer todayCluesNum) {
        this.todayCluesNum = todayCluesNum;
    }

    public Integer getTodayBusinessNum() {
        return todayBusinessNum;
    }

    public void setTodayBusinessNum(Integer todayBusinessNum) {
        this.todayBusinessNum = todayBusinessNum;
    }

    public Integer getTodayContractNum() {
        return todayContractNum;
    }

    public void setTodayContractNum(Integer todayContractNum) {
        this.todayContractNum = todayContractNum;
    }

    public Double getTodaySalesAmount() {
        return todaySalesAmount;
    }

    public void setTodaySalesAmount(Double todaySalesAmount) {
        this.todaySalesAmount = todaySalesAmount;
    }
}
