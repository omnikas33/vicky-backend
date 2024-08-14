package com.lsit.entity;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_payment_uploads")
public class PaymentUploads {

    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "com.lsit.utils.CustomUUIDGenerator")
    @Column(name = "upload_id", nullable = false, updatable = false)
    private String uploadId;

    @Lob
    @Column(name = "dd_upload", columnDefinition = "LONGBLOB")
    private byte[] ddUpload;

    @Lob
    @Column(name = "counter_slip", columnDefinition = "LONGBLOB")
    private byte[] counterSlip;

    @Temporal(TemporalType.DATE)
    @Column(name = "upload_date", nullable = true)
    private Date uploadDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    @JsonBackReference
    private Payment payment;

    public void setDdUpload(byte[] ddUpload) {
        this.ddUpload = ddUpload;
        updateUploadDate();
    }

    public void setCounterSlip(byte[] counterSlip) {
        this.counterSlip = counterSlip;
        updateUploadDate();
    }

    private void updateUploadDate() {
        if (this.ddUpload != null || this.counterSlip != null) {
            this.uploadDate = new Date();
        }
    }
}
