/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mse
 */
@Embeddable
public class SkillsforusersPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "USERID")
    private int userid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SKILLSID")
    private int skillsid;

    public SkillsforusersPK() {
    }

    public SkillsforusersPK(int userid, int skillsid) {
        this.userid = userid;
        this.skillsid = skillsid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getSkillsid() {
        return skillsid;
    }

    public void setSkillsid(int skillsid) {
        this.skillsid = skillsid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userid;
        hash += (int) skillsid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SkillsforusersPK)) {
            return false;
        }
        SkillsforusersPK other = (SkillsforusersPK) object;
        if (this.userid != other.userid) {
            return false;
        }
        if (this.skillsid != other.skillsid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SkillsforusersPK[ userid=" + userid + ", skillsid=" + skillsid + " ]";
    }
    
}
