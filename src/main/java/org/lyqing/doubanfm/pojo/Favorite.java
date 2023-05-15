package org.lyqing.doubanfm.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Favorite {

    private String id;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    private String userId;

    private String type;

    private String itemType;

    private String itemId;
}
