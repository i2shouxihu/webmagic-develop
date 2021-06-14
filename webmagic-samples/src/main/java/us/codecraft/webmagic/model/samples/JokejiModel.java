package us.codecraft.webmagic.model.samples;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * @author code4crafter@gmail.com
 */
@TargetUrl("http://data.eastmoney.com/stockcomment/")
@HelpUrl("http://data.eastmoney.com/stockcomment/")
public class JokejiModel {

    @ExtractBy("/html/body/div[1]/div[8]/div[2]/div[2]/div[1]/div")
    private String title;

    @ExtractBy("/html/body/div[1]/div[8]/div[2]/div[6]/table/tbody/tr[1]/td[10]")
    private String content;

    public static void main(String[] args) {
        OOSpider.create(Site.me().setDomain("data.eastmoney.com").setCharset("utf-8").setSleepTime(100).setTimeOut(3000)
                .setUserAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)")
                , new ConsolePageModelPipeline(), JokejiModel.class).addUrl("http://data.eastmoney.com").thread(2)
                .run();
    }

}
