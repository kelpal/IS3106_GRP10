/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CustomisationRequestSessionBeanLocal;
import entity.CustomisationRequest;
import entity.StaffEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import util.exception.ArtistNotFoundException;

/**
 *
 * @author harmo
 */
@Named(value = "viewMyRequestsManagedBean")
@ViewScoped
public class ViewMyRequestsManagedBean implements Serializable {

    

    @EJB
    private CustomisationRequestSessionBeanLocal customisationRequestSessionBeanLocal;
    
    private List<CustomisationRequest> customisationRequests;
    private StaffEntity currentArtist;
    

    /**
     * Creates a new instance of ViewMyRequestsManagedBean
     */
    public ViewMyRequestsManagedBean() {
        this.customisationRequests = new ArrayList<>();
        this.currentArtist = (StaffEntity)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentStaff");

    }
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            this.customisationRequests = customisationRequestSessionBeanLocal.retreiveCustomisationRequests(getCurrentArtist().getStaffId());
        } catch(ArtistNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
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
     * @return the currentArtist
     */
    public StaffEntity getCurrentArtist() {
        return currentArtist;
    }

    /**
     * @param currentArtist the currentArtist to set
     */
    public void setCurrentArtist(StaffEntity currentArtist) {
        this.currentArtist = currentArtist;
    }
    
}
