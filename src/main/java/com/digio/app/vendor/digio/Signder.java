package com.digio.app.vendor.digio;

import javax.validation.constraints.NotEmpty;

public class Signder {

    @NotEmpty
    private String identifier;
    private String name;

    public Signder() {
    }

    public Signder(@NotEmpty String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
