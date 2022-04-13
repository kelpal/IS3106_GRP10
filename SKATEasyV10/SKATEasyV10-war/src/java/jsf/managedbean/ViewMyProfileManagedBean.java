/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.StaffEntitySessionBeanLocal;
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

/**
 *
 * @author harmo
 */
@Named(value = "viewMyProfileManagedBean")
@ViewScoped
public class ViewMyProfileManagedBean implements Serializable{

    

    @EJB
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;
    
    private StaffEntity currentStaffEntity;
    private StaffEntity staffEntityToUpdate;
    private String currentPassword;
    private String newPassword;

    /**
     * Creates a new instance of ViewMyProfileManagedBean
     */
    public ViewMyProfileManagedBean() {
        this.currentStaffEntity = new StaffEntity();
        this.staffEntityToUpdate = new StaffEntity();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        this.staffEntityToUpdate = (StaffEntity)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentStaff");

        this.currentStaffEntity = (StaffEntity)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentStaff");
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
    
}
