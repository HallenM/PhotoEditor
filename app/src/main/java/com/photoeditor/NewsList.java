package com.photoeditor;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class NewsList {

    private String header;
    private String imageLink;

    public NewsList(String header, String imageLink) {
        this.header = header;
        this.imageLink = imageLink;
    }

    public String getHeader(){
        return this.header;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public static ArrayList<NewsList> createNewsList(String response){
        ArrayList<NewsList> newsList = new ArrayList<NewsList>();
        InputStream inStream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));

        try {
            // Create tree builder
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Create DOM tree from file
            Document document = docBuilder.parse(inStream);

            // Getting the root elem and nodes belong to concrete tag
            Element root = document.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("item");

            if(nodeList.getLength() > 0) {
                for(int i = 0; i < nodeList.getLength(); i++) {
                    Element elem = (Element) nodeList.item(i);
                    Element titleEl = (Element) elem.getElementsByTagName("title").item(0);
                    Element imgEl = (Element) elem.getElementsByTagName("image").item(0);
                    Element enclosureEl = (Element) elem.getElementsByTagName("enclosure").item(0);
                    Element descriptionEl = (Element) elem.getElementsByTagName("description").item(0);

                    String title = titleEl.getFirstChild().getNodeValue();
                    String image = "", description = "";

                    if(imgEl != null) {
                        image = imgEl.getFirstChild().getNodeValue();
                    } else {
                        if(enclosureEl != null) {
                            image = enclosureEl.getAttribute("url");
                        } else{

                            String str = descriptionEl.getFirstChild().getNodeValue();
                            org.jsoup.nodes.Document descrNode = Jsoup.parse("<html><body>" + str + "</body></html>");
                            Elements pngs = descrNode.select("img[src~=.(png|jpe?g|JPG|jpg|gif|GIF)]");
                            for (org.jsoup.nodes.Element element : pngs) {
                                description += element.attr("src");
                            }
                        }
                    }

                    /*TextView debugText = findViewById(R.id.textViewww);
                    debugText.setText(" title: " + title + "      image: " + image + "     description:" + description);*/

                    NewsList news = new NewsList(title, image);
                    newsList.add(news);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
