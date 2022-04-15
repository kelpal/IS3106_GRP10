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
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.enumeration.StatusEnum;
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
    private CustomisationRequest customisationRequestToView;
    private Boolean moveOn;
    

    /**
     * Creates a new instance of ViewMyRequestsManagedBean
     */
    public ViewMyRequestsManagedBean() {
        this.customisationRequests = new ArrayList<>();
        this.currentArtist = (StaffEntity)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentStaff");
        this.customisationRequestToView = new CustomisationRequest();
        this.moveOn = false;
    }
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            this.customisationRequests = customisationRequestSessionBeanLocal.retreiveCustomisationRequestsByDate(getCurrentArtist().getStaffId());
        } catch(ArtistNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public void doViewProduct(ActionEvent event)
    {
        this.setCustomisationRequestToView((CustomisationRequest)event.getComponent().getAttributes().get("requestToView"));
        
    }
    
    public void acceptRequest(ActionEvent event)
    {

        customisationRequestToView.setStatus(StatusEnum.ACCEPTED);

        customisationRequestSessionBeanLocal.updateCustomisationRequest(customisationRequestToView);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Request has been accepted! ID: " + customisationRequestToView.getCustomisationRequestId(), null));

    }
    
    public void completeRequest(ActionEvent event)
    {
            customisationRequestToView.setStatus(StatusEnum.COMPLETED);
            customisationRequestToView.setCompletionDate(new Date());

            customisationRequestSessionBeanLocal.updateCustomisationRequest(customisationRequestToView);
           
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Request has been completed! ID: " + customisationRequestToView.getCustomisationRequestId(), null));
    }
    
    public void rejectRequest(ActionEvent event)
    {

        customisationRequestToView.setStatus(StatusEnum.REJECTED);

        customisationRequestSessionBeanLocal.updateCustomisationRequest(customisationRequestToView);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Request has been rejected! ID: " + customisationRequestToView.getCustomisationRequestId(), null));
        this.moveOn = false;

    }
    
    public void openRejectionReason(ActionEvent event)
    {
        this.moveOn = true;
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

    /**
     * @return the customisationRequestToView
     */
    public CustomisationRequest getCustomisationRequestToView() {
        return customisationRequestToView;
    }

    /**
     * @param customisationRequestToView the customisationRequestToView to set
     */
    public void setCustomisationRequestToView(CustomisationRequest customisationRequestToView) {
        this.customisationRequestToView = customisationRequestToView;
    }

    /**
     * @return the moveOn
     */
    public Boolean getMoveOn() {
        return moveOn;
    }

    /**
     * @param moveOn the moveOn to set
     */
    public void setMoveOn(Boolean moveOn) {
        this.moveOn = moveOn;
    }
}
