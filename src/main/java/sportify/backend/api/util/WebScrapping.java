package sportify.backend.api.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class WebScrapping {
    public Document getData(String url)throws Exception{
        return Jsoup.connect(url).get();
    }
}
