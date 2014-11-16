package traviata.testbericht;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import traviata.base.xml.XMLDocument;
import traviata.testreport.TbAction;
import traviata.testreport.TbBlock;
import traviata.testreport.TbErrorMessage;
import traviata.testreport.TbLine;
import traviata.testreport.TbTable;
import traviata.testreport.TbTestcase;
import traviata.testreport.TbTestreport;

public class TestTestreportPersistence {

	@Test
	public void tbTestberichtToXML() {
		TbTestreport tb = create();
		XMLDocument dok = tb.getDok();
		dok.saveFile("temp.xml");
		new File("temp.xml").deleteOnExit();
	}
	
	@Test
	public void xmlToTbTestbericht() {
		XMLDocument dok = create().getDok();
		TbTestreport tb = new TbTestreport(dok);
		Assert.assertEquals("Testcases <>", 1, tb.getTestfaelle().size());
		List<TbAction> actions = tb.getTestfaelle().get(0).getActions();
		Assert.assertEquals("Actions <>", 1, actions.size());
		Assert.assertEquals("Items <>", 4, actions.get(0).getItems().size());
		Assert.assertTrue("TbLine <>", actions.get(0).getItems().get(0) instanceof TbLine);
		Assert.assertTrue("TbBlock <>", actions.get(0).getItems().get(1) instanceof TbBlock);
		Assert.assertEquals("TbBlock.title <>", "A block.", ((TbBlock) actions.get(0).getItems().get(1)).getTitle());
		Assert.assertTrue("TbTable <>", actions.get(0).getItems().get(2) instanceof TbTable);
		Assert.assertTrue("TbErrorMessage <>", actions.get(0).getItems().get(3) instanceof TbErrorMessage);
		Assert.assertFalse("TbErrorMessage.text <>", ((TbErrorMessage) actions.get(0).getItems().get(3)).getText().isEmpty());
		Assert.assertNotNull("TbTable.title <>", ((TbTable) actions.get(0).getItems().get(2)).getTitle());
		Assert.assertFalse("TbTable.headers <>", ((TbTable) actions.get(0).getItems().get(2)).getHeaders().isEmpty());
		Assert.assertFalse("TbTable.lines <>", ((TbTable) actions.get(0).getItems().get(2)).getLines().isEmpty());
	}

	private TbTestreport create() {
		TbTestreport tb = new TbTestreport();

		TbTestcase tf = new TbTestcase();
		tf.setTitle("Der 1. Testfall");
		tf.setScriptname("vertrag/Neuanlage");
		tf.setScript("GPVertragNeuanlage\n\tPartnersuche\n\t\tperson = true\nGPVertragAuskunft\n");
		tf.setSize(1);
		tf.setEndtime(new java.util.Date());
		tf.setCurrentNumber(1);
		tf.setRunNumber(1);
		tf.setStarttime(new java.util.Date());
		tf.getVars().put("hey", "ho");
		tb.getTestfaelle().add(tf);
		
		TbAction gp = new TbAction();
		gp.setStarttime(new java.util.Date());
		gp.setTitle("Vertrag:Neuanlage");
		tf.getActions().add(gp);
		
		TbLine z = new TbLine("Vertragsnr", "47110815");
		gp.getItems().add(z);
		
		TbBlock b = new TbBlock();
		b.setTitle("A block.");
		z = new TbLine();
		z.setLabel("Tarif");
		z.setText("T1001");
		b.getLines().add(z);
		b.getLines().add(new TbLine("Produktlinie", "Best Selection"));
		gp.getItems().add(b);
		
		createTabelle(gp);
		
		TbErrorMessage f = new TbErrorMessage();
		f.setText("java.lang.ClassCastException: Das Konvertieren auf Pussemuckel hat nicht geklappt.\n"
				+ "\tat de.irgendwo.Pussemuckel.troete(Pussemuckel.java:17)");
		gp.getItems().add(f);
		
		return tb;
	}

	private void createTabelle(TbAction gp) {
		TbTable t = new TbTable();
		t.setTitle("Eine Tabelle.");
		ArrayList<String> u = new ArrayList<String>();
		u.add("Spalte eins");
		u.add("Zwei");
		u.add("Dritte Spalte");
		t.setHeaders(u);
		List<List<String>> zeilen = new ArrayList<List<String>>();
		List<String> zeile;
		zeile = new ArrayList<String>();
		zeile.add("1.0");
		zeile.add("arabische-eins");
		zeile.add("1");
		zeilen.add(zeile);
		zeile = new ArrayList<String>();
		zeile.add("2");
		zeile.add("2 (Sp.2)");
		zeile.add("2");
		zeilen.add(zeile);
		t.setLines(zeilen);
		gp.getItems().add(t);
	}
}
