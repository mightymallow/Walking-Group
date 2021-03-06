package com.example.mashu.walkinggroup.model;

import java.util.Objects;

/**
 * IdItemBase is the base class
 * for all items that
 * have an ID and href from the server.
 */

public class IdItemBase {

    protected Long id;
    protected Boolean hasFullData;
    protected String href;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getHasFullData() {
        return hasFullData;
    }
    public void setHasFullData(Boolean hasFullData) {
        this.hasFullData = hasFullData;
    }

    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdItemBase idItem = (IdItemBase) o;
        return Objects.equals(getId(), idItem.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }


    @Override
    public String toString() {
        return "IdItemBase{" +
                "id=" + id +
                ", hasFullData=" + hasFullData +
                ", href='" + href + '\'' +
                '}';
    }
}