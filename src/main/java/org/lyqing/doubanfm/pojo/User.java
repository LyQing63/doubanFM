package org.lyqing.doubanfm.pojo;

import lombok.*;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    private String userName;
    private String password;
    private String mobile;

}
