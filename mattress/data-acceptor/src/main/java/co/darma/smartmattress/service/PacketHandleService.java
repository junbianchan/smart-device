package co.darma.smartmattress.service;


import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.entity.ValidateResult;
import co.darma.smartmattress.parser.tool.ChannelCenter;
import co.darma.smartmattress.tool.ValidatorHandler;
import org.apache.log4j.Logger;

/**
 * 从队列中获取数据，并进行解析
 * <p/>
 * Created by frank on 15/10/12.
 */
public class PacketHandleService {

    private static Logger logger = Logger.getLogger(PacketHandleService.class);

    private ValidatorHandler packetValidatorHandle;

    private CommandServiceMapper mapper;


    public void handle(PacketContext context) {

        ValidateResult validateResult = packetValidatorHandle.doHandle(context);
        if (validateResult != null) {
            //TODO throw excetion;
        }

        mapper.mapperAndDeal(context.getPacket().getCommand(), context);
    }

    public void setPacketValidatorHandle(ValidatorHandler packetValidatorHandle) {
        this.packetValidatorHandle = packetValidatorHandle;
    }

    public void setMapper(CommandServiceMapper mapper) {
        this.mapper = mapper;
    }
}
