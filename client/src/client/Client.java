package client;

import java.io.*;
import java.net.*;
import java.lang.InterruptedException;

public class Client {//client başlangıç sınıfınmız
    
    public static void main(String[] args) throws SocketException, IOException, InterruptedException {
        int kayip=0,giden=0;//kaybedilen ve giden paketler için sayaçlar
      BufferedReader inFromUser =
         new BufferedReader(new InputStreamReader(System.in));//Kullanıcıdan istenen mesaj için scanner tanımı
      String ipadres =null;
      while(true){
      System.out.println("Lütfen IP adresini giriniz:");
      ipadres = inFromUser.readLine();
      if(ipadres==null){
          System.out.println("Lütfen IP adresini doğru girdiğinizden emin olun !\n ");
          continue;
      }
      else break;
      }
      DatagramSocket clientSocket = new DatagramSocket();//socket açma kodu
      InetAddress IPAddress = InetAddress.getByName(ipadres);//ip adresimizi belirliyoruz
      byte[] sendData = new byte[1024];//gönderilecek verinin tipi ve boyutunu ayarlıyoruz
      byte[] receiveData = new byte[1024];//Serverdan alınacak verinin tipi ve boyutunu ayarlıyoruz
      System.out.println("Lütfen paket içeriğini giriniz:");
      String sentence = inFromUser.readLine();//kullanıcıdan mesaj istiyoruz
      sendData = sentence.getBytes();//byte a dönüştürüyoruz çünkü soket byte larla çalışıyor
      for(int i=0;i<6;i++){//6 defa paket göndermek için
      
      double time1 = System.nanoTime();//paketin göndermeye başlandığı zaman
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 25400);//paketin gideceği yol ve içindekiler
      clientSocket.send(sendPacket);//paketi gönderdik
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);//serverdan bize gelecek veri
      clientSocket.receive(receivePacket);//serverdan gelen paketi aldık
      String modifiedSentence = new String(receivePacket.getData());//veriye gönüştürdük
      double time2 = System.nanoTime();//bitiş süresi
      double sure=(time2-time1)/1000000;//işlemin toplam yapılma süresi
      System.out.println("Server Cevabı:" + modifiedSentence+" Süre:"+ sure+" MS"+" Boyut:"+modifiedSentence.length());
      Thread.sleep(2000);//2 sn de bir diğer paketi göndermesi için
      if(sendData.equals(receivePacket.getData())){//veri kaybı kontrolü 
      System.out.println("Veri Kaybı Oldu!!");
      kayip++;
      continue;//kayıp varsa tekrar gönder
      }
      giden++;
      
      }
      clientSocket.close();//soketi kapattık (Bağlantıyı sonlandırdık)
      System.out.println("["+IPAddress+"]"+"["+6+"]"+"["+giden+"]"+"["+kayip+"]");//çıktı raporu
}
}
