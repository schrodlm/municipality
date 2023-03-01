package cz.schrodlm.municipality;

import cz.schrodlm.municipality.dao.MunicipalityRepository;
import cz.schrodlm.municipality.domain.Municipality;
import cz.schrodlm.municipality.file.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;


@SpringBootApplication
public class MunicipalityApplication implements CommandLineRunner {

    @Autowired
    MunicipalityRepository r;
    public static void main(String[] args) {

        SpringApplication.run(MunicipalityApplication.class, args);


    }

    @Override
    public void run(String... args) throws Exception {

        String link = "https://www.smartform.cz/download/kopidlno.xml.zip";
        File out = new File("resources/download.xml.zip");

        FileUtility fileUtility = new FileUtility();

        fileUtility.download(link, out);
        //dynamically unzip the downloaded file
        fileUtility.unzip(out.getPath(), out.getParent());

        // Create a new entity
        Municipality m = new Municipality();
        m.setCode(34401L);
        m.setName("Domažlice");

        // Save the entity to the repository
        r.save(m);

        // Delete downloaded zip file
        out.delete();
    }


}
