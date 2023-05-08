package org.lyqing.doubanfm.pojo;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Subject {
    private String id;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;
    private LocalDateTime publishedDate;
    private String name;
    private String description;
    private String cover;
    private String master;
    private List<String> songIds;
    private String subjectType;
    private String subjectSubType;
}
