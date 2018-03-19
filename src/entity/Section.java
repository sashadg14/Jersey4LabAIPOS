package entity;

import java.util.List;

public class Section {
    private String name;
    private List<String> subsectionsNames;

    public Section(){};

    public Section(String name, List<String> subsectionsNames) {
        this.name = name;
        this.subsectionsNames = subsectionsNames;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getSubsectionsNames() {
        return subsectionsNames;
    }

    public void setSubsectionsNames(List<String> subsectionsNames) {
        this.subsectionsNames = subsectionsNames;
    }
}
