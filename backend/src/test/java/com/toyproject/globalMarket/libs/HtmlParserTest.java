package com.toyproject.globalMarket.libs;

import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.libs.HtmlParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HtmlParserTest {

    @Test
    void getDetailContentImages() {
        HtmlParser htmlParser = new HtmlParser();
        String detailContent = "<img src=\"https://raw.githubusercontent.com/GlobalMarketKOR/Images/master/detail/0.png\"/><div class=\"detailmodule_html\"><div class=\"detail-desc-decorate-richtext\"><div id=\"tl_1\">\n" +
                " 이름: 실내 야외 침실 레저 흔들 의자 포함 쿠션 포함 \n" +
                " <br />해당 장면: 정원, 안뜰, 테라스, 거실, 아파트 \n" +
                " <br /> \n" +
                " <br />절묘한 실내 장식으로 침실을위한 부드럽고 편안한 흔들 의자는 우아함과 편안함을 결합합니다. 신중하게 설계된 커브는 라운징을위한 완벽한 코너를 만듭니다. 실내 장식품은 더 편안한 앉기와 수면을위한 확고한 지원을 제공하며, 고급 패브릭 질감은 촉감에 따뜻함을 더합니다. 여기에서 조용한 독서, 명상 또는 휴식의 순간을 즐길 수 있습니다. 낮잠이든 명상의 밤이든, 이 침실 흔들 의자는 따뜻한 동반자입니다. \n" +
                " <br /> \n" +
                " <br />친절한 팁: \n" +
                " <br />■수동 측정에 의한 1-2cm 차이를 허용해 주세요. \n" +
                " <br />■조명, 디스플레이 및 기타 요인으로 인해 수신 된 물체와 그림 사이에 차이가있을 수 있으므로 실제 물체를 참조하십시오. \n" +
                " <br />■ 애프터 서비스: 사용 중에 질문이 있거나 제품이 누락 된 경우 언제든지 문의하십시오. 각 고객이 만족스러운 쇼핑 경험을 할 수 있도록 24 시간 이내에 회신하겠습니다.\n" +
                "</div> \n" +
                "<p style=\"text-align:left;margin:0px;margin-bottom:0px;margin-top:0px;margin-left:0px;margin-right:0px\" align=\"left\"><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/S2e822010452a4c21be7acf95f67b4a4eG.jpg?width=780&amp;height=1510&amp;hash=2290\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/Se6e5f03d3951479fb9e0151c4020ece7p.jpg?width=750&amp;height=1408&amp;hash=2158\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/Sdb163d4fd4c546bc8001b4b9393325997.jpg?width=780&amp;height=1176&amp;hash=1956\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/S89b3d09339614808ac303491accd3cafA.jpg?width=780&amp;height=1250&amp;hash=2030\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/Sfd94421cfc7b4ff2a5fb175e7ef6b52bn.jpg?width=780&amp;height=1188&amp;hash=1968\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/S1d35f8d818c44989a23c4d40e0e8fd2fn.jpg?width=780&amp;height=1604&amp;hash=2384\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/S7bc28a9d570c42d3894f0fc82b3e8b34y.jpg?width=780&amp;height=800&amp;hash=1580\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/S12f6df48533440f7a4b306b996f3a870e.jpg?width=780&amp;height=1442&amp;hash=2222\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/S88a0757c27a34ad18a61a0bd8cb3b1615.jpg?width=780&amp;height=2186&amp;hash=2966\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/S709388618764473db3a94d4510e06509f.jpg?width=780&amp;height=1038&amp;hash=1818\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/S3c5a50a100734c36865727310b5cdf844.jpg?width=750&amp;height=1000&amp;hash=1750\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/S83661125cada4a758dd16d9d65722979J.jpg?width=780&amp;height=1989&amp;hash=2769\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/Scf22ce07d0544943ae86bf8c87ccb31dP.jpg?width=780&amp;height=2313&amp;hash=3093\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/Sbcbc8e52fb2349ddaac548e7ebdac18cW.jpg?width=780&amp;height=2694&amp;hash=3474\" slate-data-type=\"image\" /><img style=\"width:780px\" width=\"780px\" src=\"https://ae01.alicdn.com/kf/S7ba561c0a43249f18f54443745c3334dh.jpg?width=780&amp;height=2101&amp;hash=2881\" slate-data-type=\"image\" /><img src=\"https://ae01.alicdn.com/kf/Sb2bca72e611444c6a492cfcb9511c371E.jpg_640x640q90.jpg\" slate-data-type=\"image\" /></p> \n" +
                "<p><br /></p></div></div>\n" +
                "\n" +
                "<script>window.adminAccountId=2672906622;</script>\n" +
                "<link href=\"//g.alicdn.com/ae-seller-gsp/ams-tools/0.0.2/material/itemdesc/parse.css\" rel=\"stylesheet\">\n" +
                "<script src=\"//g.alicdn.com/ae-seller-gsp/ams-tools/0.0.2/material/itemdesc/parse.js\"></script>\n";
        Images images = new Images();


        htmlParser.getDetailContentImages(detailContent, images);


        assertNotEquals(null, images.optionalImages.get(0).url);
    }
}