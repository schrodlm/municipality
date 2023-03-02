package cz.schrodlm.municipality;

import cz.schrodlm.municipality.dao.MunicipalityPartRepository;
import cz.schrodlm.municipality.dao.MunicipalityRepository;
import cz.schrodlm.municipality.domain.Municipality;
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

    @Override
    public void run(String... args) throws Exception {

        String link = "https://www.smartform.cz/download/kopidlno.xml.zip";

        //Create a directory for the resources
        File destDir = new File("resources/municipality_data");
        if (!destDir.exists()) {
            destDir.mkdir();
        }


        File out = new File(destDir.getPath() + "/download.xml.zip");

        FileUtility fileUtility = new FileUtility();

        //download the file with provided link
        fileUtility.download(link, out);

        //dynamically unzip the downloaded file
        fileUtility.unzip(out.getPath(), out.getParent());

        //parse unzipped XML files and push data to the database
        XMLMunicipalityParser parser = new XMLMunicipalityParser(municipalityRepository,municipalityPartRepository);


        parser.parse("resources/municipality_data");

        // Delete downloaded zip file
        out.delete();
    }


}
