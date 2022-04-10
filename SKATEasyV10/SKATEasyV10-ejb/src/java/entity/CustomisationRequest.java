/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.StatusEnum;

/**
 *
 * @author harmo
 */
@Entity
public class CustomisationRequest implements Serializable {

    

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customisationRequestId;
    @Column(length = 128)
    @Size(max = 128)
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;  
    @Column(nullable = false)
    @NotNull
    private StatusEnum status;
    @Column(length = 128, nullable = true)
    @Size(max = 128)
    private String rejectionReason;
    
    @ManyToOne
    private CustomerEntity customerEntity;
    
    @ManyToOne
    private ArtistEntity artistEntity;

    public CustomisationRequest(String description, Date requestDate, StatusEnum status) {
        this.description = description;
        this.requestDate = requestDate;
        this.status = status;
    }

    public CustomisationRequest() {
    }

    public Long getCustomisationRequestId() {
        return customisationRequestId;
    }

    public void setCustomisationRequestId(Long customisationRequestId) {
        this.customisationRequestId = customisationRequestId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customisationRequestId != null ? customisationRequestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomisationRequest)) {
            return false;
        }
        CustomisationRequest other = (CustomisationRequest) object;
        if ((this.customisationRequestId == null && other.customisationRequestId != null) || (this.customisationRequestId != null && !this.customisationRequestId.equals(other.customisationRequestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CustomisationRequest[ id=" + customisationRequestId + " ]";
    }

    /**
     * @return the requestDate
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
    
    /**
     * @return the status
     */
    public StatusEnum getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    /**
     * @return the rejectionReason
     */
    public String getRejectionReason() {
        return rejectionReason;
    }

    /**
     * @param rejectionReason the rejectionReason to set
     */
    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    /**
     * @return the customerEntity
     */
    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    /**
     * @param customerEntity the customerEntity to set
     */
    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    /**
     * @return the artistEntity
     */
    public ArtistEntity getArtistEntity() {
        return artistEntity;
    }

    /**
     * @param artistEntity the artistEntity to set
     */
    public void setArtistEntity(ArtistEntity artistEntity) {
        this.artistEntity = artistEntity;
    }
    
}
