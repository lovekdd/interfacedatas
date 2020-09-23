package lengj.interfacedata.demo.entity;

import java.io.Serializable;

/**
 * 接口数据相关实体类
 */
public class InterfaceDataEntity implements Serializable {
    //序列化id
    private static final long serialVersionUID = 6177243581916184841L;
    //接口测试id
    private String id;
    //接口测试名
    private String name;
    //接口测试url
    private String url;
    //接口测试参数
    private String params;
    //接口测试请求方式
    private String method;
    //接口请求原始编码方式
    private String srcEncoding;
    //接口请求目标编码方式
    private String dstEncoding;

    public String getDstEncoding() {
        return dstEncoding;
    }

    public void setDstEncoding(String dstEncoding) {
        this.dstEncoding = dstEncoding;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSrcEncoding() {
        return srcEncoding;
    }

    public void setSrcEncoding(String srcEncoding) {
        this.srcEncoding = srcEncoding;
    }

}
