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

	public TbTestreport(XMLDocument dok) {
		for (XMLElement e : dok.getChildren()) {
			testcases.add(new TbTestcase(e));
		}
	}
	
	public List<TbTestcase> getTestfaelle() {
		return testcases;
	}
	
	public XMLDocument getDok() {
		XMLDocument dok = new XMLDocument("<Testreport/>");
		XMLElement root = dok.getElement();
		for (TbTestcase tf : testcases) {
			tf.appendTo(root);
		}
		return dok;
	}
	
	public TbTestcase getTestfall1() {
		if (testcases.size() > 0) {
			return testcases.get(0);
		}
		return new TbTestcase();
	}
}
