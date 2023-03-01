package cz.schrodlm.municipality.parsing;

import cz.schrodlm.municipality.dao.MunicipalityPartRepository;
import cz.schrodlm.municipality.dao.MunicipalityRepository;
import cz.schrodlm.municipality.domain.Municipality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

@Component
public class XMLMunicipalityParser {


    MunicipalityRepository municipalityRepository;

    MunicipalityPartRepository municipalityPartRepository;

    public XMLMunicipalityParser(MunicipalityRepository municipalityRepository, MunicipalityPartRepository municipalityPartRepository) {
        this.municipalityRepository = municipalityRepository;
        this.municipalityPartRepository = municipalityPartRepository;
    }

    public void parse(String filePath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filePath));

        //NOrmalize the xml structure
        document.getDocumentElement().normalize();

        // Get all elements by their tag name
        NodeList municipalityList = document.getElementsByTagName("vf:Obec");


        for(int i = 0; i <municipalityList.getLength(); i++) {
            Node municipality = municipalityList.item(i);
            if(municipality.getNodeType() == Node.ELEMENT_NODE) {

                Element municipalityElement = (Element) municipality;

                //get specific municipality details
                NodeList municipalityDetails =  municipality.getChildNodes();

                String code = municipalityDetails.item(1).getTextContent();
                String name = municipalityDetails.item(3).getTextContent();

                Municipality municipalityEntity = new Municipality();
                municipalityEntity.setName(name);
                municipalityEntity.setCode(Long.parseLong(code));

                municipalityRepository.save(municipalityEntity);

            }

        }

    }
}


