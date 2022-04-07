package com.ai.st.microservice.operators.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "deliveries", schema = "operators")
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "observations", nullable = false, length = 500)
    private String observations;

    @Column(name = "manager_code", nullable = false)
    private Long managerCode;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "municipality_code", nullable = false, length = 10)
    private String municipalityCode;

    @Column(name = "download_report_url", nullable = true, length = 1000)
    private String downloadReportUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id", referencedColumnName = "id", nullable = false)
    private OperatorEntity operator;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
    private List<SupplyDeliveredEntity> supplies = new ArrayList<SupplyDeliveredEntity>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Long getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(Long managerCode) {
        this.managerCode = managerCode;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public OperatorEntity getOperator() {
        return operator;
    }

    public void setOperator(OperatorEntity operator) {
        this.operator = operator;
    }

    public List<SupplyDeliveredEntity> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<SupplyDeliveredEntity> supplies) {
        this.supplies = supplies;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getDownloadReportUrl() {
        return downloadReportUrl;
    }

    public void setDownloadReportUrl(String downloadReportUrl) {
        this.downloadReportUrl = downloadReportUrl;
    }

}
