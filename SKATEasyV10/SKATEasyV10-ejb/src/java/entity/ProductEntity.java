/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 *
 * @author kel
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(nullable = false, unique = true, length = 7)
    @NotNull
    @Size(min = 7, max = 7)
    private String skuCode;
    @Column(nullable = false, length = 200)
    @NotNull
    @Size(max = 200)
    private String name;
    @Column(length = 300)
    @Size(max = 300)
    private String description;
    @Column(length = 300)
    @Size (max = 300)
    private String imageLink;
    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer quantityOnHand;
    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer reorderQuantity;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2) // 11 - 2 digits to the left of the decimal point
    private BigDecimal unitPrice;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2) // 11 - 2 digits to the left of the decimal point
    private BigDecimal originalPrice;
    @Column(nullable = false)
    @NotNull
    @Positive
    @Min(1)
    @Max(5)
    private Integer productRating;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CategoryEntity categoryEntity;
    
    @ManyToMany(mappedBy = "productEntities")
    private List<TagEntity> tagEntities;

    
    public ProductEntity() {
        this.tagEntities = new ArrayList<>();
    }

    public ProductEntity(String productName) {
        this(); //good practice to retain convention even if default constructor is empty
        this.name = productName;
    }

//    public ProductEntity(String skuCode, String productName, String description, Integer quantityOnHand, Integer reorderQuantity, BigDecimal unitPrice, BigDecimal originalPrice, Integer productRating) {
//        this.skuCode = skuCode;
//        this.name = productName;
//        this.description = description;
//        this.quantityOnHand = quantityOnHand;
//        this.reorderQuantity = reorderQuantity;
//        this.unitPrice = unitPrice;
//        this.originalPrice = originalPrice;
//        this.productRating = productRating;
//    }
    
    public ProductEntity(String skuCode, String productName, String description, String imageLink, Integer quantityOnHand, Integer reorderQuantity, BigDecimal unitPrice, BigDecimal originalPrice, Integer productRating) {
        this.skuCode = skuCode;
        this.name = productName;
        this.description = description;
        this.imageLink = imageLink;
        this.quantityOnHand = quantityOnHand;
        this.reorderQuantity = reorderQuantity;
        this.unitPrice = unitPrice;
        this.originalPrice = originalPrice;
        this.productRating = productRating;
    }    
    
    
    public void addTag(TagEntity tagEntity)
    {
        if(tagEntity != null)
        {
            if(!this.tagEntities.contains(tagEntity))
            {
                this.tagEntities.add(tagEntity);
                
                if(!tagEntity.getProductEntities().contains(this))
                {                    
                    tagEntity.getProductEntities().add(this);
                }
            }
        }
    }
    
    
    
    public void removeTag(TagEntity tagEntity)
    {
        if(tagEntity != null)
        {
            if(this.tagEntities.contains(tagEntity))
            {
                this.tagEntities.remove(tagEntity);
                
                if(tagEntity.getProductEntities().contains(this))
                {
                    tagEntity.getProductEntities().remove(this);
                }
            }
        }
    }
    
    
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {       
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductEntity)) {
            return false;
        }
        ProductEntity other = (ProductEntity) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductEntity[ id=" + productId + " ]";
    }

    /**
     * @return the productName
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the productName to set
     */
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
     * @retun the imageLink
     */    
    public String getImageLink() {
        return imageLink;
    }

    /**
     * @param imageLink the imageLink to set
     */    
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    /**
     * @return the quantityOnHand
     */
    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    /**
     * @param quantityOnHand the quantityOnHand to set
     */
    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    /**
     * @return the reorderQuantity
     */
    public Integer getReorderQuantity() {
        return reorderQuantity;
    }

    /**
     * @param reorderQuantity the reorderQuantity to set
     */
    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    /**
     * @return the unitPrice
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the productRating
     */
    public Integer getProductRating() {
        return productRating;
    }

    /**
     * @param productRating the productRating to set
     */
    public void setProductRating(Integer productRating) {
        this.productRating = productRating;
    }

    /**
     * @return the categoryEntity
     */
    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    /**
     * @param categoryEntity the categoryEntity to set
     */
    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    /**
     * @return the tagEntities
     */
    public List<TagEntity> getTagEntities() {
        return tagEntities;
    }

    /**
     * @param tagEntities the tagEntities to set
     */
    public void setTagEntities(List<TagEntity> tagEntities) {
        this.tagEntities = tagEntities;
    }

    /**
     * @return the originalPrice
     */
    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    /**
     * @param originalPrice the originalPrice to set
     */
    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     * @return the skuCode
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * @param skuCode the skuCode to set
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

 
}
