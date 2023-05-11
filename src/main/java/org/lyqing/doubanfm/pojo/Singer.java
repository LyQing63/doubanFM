package org.lyqing.doubanfm.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Singer implements Serializable {

    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime gmtCreated;
    private String name;
    private String avatar;
    private String homepage;
    private List<String> similarSingerIds;

}
