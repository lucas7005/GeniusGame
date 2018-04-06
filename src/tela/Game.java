package tela;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game {
	
	
	boolean verificador = false; //verifica se o jogo foi iniciado após apertar start
	int lvl = 1; // define a quantidade de vezes que as luzes serão piscadas
	int nivel = 1; //o nível inicial
	List<Integer> valida = new ArrayList<Integer>(); // Recebe a sequencia de cores 
	List<Integer> answer = new ArrayList<Integer>(); // recebe a resposta
	ExecutorService executorService = Executors.newSingleThreadExecutor(); // Cria o executor das threads
	int count = 0; // contador para percorrer os vetores
	Player player = new Player(); // Cria o jogador para receber os pontos
	int pontos = 0; //Pontos

	// Configração da Janela
	JFrame janela = new JFrame("Genius");
	JPanel painel = new JPanel(new GridLayout(3, 2, 4, 4));
	JPanel linha1 = new JPanel(new GridLayout(1, 2, 4, 4));
	JPanel linha2 = new JPanel(new GridLayout(1, 2, 4, 4));
	JPanel linha3 = new JPanel(new GridLayout(1, 1));
	

	// botões com os caminhos das imagens
	Botao verde = new Botao("image/verde.png", "image/verdeBlink.png");
	Botao amarelo = new Botao("image/amarelo.png", "image/amareloBlink.png");
	Botao vermelho = new Botao("image/vermelho.png", "image/vermelhoBlink.png");
	Botao azul = new Botao("image/azul.png", "image/azulBlink.png");
	Botao start = new Botao("image/start.png", "image/startBlink.png");

	public void desenhaTela() {
		// Aplica a ação listener nos botões
		start.addActionListener(new Acao());
		vermelho.addActionListener(new Acao());
		verde.addActionListener(new Acao());
		azul.addActionListener(new Acao());
		amarelo.addActionListener(new Acao());

		
		// add os botões no painel conforme o layout
		linha1.add(verde);
		linha1.add(amarelo);
		painel.add(linha1);
		
		linha2.add(azul);
		linha2.add(vermelho);
		painel.add(linha2);

		linha3.add(start);
		painel.add(linha3);
		janela.add(painel);
		
		// faz o set da cor de fundo em todos os paineis
		painel.setBackground(Color.white);
		linha1.setBackground(Color.white);
		linha2.setBackground(Color.white);
		linha3.setBackground(Color.white);

		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.pack();
		// centraliza a janela
		janela.setLocationRelativeTo(null);
		// torna a janela visível
		janela.setVisible(true);
		
		
		//Abre a janela para a inserção do nome do jogador
		while(player.getNome() == null) {
			
			String nome;
			nome = JOptionPane.showInputDialog("Qual o seu nome?");
			
			//Se o botão cancelar for clicado o programa fecha
			if(nome == null) {
				System.exit(0);
			}else {				
				player.setNome(nome);
			}
			
		}

	}

	public class Acao implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(start)) {
				if(!verificador) {
					verificador = true;
				start.startBlink();
				sequencia();
				}
				
			
			}

			if (e.getSource().equals(vermelho) && count <= answer.size()) {

				answer.add(count, vermelho.blink());

				logicaJogo();

			}

			if (e.getSource().equals(verde) && count <= answer.size()) {

				answer.add(count, verde.blink());


				logicaJogo();

			}

			if (e.getSource().equals(azul) && count <= answer.size()) {

				answer.add(count, azul.blink());

				logicaJogo();

			}

			if (e.getSource().equals(amarelo) && count <= answer.size()) {

				answer.add(count, amarelo.blink());

				logicaJogo();

			}

		}

	}


	//Função para fazer os botões piscarem em sequencia
	public int sequencia() {

		int jogoIniciado = 1;

		// loop recebe o level com a quantidade de vezes que será piscada
		for (int i = count; i < lvl; i++) {
			// Cria um Random
			Random r = new Random();

			// Switch recebe o random com números entre de 0 á 3
			// cada número representa uma luz que será piscada
			switch (r.nextInt(4)) {
			case 0:
				valida.add(i, 1);
				break;
			case 1:
				valida.add(i, 2);
				break;

			case 2:
				valida.add(i, 3);
				break;
			case 3:
				valida.add(i, 4);
				break;

			}

		}
		
		
		//lopp que faz os botões piscarem conforme a sequencia recebida randomicamente
		for (int i = 0; i < valida.size(); i++) {
			
			//Imprime os valores com as sequencias preenchidas
			//System.out.println(valida.get(i));

			switch (valida.get(i)) {
			case 1:
				executorService.submit(amarelo.blinkSequence());
				break;
			case 2:
				executorService.submit(azul.blinkSequence());
				break;
			case 3:
				executorService.submit(verde.blinkSequence());
				break;
			case 4:
				executorService.submit(vermelho.blinkSequence());
				break;

			}

		}

		return jogoIniciado;

	}

	public void logicaJogo() {

		// implementa a lógica do jogo

		try {
			
			//compara o valor da mesma posição dos dois vetores para verificar se o botão clicado é o mesmo do que foi pedido
			if (answer.get(count).equals(valida.get(count))) {
				count++;
				pontos += 10;

				//Se o contador chega no mesmo valor do level, o list de sequencia recebe mais duas cores
				if (count == lvl) {
					lvl = lvl + 1;
					nivel++;
					//if()
					JOptionPane.showMessageDialog(null, "Parabéns " + player.getNome() +"!"+ " \nVocê passou para o nível " + nivel, "Genius Game",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("image/win.png") );
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					//Função sequencia é iniciada novamente
					sequencia();
					
					//contado recebe zero para percorrer o vetor desde o inicio novamente a cada nível
					count = 0;

				}

			} else {
				//Insere a pontuação na classe player
				player.setPontos(pontos);
				JOptionPane.showMessageDialog(null, "Que pena " + player.getNome() + " você Perdeu! \nSua pontuação foi de " + player.getPontos() + " Pontos" + "\nVocê Chegou ao Nível " + nivel,"Game Over",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("image/lost.png"));
				//Faz o set de toda a lógica voltar ao inicio.
				valida.clear();
				answer.clear();
				count = 0;
				lvl = 1;
				nivel = 0;
				pontos = 0;
				//verificador recebe false para que o botão start possa ser apertado novamente
				verificador = false;
			}

		} catch (IndexOutOfBoundsException e) {
			// Faz o controle se o botão start foi iniciado, ao contrario retornará uma
			// mensagem de erro.
			JOptionPane.showMessageDialog(null, "Jogo não iniciado, inicie o jogo!");
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
