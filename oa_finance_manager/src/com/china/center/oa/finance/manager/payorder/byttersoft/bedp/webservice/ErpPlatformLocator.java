/**
 * ErpPlatformLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice;

public class ErpPlatformLocator extends org.apache.axis.client.Service implements com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatform {

    public ErpPlatformLocator() {
    }


    public ErpPlatformLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ErpPlatformLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for erpPlatformHttpSoap11Endpoint
    private java.lang.String erpPlatformHttpSoap11Endpoint_address = "http://101.37.13.154:8090/BisOutPlatform/services/erpPlatform.erpPlatformHttpSoap11Endpoint/";

    public java.lang.String geterpPlatformHttpSoap11EndpointAddress() {
        return erpPlatformHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String erpPlatformHttpSoap11EndpointWSDDServiceName = "erpPlatformHttpSoap11Endpoint";

    public java.lang.String geterpPlatformHttpSoap11EndpointWSDDServiceName() {
        return erpPlatformHttpSoap11EndpointWSDDServiceName;
    }

    public void seterpPlatformHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        erpPlatformHttpSoap11EndpointWSDDServiceName = name;
    }

    public com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformPortType geterpPlatformHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(erpPlatformHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return geterpPlatformHttpSoap11Endpoint(endpoint);
    }

    public com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformPortType geterpPlatformHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformSoap11BindingStub _stub = new com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformSoap11BindingStub(portAddress, this);
            _stub.setPortName(geterpPlatformHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void seterpPlatformHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        erpPlatformHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformPortType.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformSoap11BindingStub _stub = new com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformSoap11BindingStub(new java.net.URL(erpPlatformHttpSoap11Endpoint_address), this);
                _stub.setPortName(geterpPlatformHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("erpPlatformHttpSoap11Endpoint".equals(inputPortName)) {
            return geterpPlatformHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.bedp.byttersoft.com", "erpPlatform");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.bedp.byttersoft.com", "erpPlatformHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("erpPlatformHttpSoap11Endpoint".equals(portName)) {
            seterpPlatformHttpSoap11EndpointEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
