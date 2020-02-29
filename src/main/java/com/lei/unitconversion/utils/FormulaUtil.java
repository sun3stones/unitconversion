package com.lei.unitconversion.utils;

import javax.script.*;
import java.math.BigDecimal;

public class FormulaUtil {

    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

    public static BigDecimal calculate(String script, BigDecimal value){
        BigDecimal result = null;
        Compilable compilable = (Compilable) engine;
        Bindings bindings = engine.createBindings(); //Local级别的Binding
        //String script = "(X-32)*5/9"; //定义函数并调用
        try {
            CompiledScript jSFunction = compilable.compile(script);//解析编译脚本函数
            bindings.put("X", value);
            Double rs = (Double) jSFunction.eval(bindings);
            result = BigDecimal.valueOf(rs);
        } catch (ScriptException e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }
}
