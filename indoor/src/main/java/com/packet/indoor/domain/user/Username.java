package com.packet.indoor.domain.user;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Username {
    @Column(length = 100, unique = true, nullable = false)
    private String name;
    @Column(length = 100)
    private String group;

    @Override
    public String toString(){
        String groupName = (StringUtils.isNotEmpty(this.group)) ? this.group : "NoGroup";
        StringBuilder stringBuilder = new StringBuilder(this.name);
        stringBuilder.append("@");
        stringBuilder.append(groupName);
        return stringBuilder.toString();
    }
}
