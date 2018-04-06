package tela;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Botao extends JButton {
	ImageIcon image;
	private String cor;
	private String corB;
	ImageIcon icon;
	File file;

	//faz set up dos botões
	public Botao(String cor, String corB) {
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		//Preenche o botão com a imagem removendo as bordas
		setBorder(BorderFactory.createEmptyBorder());
		icon = new ImageIcon(cor);
		setIcon(icon);
		this.cor = cor;
		this.corB = corB;
	}

	public Botao() {

	}

	public int blink() {
		int resultado = -1;
		;

		switch (cor) {
		case "image/amarelo.png":

			resultado = 1;
			break;

		case "image/azul.png":

			resultado = 2;
			break;
		case "image/verde.png":

			resultado = 3;
			break;

		case "image/vermelho.png":

			resultado = 4;
			break;
		}

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				//System.out.println("Mudança está sendo executada: " + cor);
				try {
					emitirSom();
					muda(500);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		t1.start();

		return resultado;
	}
	
	//Faz o botão start piscar de maneira independente
	public void startBlink() {

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				//System.out.println("Mudança está sendo executada: " + cor);
				try {
					muda(150);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		t1.start();

	}
	
	//Função para fazer os botões piscarem implementada em runnable, para que possa ser executado em sequencia.
	public Runnable blinkSequence() {

		Runnable r = new Runnable() {

			@Override
			public void run() {
				// muda as cores par piscar os botões
				emitirSom();
				muda(500);

			}
		};

		return r;
	}
	
	//Função que muda a cor dos botões
	public void muda(int delay) {
		//System.out.println("Mudança está sendo executada: " + cor);
		try {

			image = new ImageIcon(corB);
			setIcon(image);
			Thread.sleep(delay);
			image = new ImageIcon(cor);
			setIcon(image);
			Thread.sleep(delay);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//Função para emitir som ao clicar nos botões	
	public void emitirSom() {

		switch (cor) {
		case "image/amarelo.png":

			file = new File("sounds/sound1.wav");

			break;

		case "image/azul.png":

			file = new File("sounds/sound3.wav");

			break;
		case "image/verde.png":

			file = new File("sounds/sound2.wav");

			break;

		case "image/vermelho.png":

			file = new File("sounds/sound4.wav");

			break;
		}

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getCor() {
		return cor;
	}

	public String getCorB() {
		return corB;
	}

	public void setCor(String cor) {
		this.cor = cor;
		icon = new ImageIcon(cor);
		setIcon(icon);
	}

	public void setCorB(String corB) {
		this.corB = corB;
		icon = new ImageIcon(corB);
		setIcon(icon);
	}

}
