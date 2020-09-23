package lengj.interfacedata.demo.service;

import lengj.interfacedata.demo.entity.InterfaceDataEntity;
import org.json.JSONException;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface InterfaceDataRequest {
    public String send(InterfaceDataEntity ide) throws JSONException, KeyManagementException, NoSuchAlgorithmException, Exception;
}
