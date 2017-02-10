package co.darma.smartmattress.analysis.service;

import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.entity.ServicePacket;

import java.util.List;

/**
 * Created by frank on 15/10/12.
 */
public interface PacketAnalysisService {

    public void analysis(ServiceContext input, ServiceContext output);

}
