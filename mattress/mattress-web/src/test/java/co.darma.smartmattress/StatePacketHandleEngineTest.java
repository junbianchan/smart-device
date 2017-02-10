package co.darma.smartmattress;

import co.darma.smartmattress.analysis.engine.ServicesEngine;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.entity.ServicePacket;
import co.darma.smartmattress.analysis.service.impl.DefaultHealthPacketAnalysisService;
import co.darma.smartmattress.analysis.handle.StateChangeHandleService;
import co.darma.smartmattress.entity.MattressPacket;
import co.darma.smartmattress.exception.ParseException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/11/24.
 */
public class StatePacketHandleEngineTest extends BaseTestCase {

    public void testPush() throws ParseException {
        ServicesEngine statePacketHandleEngine = (ServicesEngine) getContext().getBean("statePacketHandleEngine");

        byte[] onesecond = TestIbatis.getOneSecondeByte(0, (byte) 0x3);
        MattressPacket mattressPacket = new MattressPacket();
        mattressPacket.parsePacket(onesecond);

        ServicePacket packet = new ServicePacket(mattressPacket);


        ServiceContext input = new ServiceContext();
        List<ServicePacket> packetList = new LinkedList<>();
        packetList.add(packet);
        input.putArgument(DefaultHealthPacketAnalysisService.PACKET_LIST, packetList);
        input.putArgument(StateChangeHandleService.STAT_PACKET, packet);

        statePacketHandleEngine.process(input);
    }
}
