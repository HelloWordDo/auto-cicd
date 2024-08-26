package com.arkmon.autocicd.domains.dto.response;

import lombok.Data;

import java.util.List;

/**
 * @author X.J
 * @date 2021/2/25
 */
@Data
public class JenkinsJobDetailRspDTO {

    /**
     * _class : org.jenkinsci.plugins.workflow.job.WorkflowJob
     * actions : [{},{},{},{},{},{},{},{},{},{},{},{"_class":"com.cloudbees.plugins.credentials.ViewCredentialsAction"}]
     * description :
     * displayName : weather-be-service
     * displayNameOrNull : null
     * fullDisplayName : sveco » sveco-dev-weather » weather-be-service
     * fullName : sveco/sveco-dev-weather/weather-be-service
     * name : weather-be-service
     * url : https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/
     * buildable : true
     * builds : [{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":47,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/47/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":46,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/46/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":45,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/45/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":44,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/44/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":43,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/43/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":42,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/42/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":41,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/41/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":40,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/40/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":39,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/39/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":38,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/38/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":37,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/37/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":36,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/36/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":35,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/35/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":34,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/34/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":33,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/33/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":32,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/32/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":31,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/31/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":30,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/30/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":29,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/29/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":28,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/28/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":27,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/27/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":26,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/26/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":25,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/25/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":24,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/24/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":23,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/23/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":22,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/22/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":21,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/21/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":20,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/20/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":19,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/19/"},{"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":18,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/18/"}]
     * color : blue
     * firstBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":18,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/18/"}
     * healthReport : [{"description":"Build stability: No recent builds failed.","iconClassName":"icon-health-80plus","iconUrl":"health-80plus.png","score":100}]
     * inQueue : false
     * keepDependencies : false
     * lastBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":47,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/47/"}
     * lastCompletedBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":47,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/47/"}
     * lastFailedBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":34,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/34/"}
     * lastStableBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":47,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/47/"}
     * lastSuccessfulBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":47,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/47/"}
     * lastUnstableBuild : null
     * lastUnsuccessfulBuild : {"_class":"org.jenkinsci.plugins.workflow.job.WorkflowRun","number":46,"url":"https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/46/"}
     * nextBuildNumber : 48
     * property : [{"_class":"hudson.security.AuthorizationMatrixProperty"},{"_class":"com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty"},{"_class":"hudson.model.ParametersDefinitionProperty","parameterDefinitions":[{"_class":"hudson.model.ChoiceParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"git_repo_url","value":"https://gitlab-rd0.maezia.com/eziash/weather/ezia-weather-be_weather"},"description":"This is your source code repository link for the build job.If it is not right, pls contact with DevOps team to correct it. 这个是该微服务构建的源代码仓库链接。如果不对，请联系DevOps团队进行更正。（微信群\u2018运维日常沟通群\u2018）","name":"git_repo_url","type":"ChoiceParameterDefinition","choices":["https://gitlab-rd0.maezia.com/eziash/weather/ezia-weather-be_weather"]},{"_class":"hudson.model.StringParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"branch","value":"develop"},"description":"Pls fill the branch name of your source code repository which will be used in this build. 请提供你的源代码仓库的分支名称。","name":"branch","type":"StringParameterDefinition"},{"_class":"hudson.model.StringParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"imgversion","value":"1.0.0"},"description":"Please fill your docker image version for this build. 请填写本次构建的容器镜像版本。","name":"imgversion","type":"StringParameterDefinition"},{"_class":"hudson.model.ChoiceParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"PipelineName","value":"cipipeline"},"description":"This is jenkins pipeline for your microservice build job.If it is not right, pls contact with DevOps team to correct it. 这个是该微服务构建的源代码仓库链接。如果不对，请联系DevOps团队进行更正。（微信群\u2018运维日常沟通群\u2018）","name":"PipelineName","type":"ChoiceParameterDefinition","choices":["cipipeline"]},{"_class":"hudson.model.StringParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"java_opts","value":""},"description":"jvm参数,可为空,如需设置默认值请联系DevOps团队编辑","name":"java_opts","type":"StringParameterDefinition"}]},{"_class":"jenkins.model.BuildDiscarderProperty"}]
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
    private LastFailedBuildBean lastFailedBuild;
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
         * number : 18
         * url : https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/18/
         */

