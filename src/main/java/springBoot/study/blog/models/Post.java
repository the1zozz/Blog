package springBoot.study.blog.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name = "title" ,  nullable = false)
    private String title ;
    @Column(nullable = false , name = "description")
    private String description ;
    @Column(nullable = false)
    private String content ;
    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL , orphanRemoval = true)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Set<Comment> comments = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "category_id" )
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Category category ;
    @CreatedBy
    private String createdBy ;
    @CreatedDate
    private LocalDateTime createdDate ;
    @LastModifiedBy
    private String updatedBy ;
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate ;



}
