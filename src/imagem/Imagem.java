package imagem;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Imagem {
	private String caminhoImg;
	private boolean existeImg = false;
	private Mat src;
	private Mat dst;

	// escolhe uma imagem para ser usada no programa
	public void escolheImagem(Scanner scan) {
		File cam;
		do {
			System.out.println("Digite o caminho da imagem");
			System.out.print("caminho: ");
			this.setCaminhoImg(scan.nextLine());
			cam = new File(this.getCaminhoImg());
			if (cam.exists()) {
				this.setExisteImg(true);
			} else {
				System.out.println("a imagem do caminho passado não existe");
			}
		} while (this.isExisteImg() != true);
	}

	// aplica filtro de cinza com open cv
	public void aplicaFiltroDeCinza() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		BufferedImage novaImagemBI = null;
		try {
			File img = new File(this.getCaminhoImg());
			BufferedImage imagemBI = ImageIO.read(img);
			byte[] data = ((DataBufferByte) imagemBI.getRaster().getDataBuffer()).getData();
			this.src = new Mat(imagemBI.getHeight(), imagemBI.getWidth(), CvType.CV_8UC3);
			this.src.put(0, 0, data);
			this.dst = new Mat(imagemBI.getHeight(), imagemBI.getWidth(), CvType.CV_8UC1);
			Imgproc.cvtColor(this.src, this.dst, Imgproc.COLOR_RGB2GRAY);
			byte[] data1 = new byte[this.dst.rows() * this.dst.cols() * (int) (this.dst.elemSize())];
			this.dst.get(0, 0, data1);
			novaImagemBI = new BufferedImage(this.dst.cols(), this.dst.rows(), BufferedImage.TYPE_BYTE_GRAY);
			novaImagemBI.getRaster().setDataElements(0, 0, this.dst.cols(), this.dst.rows(), data1);
			String novo = "imgs/filtros/novaimagem.png"; // criar método para descobrir extensão exata, para poder
															// trabalhar com outras além do png
			File resultado = new File(novo);
			ImageIO.write(novaImagemBI, "png", resultado);
			System.out.println("Nova imagem com filtro de cinza salva na pasta imgs/filtros");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// filtro de detecção de borda por Sobel
	public void aplicaDeteccaoDeBordaComSobel(Scanner scan) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		this.src = Imgcodecs.imread(this.getCaminhoImg());
		this.dst = new Mat();
		System.out.println("Escolha a profundidade");
		System.out.print("profundidade: ");
		int profundidade = scan.nextInt();
		scan.nextLine();
		System.out.println("Escolha o x");
		System.out.print("x: ");
		int x = scan.nextInt();
		scan.nextLine();
		System.out.println("Escolha o y");
		System.out.print("y: ");
		int y = scan.nextInt();
		scan.nextLine();
		Imgproc.Sobel(this.src, this.dst, profundidade, x, y);
		Imgcodecs.imwrite("imgs/filtros/novaimagemcomdeteccaoporsobel.png", dst);
	}

	// filtro de detecção de borda por Canny
	public void aplicaDeteccaoDeBordaComCanny(Scanner scan) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		this.src = Imgcodecs.imread(this.getCaminhoImg());
		this.dst = new Mat();
		System.out.println("Escolha o valor de threshold 1");
		System.out.print("resposta: ");
		double th1 = scan.nextDouble();
		scan.nextLine();
		System.out.println("Escolha o valor de threshold 2");
		System.out.print("resposta: ");
		double th2 = scan.nextDouble();
		scan.nextLine();
		// Canny usado será public static void Canny(Mat image, Mat edges,
		// double threshold1, double threshold2)
		Imgproc.Canny(this.src, this.dst, th1, th2);
		Imgcodecs.imwrite("imgs/filtros/novaimagemcomdeteccaoporcanny.png", dst);
	}

	// escolhe uma opção para a detecção de bordas
	public int escolheDeteccaoDeBorda(Scanner scan) {
		int n = -1;
		do {
			try {
				System.out.println("Escolha uma das opções para aplicar a detecção de bordas");
				System.out.println("1. Roberts, 2. Sobel, 3. Canny");
				System.out.print("resposta: ");
				n = scan.nextInt();
				scan.nextLine();
				if ((n < 1) || (n > 3))
					System.out.println("opção inválida");
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				n = -1;
				scan.nextLine();
			}
		} while ((n < 1) || (n > 3));
		return n;
	}

	// mostra as informações de rgb de um pixel passado
	public void mostraRGBDoPixel(Scanner scan) {
		BufferedImage imagemBI = null;
		try {
			File img = new File(this.getCaminhoImg());
			imagemBI = ImageIO.read(img);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		int largura = imagemBI.getWidth();
		int altura = imagemBI.getHeight();
		int posX = -1;
		int posY = -1;
		do {
			try {
				System.out.println("Digite a largura do pixel");
				System.out.println("0 até " + (largura - 1));
				System.out.print("largura: ");
				posX = scan.nextInt();
				scan.nextLine();
				if ((posX < 0) || (posX > (largura - 1)))
					System.out.println("opção inválida");
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				posX = -1;
				scan.nextLine();
			}
		} while ((posX < 0) || (posX > (largura - 1)));
		do {
			try {
				System.out.println("Digite a altura do pixel");
				System.out.println("0 até " + (altura - 1));
				System.out.print("altura: ");
				posY = scan.nextInt();
				scan.nextLine();
				if ((posY < 0) || (posY > (altura - 1)))
					System.out.println("opção inválida");
			} catch (InputMismatchException e) {
				System.out.println("opção inválida");
				posY = -1;
				scan.nextLine();
			}
		} while ((posY < 0) || (posY > (altura - 1)));
		Color cor = new Color(imagemBI.getRGB(posX, posY));
		System.out.println("red - green - blue");
		System.out.println(cor.getRed() + " - " + cor.getGreen() + " - " + cor.getBlue());
	}

	public String getCaminhoImg() {
		return caminhoImg;
	}

	public void setCaminhoImg(String caminhoImg) {
		this.caminhoImg = caminhoImg;
	}

	public boolean isExisteImg() {
		return existeImg;
	}

	public void setExisteImg(boolean existeImg) {
		this.existeImg = existeImg;
	}

	public Mat getSrc() {
		return src;
	}

	public void setSrc(Mat src) {
		this.src = src;
	}

	public Mat getDst() {
		return dst;
	}

	public void setDst(Mat dst) {
		this.dst = dst;
	}

}
