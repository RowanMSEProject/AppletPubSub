/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mse
 */
@Entity
@Table(name = "SKILLSFORUSERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Skillsforusers.findAll", query = "SELECT s FROM Skillsforusers s"),
    @NamedQuery(name = "Skillsforusers.findByUserid", query = "SELECT s FROM Skillsforusers s WHERE s.skillsforusersPK.userid = :userid"),
    @NamedQuery(name = "Skillsforusers.findBySkillsid", query = "SELECT s FROM Skillsforusers s WHERE s.skillsforusersPK.skillsid = :skillsid"),
    @NamedQuery(name = "Skillsforusers.findBySkilllevel", query = "SELECT s FROM Skillsforusers s WHERE s.skilllevel = :skilllevel"),
    @NamedQuery(name = "Skillsforusers.findByCertdate", query = "SELECT s FROM Skillsforusers s WHERE s.certdate = :certdate")})
public class Skillsforusers implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SkillsforusersPK skillsforusersPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SKILLLEVEL")
    private int skilllevel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CERTDATE")
    private String certdate;
    @JoinColumn(name = "CERTID", referencedColumnName = "CERTID")
    @ManyToOne(optional = false)
    private Certnames certid;
    @JoinColumn(name = "SKILLSID", referencedColumnName = "SKILLSID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Skills skills;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Login login;

    public Skillsforusers() {
    }

    public Skillsforusers(SkillsforusersPK skillsforusersPK) {
        this.skillsforusersPK = skillsforusersPK;
    }

    public Skillsforusers(SkillsforusersPK skillsforusersPK, int skilllevel, String certdate) {
        this.skillsforusersPK = skillsforusersPK;
        this.skilllevel = skilllevel;
        this.certdate = certdate;
    }

    public Skillsforusers(int userid, int skillsid) {
        this.skillsforusersPK = new SkillsforusersPK(userid, skillsid);
    }

    public SkillsforusersPK getSkillsforusersPK() {
        return skillsforusersPK;
    }

    public void setSkillsforusersPK(SkillsforusersPK skillsforusersPK) {
        this.skillsforusersPK = skillsforusersPK;
    }

    public int getSkilllevel() {
        return skilllevel;
    }

    public void setSkilllevel(int skilllevel) {
        this.skilllevel = skilllevel;
    }

    public String getCertdate() {
        return certdate;
    }

    public void setCertdate(String certdate) {
        this.certdate = certdate;
    }

    public Certnames getCertid() {
        return certid;
    }

    public void setCertid(Certnames certid) {
        this.certid = certid;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (skillsforusersPK != null ? skillsforusersPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Skillsforusers)) {
            return false;
        }
        Skillsforusers other = (Skillsforusers) object;
        if ((this.skillsforusersPK == null && other.skillsforusersPK != null) || (this.skillsforusersPK != null && !this.skillsforusersPK.equals(other.skillsforusersPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Skillsforusers[ skillsforusersPK=" + skillsforusersPK + " ]";
    }
    
}
