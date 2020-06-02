import java.util.InputMismatchException;
import java.util.Scanner;

import imagem.Imagem;

public class Main {
	public static void main(String[] args) {
		System.out.println("    Processamento digital de imagens com open cv    ");
		Scanner scan = new Scanner(System.in);
		Imagem imagem = new Imagem();
		String menu = "Menu \n" + "1. Carrega imagem para o programa \n" + "2. Aplica filtro de cinza \n"
				+ "3. Realiza a detecção de borda por uma das opções \n" + "20. Verifica RGB de um pixel \n"
				+ "0. Sai do programa";

		int op = -1;

		do {
			try {
				System.out.println();
				System.out.println(menu);
				System.out.print("escolha uma opção: ");
				op = scan.nextInt();
				scan.nextLine();

				switch (op) {
				case 1:
					System.out.println("\nCarrega imagem para o programa");
					imagem.escolheImagem(scan);
					break;

				case 2:
					if (imagem.isExisteImg()) {
						System.out.println("\nAplica filtro de cinza");
						imagem.aplicaFiltroDeCinza();
					} else {
						System.out.println("\nNão existe imagem carregada no programa");
					}
					break;

				case 3:
					if (imagem.isExisteImg()) {
						int opDetec = imagem.escolheDeteccaoDeBorda(scan);
						if (opDetec == 1) {
							System.out.println("Detecção de bordas pelo algoritmo de Roberts");
							// aqui
						}
						if (opDetec == 2) {
							System.out.println("Detecção de bordas pelo algoritmo de Sobel");
							imagem.aplicaDeteccaoDeBordaComSobel(scan);
						}
						if (opDetec == 3) {
							System.out.println("Detecção de bordas pelo algoritmo de Canny");
							imagem.aplicaDeteccaoDeBordaComCanny(scan);
						}
					} else {
						System.out.println("\nNão existe imagem carregada no programa");
					}
					break;

				case 20:
					if (imagem.isExisteImg()) {
						System.out.println("\nVerifica um pixel");
						imagem.mostraRGBDoPixel(scan);
					} else {
						System.out.println("\nNão existe imagem carregada no programa");
					}
					break;

				case 0:
					break;

				default:
					System.out.println("\nopção inválida");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				scan.nextLine();
				op = -1;
			}
		} while (op != 0);

		scan.close();
		System.out.println("\n\nFim do programa");

	}
}
