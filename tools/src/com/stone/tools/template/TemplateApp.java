package com.stone.tools.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.stone.tools.template.parser.ITemplateFileParser;
import com.stone.tools.template.parser.RegularTemplateParser;
import com.stone.tools.template.type.ITemplateObject;

/**
 * Template app;
 * 
 * @author crazyjohn
 *
 */
public class TemplateApp {

	public static void main(String[] args) throws Exception {
		// build parser
		ITemplateFileParser parser = new RegularTemplateParser();
		// parse the file
		List<ITemplateObject> templates = parser.parseFile(System
				.getProperty("user.dir")
				+ "/resources/template/data/Item.templ");
		// context
		for (ITemplateObject eachTemplate : templates) {
			generateTemplateClassFile(eachTemplate);
		}
	}

	/**
	 * Generate template class file;
	 * 
	 * @param eachTemplate
	 * @throws IOException
	 */
	private static void generateTemplateClassFile(ITemplateObject eachTemplate)
			throws IOException {
		Properties props = new Properties();
		props.put("file.resource.loader.path", "resources/template");
		Velocity.init(props);
		VelocityContext context = new VelocityContext();
		context.put("templateName", eachTemplate.getName());
		context.put("fields", eachTemplate.getAllFileds());
		// merge
		FileWriter out = new FileWriter(
				new File(System.getProperty("user.dir")
						+ "/resources/template/src/" + eachTemplate.getName()
						+ ".java"));
		Velocity.mergeTemplate("Template.vm", "UTF-8", context, out);
		// close
		out.close();
		System.out.println("Succeed generate class file: "
				+ eachTemplate.getName() + ".");
	}
}
