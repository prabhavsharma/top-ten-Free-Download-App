package com.prabhav.top10downloader;

import android.app.Application;
import android.util.Log;

import com.prabhav.top10downloader.Model.ApplicationModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by ps292 on 2/27/2016.
 */
public class ParseApplication {
    private String xmldata;
    private ArrayList<ApplicationModel> applications;

    public ParseApplication(String xmldata) {
        this.xmldata = xmldata;
        applications = new ArrayList<>();
    }

    public ArrayList<ApplicationModel> getApplications() {
        return applications;
    }
    public boolean process()
    {
        boolean status =true;
        ApplicationModel currentRecord =null;
        boolean inEntry = false;
        String txtval ="";
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmldata));
            int eventType = xpp.getEventType();
            while (eventType!= XmlPullParser.END_DOCUMENT)
            {
                String tagname = xpp.getName();
                switch(eventType) {
                    case XmlPullParser.START_TAG:
                        //Log.d("ParseApp","Start Tag for "+tagname);
                        if (tagname.equalsIgnoreCase("entry")) {
                            inEntry = true;
                            currentRecord = new ApplicationModel();

                        }
                        break;
                    case XmlPullParser.TEXT:
                        txtval = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        //Log.d("ParseApp","End Tag for "+tagname);
                        if(inEntry) {
                            if (tagname.equalsIgnoreCase("entry")) {
                                applications.add(currentRecord);
                                inEntry = false;
                            }
                                else if (tagname.equalsIgnoreCase("name")) {
                                    currentRecord.setName(txtval);
                                } else if (tagname.equalsIgnoreCase("artist")) {
                                    currentRecord.setArtist(txtval);

                                } else if (tagname.equalsIgnoreCase("releasedate")) {
                                    currentRecord.setRelease(txtval);
                                }

                        }
                        break;
                    default:
                        //do something
                }
                eventType = xpp.next();
            }
        }
        catch(Exception e)
        {
            status =false;
            e.printStackTrace();
        }
for(ApplicationModel app :applications)
{
    Log.d("ParseApplication","@@@@@@@@");
    Log.d("ParseApplication","Name"+app.getName());
    Log.d("ParseApplication","Artist"+app.getArtist());
    Log.d("ParseApplication","Releasedate"+app.getRelease());

}
        return true;
    }
}
