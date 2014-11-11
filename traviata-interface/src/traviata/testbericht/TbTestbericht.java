package traviata.testbericht;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import traviata.base.xml.XMLDocument;
import traviata.base.xml.XMLElement;

public class TbTestbericht implements Serializable {
	private final List<TbTestfall> testfaelle = new ArrayList<TbTestfall>();

	public TbTestbericht() {
	}

	public TbTestbericht(XMLDocument dok) {
		for (XMLElement e : dok.getChildren()) {
			testfaelle.add(new TbTestfall(e));
		}
	}
	
	public List<TbTestfall> getTestfaelle() {
		return testfaelle;
	}
	
	public XMLDocument getDok() {
		XMLDocument dok = new XMLDocument("<Testbericht/>");
		XMLElement root = dok.getElement();
		for (TbTestfall tf : testfaelle) {
			tf.appendTo(root);
		}
		return dok;
	}
	
	public TbTestfall getTestfall1() {
		if (testfaelle.size() > 0) {
			return testfaelle.get(0);
		}
		return new TbTestfall();
	}
}
