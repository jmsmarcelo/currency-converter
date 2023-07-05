package com.github.jmsmarcelo.exchange.gui;

import javax.swing.JOptionPane;

import com.github.jmsmarcelo.exchange.head.Converter;
import com.github.jmsmarcelo.exchange.head.Inflation;
import com.github.jmsmarcelo.exchange.head.Selic;

public class Swing {
	private Object[] optMenu = {"Conversor de Moeda", "Taxa Selic", "Infla��o"};
	private double moneyInput;
	private Object[][] convTypes = {
			{"De Reais para D�lares",
			"De Reais para Euros",
			"De Reais para Libras Esterlinas",
			"De Reais para Pesos Argentinos",
			"De Reais para Pesos Chilenos",
			"De D�lares para Reais",
			"De Euros para Reais",
			"De Libras Esterlinas para Reais",
			"De Pesos Argentinos para Reais",
			"De Pesos Chilenos para Reais"},
			{"BRL-USD", "BRL-EUR", "BRL-GBP", "BRL-ARS", "BRL-CLP",
				"USD-BRL", "EUR-BRL", "GBP-BRL", "ARS-BRL", "CLP-BRL"}
	};
	private Object selectedOpt = convTypes[0][5];
	private Object convType;
	private Boolean nextCmd = true;
	private Object input;
	
	public void get() {
		
		while(nextCmd) {
			optMenuMain();
			if(input == null) break;
			if(input == "Conversor de Moeda") {
				setValue();
				if(input == null) continue;
				setConType();
				if(input == null) continue;
				getExchange();
			}
			else if(input == "Taxa Selic")
				getSelic();
			else if(input == "Infla��o")
				getInflation();
			if(nextCmd == false) break;

		}
	}
	
	private void optMenuMain() {
		input = JOptionPane.showInputDialog(null, "Escolha uma op��o",
				"Menu Principal", JOptionPane.DEFAULT_OPTION, null, optMenu, null);
		if(input == null) return;
	}
	private void setValue() {
		input = JOptionPane.showInputDialog(null, "Insira um valor",
				"Valor a converter", JOptionPane.NO_OPTION);
		if(input == null) return;
			if(((String)input).matches("\\d+[\\.|\\,]?\\d*"))
				moneyInput = Double.parseDouble(((String)input).replace(",", "."));
			else
				this.valInvalid();
	}
	private void valInvalid() {
		endInfo("S� � permitido n�meros", "Valor inv�lido");
		this.setValue();
	}
	private void setConType() {
		input = JOptionPane.showInputDialog(null,
				"Escolha as moedas que voc� deseja converter",
				"Moedas", JOptionPane.DEFAULT_OPTION, null, convTypes[0], selectedOpt);
		if(input == null) return;
		selectedOpt = convType = input;
	}
	private String getConvType(Object m) {
		for(int i = 0; i < convTypes[0].length; i++)
			if((convTypes[0][i].toString()).matches(m.toString()))
				return (String)convTypes[1][i];
		return null;
	}
	private void getExchange() {
		Converter exchange = new Converter();
		JOptionPane.showMessageDialog(null, exchange.getCurrency(moneyInput, getConvType(convType)),
				(String)convType, 1);
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
				null, todayInflation.get(), "Infla��o", 1);
		this.endDialog();
	}
	private void endDialog() {
		input = JOptionPane.showConfirmDialog(
				null, "Deseja continuar?", "Escolha uma Op��o",
				JOptionPane.YES_NO_CANCEL_OPTION);
		
		if((int)input == 0) this.get();
		else if((int)input == 1) endInfo("Programa finalizado", "github.com/jmsmarcelo");
		else endInfo("Programa conclu�do", "github.com/jmsmarcelo");
		nextCmd = false;
	}
	private void endInfo(String msg, String tt) {
		JOptionPane.showMessageDialog(
				null, msg, tt, 1);
	}
}
