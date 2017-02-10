package co.darma.smartmattress.upgrade.entity;

import co.darma.smartmattress.entity.Project;

/**
 * 固件和具体项目关联后的一些其他信息
 * <p/>
 * Created by frank on 15/12/17.
 */
public class FirmwareExtendInfo {

    private Firmware firmware;

    private Project project;

    public Firmware getFirmware() {
        return firmware;
    }

    public void setFirmware(Firmware firmware) {
        this.firmware = firmware;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
