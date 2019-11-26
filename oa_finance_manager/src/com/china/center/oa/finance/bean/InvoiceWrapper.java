package com.china.center.oa.finance.bean;

import java.io.Serializable;

public class InvoiceWrapper implements Serializable {
    private String invoiceId;

    private String payload;

    public InvoiceWrapper(String invoiceId, String payload) {
        this.invoiceId = invoiceId;
        this.payload = payload;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
