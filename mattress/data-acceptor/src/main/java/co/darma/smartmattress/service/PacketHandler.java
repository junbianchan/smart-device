package co.darma.smartmattress.service;

import co.darma.smartmattress.entity.PacketContext;

/**
 * Created by frank on 15/10/29.
 */
public interface PacketHandler {

    public void handle(PacketContext context);
}
