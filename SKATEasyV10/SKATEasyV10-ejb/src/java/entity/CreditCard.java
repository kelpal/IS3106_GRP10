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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author harmo
 */
@Entity
public class CreditCard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditCardId;
    @Column(nullable = false)
    @NotNull
    @Size(min = 16, max = 16)
    private String creditCardNumber;
    @Column(nullable = false, length = 200)
    @NotNull
    @Size(max = 200)
    private String creditCardHolderName;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date expiryDate;  
    @Column(nullable = false)
    @NotNull
    @Size(min = 3, max = 3)
    private String cvvNumber;
    
    @OneToOne(mappedBy = "creditCard")
    @JoinColumn(nullable = false)
    private CustomerEntity customer;

    public CreditCard() {
    }

    public CreditCard(String creditCardNumber, String creditCardHolderName, Date expiryDate, String cvvNumber) {
        this.creditCardNumber = creditCardNumber;
        this.creditCardHolderName = creditCardHolderName;
        this.expiryDate = expiryDate;
        this.cvvNumber = cvvNumber;
    }
    
    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creditCardId != null ? creditCardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditCard)) {
            return false;
        }
        CreditCard other = (CreditCard) object;
        if ((this.creditCardId == null && other.creditCardId != null) || (this.creditCardId != null && !this.creditCardId.equals(other.creditCardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CreditCard[ id=" + creditCardId + " ]";
    }

    /**
     * @return the creditCardNumber
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * @param creditCardNumber the creditCardNumber to set
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * @return the creditCardHolderName
     */
    public String getCreditCardHolderName() {
        return creditCardHolderName;
    }

    /**
     * @param creditCardHolderName the creditCardHolderName to set
     */
    public void setCreditCardHolderName(String creditCardHolderName) {
        this.creditCardHolderName = creditCardHolderName;
    }

    /**
     * @return the expiryDate
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate the expiryDate to set
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return the cvvNumber
     */
    public String getCvvNumber() {
        return cvvNumber;
    }

    /**
     * @param cvvNumber the cvvNumber to set
     */
    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    /**
     * @return the customer
     */
    public CustomerEntity getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
    
}
