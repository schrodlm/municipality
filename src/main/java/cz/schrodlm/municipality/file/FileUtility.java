package cz.schrodlm.municipality.file;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtility {
    public FileUtility() {}

    private static final int BUFFER_SIZE = 4096;

    public void download(String link, File out) throws IOException{
        try {

            URL url = new URL(link);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            double fileSize = (double)http.getContentLengthLong();
            BufferedInputStream in = new BufferedInputStream(http.getInputStream());
            FileOutputStream fos = new FileOutputStream(out);
            BufferedOutputStream bout = new BufferedOutputStream(fos,BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            double downloaded = 0.00;
            int read = 0;
            double percentDownloaded = 0.00;

            int iteration = 0;
            while((read = in.read(buffer,0,BUFFER_SIZE)) >= 0)
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
        }
    }


    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();

    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}

