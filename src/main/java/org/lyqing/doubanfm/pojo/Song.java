package org.lyqing.doubanfm.pojo;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Song implements Serializable {
    private String id;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;
    private String name;
    private String lyrics;
    private String cover;
    private String url;
    private List<String> singerId;
}
