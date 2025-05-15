package movtery;

import java.lang.instrument.Instrumentation;

public class MainAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new NoGuiTransformer());
    }
}