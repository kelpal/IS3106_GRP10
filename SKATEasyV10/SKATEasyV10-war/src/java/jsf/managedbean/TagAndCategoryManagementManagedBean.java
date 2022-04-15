/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CategoryEntitySessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import entity.CategoryEntity;
import entity.TagEntity;
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
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewTagException;
import util.exception.DeleteCategoryException;
import util.exception.DeleteTagException;
import util.exception.InputDataValidationException;
import util.exception.TagNotFoundException;
import util.exception.UpdateCategoryException;
import util.exception.UpdateTagException;

/**
 *
 * @author harmo
 */
@Named(value = "tagAndCategoryManagementManagedBean")
@ViewScoped
public class TagAndCategoryManagementManagedBean implements Serializable {
    @EJB
    private CategoryEntitySessionBeanLocal categoryEntitySessionBeanLocal;

    @EJB
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;
    
    private List<CategoryEntity> categories;
    private List<TagEntity> tags;
    
    private CategoryEntity newCategory;
    private Long parentIdNew;
    private TagEntity newTag;
    
    private CategoryEntity categoryToView;
    private TagEntity tagToView;
    
    private CategoryEntity categoryToUpdate;
    private TagEntity tagToUpdate;
    private Long parentIdUpdate;
    
    

    /**
     * Creates a new instance of TagsAndCategoriesManagementManagedBean
     */
    public TagAndCategoryManagementManagedBean() {
        this.categories = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.newCategory = new CategoryEntity();
        this.newTag = new TagEntity();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        this.setCategories(categoryEntitySessionBeanLocal.retrieveAllCategories());
        this.setTags(tagEntitySessionBeanLocal.retrieveAllTags());
    }
    
    public void createNewCategory(ActionEvent event)
    {
        try
        {
            if(parentIdNew == 0)
            {
                parentIdNew = null;
            }
            
            categoryEntitySessionBeanLocal.createNewCategoryEntity(newCategory, parentIdNew);
            categories.add(newCategory);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Category has been created! ID: "+ newCategory.getCategoryId(), null));
            
            this.newCategory = new CategoryEntity();    
        } catch(CreateNewCategoryException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new category: " + ex.getMessage(), null));
        }
    }
    
    public void doUpdateCategory(ActionEvent event)
    {
        this.categoryToUpdate = (CategoryEntity)event.getComponent().getAttributes().get("categoryToUpdate");
    }
    
    public void doViewCategory(ActionEvent event)
    {
        this.categoryToView = (CategoryEntity)event.getComponent().getAttributes().get("categoryToView");
        System.out.println(categoryToView.getProductEntities());
        System.out.println(categoryToView.getSubCategoryEntities());        
    }
    
    public void updateCategory(ActionEvent event)
    {
        try
        {
            if(parentIdUpdate == 0)
            {
                parentIdUpdate = null;
            }
            categoryEntitySessionBeanLocal.updateCategory(categoryToUpdate, parentIdUpdate);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Category details have been updated! ID: "+ categoryToUpdate.getCategoryId(), null));
            
            this.newCategory = new CategoryEntity();    
        } catch(InputDataValidationException | CategoryNotFoundException | UpdateCategoryException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating category details: " + ex.getMessage(), null));
        }
    }
    
