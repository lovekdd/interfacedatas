package lengj.interfacedata.demo.action;

import lengj.interfacedata.demo.entity.InterfaceDataEntity;
import lengj.interfacedata.demo.service.InterfaceDataService;
import lengj.interfacedata.demo.util.StrFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 接口测试主入口
 */
@Controller
public class ActionInterfaceDataSource {

    @Autowired
    private InterfaceDataService idsService;

    @RequestMapping("")
    public String index(Model model){
        return "test";
    }
    /*
     * 测试请求
     * @param req
     */
    @RequestMapping("/testinterfacedata")
    @ResponseBody
    private String doTestRequest(HttpServletRequest req) throws Exception {

        InterfaceDataEntity idsEntity = null;
//        String id = req.getParameter("id");
//        String name = req.getParameter("name");
//        name =  StrFunc.unescapeURIComponent(name);
//        String url = req.getParameter("url");
//        url =  StrFunc.unescapeURIComponent(url);
//        String params = req.getParameter("params");
//        String method = req.getParameter("method");
//        String srcEncoding = req.getParameter("srcEncoding");
//        String dstEncoding = req.getParameter("dstEncoding");
        String id="001";
        String name = "test1";
        String url ="http://yun.zhuzhufanli.com/mini/select/";
        String params = "[{'appid':'124986','outerid':'d5ZVkBM8VuQHS0Rx','isHeader':'false','taskname':'R200921_224980823','pageno':'1'}]";
        String method ="POST";
        String dstEncoding = "UTF-8";
        String srcEncoding = "UTF-8";
        //重新指定参数，测试结果数据源
        idsEntity = new InterfaceDataEntity();
        id = StrFunc.null2default(id, UUID.randomUUID().toString());
        idsEntity.setId(id);
        idsEntity.setName(name);
        idsEntity.setUrl(url);
        idsEntity.setParams(params);
        idsEntity.setMethod(method);
        idsEntity.setDstEncoding(dstEncoding);
        idsEntity.setSrcEncoding(srcEncoding);
        //测试接口数据
        return idsService.test(idsEntity);
    }

}
