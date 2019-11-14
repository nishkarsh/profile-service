package com.intentfilter.profileservice.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Document(collection = "ps.profiles")
public class Profile {
    @Id
    String id;

    @CreatedDate
    Date createdAt;

    @LastModifiedDate
    Date updatedAt;

    @NotBlank
    @Size(max = 256)
    String displayName;

    @NotBlank
    @Size(max = 256)
    String actualFullName;

    String profilePicturePath;

    @NotNull
    LocalDate birthday;

    UUID genderId;

    UUID ethnicityId;

    UUID religionId;

    Double height;

    UUID figureTypeId;

    @NotNull
    UUID maritalStatusId;

    @Size(max = 256)
    String occupation;

    @Size(max = 5000)
    String aboutMe;

    @NotNull
    Location location;

    @Version
    long version;

    public Profile() {
        // Intentionally left blank
    }

    public Profile(String id, Date createdAt, Date updatedAt, String displayName, String actualFullName,
                   String profilePicturePath, LocalDate birthday, UUID genderId, UUID ethnicityId, UUID religionId,
                   Double height, UUID figureTypeId, UUID maritalStatusId, String occupation, String aboutMe, Location location) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.displayName = displayName;
        this.actualFullName = actualFullName;
        this.profilePicturePath = profilePicturePath;
        this.birthday = birthday;
        this.genderId = genderId;
        this.ethnicityId = ethnicityId;
        this.religionId = religionId;
        this.height = height;
        this.figureTypeId = figureTypeId;
        this.maritalStatusId = maritalStatusId;
        this.occupation = occupation;
        this.aboutMe = aboutMe;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
