package com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice;

public class ErpPlatformPortTypeProxy implements com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformPortType {
  private String _endpoint = null;
  private com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformPortType erpPlatformPortType = null;
  
  public ErpPlatformPortTypeProxy() {
    _initErpPlatformPortTypeProxy();
  }
  
  public ErpPlatformPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initErpPlatformPortTypeProxy();
  }
  
  private void _initErpPlatformPortTypeProxy() {
    try {
      erpPlatformPortType = (new com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformLocator()).geterpPlatformHttpSoap11Endpoint();
      if (erpPlatformPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)erpPlatformPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)erpPlatformPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (erpPlatformPortType != null)
      ((javax.xml.rpc.Stub)erpPlatformPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformPortType getErpPlatformPortType() {
    if (erpPlatformPortType == null)
      _initErpPlatformPortTypeProxy();
    return erpPlatformPortType;
  }
  
  public java.lang.String serverErpXml(java.lang.String erpReqXml) throws java.rmi.RemoteException{
    if (erpPlatformPortType == null)
      _initErpPlatformPortTypeProxy();
    return erpPlatformPortType.serverErpXml(erpReqXml);
  }
  
  
}