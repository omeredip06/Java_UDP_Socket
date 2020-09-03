/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpsocket;

import java.io.*;
import java.net.*;

/**
 *
 * @author makine
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, IOException {
        // TODO code application logic here
        
        DatagramSocket serverSocket = new DatagramSocket(25400);//server soketi için port belirledik
            byte[] receiveData = new byte[1024];//Clienttan alınacak verinin tipi ve boyutunu ayarlıyoruz
            byte[] sendData = new byte[1024];//gönderilecek verinin tipi ve boyutunu ayarlıyoruz
            while(true)//Server sürekli açık kalsın diye sonsuz döngüye soktuk
               {
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);//alınacak veri için tanımlama 
                  serverSocket.receive(receivePacket);//clienttan gelen veriyi server aldı
                  String sentence = new String( receivePacket.getData());//gelen byte tipindeki paketi veriye dönüştürüyoruz
                  System.out.println("Alınan: " + sentence);
                  InetAddress IPAddress = receivePacket.getAddress();//clienttan gelen ip yi alıyoruz.
                  int port = receivePacket.getPort();//clienttan gelen port numarasını aldık
                  String capitalizedSentence = sentence.toUpperCase();//gelen mesajı büyük harfe çevirdik
                  sendData = capitalizedSentence.getBytes();//veriyi byte a dönüştürdük
                  DatagramPacket sendPacket =
                  new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  serverSocket.send(sendPacket);//veriyi gönderdik
    }
    }
}
    
