//import org.activiti.engine.*;
//import org.activiti.engine.repository.Deployment;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;


public class ActivitiTest {

//    @Test
//    public void testGenTable(){
//        //1.创建ProcessEngineConfiguration对象  第一个参数:配置文件名称  第二个参数是processEngineConfiguration的bean的id
//        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
//                .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
//        //2.创建ProcesEngine对象
//        ProcessEngine processEngine = configuration.buildProcessEngine();
//
//        //3.输出processEngine对象
//        System.out.println(processEngine);
//    }
//
//    /**
//     * ACT_RE_DEPLOYMENT 发布任务表
//     * ACT_RE_PROCDEF 流程
//     */
//    @Test
//    public void testDeployment(){
////        1、创建ProcessEngine
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
////        2、获取RepositoryServcie
//        RepositoryService repositoryService = processEngine.getRepositoryService();
////        3、使用service进行流程的部署，定义一个流程的名字，把bpmn和png部署到数据中
//        Deployment deploy = repositoryService.createDeployment()
//                .name("出差申请流程")
//                .addClasspathResource("bpmn/evection.bpmn")
//                .addClasspathResource("bpmn/evection.png")
//                .deploy();
////        4、输出部署信息
//        System.out.println("流程部署id="+deploy.getId());
//        System.out.println("流程部署名字="+deploy.getName());
//    }
//
//
//    /**
//     * 启动流程实例
//     * `act_hi_actinst`    流程实例执行历史信息
//     * `act_hi_identitylink` 流程参与用户的历史信息
//     * `act_hi_procinst`     流程实例的历史信息
//     * `act_hi_taskinst`     流程任务的历史信息
//     * `act_ru_execution`    流程执行信息
//     * `act_ru_identitylink`  流程的正在参与用户信息
//     * `act_ru_task`         流程当前任务信息
//     */
//    @Test
//    public void testStartProcess(){
////        1、创建ProcessEngine
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
////        2、获取RunTimeService
//        RuntimeService runtimeService = processEngine.getRuntimeService();
////        3、根据流程定义的id启动流程
//        ProcessInstance instance = runtimeService.startProcessInstanceByKey("myEvection");
////        4、输出内容
//        System.out.println("流程定义ID："+instance.getProcessDefinitionId());
//        System.out.println("流程实例ID："+instance.getId());
//        System.out.println("当前活动的ID："+instance.getActivityId());
//    }
//
//
//
//    @Test
//    public void completTask(){
////        获取引擎
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
////        获取操作任务的服务 TaskService
//        TaskService taskService = processEngine.getTaskService();
////        完成任务,参数：任务id,完成zhangsan的任务
// //      taskService.complete("5005");
////        获取jerry - myEvection 对应的任务
////        Task task = taskService.createTaskQuery()
////                .processDefinitionKey("myEvection")
////                .taskAssignee("jerry")
////                .singleResult();
////        完成jack的任务
////        Task task = taskService.createTaskQuery()
////                .processDefinitionKey("myEvection")
////                .taskAssignee("jack")
////                .singleResult();
////        完成rose的任务
//        Task task = taskService.createTaskQuery()
//                .processDefinitionKey("myEvection")
//                .taskAssignee("rose")
//                .singleResult();
//
//
//        System.out.println("流程实例id="+task.getProcessInstanceId());
//        System.out.println("任务Id="+task.getId());
//        System.out.println("任务负责人="+task.getAssignee());
//        System.out.println("任务名称="+task.getName());
//       // 完成jerry的任务 、完成jack的任务、完成rose的任务
//        taskService.complete(task.getId());
//    }
//
//
//
//    /**
//     * 添加业务key 到Activiti的表
//     */
//    @Test
//    public void addBusinessKey(){
////        1、获取流程引擎
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
////        2、获取RuntimeService
//        RuntimeService runtimeService = processEngine.getRuntimeService();
////        3、启动流程的过程中，添加businesskey
////           第一个参数：流程定义的key
////           第二个参数：businessKey，存出差申请单的id，就是1001
//        ProcessInstance instance = runtimeService.
//                startProcessInstanceByKey("myEvection", "1002");
////        4、输出
//        System.out.println("businessKey=="+instance.getBusinessKey());
//
//    }

}
