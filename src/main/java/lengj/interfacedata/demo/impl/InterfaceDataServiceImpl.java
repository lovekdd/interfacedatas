package lengj.interfacedata.demo.impl;

import lengj.interfacedata.demo.entity.InterfaceDataEntity;
import lengj.interfacedata.demo.service.InterfaceDataRequest;
import lengj.interfacedata.demo.service.InterfaceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterfaceDataServiceImpl implements InterfaceDataService {

    @Autowired
    private InterfaceDataRequest request;

    @Override
    public String test(InterfaceDataEntity ide) throws Exception {
        return request.send(ide);
    }
}
