package com.github.jmsmarcelo.exchange.gui;

import javax.swing.JOptionPane;

import com.github.jmsmarcelo.exchange.head.Converter;
import com.github.jmsmarcelo.exchange.head.Inflation;
import com.github.jmsmarcelo.exchange.head.Selic;

public class Swing {
	private Object[] optMenu = {"Conversor de Moeda", "Taxa Selic", "Inflação"};
	private String selectedOpt;
	private double moneyInput;
	private Object[][] convTypes = {
			{"De Reais para Dólares",
			"De Reais para Euros",
			"De Reais para Libras Esterlinas",
			"De Reais para Pesos Argentinos",
			"De Reais para Pesos Chilenos",
			"De Dólares para Reais",
			"De Euros para Reais",
			"De Libras Esterlinas para Reais",
			"De Pesos Argentinos para Reais",
			"De Pesos Chilenos para Reais"},
			{"BRL-USD", "BRL-EUR", "BRL-GBP", "BRL-ARS", "BRL-CLP",
				"USD-BRL", "EUR-BRL", "GBP-BRL", "ARS-BRL", "CLP-BRL"}
	};
	private Object convType;
	private Object nextCmd = true;
	
	public void get() {
		nextCmd = true;
		this.optMenuMain();
		if(this.selectedOpt == "Conversor de Moeda")
			this.setValue();
		else if(this.selectedOpt == "Taxa Selic") {
			this.getSelic();
			return;
		}
		else if(this.selectedOpt == "Inflação") {
			this.getInflation();
			return;
		}
		else return;
		if(nextCmd != null)
			this.setConType();
		else
			return;
		if(convType != null)
			this.getExchange();
		else return;
	}
	
	private void optMenuMain() {
		Object menuMain = JOptionPane.showInputDialog(null, "Escolha uma opção",
				"Menu Principal", JOptionPane.DEFAULT_OPTION, null, optMenu, null);
		selectedOpt = menuMain.toString();
	}
	private void setValue() {
		Object inValue = JOptionPane.showInputDialog(null, "Insira um valor",
				"Valor a converter", JOptionPane.NO_OPTION);
		if(inValue != null) {
			if(((String)inValue).matches("\\d+[\\.|\\,]?\\d*"))
				moneyInput = Double.parseDouble(((String)inValue).replace(",", "."));
			else
				this.valInvalid();
		} else
			nextCmd = inValue;
	}
	private void valInvalid() {
		JOptionPane.showMessageDialog(null, "Valor inválido", "Só é permitido números", 0);
		this.setValue();
	}
	private void setConType() {
		convType = JOptionPane.showInputDialog(null,
				"Escolha as moedas que você deseja converter",
				"Moedas", JOptionPane.DEFAULT_OPTION, null, convTypes[0], convTypes[0][5]);
	}
	private String getConvType(Object m) {
		for(int i = 0; i < convTypes[0].length; i++)
			if((convTypes[0][i].toString()).matches(m.toString()))
				return convTypes[1][i].toString();
		return null;
	}
	private void getExchange() {
		Converter exchange = new Converter();
		JOptionPane.showMessageDialog(null, exchange.getCurrency(moneyInput, getConvType(convType)),
				convType.toString(), 1);
		this.endDialog();
	}
	private void getSelic() {
		Selic todaySelic = new Selic();
		JOptionPane.showMessageDialog(
				null, todaySelic.get(), "Taxa Selic", 1);
		this.endDialog();
	}
	private void getInflation() {
		Inflation todayInflation = new Inflation();
		JOptionPane.showMessageDialog(
				null, todayInflation.get(), "Inflação", 1);
		this.endDialog();
	}
	private void endDialog() {
		Object endMessage = JOptionPane.showConfirmDialog(
				null, "Deseja continuar?", "Escolha uma Opção",
				JOptionPane.YES_NO_CANCEL_OPTION);
		
		if((int)endMessage == 0) this.get();
		else if((int)endMessage == 1) endInfo("Programa finalizado");
		else endInfo("Programa concluído");
	}
	private void endInfo(String ei) {
		JOptionPane.showMessageDialog(
				null, ei, "github.com/jmsmarcelo", 1);
	}
}
