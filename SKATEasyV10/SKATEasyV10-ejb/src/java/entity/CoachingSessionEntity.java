/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import util.enumeration.DayOfWeekEnum;
import util.enumeration.SessionNumberEnum;

/**
 *
 * @author harmo
 */
@Entity
public class CoachingSessionEntity extends ProductEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(nullable = false)
    @NotNull
    private DayOfWeekEnum dayOfWeek;
    @Column(nullable = false)
    @NotNull
    private SessionNumberEnum session;
    
    
    public CoachingSessionEntity() {
        super();
    }

    public CoachingSessionEntity(DayOfWeekEnum dayOfWeek, SessionNumberEnum session) {
        super();
        this.dayOfWeek = dayOfWeek;
        this.session = session;
    }

    public CoachingSessionEntity(DayOfWeekEnum dayOfWeek, SessionNumberEnum session, String productName) {
        super(productName);
        this.dayOfWeek = dayOfWeek;
        this.session = session;
    }

    public CoachingSessionEntity(DayOfWeekEnum dayOfWeek, SessionNumberEnum session, String skuCode, String productName, String description, String imageLink, Integer quantityOnHand, Integer reorderQuantity, BigDecimal unitPrice, BigDecimal originalPrice, Integer productRating) {
        super(skuCode, productName, description, imageLink, quantityOnHand, reorderQuantity, unitPrice, originalPrice, productRating);
        this.dayOfWeek = dayOfWeek;
        this.session = session;
    }
    
    /**
     * @return the dayOfWeekss
     */
    public DayOfWeekEnum getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * @param dayOfWeek the dayOfWeek to set
     */
    public void setDayOfWeek(DayOfWeekEnum dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * @return the session
     */
    public SessionNumberEnum getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(SessionNumberEnum session) {
        this.session = session;
    }
    
}
