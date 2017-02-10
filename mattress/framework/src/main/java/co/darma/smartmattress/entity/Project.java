package co.darma.smartmattress.entity;

/**
 * 项目，用于管理不同公司的不同项目
 * <p/>
 * Created by frank on 15/12/17.
 */
public class Project {

    private Integer projectId;

    private String projectName;

    private String companyName;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("[projectId:").append(projectId).append(",projectName:").append(projectName).append("]");
        return sb.toString();
    }
}
