/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.AccessRightEnum;

/**
 *
 * @author harmo
 */
@Entity
public class ArtistEntity extends StaffEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(length = 1500)
    @Size(max = 1500)
    private String portfolio;
    
    @Column(length = 2000)
    @Size (max = 2000)
    private String bestWork;
    
    @OneToMany(mappedBy = "artistEntity", fetch = FetchType.LAZY)
    private List<CustomisationRequest> customisationRequests;
    
    public ArtistEntity() {
    }

    public ArtistEntity(String portfolio, List<CustomisationRequest> customisationRequests) {
        this.portfolio = portfolio;
        this.customisationRequests = customisationRequests;
    }

    public ArtistEntity(String portfolio, List<CustomisationRequest> customisationRequests, String firstName, String lastName, AccessRightEnum accessRightEnum, String username, String password) {
        super(firstName, lastName, accessRightEnum, username, password);
        this.portfolio = portfolio;
        this.customisationRequests = customisationRequests;
    }

    public ArtistEntity(String firstName, String lastName, AccessRightEnum accessRightEnum, String username, String password) {
        super(firstName, lastName, accessRightEnum, username, password);
    }

    /**
     * @return the portfolio
     */
    public String getPortfolio() {
        return portfolio;
    }

    /**
     * @param portfolio the portfolio to set
     */
    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
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
     * @return the bestWork
     */
    public String getBestWork() {
        return bestWork;
    }

    /**
     * @param bestWork the bestWork to set
     */
    public void setBestWork(String bestWork) {
        this.bestWork = bestWork;
    }

   

}