    public void deleteCategory(ActionEvent event)
    {
        try
        {
            CategoryEntity categoryToDelete = (CategoryEntity)event.getComponent().getAttributes().get("categoryToDelete");

            
            categoryEntitySessionBeanLocal.deleteCategory(categoryToDelete.getCategoryId());
            categories.remove(categoryToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Category has been deleted!", null));

        } catch (CategoryNotFoundException | DeleteCategoryException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting category: " + ex.getMessage(), null));
        }
    }

    public void createNewTag(ActionEvent event)
    {
        try
        {
            
            tagEntitySessionBeanLocal.createNewTagEntity(newTag);
            tags.add(newTag);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Tag has been created! ID: "+ newTag.getTagId(), null));
            
            this.newCategory = new CategoryEntity();    
        } catch(CreateNewTagException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new tag: " + ex.getMessage(), null));
        }
    }
    
    public void doUpdateTag(ActionEvent event)
    {
        this.tagToUpdate = (TagEntity)event.getComponent().getAttributes().get("tagToUpdate");
    }
    
    public void doViewTag(ActionEvent event)
    {
        this.setTagToView((TagEntity)event.getComponent().getAttributes().get("tagToView"));
    }
    
    public void updateTag(ActionEvent event)
    {
        try
        {
           
            tagEntitySessionBeanLocal.updateTag(tagToUpdate);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tag details have been updated! ID: "+ tagToUpdate.getTagId(), null));
            
            this.newTag = new TagEntity();    
        } catch(InputDataValidationException | TagNotFoundException | UpdateTagException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating tag details: " + ex.getMessage(), null));
        }
    }
    
    public void deleteTag(ActionEvent event)
    {
        try
        {
            TagEntity tagToDelete = (TagEntity)event.getComponent().getAttributes().get("tagToDelete");
            tags.remove(tagToDelete);
            
            tagEntitySessionBeanLocal.deleteTag(tagToDelete.getTagId());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tag has been deleted!", null));

        } catch (DeleteTagException | TagNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting tag: " + ex.getMessage(), null));
        }
    }
    /**
     * @return the categories
     */
    public List<CategoryEntity> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    /**
     * @return the tags
     */
    public List<TagEntity> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<TagEntity> tags) {
        this.tags = tags;
    }

    /**
     * @return the newCategory
     */
    public CategoryEntity getNewCategory() {
        return newCategory;
    }

    /**
     * @param newCategory the newCategory to set
     */
    public void setNewCategory(CategoryEntity newCategory) {
        this.newCategory = newCategory;
    }

    /**
     * @return the newTag
     */
    public TagEntity getNewTag() {
        return newTag;
    }

    /**
     * @param newTag the newTag to set
     */
    public void setNewTag(TagEntity newTag) {
        this.newTag = newTag;
    }

    /**
     * @return the categoryToView
     */
    public CategoryEntity getCategoryToView() {
        return categoryToView;
    }

    /**
     * @param categoryToView the categoryToView to set
     */
    public void setCategoryToView(CategoryEntity categoryToView) {
        this.categoryToView = categoryToView;
    }

    /**
     * @return the tagToView
     */
    public TagEntity getTagToView() {
        return tagToView;
    }

    /**
     * @param tagToView the tagToView to set
     */
    public void setTagToView(TagEntity tagToView) {
        this.tagToView = tagToView;
    }

    /**
     * @return the categoryToUpdate
     */
    public CategoryEntity getCategoryToUpdate() {
        return categoryToUpdate;
    }

    /**
     * @param categoryToUpdate the categoryToUpdate to set
     */
    public void setCategoryToUpdate(CategoryEntity categoryToUpdate) {
        this.categoryToUpdate = categoryToUpdate;
    }

    /**
     * @return the tagToUpdate
     */
    public TagEntity getTagToUpdate() {
        return tagToUpdate;
    }

    /**
     * @param tagToUpdate the tagToUpdate to set
     */
    public void setTagToUpdate(TagEntity tagToUpdate) {
        this.tagToUpdate = tagToUpdate;
    }

    /**
     * @return the parentIdNew
     */
    public Long getParentIdNew() {
        return parentIdNew;
    }

    /**
     * @param parentIdNew the parentIdNew to set
     */
    public void setParentIdNew(Long parentIdNew) {
        this.parentIdNew = parentIdNew;
    }

    /**
     * @return the parentIdUpdate
     */
    public Long getParentIdUpdate() {
        return parentIdUpdate;
    }

    /**
     * @param parentIdUpdate the parentIdUpdate to set
     */
    public void setParentIdUpdate(Long parentIdUpdate) {
        this.parentIdUpdate = parentIdUpdate;
    }
}
