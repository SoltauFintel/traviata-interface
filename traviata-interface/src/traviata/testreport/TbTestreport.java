package traviata.testreport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import traviata.base.xml.XMLDocument;
import traviata.base.xml.XMLElement;

// Prefix "Tb" = Test report (Testbericht in German)
public class TbTestreport implements Serializable {
	private final List<TbTestcase> testcases = new ArrayList<TbTestcase>();
	private final Map<String, String> parameters = new TreeMap<String, String>();
	private String version;

	public TbTestreport() {
	}

	public TbTestreport(XMLDocument doc) {
		for (XMLElement e : doc.getElement().selectNodes("head/parameter")) {
			parameters.put(e.getValue("name"), e.getText());
		}
		XMLElement ee = doc.getElement().selectSingleNode("version");
		version = ee == null ? "" : ee.getText();
		for (XMLElement e : doc.getElement().selectNodes("Testcase")) {
			testcases.add(new TbTestcase(e));
		}
	}
	
	public List<TbTestcase> getTestcases() {
		return testcases;
	}
	
	public Map<String, String> getParameters() {
		return parameters;
	}
	
	public void setPar(String name, String value) {
		parameters.put(name, value);
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public XMLDocument getDoc() {
		XMLDocument doc = new XMLDocument("<Testreport/>");
		XMLElement root = doc.getElement();
		XMLElement head = root.add("head");
		for (Map.Entry<String, String> e : parameters.entrySet()) {
			XMLElement parameter = head.add("parameter");
			parameter.setValue("name", e.getKey());
			parameter.setText(e.getValue());
		}
		root.add("version").setText(version == null ? "" : version);
		for (TbTestcase tf : testcases) {
			tf.appendTo(root);
		}
		return doc;
	}
	
	public TbTestcase getFirstTestcase() {
		if (testcases.size() > 0) {
			return testcases.get(0);
		}
		TbTestcase first = new TbTestcase();
		testcases.add(first);
		return first;
	}
	
	public TbTestcase getLastTestcase() {
		return testcases.get(testcases.size() - 1);
	}
}
