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
				"Menu Principal", -1, null, optMenu, null);
		if(input == null) return;
	}
	private void setValue() {
		input = JOptionPane.showInputDialog(null, "Insira um valor", "Valor a converter", 1);
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
		input = JOptionPane.showInputDialog(null, "Escolha as moedas que voc� deseja converter",
				"Moedas", -1, null, convTypes[0], selectedOpt);
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
		try {
			endInfo(exchange.getCurrency(moneyInput, getConvType(convType)), (String)convType);
		} catch (Exception e) {
			endInfo("N�o foi poss�vel obter os dados!", (String)convType);
		}
		
		this.endDialog();
	}
	private void getSelic() {
		Selic todaySelic = new Selic();
		try {
			endInfo(todaySelic.get(), "Taxa Selic");
		} catch (Exception e) {
			endInfo("N�o foi poss�vel obter os dados!", "Taxa Selic");
		}
		this.endDialog();
	}
	private void getInflation() {
		Inflation todayInflation = new Inflation();
		try {
			endInfo(todayInflation.get(), "Infla��o");
		} catch (Exception e) {
			endInfo("N�o foi poss�vel obter os dados!", "Infla��o");
		}
		this.endDialog();
	}
	private void endDialog() {
		input = JOptionPane.showConfirmDialog(null, "Deseja continuar?", "Escolha uma Op��o", 1);
		if((int)input == 0) return;
		else if((int)input == 1) endInfo("Programa finalizado", "github.com/jmsmarcelo");
		else endInfo("Programa conclu�do", "github.com/jmsmarcelo");
		nextCmd = false;
	}
	private void endInfo(String msg, String tt) {
		JOptionPane.showMessageDialog(null, msg, tt, 1);
	}
}
