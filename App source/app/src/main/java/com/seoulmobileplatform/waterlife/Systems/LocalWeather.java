package com.seoulmobileplatform.waterlife.Systems;

import android.content.Context;
import android.os.AsyncTask;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.ArrayList;

/*===========================================================
              @ Version : v1.0.0
              @ Since : 2016-10-21
              @ Author : skydoves@Naver.com(엄재웅)
 ===========================================================*/

public class LocalWeather extends AsyncTask<String, Integer, String> {

    // Systems
    String TAG = "LocalWeather";
    Context mContext;
    ExceptionLog exceptionLog;

    ArrayList<ShortWeather> shortWeathers = new ArrayList<ShortWeather>();

    public LocalWeather(Context context){
        this.mContext = context;
        this.exceptionLog = new ExceptionLog(mContext, TAG);
    }

    // doInBackground //
    public String doInBackground(String[] StringParams) {
        String url = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1159068000";
        Response response;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        // Xml Parsing : Get Reh Data
        try {
            response = client.newCall(request).execute();
            int total = parseXML(response.body().string());
            return String.valueOf(total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // onPostExecute //
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    // Xml Parsing : Get Reh Data //
    //region
    int parseXML(String xml) {
        int total=0;
        try {
            int i = 0;
            String tagName = "";
            boolean onreh = false;
            boolean onEnd = false;
            boolean isItemTag1 = false;

            // Initialize XmlPullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    if (tagName.equals("data")) {
                        shortWeathers.add(new ShortWeather());
                        onEnd = false;
                        isItemTag1 = true;
                    }
                } else if (eventType == XmlPullParser.TEXT && isItemTag1) {
                    if(tagName.equals("reh") && !onreh){
                        shortWeathers.get(i).setReh(parser.getText());
                        total += Integer.parseInt(parser.getText());
                        onreh = true;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (tagName.equals("s06") && onEnd == false) {
                        i++;
                        isItemTag1 = false;
                        onreh = false;
                        onEnd = true;
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
                exceptionLog.Exception_XMLParsing(e.getMessage());
        }
        return total/shortWeathers.size();
    }

    // Short Local Weather //
    public class ShortWeather {
        private String reh;

        public String getReh() {
            return reh;
        }

        public void setReh(String reh) {
            this.reh = reh;
        }
    }
    //endregion
}