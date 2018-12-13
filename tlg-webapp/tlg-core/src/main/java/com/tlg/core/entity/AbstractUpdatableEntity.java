package com.tlg.core.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.StringJoiner;


@MappedSuperclass
public class AbstractUpdatableEntity extends AbstractEntity {

    protected Date createDate;

    protected Date updateDate;

    @Column(name = "create_date", nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
//    @CreationTimestamp
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "update_date", nullable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
//    @UpdateTimestamp
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add("id=" + id)
                .add("createDate=" + createDate)
                .add("updateDate=" + updateDate)
                .toString();
    }
}
