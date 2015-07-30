package com.example.tony.plusreader;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.util.Log;

/**
 * Parses the RSS xml to return RSSFeed object(Gets html -> parses html to get RSS url ->
 * After getting rss url will get rss xml and parse the xml. ->
 * Once rss xml parsing is done will return RSSFeed object of the rss xml)
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

    // constructor
    public RSSParser() {

    }

    /***
     * Gets the RSS feed from url
     *
     * @param url - is url of the website
     * @return RSSFeed class object
     */

    public RSSFeed getRSSFeed(String url) {
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
    }
}
