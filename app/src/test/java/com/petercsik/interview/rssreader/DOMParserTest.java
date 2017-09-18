package com.petercsik.interview.rssreader;

import com.petercsik.interview.rssreader.parser.DOMParser;
import com.petercsik.interview.rssreader.parser.RSSItem;

import org.junit.Test;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class DOMParserTest {

    private final String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<rss version=\"2.0\">\n" +
            "\n" +
            "<channel>\n" +
            "  <title>W3Schools Home Page</title>\n" +
            "  <link>https://www.w3schools.com</link>\n" +
            "  <description>Free web building tutorials</description>\n" +
            "  <item>\n" +
            "    <title>RSS Tutorial</title>\n" +
            "    <link>https://www.w3schools.com/xml/xml_rss.asp</link>\n" +
            "    <description>New RSS tutorial on W3Schools</description>\n" +
            "  </item>\n" +
            "</channel>\n" +
            "\n" +
            "</rss> ";


    private final DOMParser parser = new DOMParser();
    private final InputSource is = new InputSource();

    @Test
    public void rssFeedCountTest() throws Exception {
        is.setCharacterStream(new StringReader(testXml));
        List<RSSItem> feed = parser.parseXML(is);

        assertEquals(1, feed.size());
    }

    @Test
    public void rssFeedTitleTest() throws Exception {
        is.setCharacterStream(new StringReader(testXml));
        List<RSSItem> feed = parser.parseXML(is);

        assertEquals("RSS Tutorial", feed.get(0).getTitle());
    }

    @Test
    public void rssFeedLinkTest() throws Exception {
        is.setCharacterStream(new StringReader(testXml));
        List<RSSItem> feed = parser.parseXML(is);

        assertEquals("https://www.w3schools.com/xml/xml_rss.asp", feed.get(0).getLink());
    }

    @Test
    public void rssFeedDescriptionTest() throws Exception {
        is.setCharacterStream(new StringReader(testXml));
        List<RSSItem> feed = parser.parseXML(is);

        assertEquals("New RSS tutorial on W3Schools", feed.get(0).getDescription());
    }


}