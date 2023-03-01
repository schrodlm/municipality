package cz.schrodlm.municipality.file;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader implements Runnable{

    String link;
    File out;

    public Downloader(String link, File out){
        this.link = link;
        this.out = out;
    }

    @Override
    public void run(){
        try {
            int bufferSize = 4096;
            URL url = new URL(link);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            double fileSize = (double)http.getContentLengthLong();
            BufferedInputStream in = new BufferedInputStream(http.getInputStream());
            FileOutputStream fos = new FileOutputStream(this.out);
            BufferedOutputStream bout = new BufferedOutputStream(fos,bufferSize);
            byte[] buffer = new byte[bufferSize];
            double downloaded = 0.00;
            int read = 0;
            double percentDownloaded = 0.00;

            int iteration = 0;
            while((read = in.read(buffer,0,bufferSize)) >= 0)
            {
                bout.write(buffer,0,read);
                downloaded += read;
                percentDownloaded = (downloaded*100)/fileSize;
                if(iteration++ % 15 == 0){
                    String percent = String.format("%.1f", percentDownloaded);
                    System.out.println("Downloaded " + percent + "% of a file");
                }

            }
            bout.close();
            in.close();
            System.out.println("Download completed!");


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
