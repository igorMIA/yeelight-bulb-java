package bulb;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

class Command {
	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	
	public boolean set(String ip, int port) {
		try {
			try {
				if (!clientSocket.isClosed()) {
					clientSocket.close();	
				}
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}
			clientSocket = new Socket(ip, port);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private String sendTCP(String command) {
		String data = "NONE";
		try {
			outToServer.writeBytes("{\"id\":0,\"method\":" + command + "}" + "\r\n");
			data = inFromServer.readLine();
			System.out.println(data);
			//clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public ArrayList<String> connect() {
		ArrayList<String> arr = new ArrayList<String>();
		
		try {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("239.255.255.250");
		byte[] receiveData = new byte[1024];
		byte[] sendData = ("M-SEARCH * HTTP/1.1\r\n" +
		       "HOST: 239.255.255.250:1982\r\n" +
		       "MAN: \"ssdp:discover\"\r\n" +
		       "ST: wifi_bulb").getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1982);
		clientSocket.setSoTimeout(10000);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			clientSocket.receive(receivePacket);
			
			arr.add(receivePacket.getAddress().toString().substring(1) + "");
			Scanner sc = new Scanner(new String(receivePacket.getData()));
			String port = "NONE";
			for (int x = 0; x < 5; x++) {
				port = sc.nextLine();
			}
			arr.add(port.substring(port.lastIndexOf(":")+1));
			arr.add(new String(receivePacket.getData()));
			
			for (int x = 0; x < 7; x++) {
				port = sc.nextLine();
			}
			arr.add(port.substring(port.lastIndexOf(":")+2));
			
			clientSocket.close();
		} catch (SocketTimeoutException e) {
				System.out.println(e.getMessage());
				arr.clear();
				arr.add("");
				arr.add("");
				arr.add("Receive timed out");
				arr.add("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	public void setBright(int percent, String how, int t) {
		sendTCP("\"set_bright\",\"params\":[" + percent + ", \""+ how + "\", "+ t + "]");
	}
	
	public void setPower(String status, String how, int t) {
		sendTCP("\"set_power\",\"params\":[\"" + status + "\", \""+ how + "\", "+ t + "]");
	}
	
	public String getProp() {
		return sendTCP("\"get_prop\",\"params\":[\"power\", \"bright\"]");
	}
}