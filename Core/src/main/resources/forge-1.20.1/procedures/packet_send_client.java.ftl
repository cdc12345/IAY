if (${input$target} instanceof ServerPlayer _ser)
    ${JavaModName}.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(()->_ser),new StringPacket(${input$msg}));