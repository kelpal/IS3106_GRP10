/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author harmo
 */
@Entity
public class CategoryEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(max = 32)
    private String name;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String description;
    
    @OneToMany(mappedBy = "parentCategoryEntity")
    private List<CategoryEntity> subCategoryEntities;
    @ManyToOne
    private CategoryEntity parentCategoryEntity;
    
    @OneToMany(mappedBy = "categoryEntity")
    private List<ProductEntity> productEntities;

    public CategoryEntity() 
    {
        subCategoryEntities = new ArrayList<>();
        productEntities = new ArrayList<>();
    }

    
    
    public CategoryEntity(String categoryName, String description) 
    {
        this();
        
        this.name = categoryName;
        this.description = description;
    }
    
    
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryEntity)) {
            return false;
        }
        CategoryEntity other = (CategoryEntity) object;
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CategoryEntity[ id=" + categoryId + " ]";
    }
        /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the subCategoryEntities
     */
    public List<CategoryEntity> getSubCategoryEntities() {
        return subCategoryEntities;
    }

    /**
     * @param subCategoryEntities the subCategoryEntities to set
     */
    public void setSubCategoryEntities(List<CategoryEntity> subCategoryEntities) {
        this.subCategoryEntities = subCategoryEntities;
    }

    /**
     * @return the parentCategoryEntity
     */
    public CategoryEntity getParentCategoryEntity() {
        return parentCategoryEntity;
    }

    /**
     * @param parentCategoryEntity the parentCategoryEntity to set
     */
    public void setParentCategoryEntity(CategoryEntity parentCategoryEntity) {
        this.parentCategoryEntity = parentCategoryEntity;
    }

    /**
     * @return the productEntities
     */
    public List<ProductEntity> getProductEntities() {
        return productEntities;
    }

    /**
     * @param productEntities the productEntities to set
     */
    public void setProductEntities(List<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }
}
