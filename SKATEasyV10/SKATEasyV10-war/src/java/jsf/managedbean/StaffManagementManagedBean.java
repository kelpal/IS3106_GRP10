/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.StaffEntitySessionBeanLocal;
import entity.StaffEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.enumeration.AccessRightEnum;
import util.exception.DeleteStaffException;
import util.exception.InputDataValidationException;
import util.exception.StaffNotFoundException;
import util.exception.StaffUsernameExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateStaffException;

/**
 *
 * @author harmo
 */
@Named(value = "staffManagementManagedBean")
@ViewScoped
public class StaffManagementManagedBean implements Serializable {

    

    

    @EJB
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;
    
    private List<StaffEntity> staff;
    
    private StaffEntity newStaff;
    private AccessRightEnum[] accessRightEnums;
    
    private StaffEntity staffToUpdate;
    
    private StaffEntity staffToView;

    /**
     * Creates a new instance of StaffManagementManagedBean
     */
    public StaffManagementManagedBean() {
        this.newStaff = new StaffEntity();
        this.staff = new ArrayList<>();
        this.staffToUpdate = new StaffEntity();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        this.setAccessRightEnums(AccessRightEnum.values());
        this.setStaff(staffEntitySessionBeanLocal.retrieveAllStaffs());
    }
    
    public void createNewStaff(ActionEvent event)
    {
        try
        {
            staffEntitySessionBeanLocal.createNewStaff(newStaff);
            getStaff().add(newStaff);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Staff has been created! ID: "+ newStaff.getStaffId(), null));
            
            this.newStaff = new StaffEntity();    
        } catch(InputDataValidationException | StaffUsernameExistException | UnknownPersistenceException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new staff: " + ex.getMessage(), null));
        }
    }
    
    public void doUpdateStaff(ActionEvent event)
    {
        this.staffToUpdate = (StaffEntity)event.getComponent().getAttributes().get("staffToUpdate");
    }
    
    public void doViewStaff(ActionEvent event)
    {
        this.setStaffToView((StaffEntity)event.getComponent().getAttributes().get("staffToView"));
    }
    
    public void updateStaff(ActionEvent event)
    {
        try
        {
            staffEntitySessionBeanLocal.updateStaff(staffToUpdate);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff details have been updated! ID: "+ staffToUpdate.getStaffId(), null));
            
            this.newStaff = new StaffEntity();    
        } catch(InputDataValidationException | StaffNotFoundException | UpdateStaffException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating staff details: " + ex.getMessage(), null));
        }
    }
    
    public void deleteStaff(ActionEvent event)
    {
        try
        {
            StaffEntity staffToDelete = (StaffEntity)event.getComponent().getAttributes().get("staffToDelete");
            staff.remove(staffToDelete);
            
            staffEntitySessionBeanLocal.deleteStaff(staffToDelete.getStaffId());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff has been deleted!", null));

        } catch (DeleteStaffException | StaffNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting staff: " + ex.getMessage(), null));
        }
    }
    
    /**
     * @return the newStaff
     */
    public StaffEntity getNewStaff() {
        return newStaff;
    }

    /**
     * @param newStaff the newStaff to set
     */
    public void setNewStaff(StaffEntity newStaff) {
        this.newStaff = newStaff;
    }

    /**
     * @return the accessRightEnums
     */
    public AccessRightEnum[] getAccessRightEnums() {
        return accessRightEnums;
    }

    /**
     * @param accessRightEnums the accessRightEnums to set
     */
    public void setAccessRightEnums(AccessRightEnum[] accessRightEnums) {
        this.accessRightEnums = accessRightEnums;
    }

    /**
     * @return the staffToUpdate
     */
    public StaffEntity getStaffToUpdate() {
        return staffToUpdate;
    }

    /**
     * @param staffToUpdate the staffToUpdate to set
     */
    public void setStaffToUpdate(StaffEntity staffToUpdate) {
        this.staffToUpdate = staffToUpdate;
    }
    
    /**
     * @return the staff
     */
    public List<StaffEntity> getStaff() {
        return staff;
    }

    /**
     * @param staff the staff to set
     */
    public void setStaff(List<StaffEntity> staff) {
        this.staff = staff;
    }

    /**
     * @return the staffToView
     */
    public StaffEntity getStaffToView() {
        return staffToView;
    }

    /**
     * @param staffToView the staffToView to set
     */
    public void setStaffToView(StaffEntity staffToView) {
        this.staffToView = staffToView;
    }
    
}
