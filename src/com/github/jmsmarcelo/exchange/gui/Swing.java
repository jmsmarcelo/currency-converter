package com.github.jmsmarcelo.exchange.gui;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import com.github.jmsmarcelo.exchange.head.Converter;
import com.github.jmsmarcelo.exchange.head.Inflation;
import com.github.jmsmarcelo.exchange.head.Selic;

public class Swing {

	private Object[] optMenu = {"Conversor de Moeda", "Taxa Selic", "Inflação"};
	private String selectedOpt;
	private double moneyInput;
	private Object[] typesConversion = {
			"De Reais para Dólares",
			"De Reais para Euros",
			"De Reais para Libras Esterlinas",
			"De Reais para Pesos Argentinos",
			"De Reais para Pesos Chilenos",
			"De Dólares para Reais",
			"De Euros para Reais",
			"De Libras Esterlinas para Reais",
			"De Pesos Argentinos para Reais",
			"De Pesos Chilenos para Reais"
	};
	private Object typeConversion;
	private Object nextCmd = true;
	
	public void get() {
		nextCmd = true;
		this.optMenuMain();
		if(this.selectedOpt == "Conversor de Moeda")
			this.setValue();
		else if(this.selectedOpt == "Taxa Selic")
			try {
				this.getSelic();
				return;
			} catch (HeadlessException | IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		else if(this.selectedOpt == "Inflação")
			try {
				this.getInflation();
				return;
			} catch (HeadlessException | IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		else return;
		if(nextCmd != null)
			this.setTypeConversion();
		else
			return;
		if(typeConversion != null)
			this.getExchange();
		else return;
	}
	
	private void optMenuMain() {
		Object menuMain = JOptionPane.showInputDialog(null, "Escolha uma opção",
				"Menu Principal", JOptionPane.DEFAULT_OPTION, null, optMenu, null);
		selectedOpt = (String) menuMain;
	}
	private void setValue() {
		Object inValue = JOptionPane.showInputDialog(null, "Insira um valor",
				"Valor a converter", JOptionPane.NO_OPTION);
		if(inValue != null) {
			if(((String) inValue).matches("\\d+[\\.|\\,]?\\d*"))
				moneyInput = Double.parseDouble(((String) inValue).replace(",", "."));
			else this.valInvalid();
		} else
			nextCmd = inValue;
	}
	private void valInvalid() {
		JOptionPane.showMessageDialog(null, "Valor inválido", "Só é permitido números", 0);
		this.setValue();
	}
	private void setTypeConversion() {
		typeConversion = JOptionPane.showInputDialog(null,
				"Escolha as moedas que você deseja converter",
				"Moedas", JOptionPane.DEFAULT_OPTION, null, typesConversion, null);
		
	}
	private void getExchange() {
		Converter exchange = new Converter();
		JOptionPane.showMessageDialog(null, exchange.getCurrency(moneyInput, typeConversion.toString()),
				typeConversion.toString(), 1);
		this.endDialog();
	}
	private void getSelic() throws HeadlessException, IOException, URISyntaxException {
		Selic todaySelic = new Selic();
		JOptionPane.showMessageDialog(null, todaySelic.get(),
				"Taxa Selic", 1);
		this.endDialog();
	}
	private void getInflation() throws HeadlessException, IOException, URISyntaxException {
		Inflation todayInflation = new Inflation();
		JOptionPane.showMessageDialog(null, todayInflation.get(),
				"Inflação", 1);
		this.endDialog();
	}
	private void endDialog() {
		Object endMessage = JOptionPane.showConfirmDialog(null,
				"Deseja continuar?", "Escolha uma Opção",
				JOptionPane.YES_NO_CANCEL_OPTION);
		
		if((int)endMessage == 0) this.get();
		else if((int)endMessage == 1) endInfo("Programa finalizado");
		else endInfo("Programa concluído");
	}
	private void endInfo(String ei) {
		JOptionPane.showMessageDialog(null, ei,
				"github.com/jmsmarcelo", 1);
	}

}
