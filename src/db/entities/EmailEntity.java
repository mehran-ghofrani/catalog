package db.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by online on 9/5/2016.
 */
@Entity
@Table(name = "email", schema = "emailcenter", catalog = "")
public class EmailEntity {
    private int id;
    private String address;
    private Timestamp addedTime;

    public EmailEntity(String address, Timestamp addedTime) {
        this.address = address;
        this.addedTime = addedTime;
    }

    public EmailEntity() {
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "addedTime", nullable = false)
    public Timestamp getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Timestamp addedTime) {
        this.addedTime = addedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailEntity that = (EmailEntity) o;

        if (id != that.id) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (addedTime != null ? !addedTime.equals(that.addedTime) : that.addedTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (addedTime != null ? addedTime.hashCode() : 0);
        return result;
    }
}
