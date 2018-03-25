package cn.itcast.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkTest {
	
	
	
	@Test
	public void test() throws Exception{
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
		
		configuration.setDirectoryForTemplateLoading(new File("src/main/webapp/WEB-INF/templates"));
		
		Template template = configuration.getTemplate("hello.ftl");
		
		Map<String,Object> m = new HashMap<String,Object>();
		
		m.put("name","sadf");
		m.put("message","sadf");
		
		template.process(m, new PrintWriter(System.out));

		
		
		
	}
}
