package e2e.enums;

import lombok.Getter;

@Getter
public enum ContactInfoTabs {
    INFO("1"),
    PHONES("2"),
    EMAILS("3"),
    ADDRESSES("4");
    public final String value;

    ContactInfoTabs(String value){
        this.value = value;
    }
}


