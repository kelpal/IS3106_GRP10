/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kel
 */
@Entity
public class CustomerEntity {
    
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String name;
    @Column(nullable = false, unique = true, length = 64)
    @NotNull
    @Size(max = 64)
    @Email
    private String email;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(min = 8, max = 32)
    private String password; 
    @NotNull
    private Boolean isDisabled;
    @Column(nullable = true, length = 64)
    @Size(max = 64)
    private String address;
    
    @OneToMany(mappedBy = "customerEntity")
    private List<SaleTransactionEntity> saleTransactionEntities;

    @OneToMany(mappedBy = "customerEntity")
    private List<CustomisationRequest> customisationRequests;
    
    @OneToOne
    private CreditCard creditCard;
    
    public CustomerEntity() {
        this.saleTransactionEntities = new ArrayList<>();
        this.customisationRequests = new ArrayList<>();
        this.isDisabled = false;
    }

    public CustomerEntity(String name, String email, String password) {
        this();
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CustomerEntity)) {
            return false;
        } 
        
        CustomerEntity other = (CustomerEntity) object;
        
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    
    }

    @Override
    public String toString() {
        return "entity.CustomerEntity[ id={" + customerId + " ]";
    }
    

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the saleTransactionEntities
     */
    public List<SaleTransactionEntity> getSaleTransactionEntities() {
        return saleTransactionEntities;
    }

    /**
     * @param saleTransactionEntities the saleTransactionEntities to set
     */
    public void setSaleTransactionEntities(List<SaleTransactionEntity> saleTransactionEntities) {
        this.saleTransactionEntities = saleTransactionEntities;
    }

    /**
     * @return the customisationRequests
     */
    public List<CustomisationRequest> getCustomisationRequests() {
        return customisationRequests;
    }

    /**
     * @param customisationRequests the customisationRequests to set
     */
    public void setCustomisationRequests(List<CustomisationRequest> customisationRequests) {
        this.customisationRequests = customisationRequests;
    }

    /**
     * @return the isDisabled
     */
    public Boolean getIsDisabled() {
        return isDisabled;
    }

    /**
     * @param isDisabled the isDisabled to set
     */
    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    /**
     * @return the creditCard
     */
    public CreditCard getCreditCard() {
        return creditCard;
    }

    /**
     * @param creditCard the creditCard to set
     */
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
