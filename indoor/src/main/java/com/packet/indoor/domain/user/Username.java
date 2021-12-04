package com.packet.indoor.domain.user;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder(builderClassName = "Builder")
@Embeddable
public class Username {
    @Column(name = "username", length = 100, unique = true, nullable = false)
    private String name;
    @Column(name = "usergroup", length = 100)
    private String group;

    protected Username(){}

    public static Username create(String username, String groupname){
        return Username.builder()
                .name(username)
                .group(groupname)
                .build();
    }

    @Override
    public String toString(){
        String groupName = (StringUtils.isNotEmpty(this.group)) ? this.group : "NoGroup";
        StringBuilder stringBuilder = new StringBuilder(this.name);
        stringBuilder.append("@");
        stringBuilder.append(groupName);
        return stringBuilder.toString();
    }
}
