package scolomfr.web.tests.controller.response;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class Result {

	private Integer errors;

	private TreeMap<String, ArrayList<String>> content;

	public Integer getErrors() {
		return errors;
	}

	public void setErrors(Integer errors) {
		this.errors = errors;
	}

	public TreeMap<String, ArrayList<String>> getContent() {
		return content;
	}

	public void setContent(TreeMap<String, ArrayList<String>> content) {
		this.content = content;
	}

}
