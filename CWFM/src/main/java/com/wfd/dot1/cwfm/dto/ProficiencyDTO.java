

package com.wfd.dot1.cwfm.dto;

public class ProficiencyDTO {
    private Boolean active;
    private Integer proficiencyLevelNumeric;
    private Integer version;
    private Integer id;
    private String name;

    public ProficiencyDTO() {
    }

    public ProficiencyDTO(Boolean active, Integer proficiencyLevelNumeric, Integer version, Integer id, String name) {
        this.active = active;
        this.proficiencyLevelNumeric = proficiencyLevelNumeric;
        this.version = version;
        this.id = id;
        this.name = name;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getProficiencyLevelNumeric() {
        return this.proficiencyLevelNumeric;
    }

    public void setProficiencyLevelNumeric(Integer proficiencyLevelNumeric) {
        this.proficiencyLevelNumeric = proficiencyLevelNumeric;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
