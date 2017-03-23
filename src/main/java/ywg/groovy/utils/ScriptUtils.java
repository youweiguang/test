package ywg.groovy.utils;

import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author youwg
 * @date 2017年3月23日
 */
public class ScriptUtils {
	private static Map<String, Script> scripts = new HashMap<String, Script>();
	public static boolean needUpdate = false;
	
	public static Script parseScript(File scriptCode) {
		try {
			String name = scriptCode.getName();
			Script script = scripts.get(name);
			if (script == null || needUpdate) {
				GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
				Class<?> scriptClass = groovyClassLoader.parseClass(scriptCode);
				script = (Script) scriptClass.newInstance();
				scripts.put(name, script);
				needUpdate = false;
				System.err.println("Groovy updated...");
			}
			return script;
		} catch (Throwable e) {
			return null;
		}
	}
}
