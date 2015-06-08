package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException{

        ArrayList<String> makeList = new ArrayList<>();
        ArrayList<String> carImgUrls = new ArrayList<>(); //  This will hold all the CarImage Urls

        String url = "http://www.hdcarwallpapers.com/";
        Document doc = getDoc(url);
        Elements links = doc.select("ul.side-panel li a");

        //This for loop gets all the car main page car makes urls
        for (Element link: links){
            String imgUrl = link.attr("abs:href");
            makeList.add(imgUrl);
        }

        //After the nested for loop it will have obtained all the cars single img urls
        for (String carImg: makeList){
            Document carImgLinks  = getDoc(carImg);
            Elements carImgPattern = carImgLinks.select("ul.wallpapers li a");
            for(Element cars: carImgPattern){
                String carLink = cars.attr("abs:href");
                carImgUrls.add(carLink);
            }
        }
        //this nested for loop generates each car makes final single img Url
        for (String hdCar: carImgUrls){
            Document carImgsUrl = getDoc(hdCar);
            Element hdCarPattern = carImgsUrl.select("div.wallpaper-resolutions a").last();

            System.out.println(hdCarPattern.attr("abs:href"));
        }
    }
    private static Document getDoc(String url){
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36")
                    .timeout(60000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

}
