/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ArtistEntitySessionBeanLocal;
import ejb.session.stateless.StaffEntitySessionBeanLocal;
import entity.ArtistEntity;
import entity.StaffEntity;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.enumeration.AccessRightEnum;
import util.exception.ArtistNotFoundException;

/**
 *
 * @author harmo
 */
@Named(value = "viewMyProfileManagedBean")
@ViewScoped
public class ViewMyProfileManagedBean implements Serializable{

    @EJB
    private ArtistEntitySessionBeanLocal artistEntitySessionBeanLocal;

    

    @EJB
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;
    
    private StaffEntity currentStaffEntity;
    private StaffEntity staffEntityToUpdate;
    private String currentPassword;
    private String newPassword;
    private String newPP;
    private Boolean moveOn;
    
    private ArtistEntity currentArtistEntity;
    private ArtistEntity artistEntityToUpdate;
    
    private String newBestWork;
    private Boolean moveOn2;
    
    private Integer portfolioLength;
    private String newPortfolio;

    /**
     * Creates a new instance of ViewMyProfileManagedBean
     */
    public ViewMyProfileManagedBean() {
        this.currentStaffEntity = new StaffEntity();
        this.staffEntityToUpdate = new StaffEntity();
        this.moveOn = false;
        this.moveOn2 = false;
    }
    
    @PostConstruct
    public void postConstruct()
    {
        this.staffEntityToUpdate = (StaffEntity)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentStaff");

        this.currentStaffEntity = (StaffEntity)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentStaff");
        
        if(this.currentStaffEntity.getAccessRightEnum() == AccessRightEnum.ARTIST)
        {
            try
            {
            this.currentArtistEntity = artistEntitySessionBeanLocal.retrieveArtistById(this.currentStaffEntity.getStaffId());
            this.artistEntityToUpdate = artistEntitySessionBeanLocal.retrieveArtistById(this.currentStaffEntity.getStaffId());
            this.setNewPortfolio(currentArtistEntity.getPortfolio());

            } catch (ArtistNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    public void updateLength()
    {
        portfolioLength = getNewPortfolio().length();
        
    }
    
    public void updatePortfolio(ActionEvent event)
    {
        artistEntityToUpdate.setPortfolio(getNewPortfolio());
        artistEntitySessionBeanLocal.updateArtist(artistEntityToUpdate);
        
        this.currentArtistEntity = artistEntityToUpdate;
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Portfolio successfully changed! ID: " + staffEntityToUpdate.getStaffId(), null));
        
    }
    
    public void updateProfile(ActionEvent event)
    {
        staffEntitySessionBeanLocal.updateProfile(staffEntityToUpdate);
        
        this.currentStaffEntity = staffEntityToUpdate;
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile successfully changed! ID: " + staffEntityToUpdate.getStaffId(), null));
        
    }
    
    public void doChangePassword(ActionEvent event)
    {
        
        if (currentPassword.equals(currentStaffEntity.getPassword()))
        {
            staffEntityToUpdate.setPassword(newPassword);
            staffEntitySessionBeanLocal.updateProfile(staffEntityToUpdate);
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password successfully changed! ID: " + staffEntityToUpdate.getStaffId(), null));
            this.currentStaffEntity = staffEntityToUpdate;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Current password does not match!", null));
        }
    }
    
    public void doUpdatePP(ActionEvent event)
    {
        this.setMoveOn((Boolean) true);
    }
    
    public void doUpdateBestWork(ActionEvent event)
    {
        this.setMoveOn2((Boolean) true);
    }
    
    public void updatePP(ActionEvent event)
    {
        this.staffEntityToUpdate.setProfilePicture(newPP);

        staffEntitySessionBeanLocal.updateProfile(staffEntityToUpdate);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile Picture successfully changed! ID: " + staffEntityToUpdate.getStaffId(), null));
        this.currentStaffEntity = staffEntityToUpdate;
        this.setMoveOn((Boolean) false);
        
    }
    
    public void updateBestWork(ActionEvent event)
    {
        this.artistEntityToUpdate.setBestWork(newBestWork);

        artistEntitySessionBeanLocal.updateArtist(artistEntityToUpdate);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Best Work successfully changed! ID: " + staffEntityToUpdate.getStaffId(), null));
        this.currentArtistEntity = artistEntityToUpdate;
        this.setMoveOn2((Boolean) false);
        
    }

    /**
     * @return the currentStaffEntity
     */
    public StaffEntity getCurrentStaffEntity() {
        return currentStaffEntity;
    }

    /**
     * @param currentStaffEntity the currentStaffEntity to set
     */
    public void setCurrentStaffEntity(StaffEntity currentStaffEntity) {
        this.currentStaffEntity = currentStaffEntity;
    }

    /**
     * @return the staffEntityToUpdate
     */
    public StaffEntity getStaffEntityToUpdate() {
        return staffEntityToUpdate;
    }

    /**
     * @param staffEntityToUpdate the staffEntityToUpdate to set
     */
    public void setStaffEntityToUpdate(StaffEntity staffEntityToUpdate) {
        this.staffEntityToUpdate = staffEntityToUpdate;
    }

    /**
     * @return the currentPassword
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * @param currentPassword the currentPassword to set
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return the newPP
     */
    public String getNewPP() {
        return newPP;
    }

    /**
     * @param newPP the newPP to set
     */
    public void setNewPP(String newPP) {
        this.newPP = newPP;
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

    /**
     * @return the currentArtistEntity
     */
    public ArtistEntity getCurrentArtistEntity() {
        return currentArtistEntity;
    }

    /**
     * @param currentArtistEntity the currentArtistEntity to set
     */
    public void setCurrentArtistEntity(ArtistEntity currentArtistEntity) {
        this.currentArtistEntity = currentArtistEntity;
    }

    /**
     * @return the artistEntityToUpdate
     */
    public ArtistEntity getArtistEntityToUpdate() {
        return artistEntityToUpdate;
    }

    /**
     * @param artistEntityToUpdate the artistEntityToUpdate to set
     */
    public void setArtistEntityToUpdate(ArtistEntity artistEntityToUpdate) {
        this.artistEntityToUpdate = artistEntityToUpdate;
    }

    /**
     * @return the newBestWork
     */
    public String getNewBestWork() {
        return newBestWork;
    }

    /**
     * @param newBestWork the newBestWork to set
     */
    public void setNewBestWork(String newBestWork) {
        this.newBestWork = newBestWork;
    }

    /**
     * @return the moveOn2
     */
    public Boolean getMoveOn2() {
        return moveOn2;
    }

    /**
     * @param moveOn2 the moveOn2 to set
     */
    public void setMoveOn2(Boolean moveOn2) {
        this.moveOn2 = moveOn2;
    }

    /**
     * @return the portfolioLength
     */
    public Integer getPortfolioLength() {
        return portfolioLength;
    }

    /**
     * @param portfolioLength the portfolioLength to set
     */
    public void setPortfolioLength(Integer portfolioLength) {
        this.portfolioLength = portfolioLength;
    }

    /**
     * @return the newPortfolio
     */
    public String getNewPortfolio() {
        return newPortfolio;
    }

    /**
     * @param newPortfolio the newPortfolio to set
     */
    public void setNewPortfolio(String newPortfolio) {
        this.newPortfolio = newPortfolio;
    }
    
}
