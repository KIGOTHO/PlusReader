
package com.example.tony.plusreader;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Parses the RSS xml to return com.example.tony.plusreader.RSSFeed object(Gets html -> parses html to get RSS url ->
 * After getting rss url will get rss xml and parse the xml. ->
 * Once rss xml parsing is done will return com.example.tony.plusreader.RSSFeed object of the rss xml)
 */
public class RSSParser {
    // RSS XML document CHANNEL tags
    private static String TAG_CHANNEL = "channel";
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_DESRIPTION = "description";
    private static String TAG_LANGUAGE = "language";
    private static String TAG_ITEM = "item";
    private static String TAG_PUB_DATE = "pubDate";
    private static String TAG_GUID = "guid";
    List  itemsList;



    // constructor
    public RSSParser() {

    }

    /***
     * Gets the RSS feed from url
     *
     * @param url - is url of the website
     * @return com.example.tony.plusreader.RSSFeed class object
     */

    public List getRSSFeed(String url) {
//        resets the RSS feed when changing website
        RSSFeed rssFeed = null;
        String rss_feed_xml = null;

        // getting rss link from html source code
        String rss_url = this.getRSSLinkFromURL(url);

        // check if rss_link is found or not
        if (rss_url != null) {

            // RSS url found
            // get RSS XML from rss ulr
            rss_feed_xml = this.getXmlFromUrl(rss_url);

            // check if RSS XML fetched or not
            if (rss_feed_xml != null) {
                // successfully fetched rss xml
                // parse the xml

                try {
                    Document doc = this.getDomElement(rss_feed_xml);
                    NodeList nodeList = doc.getElementsByTagName(TAG_CHANNEL);
                    Element e = (Element) nodeList.item(0);

                    // Getting items array
                    NodeList items = e.getElementsByTagName(TAG_ITEM);

                    // looping through each item
                    for (int i = 0; i < items.getLength(); i++) {
                        Element e1 = (Element) items.item(i);

                        String title = this.getValue(e1, TAG_TITLE);
                        String link = this.getValue(e1, TAG_LINK);
                        String description = this.getValue(e1, TAG_DESRIPTION);
                        String pubdate = this.getValue(e1, TAG_PUB_DATE);
                        String guid = this.getValue(e1, TAG_GUID);

                        RSSItem rssItem = new RSSItem(title, link, description, pubdate, guid);

                        // adding item to list
                        itemsList.add(rssItem);
                    }
                } catch (Exception e) {
                    // Check log for errors
                    e.printStackTrace();
                }
            }


        }
        // return item list
        return itemsList;
    }

        /**
         * Getting RSS feed items <item>
         *
         * @param - rss link url of the website
         * @return - List of RSSItem class objects
         * */

        public List<RSSItem> getRSSFeedItems(String rss_url){
            List<RSSItem> itemsList = new ArrayList<RSSItem>();
            String rss_feed_xml;

            // get RSS XML from rss url
            rss_feed_xml = this.getXmlFromUrl(rss_url);

            // check if RSS XML fetched or not
            if(rss_feed_xml != null){
                // successfully fetched rss xml
                // parse the xml
                try{
                    Document doc = this.getDomElement(rss_feed_xml);
                    NodeList nodeList = doc.getElementsByTagName(TAG_CHANNEL);
                    Element e = (Element) nodeList.item(0);

                    // Getting items array
                    NodeList items = e.getElementsByTagName(TAG_ITEM);

                    // looping through each item
                    for(int i = 0; i < items.getLength(); i++){
                        Element e1 = (Element) items.item(i);

                        String title = this.getValue(e1, TAG_TITLE);
                        String link = this.getValue(e1, TAG_LINK);
                        String description = this.getValue(e1, TAG_DESRIPTION);
                        String pubdate = this.getValue(e1, TAG_PUB_DATE);
                        String guid = this.getValue(e1, TAG_GUID);

                        RSSItem rssItem = new RSSItem(title, link, description, pubdate, guid);

                        // adding item to list
                        itemsList.add(rssItem);
                    }
                }catch(Exception e){
                    // Check log for errors
                    e.printStackTrace();
                }
            }

            // return item list
            return itemsList;
        }



    /**
     * Getting RSS feed link from HTML source code
     *
    // * @param ulr is url of the website
     * @returns url of rss link of website
     * */


    public String getRSSLinkFromURL(String url) {
        // RSS url
        String rss_url = null;

        try {
            // Using JSoup library to parse the html source code
            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
            // finding rss links which are having link[type=application/rss+xml]
            org.jsoup.select.Elements links = doc
                    .select("link[type=application/rss+xml]");

            Log.d("No of RSS links found", " " + links.size());

            // check if urls found or not
            if (links.size() > 0) {
                rss_url = links.get(0).attr("href").toString();
            } else {
                // finding rss links which are having link[type=application/rss+xml]
                org.jsoup.select.Elements links1 = doc
                        .select("link[type=application/atom+xml]");
                if(links1.size() > 0){
                    rss_url = links1.get(0).attr("href").toString();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // returing RSS url
        return rss_url;
    }


    /**
     * Method to get xml content from url HTTP Get request
     * */
    public String getXmlFromUrl(String url) {
        String xml = null;

        try {
            // request method is GET
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return XML
        return xml;
    }
    /**
     * Getting XML DOM element
     *
     //* @param XML string
     * */
    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = (Document) db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }

        return doc;
    }

    /**
     * Getting node value
     *
   //  * @param Element node
   //  * @param key  string
     **/
    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getValue((Element) n.item(0),str);
    }


}








