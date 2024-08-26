package com.arkmon.autocicd.domains.dto.response;

import lombok.Data;

import java.util.List;

/**
 * @author X.J
 * @date 2021/2/26
 */
@Data
public class JenkinsTagDetailRspDTO {

    /**
     * _class : org.jenkinsci.plugins.workflow.job.WorkflowJob
     * actions : [{},{},{},{},{},{},{},{},{},{"_class":"com.cloudbees.plugins.credentials.ViewCredentialsAction"}]
     * description :
     * displayName : Code_Baseline_Creation_SIT_SVECO
     * displayNameOrNull : null
     * fullDisplayName : sveco » tag-sit » Code_Baseline_Creation_SIT_SVECO
     * fullName : sveco/tag-sit/Code_Baseline_Creation_SIT_SVECO
     * name : Code_Baseline_Creation_SIT_SVECO
     * url : https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/
     * buildable : true
     * builds : [{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":319,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/319/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":318,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/318/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":317,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/317/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":316,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/316/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":315,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/315/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":314,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/314/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":313,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/313/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":312,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/312/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":311,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/311/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":310,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/310/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":309,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/309/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":308,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/308/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":307,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/307/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":306,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/306/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":305,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/305/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":304,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/304/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":303,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/303/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":302,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/302/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":301,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/301/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":300,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/300/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":299,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/299/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":298,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/298/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":297,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/297/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":296,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/296/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":295,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/295/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":294,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/294/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":293,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/293/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":292,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/292/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":291,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/291/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":290,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/290/"}]
     * color : blue
     * firstBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":290,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/290/"}
     * healthReport : [{"description":"Build stability: No recent builds failed.","iconClassName":"icon-health-80plus","iconUrl":"health-80plus.png","score":100}]
     * inQueue : false
     * keepDependencies : false
     * lastBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":319,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/319/"}
     * lastCompletedBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":319,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/319/"}
     * lastFailedBuild : null
     * lastStableBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":319,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/319/"}
     * lastSuccessfulBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":319,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/319/"}
     * lastUnstableBuild : null
     * lastUnsuccessfulBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":318,"url":"https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/318/"}
     * nextBuildNumber : 320
     * property : [{"_class":"hudson.plugins.jira.JiraProjectProperty"},{"_class":"hudson.security.AuthorizationMatrixProperty"},{"_class":"jenkins.model.BuildDiscarderProperty"},{"_class":"com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty"},{"_class":"hudson.model.ParametersDefinitionProperty","parameterDefinitions":[{"_class":"hudson.model.ChoiceParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"cloud","value":"ma"},"description":"请选择所选微服务是MA环境还是MX环境，sit环境无需选择","name":"cloud","type":"ChoiceParameterDefinition","choices":["ma","mx"]},{"_class":"hudson.model.ChoiceParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"namespace","value":"sveco-sit"},"description":"","name":"namespace","type":"ChoiceParameterDefinition","choices":["sveco-sit"]},{"_class":"hudson.model.ChoiceParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"project_version","value":"CNS3SOP2_0.1.0"},"description":"","name":"project_version","type":"ChoiceParameterDefinition","choices":["CNS3SOP2_0.1.0","CNS3SOP2_0.2.0","CNS3SOP2_0.3.0","CNS3SOP2_0.4.0","CNS3SOP2_0.5.0","CNS3SOP2_0.6.0","CNS3SOP2_0.7.0","CNS3SOP2_0.8.0","CNS3SOP2_0.9.0","CNS3SOP2_0.10.0","CNS3SOP2_0.11.0","CNS3SOP2_0.12.0","CNS3SOP2_0.13.0","CNS3SOP2_0.14.0","CNS3SOP2_0.15.0","CNS3SOP2_0.16.0","CNS3SOP2_0.17.0","CNS3SOP2_0.18.0","CNS3SOP2_0.19.0","CNS3SOP2_0.20.0","CNS3SOP2_0.21.0","CNS3SOP2_0.22.0","CNS3SOP2_0.23.0","CNS3SOP2_0.24.0","CNS3SOP2_0.25.0","CNS3SOP2_0.26.0","CNS3SOP2_0.27.0","CNS3SOP2_0.28.0","CNS3SOP2_0.29.0","CNS3SOP2_0.30.0","CNS3SOP2_0.31.0","CNS3SOP2_0.32.0","CNS3SOP2_0.33.0","CNS3SOP2_0.34.0"]},{"_class":"com.cwctravel.hudson.plugins.extended_choice_parameter.ExtendedChoiceParameterDefinition","defaultParameterValue":null,"description":"","name":"service","type":"PT_CHECKBOX"}]}]
     * queueItem : null
     * concurrentBuild : true
     * resumeBlocked : false
     */

    private String _class;
    private String description;
    private String displayName;
    private Object displayNameOrNull;
    private String fullDisplayName;
    private String fullName;
    private String name;
    private String url;
    private boolean buildable;
    private String color;
    private FirstBuildBean firstBuild;
    private boolean inQueue;
    private boolean keepDependencies;
    private LastBuildBean lastBuild;
    private LastCompletedBuildBean lastCompletedBuild;
    private Object lastFailedBuild;
    private LastStableBuildBean lastStableBuild;
    private LastSuccessfulBuildBean lastSuccessfulBuild;
    private Object lastUnstableBuild;
    private LastUnsuccessfulBuildBean lastUnsuccessfulBuild;
    private int nextBuildNumber;
    private Object queueItem;
    private boolean concurrentBuild;
    private boolean resumeBlocked;
    private List<ActionsBean> actions;
    private List<BuildsBean> builds;
    private List<HealthReportBean> healthReport;
    private List<PropertyBean> property;

