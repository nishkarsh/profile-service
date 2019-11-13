package com.intentfilter.profileservice.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Document(collection = "ps.profiles")
public class Profile {
    @Id
    private ObjectId id;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @NotBlank
    @Size(max = 256)
    private String displayName;

    @NotBlank
    @Size(max = 256)
    private String actualFullName;

    private String profilePicturePath;

    @NotBlank
    private LocalDate birthday;

    private UUID genderId;

    private UUID ethnicityId;

    private UUID religionId;

    private Double height;

    private UUID figureTypeId;

    @NotBlank
    private UUID maritalStatusId;

    @Size(max = 256)
    private String occupation;

    @Size(max = 5000)
    private String aboutMe;

    @NotBlank
    private UUID locationId;

    @Version
    private long version;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
