package traviata.testreport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import traviata.base.xml.XMLDocument;
import traviata.base.xml.XMLElement;

// Prefix "Tb" = Test report (Testbericht in German)
public class TbTestreport implements Serializable {
	private final List<TbTestcase> testcases = new ArrayList<TbTestcase>();

	public TbTestreport() {
	}

	public TbTestreport(XMLDocument doc) {
		for (XMLElement e : doc.getChildren()) {
			testcases.add(new TbTestcase(e));
		}
	}
	
	public List<TbTestcase> getTestfaelle() {
		return testcases;
	}
	
	public XMLDocument getDoc() {
		XMLDocument doc = new XMLDocument("<Testreport/>");
		XMLElement root = doc.getElement();
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
