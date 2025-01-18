package springBoot.study.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> content;
    private int pagaeNo ;
    private int pageSize ;
    private long totalElement ;
    private int totalPages ;
    private boolean last ;

}
