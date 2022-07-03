![](https://pip.itcast.cn/uploads/5e0583b1d715498c9ea637fe5ed94697.png)
**沉浸式项目实战-CRM:汇客CRM(客达管家)-基于SSM传统行业单体架构及技术演进的初级项目课程**
=========================
[**产品原型线上地址(选择汇客CRM实战版)**](https://app.mockplus.cn/s/hvKXEoWW3g2l)  
[**项目基板**](https://github.com/LMWC/JavaProject02_huike-crm-backend/tree/master)  
[**前端文件**](https://github.com/LMWC/JavaProject02_huike-crm-web)  
[**day52_初出茅庐**](https://github.com/LMWC/JavaProject02_huike-crm-backend/tree/day52_%E5%88%9D%E5%87%BA%E8%8C%85%E5%BA%90)  
-利用系统的三层架构mybatis_review完成最简单的增删改查  
[**day53_牛刀小试**](https://github.com/LMWC/JavaProject02_huike-crm-backend/tree/day53_%E7%89%9B%E5%88%80%E5%B0%8F%E8%AF%95)  
-商机管理-公海池-商机捞取-提示信息异常：捞取失败！最大保有量(10)，剩余可以捞取-5条线索  
-权限管理-用户管理-高级搜索-手机号搜索不可用  
-公海池-创建时间搜索 -没有效果  
-商机管理-商机状态搜索框不可用  
-线索管理- 线索ID和手机号应该支持模糊搜索  
-线索管理-添加线索-活动信息-应该只展示 正在活动时间内的活动  
-使用商机专员账号--公海池捞取数据时-提示没有操作权限  
-当线索转商机时，使用规则来进行自动分配,没有按照规则来进行自动分配  
-预加载活动编号  
[**day54_熟能生巧**](https://github.com/LMWC/JavaProject02_huike-crm-backend/tree/day54_%E7%86%9F%E8%83%BD%E7%94%9F%E5%B7%A7)  
-今日简报接  
-首页--今日待办数据展示  
-统计分析--线索统计--新增线索数量折线图  
-统计分析--学科客户分布饼图  
-统计分析--线索统计--线索转化率漏斗图  
-首页数据--商机转化龙虎榜    
-首页数据--线索转化龙虎榜  
-首页基本数据展示使用并发编程  
[**day55_并肩作战**](https://github.com/LMWC/JavaProject02_huike-crm-backend/tree/day55_%E5%B9%B6%E8%82%A9%E4%BD%9C%E6%88%98)  
-查询线索跟进记录列表  
-添加线索根进记录  
-查询商机跟进记录列表  
-新增商机跟进记录  
[**day56_勇于突破**](https://github.com/LMWC/JavaProject02_huike-crm-backend/tree/day56_%E5%8B%87%E4%BA%8E%E7%AA%81%E7%A0%B4)  
-线索批量导入  
-合同上传  
-紧急修复  



**运行方式**
=========================
- 打开并运行本地redis-server.exe
- 启动运行crm\huike-admin\src\main\java\com\huike\HuiKeApplication.java
- 启动运行huike-crm-web前端程序
- 在浏览器中输入网址进入汇客CRM管理系统http://localhost/



**参考环境**
=========================
- IntelliJ IDEA 2020.1.3 (Ultimate Edition)  
  Non-Bundled Plugins: JBLJavaToWeb, Lombook Plugin, mobi.hsz.idea.gitignore, MavenRunHelper,        com.baomidou.plugin.idea.mybatisx
- node-v14.18.3-x64
- maven-3.5.3
- jdk 1.8.0_162
- mysql-5.7.29-winx64
- redis-x64-3.2.100



**在线演示**
=========================
- [官方主页](https://pip.itcast.cn/java-hk)
- [演示地址](http://huike-crm.itheima.net/#/login)

**项目背景**
=========================

- 随着时代的发展，越来越多的国内企业开始采用CRM客户关系管理系统，近年来CRM不仅受到大型企业的青睐，也是中小企业快速成长的催化剂。市场、客户信息、产品、业务人员、渠道等各类信息流的不断增长，传统方式对于管理全渠道信息流成本越来越高，所以需要一个软件产品来解决这些问题。除了这些需求，整个市场各种竞品对于整个消费市场的瓜分，各个软件产品有各自的功能。

  互联网+的出现，改变了很多行业同样很多行业的理念也在转变，伴随着电子商务、智能手机的不断应用，使企业更加简单的接触消费者，直接与消费者互动收集消费者反馈成为可能。那原本以产品以企业为核心的理念逐渐在转变，转变以“客户为中心”，及时服务客户，提高客户满意程度，从而提高企业营收。

  依托强大的信息技术，企业可以借助CRM系统不断把客户跟进，服务，成交，售后打通，而且不断依托CRM工具来优化这个流程。

- [了解更多项目](https://project-dev.itheima.net/java)，改变命运，少走弯路，进入真项目知识海洋畅游。

# 业务介绍

- 我公司作为一家互联网教育机构，在客户关系管理方面面临很多问题，包括：市场部门渠道增多、线索量加大、市场的客户需要客户细分、分配业务人员对于客户信息的跟进信息和记录、管理者对于销售目标的设定、销售计划的统计等。


- 汇客CRM系统，主要是为企业销售人员提供服务以下服务：


1. 辅助销售人员对销售线索、商机、客户进行跟进转化，提高转化效率，实现销售线索的价值最大化；

2. 为企业提供自动化营销服务；

3. 第三，对销售业绩、销售趋势进行数据汇总分析；

4. 第四，销售数据统计为销售管理工作提供依据；

5. 第五，为优化公司的业务发展，提供数据支撑。


# 功能特性

![](docs/img/功能特色.png)

系统架构
=========================
![](docs/crm-framework.png)

功能结构
=========================
![](docs/img/功能结构图.png)

技术架构
=========================
![](docs/img/技术架构.png)

更多流程图
=========================
[全部流程图](docs/img/流程图)

环境要求
=========================
- Node： 14.15+（集成npm）
  https://nodejs.org/en/download
- jdk 1.8+
- mysql 8.0+
- redis 5.6+

- docs/安装手册/install.md)

页面一览
=========================

线索管理![](docs/img/页面/线索管理.png)

商机管理

![](docs/img/页面/商机管理.png)

统计分析

![](docs/img/页面/统计分析.png)




