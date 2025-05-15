package springBoot.study.blog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
@Audited
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private String email ;
    private String body ;
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "post_id" , nullable = false)
    private Post post ;
}
