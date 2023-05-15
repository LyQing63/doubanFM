package org.lyqing.doubanfm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.lyqing.doubanfm.pojo.Subject;
import org.lyqing.doubanfm.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MhzViewModel {

    private String title;
    private List<Subject> subjects;

}
