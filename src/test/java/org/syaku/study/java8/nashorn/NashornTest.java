package org.syaku.study.java8.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class NashornTest {
    @Test
    public void test() throws Exception {
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        scriptEngine.eval("print('Hello World~~')");
    }
}
