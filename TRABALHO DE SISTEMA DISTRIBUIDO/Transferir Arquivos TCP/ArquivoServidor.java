import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ArquivoServidor{
	public static void main(String[] args) {
		
		String nomeArqu = null;
		Scanner ler = new Scanner(System.in);
		
		System.out.println("digite o nome da imagem: ");
		nomeArqu = ler.nextLine();

	    // Criando servidor
		
		ArquivoServidor server = new ArquivoServidor();

		// Aguardar conexao de cliente para transferia
		
		server.waitForClient(nomeArqu);
		
	}

	public void waitForClient(String nomeArqu) {
		// Checa se a transferencia foi completada com sucesso
		
		OutputStream socketOut = null;
		ServerSocket servsock = null;
		FileInputStream fileIn = null;

		try {
			// Abrindo porta para conexao de clients
			
			servsock = new ServerSocket(13267);
			
			System.out.println("Porta de conexao aberta 13267");

			// Cliente conectado
			
			Socket sock = servsock.accept();
			
			System.out.println("Conexao recebida pelo cliente");

			// Criando tamanho de leitura
			
			byte[] cbuffer = new byte[1024];
			
			int bytesRead;

			// Criando arquivo que sera transferido pelo servidor
			
			File file = new File("C://Users//PC1//Desktop//JAVATEXTO//"+nomeArqu);
			
			fileIn = new FileInputStream(file);
			
			System.out.println("Lendo arquivo...");
			
			// Criando canal de transferencia
			
			socketOut = sock.getOutputStream();

			// Lendo arquivo criado e enviado para o canal de transferencia
			
			System.out.println("Enviando Arquivo...");
			
			while ((bytesRead = fileIn.read(cbuffer)) != -1) {
				socketOut.write(cbuffer, 0, bytesRead);
				socketOut.flush();
			}

			System.out.println("Arquivo Enviado!");
			
		} catch (Exception e) {
			
			// Mostra erro no console
			
			e.printStackTrace();
		} finally {
			
			if (socketOut != null) {
				try {
					socketOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (servsock != null) {
				try {
					servsock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
