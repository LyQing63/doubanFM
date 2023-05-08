package org.lyqing.doubanfm.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongQueryParam {
    private int pageNum = 1;
    private int pageSize = 10;
}
