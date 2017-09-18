package com.petercsik.interview.rssreader.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class DOMParser {

    public List<RSSItem> parseXML(InputSource feedSource) throws Exception {
        List<RSSItem> feedList = new ArrayList<>();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(feedSource);
        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("item");
        int length = list.getLength();

        for (int i = 0; i < length; i++) {
            Node currentNode = list.item(i);
            RSSItem item = new RSSItem();
            NodeList nodeChild = currentNode.getChildNodes();
            int cLength = nodeChild.getLength();

            for (int j = 1; j < cLength; j = j + 2) {
                String nodeName = nodeChild.item(j).getNodeName();
                String nodeString = null;

                if (nodeChild.item(j).getFirstChild() != null) {
                    nodeString = nodeChild.item(j).getFirstChild().getNodeValue();
                }
                if (nodeString != null) {
                    if ("title".equals(nodeName)) {
                        item.setTitle(nodeString);
                    } else if ("content:encoded".equals(nodeName)) {
                        item.setDescription(nodeString);
                    } else if ("pubDate".equals(nodeName)) {
                        item.setDate(nodeString.replace(" +0000", ""));
                    } else if ("author".equals(nodeName) || "dc:creator".equals(nodeName)) {
                        item.setAuthor(nodeString);
                    } else if ("link".equals(nodeName)) {
                        item.setLink(nodeString);
                    } else if ("thumbnail".equals(nodeName)) {
                        item.setThumb(nodeString);
                    } else if ("description".equals(nodeName)) {
                        item.setDescription(nodeString);
                    }
                }
            }
            feedList.add(item);
        }
        return feedList;
    }

}