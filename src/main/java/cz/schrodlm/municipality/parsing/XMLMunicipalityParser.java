package cz.schrodlm.municipality.parsing;

import cz.schrodlm.municipality.dao.MunicipalityPartRepository;
import cz.schrodlm.municipality.dao.MunicipalityRepository;
import cz.schrodlm.municipality.domain.Municipality;
import cz.schrodlm.municipality.domain.MunicipalityPart;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class XMLMunicipalityParser {

    MunicipalityRepository municipalityRepository;

    MunicipalityPartRepository municipalityPartRepository;

    /**
     * Contructor
     *
     * @param municipalityRepository            - repository of municipality
     * @param municipalityPartRepository        - repository of municipalityPart
     */
    public XMLMunicipalityParser(MunicipalityRepository municipalityRepository, MunicipalityPartRepository municipalityPartRepository) {
        this.municipalityRepository = municipalityRepository;
        this.municipalityPartRepository = municipalityPartRepository;
    }

    /**
     *  Recursively parse all XML files in provided directory and saves them to the database. When file is parsed, it is deleted.
     *
     * @param dataDirectory                         - directory of XML files
     */
    public void parse(File dataDirectory) throws ParserConfigurationException, IOException, SAXException {

        //directory is empty
        if(dataDirectory.listFiles() == null){
            System.out.println("Resource directory " + dataDirectory.getPath() + "is empty");
            return;
        }

        System.out.println("Parsing all xml files from " + dataDirectory.getPath());

        for(final File fileEntry : Objects.requireNonNull(dataDirectory.listFiles())) {
            if(fileEntry.isDirectory()){
                parse(fileEntry);
                continue;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(fileEntry);

            //Normalize the XML structure
            document.getDocumentElement().normalize();

            //============================================================
            // MUNICIPALITY PARSING
            //============================================================

            // Get all municipalities by their tag name
            NodeList municipalityList = document.getElementsByTagName("vf:Obec");


            for (int i = 0; i < municipalityList.getLength(); i++) {
                Node municipality = municipalityList.item(i);
                if (municipality.getNodeType() == Node.ELEMENT_NODE) {

                    Element municipalityElement = (Element) municipality;


                    //get specific municipality details
                    //Municipality Entity will only have one code and one name provided so fetching item(0) can be done
                    try {
                        String code = municipalityElement.getElementsByTagName("obi:Kod").item(0).getTextContent();
                        String name = municipalityElement.getElementsByTagName("obi:Nazev").item(0).getTextContent();


                        Municipality municipalityEntity = new Municipality(Long.parseLong(code), name);

                        municipalityRepository.save(municipalityEntity);
                    } catch (NullPointerException e) {
                        throw new RuntimeException("Error: Insufficient information/wrong format provided for Municipality Entity in the XML file");
                    }
                }

            }

            //============================================================
            // MUNICIPALITY PART PARSING
            //============================================================

            NodeList municipalityPartList = document.getElementsByTagName("vf:CastObce");


            for (int i = 0; i < municipalityPartList.getLength(); i++) {
                Node municipalityPart = municipalityPartList.item(i);
                if (municipalityPart.getNodeType() == Node.ELEMENT_NODE) {

                    Element municipalityPartElement = (Element) municipalityPart;


                    //get specific municipalityPart details

                    try {
                        String code = municipalityPartElement.getElementsByTagName("coi:Kod").item(0).getTextContent();
                        String name = municipalityPartElement.getElementsByTagName("coi:Nazev").item(0).getTextContent();
                        String municipality_code = municipalityPartElement.getElementsByTagName("coi:Obec").item(0).getChildNodes().item(1).getTextContent();

                        MunicipalityPart municipalityPartEntity = new MunicipalityPart(Long.parseLong(code), name, municipality_code);

                        municipalityPartRepository.save(municipalityPartEntity);
                    } catch (NullPointerException e) {
                        throw new RuntimeException("Error: Insufficient information/wrong format provided for MunicipalityPart Entity in the XML file");
                    }
                }
            }
            System.out.println("Parsing of " + fileEntry.getName() + " completed.");

        }

    }
}
