package com.china.center.oa.finance.manager.payorder.sf.jaxobject.req;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Order")
public class SfOrderService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String orderid;
	
	private String mailno;
	
	private String j_company;
	
	private String j_contact;
	
	private String j_tel;
	
	private String j_mobile;
	
	private String j_province;
	
	private String j_city;
	
	private String j_county;
	
	private String j_address;
	
	private String d_company;
	
	private String d_contact;
	
	private String d_tel;
	
	private String d_mobile;
	
	private String d_province;
	
	private String d_city;
	
	private String d_county;
	
	private String d_address;
	
	private String custid;
	
	private String pay_method;
	
	private String express_type;
	
	private String parcel_quantity;
	
	private String cargo_length;
	
	private String cargo_width;
	
	private String cargo_height;
	
	private String volume;
	
	private String cargo_total_weight;
	
	private String sendstarttime;
	
	private String is_docall;
	
	private String need_return_tracking_no;
	
	private String return_tracking;
	
	private String temp_range;
	
	private String template;
	
	private String remark;
	
	private String oneself_pickup_flg;
	
	private String special_delivery_type_code;
	
	private String special_delivery_value;
	
	private String realname_num;
	
	private String routelabelForReturn;
	
	private String routelabelService;
	
	private String is_unified_waybill_no;
	
	@XmlAttribute(name = "orderid")
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	@XmlAttribute(name = "mailno")
	public String getMailno() {
		return mailno;
	}

	public void setMailno(String mailno) {
		this.mailno = mailno;
	}

	@XmlAttribute(name = "j_company")
	public String getJ_company() {
		return j_company;
	}

	public void setJ_company(String j_company) {
		this.j_company = j_company;
	}

	@XmlAttribute(name = "j_contact")
	public String getJ_contact() {
		return j_contact;
	}

	public void setJ_contact(String j_contact) {
		this.j_contact = j_contact;
	}

	@XmlAttribute(name = "j_tel")
	public String getJ_tel() {
		return j_tel;
	}

	public void setJ_tel(String j_tel) {
		this.j_tel = j_tel;
	}

	@XmlAttribute(name = "j_mobile")
	public String getJ_mobile() {
		return j_mobile;
	}

	public void setJ_mobile(String j_mobile) {
		this.j_mobile = j_mobile;
	}

	@XmlAttribute(name = "j_province")
	public String getJ_province() {
		return j_province;
	}

	public void setJ_province(String j_province) {
		this.j_province = j_province;
	}

	@XmlAttribute(name = "j_city")
	public String getJ_city() {
		return j_city;
	}

	public void setJ_city(String j_city) {
		this.j_city = j_city;
	}

	@XmlAttribute(name = "j_county")
	public String getJ_county() {
		return j_county;
	}

	public void setJ_county(String j_county) {
		this.j_county = j_county;
	}

	@XmlAttribute(name = "j_address")
	public String getJ_address() {
		return j_address;
	}

	public void setJ_address(String j_address) {
		this.j_address = j_address;
	}

	@XmlAttribute(name = "d_company")
	public String getD_company() {
		return d_company;
	}

	public void setD_company(String d_company) {
		this.d_company = d_company;
	}

	@XmlAttribute(name = "d_contact")
	public String getD_contact() {
		return d_contact;
	}

	public void setD_contact(String d_contact) {
		this.d_contact = d_contact;
	}

	@XmlAttribute(name = "d_tel")
	public String getD_tel() {
		return d_tel;
	}

	public void setD_tel(String d_tel) {
		this.d_tel = d_tel;
	}

	@XmlAttribute(name = "d_mobile")
	public String getD_mobile() {
		return d_mobile;
	}

	public void setD_mobile(String d_mobile) {
		this.d_mobile = d_mobile;
	}

	@XmlAttribute(name = "d_province")
	public String getD_province() {
		return d_province;
	}

	public void setD_province(String d_province) {
		this.d_province = d_province;
	}

	@XmlAttribute(name = "d_city")
	public String getD_city() {
		return d_city;
	}

	public void setD_city(String d_city) {
		this.d_city = d_city;
	}

	@XmlAttribute(name = "d_county")
	public String getD_county() {
		return d_county;
	}

	public void setD_county(String d_county) {
		this.d_county = d_county;
	}

	@XmlAttribute(name = "d_address")
	public String getD_address() {
		return d_address;
	}

	public void setD_address(String d_address) {
		this.d_address = d_address;
	}

	@XmlAttribute(name = "custid")
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	@XmlAttribute(name = "pay_method")
	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	@XmlAttribute(name = "express_type")
	public String getExpress_type() {
		return express_type;
	}

	public void setExpress_type(String express_type) {
		this.express_type = express_type;
	}

	@XmlAttribute(name = "parcel_quantity")
	public String getParcel_quantity() {
		return parcel_quantity;
	}

	public void setParcel_quantity(String parcel_quantity) {
		this.parcel_quantity = parcel_quantity;
	}

	@XmlAttribute(name = "cargo_length")
	public String getCargo_length() {
		return cargo_length;
	}

	public void setCargo_length(String cargo_length) {
		this.cargo_length = cargo_length;
	}

	@XmlAttribute(name = "cargo_width")
	public String getCargo_width() {
		return cargo_width;
	}

	public void setCargo_width(String cargo_width) {
		this.cargo_width = cargo_width;
	}

	@XmlAttribute(name = "cargo_height")
	public String getCargo_height() {
		return cargo_height;
	}

	public void setCargo_height(String cargo_height) {
		this.cargo_height = cargo_height;
	}

	@XmlAttribute(name = "volume")
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	@XmlAttribute(name = "cargo_total_weight")
	public String getCargo_total_weight() {
		return cargo_total_weight;
	}

	public void setCargo_total_weight(String cargo_total_weight) {
		this.cargo_total_weight = cargo_total_weight;
	}

	@XmlAttribute(name = "sendstarttime")
	public String getSendstarttime() {
		return sendstarttime;
	}

	public void setSendstarttime(String sendstarttime) {
		this.sendstarttime = sendstarttime;
	}

	@XmlAttribute(name = "is_docall")
	public String getIs_docall() {
		return is_docall;
	}

	public void setIs_docall(String is_docall) {
		this.is_docall = is_docall;
	}

	@XmlAttribute(name = "need_return_tracking_no")
	public String getNeed_return_tracking_no() {
		return need_return_tracking_no;
	}

	public void setNeed_return_tracking_no(String need_return_tracking_no) {
		this.need_return_tracking_no = need_return_tracking_no;
	}

	@XmlAttribute(name = "return_tracking")
	public String getReturn_tracking() {
		return return_tracking;
	}

	public void setReturn_tracking(String return_tracking) {
		this.return_tracking = return_tracking;
	}

	@XmlAttribute(name = "temp_range")
	public String getTemp_range() {
		return temp_range;
	}

	public void setTemp_range(String temp_range) {
		this.temp_range = temp_range;
	}

	@XmlAttribute(name = "template")
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@XmlAttribute(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@XmlAttribute(name = "oneself_pickup_flg")
	public String getOneself_pickup_flg() {
		return oneself_pickup_flg;
	}

	public void setOneself_pickup_flg(String oneself_pickup_flg) {
		this.oneself_pickup_flg = oneself_pickup_flg;
	}

	@XmlAttribute(name = "special_delivery_type_code")
	public String getSpecial_delivery_type_code() {
		return special_delivery_type_code;
	}

	public void setSpecial_delivery_type_code(String special_delivery_type_code) {
		this.special_delivery_type_code = special_delivery_type_code;
	}

	@XmlAttribute(name = "special_delivery_value")
	public String getSpecial_delivery_value() {
		return special_delivery_value;
	}

	public void setSpecial_delivery_value(String special_delivery_value) {
		this.special_delivery_value = special_delivery_value;
	}

	@XmlAttribute(name = "realname_num")
	public String getRealname_num() {
		return realname_num;
	}

	public void setRealname_num(String realname_num) {
		this.realname_num = realname_num;
	}

	@XmlAttribute(name = "routelabelForReturn")
	public String getRoutelabelForReturn() {
		return routelabelForReturn;
	}

	public void setRoutelabelForReturn(String routelabelForReturn) {
		this.routelabelForReturn = routelabelForReturn;
	}

	@XmlAttribute(name = "routelabelService")
	public String getRoutelabelService() {
		return routelabelService;
	}

	public void setRoutelabelService(String routelabelService) {
		this.routelabelService = routelabelService;
	}

	@XmlAttribute(name = "is_unified_waybill_no")
	public String getIs_unified_waybill_no() {
		return is_unified_waybill_no;
	}

	public void setIs_unified_waybill_no(String is_unified_waybill_no) {
		this.is_unified_waybill_no = is_unified_waybill_no;
	}

}
