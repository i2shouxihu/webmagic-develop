package us.codecraft.webmagic.main;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EastMoney2 {

    public static void main(String[] args) {

        List<String> l=new ArrayList<>();
        Collections.addAll(l,
                "300137","300097","002512","000883","600578","002762","000605","600969","000503","600509","601158","002893","002647","600168","603222","605055","000544","001896","603318","600339","600807","605188","603900","002369","605018","603080","601919","002885","600640","000151","603098","603988","003040","002846","601330","000065","000856","603603","603616","600403","002542","601000","003036","003016","000034","002941","002658","600874","002731","600576","603777","000531","601811","600540","000565","000600","601618","600769","002875","000928","002769","600780","002177","000958","000007","603169","601068","002316","002269","600653","000039","300692"        );



        System.out.println(l);

        for (String t:l){
            System.out.print("\""+t+"\",");
        }

        for (String t:l){
            StringBuilder stringBuilder=new StringBuilder("http://data.eastmoney.com/stockcomment/stock/");
            stringBuilder.append(t);
            stringBuilder.append(".html#zlkp");
            System.out.println(stringBuilder);
        }









    }


}
