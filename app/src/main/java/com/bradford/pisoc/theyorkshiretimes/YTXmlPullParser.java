package com.bradford.pisoc.theyorkshiretimes;

/**
 * Created by charmstrange on 07/06/15.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

public class YTXmlPullParser {

    static final String KEY_ITEM = "item";
    static final String KEY_TITLE = "title";
    static final String KEY_LINK = "link";
    static final String KEY_PUBDATE = "pubDate";
    static final String KEY_DES = "description";
    static final String KEY_GUID = "guid";
    static final String KEY_AUTHOR = "author";


    public static List<articleInf> getArticlesFromFile(Context ctx) {
// List of Articles that we will return
        List<articleInf> Articles;
        Articles = new ArrayList<articleInf>();
// temp holder for current article while parsing
        articleInf curArt = null;
// temp holder for current text value while parsing
        String curText = "";
        try {

// Get our factory and PullParser


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

// point the parser to our file.

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream((ctx.getFilesDir().getPath().toString() + "/Articles.xml"))));
            xpp.setInput(reader);

// get initial eventType

            int eventType = xpp.getEventType();

// Loop through pull events until we reach END_DOCUMENT

            while (eventType != XmlPullParser.END_DOCUMENT) {

// Get the current tag

                String tagname = xpp.getName();

// React to different event types appropriately

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(KEY_ITEM)) {

// If we are starting a new <item> block we need
//a new articleInf object to represent it

                            curArt = new articleInf();
                        }
                        break;
                    case XmlPullParser.TEXT:
//grab the current text so we can use it in END_TAG event
                        curText = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase(KEY_ITEM)) {
// if </item> then we are done with current article
// add it to the list.
                            Articles.add(curArt);
                        } else if (tagname.equalsIgnoreCase(KEY_TITLE)) {
// if </title> use setTitle() on curArt
                            curArt.setTitle(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_LINK)) {
// if </link> use setLink() on curArt
                            curArt.setLink(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_PUBDATE)) {
// if </pubDate> use setPubDate() on curArt
                            curArt.setPubDate(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_DES)) {
// if </description> use setDescription() on curArt
                            curArt.setDescription(curText);

                        } else if (tagname.equalsIgnoreCase(KEY_GUID)) {
// if </guid> use setGuid() on curArt
                            curArt.setGuid(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_AUTHOR)) {
// if </author> use setAuthor() on curArt
                            curArt.setAuthor(curText);
                        }
                        break;
                    default:
                        break;
                }
//move on to next iteration
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
// return the populated list.
        return Articles;


    }
}