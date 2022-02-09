//package com.huike.clues.utils;
//
//import com.huike.clues.domain.TbRuleAssign;
//import com.huike.clues.domain.TbRuleExpression;
//import com.huike.common.constant.Constants;
//import com.huike.common.utils.spring.SpringUtils;
//import com.huike.clues.service.ITbRuleAssignService;
//import org.kie.api.KieServices;
//import org.kie.api.builder.KieBuilder;
//import org.kie.api.builder.KieFileSystem;
//import org.kie.api.builder.Message;
//import org.kie.api.builder.ReleaseId;
//import org.kie.api.runtime.KieContainer;
//import org.kie.api.runtime.KieSession;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import java.util.TreeSet;
//
//public class RuleUtils {
//
//
//
//    public static final String RESOURCE_PATH = "src/main/resources/";
//
//    public static KieContainer kieContainer_clue;
//
//
//    public static KieContainer kieContainer_business;
//
//    public static final String VERSION="1.0";// protected static Map<ReleaseId, KieContainer> containerMapper = new ConcurrentHashMap<>();
//
//
//    public static boolean assignment(Long id,String type, String assignUser, String assignType){
//        System.out.println("todo ------assignment");
//        ITbRuleAssignService tbRuleAssignService= SpringUtils.getBean(ITbRuleAssignService.class);
//        return  tbRuleAssignService.assignment(id,type,assignUser,assignType);
//    }
//
//
//    /**
//     * 规则构造
//     * @param type
//     * @param ruleAssigns
//     */
//    public static void  buildRules(String type, List<TbRuleAssign> ruleAssigns) {
//        String content="";
//        if(Constants.rule_type_clue.equals(type)){
//            content="package clues\n" +
//                    "import com.huike.clues.domain.TbClue\n" +
//                    "import java.util.Date\n" +
//                    "import com.huike.clues.utils.RuleUtils\n";
//        }else{
//            content="package business\n" +
//                    "import com.huike.business.domain.TbBusiness\n" +
//                    "import com.huike.clues.domain.TbClue\n" +
//                    "import java.util.Date\n" +
//                    "import com.huike.clues.utils.RuleUtils\n";
//        }
//        for (TbRuleAssign ruleAssign : ruleAssigns) {
//            content+="\n"+ruleAssign.getRuleExpression()+"\n";
//        }
//        System.out.println("---------expressions-----------  "+ content);
//        KieServices kService = KieServices.Factory.get();
//        ReleaseId releaseId = kService.newReleaseId(type, type+content.hashCode(), VERSION);
//        KieFileSystem fileSystem = kService.newKieFileSystem();
//        fileSystem.generateAndWritePomXML(releaseId);
////        String path = RESOURCE_PATH + releaseId.getGroupId() + "/" + releaseId.getArtifactId() + "-"
////                + releaseId.getVersion() + ".drl";
//        String path = RESOURCE_PATH + content.hashCode() + ".drl";
//        System.out.println("### build rule content : " + path);
//        fileSystem.write(path, content);
//        KieBuilder kbuilder = kService.newKieBuilder(fileSystem);
//        kbuilder.buildAll();
//
//        if (kbuilder.getResults().hasMessages(Message.Level.ERROR)) {
//            throw new RuntimeException("Rule Build Errors:\n" + kbuilder.getResults().toString());
//        }
//        if(Constants.rule_type_clue.equals(type)){
//             kieContainer_clue = kService.newKieContainer(type+content.hashCode(), releaseId);
//        }else if(Constants.rule_type_business.equals(type)){
//             kieContainer_business = kService.newKieContainer(type+content.hashCode(), releaseId);
//        }
//
//    }
//
//
//    public static String getExpression(TbRuleAssign tbRuleAssign){
//        List<TbRuleExpression> rulesList=tbRuleAssign.getRulesList();
//        String ruleCondtion= tbRuleAssign.getRuleCondition();
//        String[] condtions=  ruleCondtion.split(",|;");
//        Set<String> mySet = new TreeSet<>(Arrays.asList(condtions));
//        int index = 0;
//        for (String condtion : mySet) {
//            String expression="";
//            TbRuleExpression tbRuleExpression = rulesList.get(index);
//            index++;
//            if(tbRuleExpression.getExpression().equals("in")||tbRuleExpression.getExpression().equals("not in")){
//                expression = tbRuleExpression.getRuleKey()+" "+tbRuleExpression.getExpression()+"("+tbRuleExpression.getRuleValue()+")";
//            }  else if(tbRuleExpression.getRuleKey().equals("createTime")){
//                if("between".equals(tbRuleExpression.getExpression())){
//                    //expression = "createTimeStr"+tbRuleExpression.getExpression()+"'"+tbRuleExpression.getRuleValue()+"'";
//                    String[] compareTimes= tbRuleExpression.getRuleValue().split(",");
//                    expression = "createTimeStr>='"+compareTimes[0]+"' && createTimeStr<= '"+compareTimes[1]+"'";
//                } else{
//                    expression = "createTimeStr"+tbRuleExpression.getExpression()+"'"+tbRuleExpression.getRuleValue()+"'";
//                }
//            }  else if(tbRuleExpression.getRuleKey().equals("region")) {
//                expression = tbRuleExpression.getRuleKey()+tbRuleExpression.getExpression()+"'"+tbRuleExpression.getRuleValue()+"'";
//            } else{
//                expression = tbRuleExpression.getRuleKey()+tbRuleExpression.getExpression()+tbRuleExpression.getRuleValue();
//            }
//            ruleCondtion=ruleCondtion.replace(condtion,expression);
//        }
//        System.out.println(ruleCondtion);
//        ruleCondtion=ruleCondtion.replace(",","&&");
//        ruleCondtion=ruleCondtion.replace(";",")||(");
//        ruleCondtion= "("+ruleCondtion+")";
//        String rule="";
//        if(Constants.rule_type_clue.equals(tbRuleAssign.getType())){
//            rule="rule \"rule_"+System.nanoTime()+"\"\n" +
//                    "    when\n" +
//                    "       $m:TbClue("+ruleCondtion+")\n" +
//                    "    then\n" +
//                    "     boolean result = RuleUtils.assignment($m.getId(),"+"\""+Constants.rule_type_clue+"\""+","+"\""+tbRuleAssign.getAssignObject()+"\""+","+"\""+tbRuleAssign.getAssignType()+"\""+");\n" +
//                    "      if(result) delete($m);\n" +
//                    "end";
//        }else if(Constants.rule_type_business.equals(tbRuleAssign.getType())){
//            rule="rule \"rule_"+System.nanoTime()+"\"\n" +
//                    "    when\n" +
//                    "       $m:TbBusiness("+ruleCondtion+")\n" +
//                    "    then\n" +
//                    "      boolean result = RuleUtils.assignment($m.getId(),"+"\""+Constants.rule_type_business+"\""+","+"\""+tbRuleAssign.getAssignObject()+"\""+","+"\""+tbRuleAssign.getAssignType()+"\""+");\n" +
//                    "      if(result) delete($m);\n" +
//                    "end";
//        }
//        return  rule;
//    }
//
////    private static ReentrantLock initContainerLock = new ReentrantLock();
////    private static KieContainer getRuleContainer(String groupId, String ruleCode, String version) {
////        KieServices kService = KieServices.Factory.get();
////        ReleaseId releaseId = kService.newReleaseId(groupId, ruleCode, version);
////
////        KieContainer kContainer = containerMapper.get(releaseId);
////
////        if (kContainer == null) {
////            initContainerLock.lock();
////            try {
////                System.out.println("get kContainer-----------"+kContainer);
////                if (kContainer == null) {
////                    kContainer = kService.newKieContainer(ruleCode, releaseId);
////                    containerMapper.put(releaseId, kContainer);
////                }
////            } finally {
////                initContainerLock.unlock();
////            }
////        }
////        return kContainer;
////    }
//
////    /**
////     * 获取规则会话，用来调用规则
////     * @param ruleCode
////     * @param version
////     * @return
////     */
////    public static KieSession getRuleSession(String ruleCode, String version) {
////        return getRuleSession(DEFAULT_GROUP_ID, ruleCode, version);
////    }
//
//    /**
//     * 获取规则会话，用来调用规则
//     * @param groupId
//     * @return
//     */
//    public static KieSession getRuleSession(String groupId) {
//        if(Constants.rule_type_clue.equals(groupId)&& kieContainer_clue!=null){
//            return kieContainer_clue.newKieSession();
//        }else if(Constants.rule_type_business.equals(groupId)&& kieContainer_business!=null){
//            return kieContainer_business.newKieSession();
//        }
//        return null;
//    }
//
//
//
//}