    @Data
    public static class FirstBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 290
         * url : https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/290/
         */

        private String _class;
        private int number;
        private String url;
    }

    @Data
    public static class LastBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 319
         * url : https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/319/
         */

        private String _class;
        private int number;
        private String url;
    }

    @Data
    public static class LastCompletedBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 319
         * url : https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/319/
         */

        private String _class;
        private int number;
        private String url;
    }

    @Data
    public static class LastStableBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 319
         * url : https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/319/
         */

        private String _class;
        private int number;
        private String url;

    }

    @Data
    public static class LastSuccessfulBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 319
         * url : https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/319/
         */

        private String _class;
        private int number;
        private String url;

    }

    @Data
    public static class LastUnsuccessfulBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 318
         * url : https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/318/
         */

        private String _class;
        private int number;
        private String url;

    }

    @Data
    public static class ActionsBean {
        /**
         * _class : com.cloudbees.plugins.credentials.ViewCredentialsAction
         */

        private String _class;

    }

    @Data
    public static class BuildsBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 319
         * url : https://jenkins.maezia.com/view/SVECO/job/sveco/view/TAG/job/tag-sit/job/Code_Baseline_Creation_SIT_SVECO/319/
         */

        private String _class;
        private int number;
        private String url;

    }

    @Data
    public static class HealthReportBean {
        /**
         * description : Build stability: No recent builds failed.
         * iconClassName : icon-health-80plus
         * iconUrl : health-80plus.png
         * score : 100
         */

        private String description;
        private String iconClassName;
        private String iconUrl;
        private int score;

    }

    @Data
    public static class PropertyBean {
        /**
         * _class : hudson.plugins.jira.JiraProjectProperty
         * parameterDefinitions : [{"_class":"hudson.model.ChoiceParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"cloud","value":"ma"},"description":"请选择所选微服务是MA环境还是MX环境，sit环境无需选择","name":"cloud","type":"ChoiceParameterDefinition","choices":["ma","mx"]},{"_class":"hudson.model.ChoiceParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"namespace","value":"sveco-sit"},"description":"","name":"namespace","type":"ChoiceParameterDefinition","choices":["sveco-sit"]},{"_class":"hudson.model.ChoiceParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"project_version","value":"CNS3SOP2_0.1.0"},"description":"","name":"project_version","type":"ChoiceParameterDefinition","choices":["CNS3SOP2_0.1.0","CNS3SOP2_0.2.0","CNS3SOP2_0.3.0","CNS3SOP2_0.4.0","CNS3SOP2_0.5.0","CNS3SOP2_0.6.0","CNS3SOP2_0.7.0","CNS3SOP2_0.8.0","CNS3SOP2_0.9.0","CNS3SOP2_0.10.0","CNS3SOP2_0.11.0","CNS3SOP2_0.12.0","CNS3SOP2_0.13.0","CNS3SOP2_0.14.0","CNS3SOP2_0.15.0","CNS3SOP2_0.16.0","CNS3SOP2_0.17.0","CNS3SOP2_0.18.0","CNS3SOP2_0.19.0","CNS3SOP2_0.20.0","CNS3SOP2_0.21.0","CNS3SOP2_0.22.0","CNS3SOP2_0.23.0","CNS3SOP2_0.24.0","CNS3SOP2_0.25.0","CNS3SOP2_0.26.0","CNS3SOP2_0.27.0","CNS3SOP2_0.28.0","CNS3SOP2_0.29.0","CNS3SOP2_0.30.0","CNS3SOP2_0.31.0","CNS3SOP2_0.32.0","CNS3SOP2_0.33.0","CNS3SOP2_0.34.0"]},{"_class":"com.cwctravel.hudson.plugins.extended_choice_parameter.ExtendedChoiceParameterDefinition","defaultParameterValue":null,"description":"","name":"service","type":"PT_CHECKBOX"}]
         */

        private String _class;
        private List<ParameterDefinitionsBean> parameterDefinitions;

        @Data
        public static class ParameterDefinitionsBean {
            /**
             * _class : hudson.model.ChoiceParameterDefinition
             * defaultParameterValue : {"_class":"hudson.model.StringParameterValue","name":"cloud","value":"ma"}
             * description : 请选择所选微服务是MA环境还是MX环境，sit环境无需选择
             * name : cloud
             * type : ChoiceParameterDefinition
             * choices : ["ma","mx"]
             */

            private String _class;
            private DefaultParameterValueBean defaultParameterValue;
            private String description;
            private String name;
            private String type;
            private List<String> choices;

            @Data
            public static class DefaultParameterValueBean {
                /**
                 * _class : hudson.model.StringParameterValue
                 * name : cloud
                 * value : ma
                 */

                private String _class;
                private String name;
                private String value;

            }
        }
    }
}
