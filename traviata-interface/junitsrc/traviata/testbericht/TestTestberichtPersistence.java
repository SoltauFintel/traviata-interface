package traviata.testbericht;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import traviata.base.xml.XMLDocument;
import traviata.testbericht.TbAction;
import traviata.testbericht.TbBlock;
import traviata.testbericht.TbFehlermeldung;
import traviata.testbericht.TbTabelle;
import traviata.testbericht.TbTestbericht;
import traviata.testbericht.TbTestfall;
import traviata.testbericht.TbZeile;

public class TestTestberichtPersistence {

	@Test
	public void tbTestberichtToXML() {
		TbTestbericht tb = create();
		XMLDocument dok = tb.getDok();
		dok.saveFile("temp.xml");
	}
	
	@Test
	public void xmlToTbTestbericht() {
		XMLDocument dok = create().getDok();
		TbTestbericht tb = new TbTestbericht(dok);
		Assert.assertEquals("Testfälle <>", 1, tb.getTestfaelle().size());
		List<TbAction> actions = tb.getTestfaelle().get(0).getActions();
		Assert.assertEquals("Actions <>", 1, actions.size());
		Assert.assertEquals("Items <>", 4, actions.get(0).getItems().size());
		Assert.assertTrue("TbZeile <>", actions.get(0).getItems().get(0) instanceof TbZeile);
		Assert.assertTrue("TbBlock <>", actions.get(0).getItems().get(1) instanceof TbBlock);
		Assert.assertEquals("TbBlock.titel <>", "Ein Block.", ((TbBlock) actions.get(0).getItems().get(1)).getTitel());
		Assert.assertTrue("TbTabelle <>", actions.get(0).getItems().get(2) instanceof TbTabelle);
		Assert.assertTrue("TbFehlermeldung <>", actions.get(0).getItems().get(3) instanceof TbFehlermeldung);
		Assert.assertFalse("TbFehlermeldung.text <>", ((TbFehlermeldung) actions.get(0).getItems().get(3)).getText().isEmpty());
		Assert.assertNotNull("TbTabelle.titel <>", ((TbTabelle) actions.get(0).getItems().get(2)).getTitel());
		Assert.assertFalse("TbTabelle.ueberschriften <>", ((TbTabelle) actions.get(0).getItems().get(2)).getUeberschriften().isEmpty());
		Assert.assertFalse("TbTabelle.zeilen <>", ((TbTabelle) actions.get(0).getItems().get(2)).getZeilen().isEmpty());
	}

	private TbTestbericht create() {
		TbTestbericht tb = new TbTestbericht();

		TbTestfall tf = new TbTestfall();
		tf.setTitel("Der 1. Testfall");
		tf.setDateinameVTScript("vertrag/Neuanlage");
		tf.setVTScript("GPVertragNeuanlage\n\tPartnersuche\n\t\tperson = true\nGPVertragAuskunft\n");
		tf.setAnzahl(1);
		tf.setEndezeit(new java.util.Date());
		tf.setLfdNr(1);
		tf.setLauf(1);
		tf.setStartzeit(new java.util.Date());
		tf.getVars().put("hey", "ho");
		tb.getTestfaelle().add(tf);
		
		TbAction gp = new TbAction();
		gp.setStartzeit(new java.util.Date());
		gp.setTitel("Vertrag:Neuanlage");
		tf.getActions().add(gp);
		
		TbZeile z = new TbZeile("Vertragsnr", "47110815");
		gp.getItems().add(z);
		
		TbBlock b = new TbBlock();
		b.setTitel("Ein Block.");
		z = new TbZeile();
		z.setLabel("Tarif");
		z.setText("T1001");
		b.getZeilen().add(z);
		b.getZeilen().add(new TbZeile("Produktlinie", "Best Selection"));
		gp.getItems().add(b);
		
		createTabelle(gp);
		
		TbFehlermeldung f = new TbFehlermeldung();
		f.setText("java.lang.ClassCastException: Das Konvertieren auf Pussemuckel hat nicht geklappt.\n"
				+ "\tat de.irgendwo.Pussemuckel.troete(Pussemuckel.java:17)");
		gp.getItems().add(f);
		
		return tb;
	}

	private void createTabelle(TbAction gp) {
		TbTabelle t = new TbTabelle();
		t.setTitel("Eine Tabelle.");
		ArrayList<String> u = new ArrayList<String>();
		u.add("Spalte eins");
		u.add("Zwei");
		u.add("Dritte Spalte");
		t.setUeberschriften(u);
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
		t.setZeilen(zeilen);
		gp.getItems().add(t);
	}
}