        private String _class;
        private int number;
        private String url;

    }

    @Data
    public static class LastBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 47
         * url : https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/47/
         */

        private String _class;
        private int number;
        private String url;

    }

    @Data
    public static class LastCompletedBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 47
         * url : https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/47/
         */

        private String _class;
        private int number;
        private String url;

    }

    @Data
    public static class LastFailedBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 34
         * url : https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/34/
         */

        private String _class;
        private int number;
        private String url;

    }

    @Data
    public static class LastStableBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 47
         * url : https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/47/
         */

        private String _class;
        private int number;
        private String url;

    }

    @Data
    public static class LastSuccessfulBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 47
         * url : https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/47/
         */

        private String _class;
        private int number;
        private String url;

    }

    @Data
    public static class LastUnsuccessfulBuildBean {
        /**
         * _class : org.jenkinsci.plugins.workflow.job.WorkflowRun
         * number : 46
         * url : https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/46/
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
         * number : 47
         * url : https://jenkins.maezia.com/job/sveco/job/sveco-dev-weather/job/weather-be-service/47/
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
         * _class : hudson.security.AuthorizationMatrixProperty
         * parameterDefinitions : [{"_class":"hudson.model.ChoiceParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"git_repo_url","value":"https://gitlab-rd0.maezia.com/eziash/weather/ezia-weather-be_weather"},"description":"This is your source code repository link for the build job.If it is not right, pls contact with DevOps team to correct it. 这个是该微服务构建的源代码仓库链接。如果不对，请联系DevOps团队进行更正。（微信群\u2018运维日常沟通群\u2018）","name":"git_repo_url","type":"ChoiceParameterDefinition","choices":["https://gitlab-rd0.maezia.com/eziash/weather/ezia-weather-be_weather"]},{"_class":"hudson.model.StringParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"branch","value":"develop"},"description":"Pls fill the branch name of your source code repository which will be used in this build. 请提供你的源代码仓库的分支名称。","name":"branch","type":"StringParameterDefinition"},{"_class":"hudson.model.StringParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"imgversion","value":"1.0.0"},"description":"Please fill your docker image version for this build. 请填写本次构建的容器镜像版本。","name":"imgversion","type":"StringParameterDefinition"},{"_class":"hudson.model.ChoiceParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"PipelineName","value":"cipipeline"},"description":"This is jenkins pipeline for your microservice build job.If it is not right, pls contact with DevOps team to correct it. 这个是该微服务构建的源代码仓库链接。如果不对，请联系DevOps团队进行更正。（微信群\u2018运维日常沟通群\u2018）","name":"PipelineName","type":"ChoiceParameterDefinition","choices":["cipipeline"]},{"_class":"hudson.model.StringParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"java_opts","value":""},"description":"jvm参数,可为空,如需设置默认值请联系DevOps团队编辑","name":"java_opts","type":"StringParameterDefinition"}]
         */

        private String _class;
        private List<ParameterDefinitionsBean> parameterDefinitions;


        @Data
        public static class ParameterDefinitionsBean {
            /**
             * _class : hudson.model.ChoiceParameterDefinition
             * defaultParameterValue : {"_class":"hudson.model.StringParameterValue","name":"git_repo_url","value":"https://gitlab-rd0.maezia.com/eziash/weather/ezia-weather-be_weather"}
             * description : This is your source code repository link for the build job.If it is not right, pls contact with DevOps team to correct it. 这个是该微服务构建的源代码仓库链接。如果不对，请联系DevOps团队进行更正。（微信群‘运维日常沟通群‘）
             * name : git_repo_url
             * type : ChoiceParameterDefinition
             * choices : ["https://gitlab-rd0.maezia.com/eziash/weather/ezia-weather-be_weather"]
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
                 * name : git_repo_url
                 * value : https://gitlab-rd0.maezia.com/eziash/weather/ezia-weather-be_weather
                 */

                private String _class;
                private String name;
                private String value;

            }
        }
    }
}
