package cz.schrodlm.municipality;

import cz.schrodlm.municipality.dao.MunicipalityPartRepository;
import cz.schrodlm.municipality.dao.MunicipalityRepository;
import cz.schrodlm.municipality.file.FileUtility;
import cz.schrodlm.municipality.parsing.XMLMunicipalityParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;


@SpringBootApplication
public class MunicipalityApplication implements CommandLineRunner {

    @Autowired
    MunicipalityRepository municipalityRepository;

    @Autowired
    MunicipalityPartRepository municipalityPartRepository;

    public static void main(String[] args) {

        SpringApplication.run(MunicipalityApplication.class, args);


    }

    /**
     * This program will download zipped XML files from the link provided, it expects specific format of XML files, it parses
     * these XML files and saves specific data to the connected database (PostGreSQL) and deletes these downloaded files.
     */
    @Override
    public void run(String... args) throws Exception {

        //Download link
        String link = "https://www.smartform.cz/download/kopidlno.xml.zip";

        //Create a directory for the resources
        File destDir = new File("resources/municipality_data");
        if (!destDir.exists()) {
             if(destDir.mkdir()) throw new RuntimeException();
        }

        //out file will be used to store the zipped content
        File out = new File(destDir.getPath() + "/download.xml.zip");

        //File utility class
        FileUtility fileUtility = new FileUtility();

        //download the file with provided link
        fileUtility.download(link, out);

        //dynamically unzip the downloaded file
        fileUtility.unzip(out.getPath(), out.getParent());

        //we can now delete downloaded zip
        out.delete();


        //parse unzipped XML files and push data to the database
        XMLMunicipalityParser parser = new XMLMunicipalityParser(municipalityRepository,municipalityPartRepository);

        //Parse and save data to the database
        parser.parse(destDir);

        System.out.println("Deleting unzipped files...");
        fileUtility.deleteDirectoryContent(destDir);

        System.out.println("Done!");
    }


}
