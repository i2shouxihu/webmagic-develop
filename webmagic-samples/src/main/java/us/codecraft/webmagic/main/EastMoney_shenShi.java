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
import java.util.List;


//筛选出沪市、深市、创业板和中小板的轻度控盘或中度控盘的股票
public class EastMoney_shenShi implements PageProcessor {

    List<String> url2s=new ArrayList<>();

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setCharset("utf-8").setRetryTimes(3).setSleepTime(1000);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
//        page.putField("author", page.getHtml().toString());
//        page.putField("name", page.getHtml().xpath("/html/body/div[1]/div[8]/div[2]/div[4]/div[1]/text()").toString());
//        if (page.getResultItems().get("name") == null) {
//            //skip this page
//            page.setSkip(true);
//        }
        //page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        //page.addTargetRequests(page.getHtml().links().regex("http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?callback=datatable2725998&type=QGQP_LB&token=70f12f2f4f091e459a279469fe49eca5&cmd=&st=Code&sr=1&p=1&ps=50&js=(%7Bpages%3A(tp)%2Cdata%3A(x)%2Cfont%3A(font)%7D)&filter=(Market%3D%276%27)&_=1617021444581").all());

        List<String> sumname=new ArrayList<>();


        String raw = String.valueOf(new JsonPathSelector("$").selectList(page.getRawText()));
        String[] split = raw.split("\\[|\\]");
        String jsonArray="{\"data\":["+split[2].toString()+"]}";

        //沪市A股 1622
        //深市A股 474
        //创业板 932
        //中小板  1012
        for (int i = 0; i < 1489; i++) {
            List<String> strings = new JsonPathSelector("$.data["+i+"]").selectList(jsonArray);
            System.out.println();
            for (String s : strings) {
                if (new JsonPathSelector("$.JGCYDType").selectList(s).toString().contains("轻度控盘")
                        ||new JsonPathSelector("$.JGCYDType").selectList(s).toString().contains("中度控盘")){
                    //System.out.println(s);
                    System.out.println(new JsonPathSelector("$.Code").selectList(s).toString());
                    System.out.println(new JsonPathSelector("$.Name").selectList(s).toString());
                    sumname.addAll(new JsonPathSelector("$.Code").selectList(s));



                    for (String t:new JsonPathSelector("$.Code").selectList(s)){
                        StringBuilder stringBuilder=new StringBuilder("http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?type=QGQP_LSJGCYD&token=70f12f2f4f091e459a279469fe49eca5&ps=22&filter=(TRADECODE=%27");
                        stringBuilder.append(t);
                        stringBuilder.append("%27)&st=TRADEDATE&sr=-1&callback=jQuery112307965527139220383_1617014060178&_=1617014060179");
                        String url2=stringBuilder.toString();
                        System.out.println(url2);
                        url2s.add(url2);
                    }
                }
            }
        }
        System.out.println(sumname);
        StringBuilder sb=new StringBuilder("{");
        for (String s1 : sumname) {
            sb.append("\""+s1+"\""+",");
        }
        System.out.println(sb.append("}").toString());


//        for (String s:split1) {
//            List<String> select = new JsonPathSelector("$.JGCYDType").selectList(s);
//            System.out.println(select);
//        }

//        List<String> JGCYDTypes = new JsonPathSelector("$.JGCYDType").selectList(split[2].toString());
//        System.out.println(JGCYDTypes);
        //JGCYDTypes.stream().filter(s->s.startsWith("轻度")).filter(s-> s.length() == 4).forEach(System.out::println);

        //System.out.println(url2s);
        //page.putField("lsJgcxdLinks", url2s);

    }
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {



        //沪市A股 1622
        //String url="http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?callback=datatable2037675&type=QGQP_LB&token=70f12f2f4f091e459a279469fe49eca5&cmd=&st=Code&sr=1&p=1&ps=1622&js=(%7Bpages%3A(tp)%2Cdata%3A(x)%2Cfont%3A(font)%7D)&filter=(Market%3D%272%27)&_=1617021552210";


        //深市A股 1489
        String url="http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?callback=datatable2725998&type=QGQP_LB&token=70f12f2f4f091e459a279469fe49eca5&cmd=&st=Code&sr=1&p=1&ps=1489&js=(%7Bpages%3A(tp)%2Cdata%3A(x)%2Cfont%3A(font)%7D)&filter=(Market%3D%276%27)&_=1617021444581";

        //创业板 932
        //String url="http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?callback=datatable9155604&type=QGQP_LB&token=70f12f2f4f091e459a279469fe49eca5&cmd=&st=Code&sr=1&p=1&ps=932&js=(%7Bpages%3A(tp)%2Cdata%3A(x)%2Cfont%3A(font)%7D)&filter=(Market%3D%2780%27)&_=1617021163192";

        //中小板  1012
        //String url="http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?callback=datatable9127408&type=QGQP_LB&token=70f12f2f4f091e459a279469fe49eca5&cmd=&st=Code&sr=1&p=1&ps=1012&js=(%7Bpages%3A(tp)%2Cdata%3A(x)%2Cfont%3A(font)%7D)&filter=(Market%3D%2713%27)&_=1617021243143";


        Request request = new Request();
        request.setMethod(HttpConstant.Method.GET);
        request.setUrl(url);

        Spider.create(new EastMoney_shenShi())
                //从"https://github.com/code4craft"开始抓
                .addRequest(request)
                .addPipeline(new FilePipeline("D:/data"))
                .addPipeline(new ConsolePipeline())
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}
