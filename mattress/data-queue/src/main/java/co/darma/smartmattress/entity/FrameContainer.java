package co.darma.smartmattress.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frank on 15/10/23.
 */
public class FrameContainer {

    private long currentFrameTime;

    private Map<String, List<Element>> frameMap = new HashMap<String, List<Element>>();


    public long getCurrentFrameTime() {
        return currentFrameTime;
    }

    public void setCurrentFrameTime(long currentFrameTime) {
        this.currentFrameTime = currentFrameTime;
    }
}
