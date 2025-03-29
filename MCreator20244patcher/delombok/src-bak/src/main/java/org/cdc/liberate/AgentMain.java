package org.cdc.liberate;

import org.cdc.liberate.transfer.AgentClassTransfer;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

public class AgentMain {
    public static void premain(String args, Instrumentation instrumentation){
        Liberate liberate = Liberate.getInstance();
        liberate.setAgentMod(true);

        if ("safeMode".equalsIgnoreCase(args)){
            Liberate.getInstance().setSafeMod(true);
            return;
        }

        try {
            Liberate.getInstance().setAgentJar(new JarFile(System.getenv("agentPath").replaceAll("\"","")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        instrumentation.addTransformer(liberate.agentClassTransfer = new AgentClassTransfer());
    }
}
