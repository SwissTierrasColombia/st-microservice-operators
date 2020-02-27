package com.ai.st.microservice.operators.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "supplies_delivered", schema = "operators")
public class SupplyDeliveredEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "observations", nullable = true, length = 500)
	private String observations;

	@Column(name = "supply_code", nullable = false)
	private Long supplyCode;

	@Column(name = "downloaded", nullable = false)
	private Boolean downloaded;

	@Column(name = "downloaded_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date downloadedAt;

	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id", referencedColumnName = "id", nullable = false)
	private DeliveryEntity delivery;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Long getSupplyCode() {
		return supplyCode;
	}

	public void setSupplyCode(Long supplyCode) {
		this.supplyCode = supplyCode;
	}

	public Boolean getDownloaded() {
		return downloaded;
	}

	public void setDownloaded(Boolean downloaded) {
		this.downloaded = downloaded;
	}

	public Date getDownloadedAt() {
		return downloadedAt;
	}

	public void setDownloadedAt(Date downloadedAt) {
		this.downloadedAt = downloadedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public DeliveryEntity getDelivery() {
		return delivery;
	}

	public void setDelivery(DeliveryEntity delivery) {
		this.delivery = delivery;
	}

}
