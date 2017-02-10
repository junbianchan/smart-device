package co.darma.smartmattress.analysis.factory;

import co.darma.smartmattress.analysis.entity.ManBehavior;
import co.darma.smartmattress.analysis.entity.ManBehaviorType;
import org.apache.log4j.Logger;

/**
 * Created by frank on 15/11/4.
 */
public class ManBehaviorFacotry {

    private static Logger logger = Logger.getLogger(ManBehaviorType.class);

    public static ManBehavior newInstanceById(int cmd) {
        ManBehaviorType manBehaviorType = ManBehaviorType.getTypeById(cmd);
        if (manBehaviorType != null) {
            ManBehavior behavior = new ManBehavior();
            behavior.setType(manBehaviorType);
            return behavior;
        } else {
            logger.error("error cmd id ,which is :" + cmd);
            return null;
        }
    }
}
